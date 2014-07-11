package com.secpro.platform.monitoring.manage.util.service;

import java.util.HashMap;

import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


/**
 * 为WEB提示服务注册与查找实现
 * @author Martin
 * 
 */
@Entity
public class ServiceHelper<T extends IService> {
    @ManyToOne
	private static PlatformLogger _logger = PlatformLogger.getLogger(ServiceHelper.class);
    private static HashMap<String, IService> _registationMap = new HashMap<String, IService>();

    /**
     * register a service into OSGI frame.
     * 
     * @param <T>
     * @param service
     * @return
     */
    public static <T extends IService> T registerService(T service) {
        return registerService(service, true);
    }

    public static <T extends IService> T registerService(T service, boolean isStartup) {
        try {
            if (isStartup == true) {
                service.start();
            }
            _registationMap.put(service.getClass().getName(), service);
            ServiceInfo serviceInfo = service.getClass().getAnnotation(ServiceInfo.class);
            if (serviceInfo == null) {
                _logger.info("registerService",service.getClass().getName(),"");
            }else{
                _logger.info("registerService",service.getClass().getName(),serviceInfo.description()); 
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }

    public static <T extends IService> T findService(Class<?> clazz) {
        return findService(clazz.getName());
    }

    @SuppressWarnings("unchecked")
    public static <T extends IService> T findService(String clazz) {
        if (clazz == null || clazz.length() == 0 || _registationMap.containsKey(clazz) == false) {
            return null;
        }
        return (T) (_registationMap.get(clazz));
    }

    public static <T extends IService> void unregisterService(Class<IService> clazz) throws Exception {
        unregisterService(clazz.getName());
    }

    public static <T extends IService> void unregisterService(String clazz) throws Exception {
        IService service = findService(clazz);
        if (service == null) {
            return;
        }
        synchronized (_registationMap) {
            _registationMap.remove(clazz);
        }
        service.stop();
        ServiceInfo serviceInfo = service.getClass().getAnnotation(ServiceInfo.class);
        if (serviceInfo == null) {
            _logger.info("unregisterService",service.getClass().getName(),"");
        }else{
            _logger.info("unregisterService",service.getClass().getName(),serviceInfo.description()); 
        }
    }
}
