package com.secpro.platform.monitoring.manage.util;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.LoggerFactory;

import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.secpro.platform.monitoring.manage.util.service.IService;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * @author baiyanwei 启动应用
 */
@Entity
public class ApplicationLaunching implements ServletContextListener {
	@ManyToOne
	private static IService applictionActivator = null;
	@ManyToOne
	private static PlatformLogger _logger = null;

	public void contextDestroyed(ServletContextEvent arg0) {
		if (applictionActivator != null) {
			try {
				applictionActivator.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent) 初始化应用参数与日志记录功能
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		//
		if (applictionActivator == null) {
			try {
				initConfigurationForLogging(ApplicationConfiguration.LOGGING_CONFIG_NAME);
				//
				_logger = PlatformLogger.getLogger(ApplicationLaunching.class);
				_logger.info("startApplication");
				//
				ApplicationConfiguration.APPLCATION_NAME = arg0.getServletContext().getInitParameter("appName");
				ApplicationConfiguration.ULTRA_CMDB_IP = arg0.getServletContext().getInitParameter("UltraCMDB.ip");
				ApplicationConfiguration.ULTRA_CMDB_PORT = arg0.getServletContext().getInitParameter("UltraCMDB.port");
				ApplicationConfiguration.SYNCHRONIZED_ACCOUNT = arg0.getServletContext().getInitParameter("UltraCMDB.synchronized_account");
				//
				_logger.info("startWithParameter", ApplicationConfiguration.ULTRA_CMDB_IP, ApplicationConfiguration.ULTRA_CMDB_PORT, ApplicationConfiguration.SYNCHRONIZED_ACCOUNT,
						ApplicationConfiguration.APPLCATION_NAME);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
			applictionActivator = new ApplictionActivator();
		}
		try {
			applictionActivator.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载应用LOG
	 * 
	 * @param logConfigruation
	 * @throws Exception
	 */
	private void initConfigurationForLogging(String logConfigruation) throws Exception {
		if (logConfigruation == null || logConfigruation.trim().equals("")) {
			throw new Exception("invaild logging configuration path.");
		}
		try {
			LoggerContext logContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(logContext);
			logContext.reset();
			configurator.doConfigure(this.getClass().getClassLoader().getResourceAsStream(logConfigruation));
		} catch (JoranException je) {
			je.printStackTrace();
			throw je;
		}
	}
}
