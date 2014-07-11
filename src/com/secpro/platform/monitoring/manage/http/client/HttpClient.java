package com.secpro.platform.monitoring.manage.http.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.base64.Base64;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.json.JSONObject;

import com.secpro.platform.monitoring.manage.util.ApplicationConfiguration;

/**
 * A asynchronous HTTP client implements with Netty
 * @author liyan
 */

public class HttpClient {

	private ClientBootstrap bootstrap = null;

	ChannelGroup allChannels = null;
	
	private String host;
	private int port;
	private String serverPath;
	public HttpClient() {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new HttpClientPipelineFactory());
		allChannels = new DefaultChannelGroup();
	}

	public void setOption(String key, Object value) {
		bootstrap.setOption(key, value);
	}

	public ChannelPipeline get(String url) throws Exception {
		return retrieve("GET", url);
	}

	public ChannelPipeline delete(String url) throws Exception {
		return retrieve("DELETE", url);
	}

	public ChannelPipeline post(String url, Object data) throws Exception {
		return retrieve("POST", url, data);
	}

	public ChannelPipeline retrieve(String method, String url) throws Exception {
		return retrieve(method, url, null);
	}

	public ChannelPipeline retrieve(String method, String url, Object rawDataObj)
			throws Exception {
		if (url == null)
			throw new Exception("url is null");
		URI uri = new URI(url);
		String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
		port=uri.getPort();
		host = uri.getHost() == null ? "localhost" : uri.getHost();
		serverPath=uri.getPath();
		System.out.println(port+"------"+host+"---------"+serverPath);
		if (!scheme.equals("http")) {
			throw new Exception("just support http protocol");
		}

		HttpRequest request = this.createHttpMessage(
				serverPath, HttpMethod.PUT,
				rawDataObj.toString());
		return retrieve(request);

	}

	public ChannelPipeline retrieve(HttpRequest request) throws Exception {
		URI uri = new URI(request.getUri());
		String[] hosts=request
				.getHeader(HttpHeaders.Names.HOST).split(":");
		int port = hosts.length == 1 ? 80 : Integer.parseInt(hosts[1]);
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(hosts[0], port));
		future.addListener(new ConnectOk(request));
		allChannels.add(future.getChannel());
		return future.getChannel().getPipeline();
	}

	public void close() {
		allChannels.close().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
	}

	private DefaultHttpRequest createHttpMessage(String accessPath,
			HttpMethod httpMethod, String content)
			throws NoSuchAlgorithmException, IOException, Exception {
		if (content == null) {
			content = "";
		}

		// fill the request parameters in to query string.
		// StringBuilder parametersBuilder = new StringBuilder("?");
		// parametersBuilder.append("c=&l=&o=");
		// create HTTP request with query parameter.

		DefaultHttpRequest request = new DefaultHttpRequest(
				HttpVersion.HTTP_1_1, httpMethod, accessPath);
		// identify HTTP port we use
		if (80 == port) {
			request.addHeader(HttpHeaders.Names.HOST,host);
		} else {
			request.addHeader(HttpHeaders.Names.HOST, host+":"+port
					);
		}
		TreeMap<String, String> requestHeaders = new TreeMap<String, String>(
				new Comparator<String>() {
					public int compare(String string0, String string1) {
						return string0.compareToIgnoreCase(string1);
					}
				});
		//
		requestHeaders.put(HttpHeaders.Names.DATE, new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss Z", Locale.US).format(new Date()));
		// md5 coding hash string.
		requestHeaders.put(HttpHeaders.Names.CONTENT_MD5,
				computeMD5Hash(content));
		requestHeaders.put(HttpHeaders.Names.CONTENT_TYPE, "text/plain");

		// We need to set the content encoding to be UTF-8 in order to have the
		// message properly decoded.
		requestHeaders.put(HttpHeaders.Names.CONTENT_ENCODING,
				ApplicationConfiguration.DEFAULT_ENCODING);
		// Create the security signature.
		String signature = this.createSignature(content, requestHeaders,
				httpMethod, ApplicationConfiguration.HMAC_SHA1_ALGORITHM);

		// Add the security parameters
		request.addHeader("SignatureMethod", ApplicationConfiguration.HMAC_SHA1_ALGORITHM);
		request.addHeader("SignatureVersion", "2");
		// Add the customer headers to the request.
		Iterator<String> iterator = requestHeaders.keySet().iterator();
		while (iterator.hasNext() == true) {
			String name = iterator.next();
			String value = requestHeaders.get(name);
			request.addHeader(name, value);
		}

		// Needs to use the size of the bytes in the string.
		byte[] bytes = content.getBytes(ApplicationConfiguration.DEFAULT_CHARSET);

		request.addHeader(HttpHeaders.Names.CONTENT_LENGTH,
				String.valueOf(bytes.length));
		request.addHeader(HttpHeaders.Names.USER_AGENT,
				"mmu");

		ChannelBuffer channelBuffer = ChannelBuffers.buffer(bytes.length);
		channelBuffer.writeBytes(bytes);
		request.setContent(channelBuffer);
		return request;
	}

	private String computeMD5Hash(String contents) {
		InputStream inputStream = new ByteArrayInputStream(contents.getBytes());
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");

			int length = inputStream.available();
			ChannelBuffer channelBuffer = ChannelBuffers.buffer(length);
			channelBuffer.writeBytes(inputStream, length);
			byte[] buffer = new byte[16384];
			int bytesRead = -1;
			while ((bytesRead = channelBuffer.readableBytes()) > 0) {
				// if the readable bytes are bigger than the buffer length.
				// need to only read the buffer length.
				bytesRead = bytesRead >= buffer.length ? buffer.length
						: bytesRead;
				channelBuffer.readBytes(buffer, 0, bytesRead);
				messageDigest.update(buffer, 0, bytesRead);
			}

			byte[] digest = messageDigest.digest();
			ChannelBuffer channelBufferMD5 = ChannelBuffers
					.buffer(digest.length);
			channelBufferMD5.writeBytes(digest);
			ChannelBuffer b64 = Base64.encode(channelBufferMD5);
			return b64.toString(ApplicationConfiguration.DEFAULT_CHARSET);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	public String createSignature(String contents,
			Map<String, String> requestHeaders, HttpMethod httpMethod,
			String hmacSha) throws NoSuchAlgorithmException, IOException,
			Exception {

		// create string to sign
		String contentMD5 = requestHeaders.get(HttpHeaders.Names.CONTENT_MD5);
		String contentType = requestHeaders.get(HttpHeaders.Names.CONTENT_TYPE);
		String stringToSign = httpMethod.toString() + "#"
				+ (contentMD5 != null ? contentMD5 : "") + "#"
				+ (contentType != null ? contentType : "") + "#"
				+ requestHeaders.get(HttpHeaders.Names.DATE) + "#";

		// return the signature
		return signWithHmacSha(stringToSign, hmacSha);
	}

	protected String signWithHmacSha(String canonicalString, String hmacSha)
			throws Exception {
		
		SecretKeySpec signingKey = null;
		try {
			signingKey = new SecretKeySpec(
					"mmu".getBytes(ApplicationConfiguration.DEFAULT_ENCODING),
					ApplicationConfiguration.HMAC_SHA256_ALGORITHM);
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		// The following HMAC/SHA1 code for the signature is taken from the
		// AWS Platform's implementation of RFC2104
		// (amazon.webservices.common.Signature)
		//
		// Acquire an HMAC/SHA1 from the raw key bytes.
		
		// Acquire the MAC instance and initialize with the signing key.
		Mac mac = null;
		try {
			mac = Mac.getInstance(hmacSha);
		} catch (NoSuchAlgorithmException e) {
			// should not happen
			throw new NoSuchAlgorithmException("Could not find sha1 algorithm",
					e);
		}
		try {
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			// also should not happen
			throw new InvalidKeyException(
					"Could not initialize the MAC algorithm", e);
		}
		// Compute the HMAC on the digest, and set it.
		try {
			byte[] encrypted = mac.doFinal(canonicalString
					.getBytes(ApplicationConfiguration.DEFAULT_ENCODING));
			ChannelBuffer channelBuffer = ChannelBuffers
					.buffer(encrypted.length);
			channelBuffer.writeBytes(encrypted);
			ChannelBuffer b64 = Base64.encode(channelBuffer);
			return b64.toString(ApplicationConfiguration.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		String url = "http://127.0.0.1:8099/mcaOperation";
		HttpClient hc = new HttpClient();
		System.out.println(url);

		try {
			JSONObject json=new JSONObject();
			json.put("operation", "restart");
			JSONObject task=new JSONObject();
			task.put("watchdog", json);
			ChannelPipeline line = hc.post(url, task);
			line.addLast("handler", new HttpResponseHandler());
			Thread.sleep(4000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		hc.close();
	}
}

class ConnectOk implements ChannelFutureListener {
	private HttpRequest request = null;

	ConnectOk(HttpRequest req) {
		this.request = req;
	}

	public void operationComplete(ChannelFuture future) {
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
			return;
		}
		Channel channel = future.getChannel();
		channel.write(request);
	}
}