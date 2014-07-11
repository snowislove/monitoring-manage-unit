package com.secpro.platform.monitoring.manage.util.httpclient;

/**
 * @author baiyanwei
 * Jul 11, 2013
 * Define The listener behavior of IClient,
 * the listener just fire succeed and error method,
 * you should do yourself logic it them.
 */
public interface IClientResponseListener {
	
	/**
	 * fire on successful
	 * @param messageObj
	 * @throws Exception
	 */
	public void fireSucceed(Object messageObj) throws Exception;

	/**
	 * fire on error
	 * @param messageObj
	 * @throws Exception
	 */
	public void fireError(Object messageObj) throws Exception;
	
}
