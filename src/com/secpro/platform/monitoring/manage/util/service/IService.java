package com.secpro.platform.monitoring.manage.util.service;

/**
 * @author Martin this interface define the one service should have action. for
 *         management
 */
public interface IService {
    /**
     * start service
     * @throws Exception
     */
    public void start() throws Exception;

    /**
     * stop service
     * @throws Exception
     */
    public void stop() throws Exception;
}
