package com.secpro.platform.monitoring.manage.util.httpclient;

import java.nio.charset.Charset;

public class HttpConstant {
	final public static String HTTP_SCHEME = "http";
	final public static String HTTPS_SCHEME = "https";
	final public static int HTTP_PORT = 80;
	final public static int HTTPS_PORT = 443;
	final public static String REQUEST_ROOT_PATH = "/";
	final public static String REQUEST_QUERY_SPLITE_CHAR = "?";
	final public static String RESPONSE_CONTENT_TPYE = "text/plain; charset=UTF-8";
	public static final String UTF_8 = "UTF-8";

	public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);

	/**
	 * The default encoding used for text data: UTF-8
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";

	public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODING);

	/**
	 * HMAC/SHA1 Algorithm per RFC 2104, used when generating S3 signatures.
	 */
	public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	/**
	 * HMAC/SHA1 Algorithm per RFC 2104, used when generating S3 signatures.
	 */
	public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
}
