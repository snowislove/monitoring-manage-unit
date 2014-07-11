package com.secpro.platform.monitoring.manage.util;

import java.util.ArrayList;

import com.secpro.platform.monitoring.manage.services.AuthenticationService;
import com.secpro.platform.monitoring.manage.services.DataBaseOperationService;
import com.secpro.platform.monitoring.manage.services.ModelService;
import com.secpro.platform.monitoring.manage.services.ResourceService;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.secpro.platform.monitoring.manage.util.service.IService;
import com.secpro.platform.monitoring.manage.util.service.ServiceHelper;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ApplictionActivator implements IService {
	@ManyToOne
	private static PlatformLogger _logger = PlatformLogger.getLogger(ApplictionActivator.class);
	ArrayList<IService> startUpServices = new ArrayList<IService>();

	public void start() throws Exception {
		_logger.info("StartService");
		startUpServices.add(ServiceHelper.registerService(new AuthenticationService()));
		startUpServices.add(ServiceHelper.registerService(new ModelService()));
		startUpServices.add(ServiceHelper.registerService(new DataBaseOperationService()));
		startUpServices.add(ServiceHelper.registerService(new ResourceService()));
	}

	public void stop() throws Exception {
		for (int i = 0; i < startUpServices.size(); i++) {
			try {
				startUpServices.get(i).stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
