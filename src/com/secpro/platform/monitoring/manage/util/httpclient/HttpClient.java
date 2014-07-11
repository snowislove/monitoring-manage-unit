package com.secpro.platform.monitoring.manage.util.httpclient;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.handler.timeout.WriteTimeoutHandler;

import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

/**
 * @author baiyanwei Jul 10, 2013
 * 
 *         a simple HTTP client ,And only support ASCII coding in URI.
 */
public class HttpClient {
	//
	public final static String READ_TIME_OUT_PIPE_LINE = "readTimeout";
	public final static String WRITE_TIME_OUT_PIPE_LINE = "writeTimeout";
	public final static String NETWORK_ERROR_CONNECTION_REFUSED = "Connection refused";
	//
	private static PlatformLogger theLogger = PlatformLogger.getLogger(HttpClient.class);
	//
	private URI _targetURI = null;
	private HttpMethod _httpMethod = null;
	private String _content = null;
	private HashMap<String, String> _headerParameterMap = new HashMap<String, String>();
	protected IClientResponseListener _responseListener = null;
	//
	private ChannelFactory _factory = null;
	private ChannelPipelineFactory _pipelineFactory = null;
	// private ChannelHandler _handler = null;
	private ClientBootstrap _bootstrap = null;
	private ChannelFuture _future = null;
	private Channel _channel = null;

	public HttpClient(URI targetURI, HttpMethod httpMethod, HashMap<String, String> headerParameterMap, String content, IClientResponseListener responseListener) {
		this._targetURI = targetURI;
		this._httpMethod = httpMethod;
		this._content = content;
		this._headerParameterMap = headerParameterMap;
		this._responseListener = responseListener;
	}

	public void start() throws Exception {
		// identify the client configuration object
		if (this._targetURI == null) {
			throw new Exception("invalid targetURI");
		}
		// if has a host.
		if (this._httpMethod == null) {
			throw new Exception("invalid httpMethod.");
		}
		// if has a host.
		if (this._content == null) {
			throw new Exception("invalid content.");
		}

		if (this._responseListener == null) {
			throw new Exception("invalid responseListener.");
		}

		String scheme = _targetURI.getScheme() == null ? "http" : _targetURI.getScheme();
		String host = _targetURI.getHost() == null ? "localhost" : _targetURI.getHost();
		int port = _targetURI.getPort();

		// if out of our supporting.
		if (!scheme.equalsIgnoreCase(HttpConstant.HTTP_SCHEME) && !scheme.equalsIgnoreCase(HttpConstant.HTTPS_SCHEME)) {
			throw new Exception("Only HTTP(S) is supported.");
		}
		// pick up a communication port.
		if (port == -1) {
			if (scheme.equalsIgnoreCase(HttpConstant.HTTP_SCHEME)) {
				port = HttpConstant.HTTP_PORT;
			} else if (scheme.equalsIgnoreCase(HttpConstant.HTTPS_SCHEME)) {
				port = HttpConstant.HTTPS_PORT;
			}
		}
		String path = _targetURI.getPath();
		if (path == null || path.trim().equals("")) {
			path = "/";
		}
		//
		boolean ssl = scheme.equalsIgnoreCase(HttpConstant.HTTPS_SCHEME);
		//
		if (this._headerParameterMap == null) {
			this._headerParameterMap = new HashMap<String, String>();
		}
		// create HTTP request.
		HttpRequest defaultHttpRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, this._httpMethod, path);
		defaultHttpRequest.setHeader(HttpHeaders.Names.HOST, host + ":" + port);
		defaultHttpRequest.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
		defaultHttpRequest.setHeader(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
		// fill the head parameter into HTTP request.
		for (Iterator<String> keyIter = this._headerParameterMap.keySet().iterator(); keyIter.hasNext();) {
			String keyName = keyIter.next();
			defaultHttpRequest.setHeader(keyName, this._headerParameterMap.get(keyName));
		}
		// fill the content
		defaultHttpRequest.setContent(ChannelBuffers.copiedBuffer(this._content, HttpConstant.DEFAULT_CHARSET));
		defaultHttpRequest.setHeader(HttpHeaders.Names.CONTENT_LENGTH, defaultHttpRequest.getContent().readableBytes());
		//
		_factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		// Configure the client.
		_bootstrap = new ClientBootstrap(_factory);
		_pipelineFactory = new HttpClientPipelineFactory(ssl, new HttpResponseHandler(this));
		// Set up the event pipeline factory.
		_bootstrap.setPipelineFactory(_pipelineFactory);
		// Start the connection attempt.
		_future = _bootstrap.connect(new InetSocketAddress(host, port));
		// Wait until the connection attempt succeeds or fails.
		_channel = _future.awaitUninterruptibly().getChannel();
		if (!_future.isSuccess()) {
			try {
				// check timeout handle exist or not
				shutDownTimeoutTimer();
				_bootstrap.releaseExternalResources();
			} catch (Exception e) {
				e.printStackTrace();
			}
			_bootstrap = null;
			_pipelineFactory = null;
			// System.out.println(_future.getCause().getMessage());
			// System.out.println(_future.getCause().getClass());
			throw new Exception(_future.getCause().getMessage());
		}
		// Send the HTTP request.
		_channel.write(defaultHttpRequest);
		// Wait for the server to close the connection.
		_channel.getCloseFuture().awaitUninterruptibly();
		//
		// check timeout handle exist or not
		shutDownTimeoutTimer();
		_bootstrap.releaseExternalResources();
	}

	//
	public void stop() {
		// This will close the socket. This happens asynchronously.
		if (_channel != null) {
			ChannelFuture future = _channel.close();
			future.addListener(new ChannelFutureListener() {

				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isDone() == true) {
						// check timeout handle exist or not
						shutDownTimeoutTimer();
						// release external resources
						if (_bootstrap != null) {
							// Shut down executor threads to exit.
							_bootstrap.releaseExternalResources();

						}
						// This will stop the timeout handler
						_factory = null;
						_bootstrap = null;
					}
				}

			});

		}

		theLogger.debug("clientClosed" + _targetURI.toString());
	}

	/**
	 * shut down the timeout timer
	 * 
	 * @throws Exception
	 */
	private void shutDownTimeoutTimer() throws Exception {
		if (_bootstrap != null && _bootstrap.getPipelineFactory() != null && _bootstrap.getPipelineFactory().getPipeline() != null) {
			if (_bootstrap.getPipelineFactory().getPipeline().get(READ_TIME_OUT_PIPE_LINE) != null) {
				ReadTimeoutHandler readTimerHandler = (ReadTimeoutHandler) _bootstrap.getPipelineFactory().getPipeline().remove(READ_TIME_OUT_PIPE_LINE);
				// stop the read timer
				readTimerHandler.releaseExternalResources();
			}
			if (_bootstrap.getPipelineFactory().getPipeline().get(WRITE_TIME_OUT_PIPE_LINE) != null) {
				WriteTimeoutHandler writeTimerHandler = (WriteTimeoutHandler) _bootstrap.getPipelineFactory().getPipeline().remove(WRITE_TIME_OUT_PIPE_LINE);
				// stop the write timer
				writeTimerHandler.releaseExternalResources();
			}
		}
	}
}
