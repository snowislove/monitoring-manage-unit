package com.secpro.platform.monitoring.manage.util;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author baiyanwei HTTP response status code from RFC 2616
 *         http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
 */
@Entity
public class HttpStatusCode {
	final public static int OK = 200;
	final public static int CREATED = 201;
	final public static int ACCEPTED = 202;
	final public static int NO_CONTENT = 204;
	final public static int BAD_REQUEST = 400;
	@Id
	@GeneratedValue
	final public static int FORBIDDEN = 403;
	final public static int NOT_FOUND = 404;
	final public static int METHOD_NOT_ALLOWED = 405;
	final public static int INTERNAL_SERVER_ERROR = 500;
}
