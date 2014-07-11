package com.secpro.platform.monitoring.manage.util;

import java.util.Date;
import javax.persistence.Entity;

@Entity
public class StringFormat {
	@SuppressWarnings("deprecation")
	public static String FormatDate(Date date) {
		return date.toLocaleString();
	}
}
