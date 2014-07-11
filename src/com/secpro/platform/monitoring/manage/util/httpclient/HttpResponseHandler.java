package com.secpro.platform.monitoring.manage.util.httpclient;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.util.CharsetUtil;

import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

public class HttpResponseHandler extends SimpleChannelUpstreamHandler {
	private static final PlatformLogger theLogger = PlatformLogger.getLogger(HttpResponseHandler.class);
	protected HttpClient _client = null;
	private boolean readingChunks = false;
	private final StringBuffer chucksContent = new StringBuffer();

	public HttpResponseHandler(HttpClient client) {
		_client = client;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		// 200<=response.code<300, size<20000,
		//
		if (!readingChunks) {
			HttpResponse response = (HttpResponse) e.getMessage();

			HttpResponseStatus status = response.getStatus();
			if (status.getCode() < 200 || status.getCode() >= 300) {
				doError(new Exception("HTTP Response Code Exception, The Response code is " + status.getCode()));
				ctx.getChannel().close();
				return;
				// Channels.fireExceptionCaught(ctx, new
				// Exception("HTTP Response Code Exception, The Response code is "
				// + status.getCode()));
				// return;
			}
			if (response.getContentLength(0) > 20000) {
				doError(new Exception("Too Many Content on Response Body ,over 20000"));
				ctx.getChannel().close();
				return;
				// Channels.fireExceptionCaught(ctx, new
				// Exception("Too Many Content on Response Body"));
				// return;
			}
			if (response.isChunked()) {
				chucksContent.setLength(0);
				readingChunks = true;
			} else {
				ChannelBuffer content = response.getContent();
				if (content.readable()) {
					doJob(content.toString(CharsetUtil.UTF_8));
					// Channels.fireMessageReceived(ctx,
					// content.toString(CharsetUtil.UTF_8),
					// e.getRemoteAddress());
				}
				ctx.getChannel().close();
			}
		} else {
			HttpChunk chunk = (HttpChunk) e.getMessage();
			if (chunk.isLast()) {
				readingChunks = false;
				doJob(chucksContent.toString());
				ctx.getChannel().close();
				// Channels.fireMessageReceived(ctx, chucksContent.toString(),
				// e.getRemoteAddress());
			} else {
				if (chucksContent.length() > 20000) {
					doError(new Exception("Too Many Content on Response Body ,over 20000"));
					ctx.getChannel().close();
					return;
					// Channels.fireExceptionCaught(ctx, new
					// Exception("Too Many Content on Response Body"));
					// return;
				}
				chucksContent.append(chunk.getContent().toString(CharsetUtil.UTF_8));
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		Channels.fireExceptionCaught(ctx, e.getCause());
		doError(new Exception(e.getCause().getMessage(), e.getCause()));
	}

	private void doError(Exception exception) {
		try {
			_client._responseListener.fireError(exception);
		} catch (Exception e) {
			theLogger.exception(e);
		}

	}

	private void doJob(String content) {
		try {
			_client._responseListener.fireSucceed(content);
		} catch (Exception e) {
			theLogger.exception(e);
		}
	}
}
