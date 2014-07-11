package com.secpro.platform.monitoring.manage.services;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.secpro.platform.monitoring.manage.util.ApplicationConfiguration;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.secpro.platform.monitoring.manage.util.service.IService;
import com.secpro.platform.monitoring.manage.util.service.ServiceHelper;
import com.secpro.platform.monitoring.manage.util.service.ServiceInfo;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


/**
 * @author baiyanwei 负责支持名种数据查询
 */
@Entity
@ServiceInfo(description = "lookup resource from cmdb")
public class ResourceService implements IService {
	@ManyToOne
	private static PlatformLogger _logger = PlatformLogger.getLogger(ResourceService.class);

	public void start() throws Exception {
	}

	public void stop() throws Exception {
	}
}
