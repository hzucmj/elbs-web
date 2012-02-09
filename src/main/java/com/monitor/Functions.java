package com.monitor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zengsource.util.DateUtil;

public class Functions {

	//字符串转为日期
	public static Date strToDate(String dateStr, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date formatDate(Date date, String formatStr){
		return null;
	};
	
	//获取当前用户角色
	public static String getLocalAuth(){
		Collection<GrantedAuthority> authCollection = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String auth = "";
		for (GrantedAuthority g : authCollection) {
			auth = g.getAuthority();
		}
		return auth;
	}
	
	public static String getLocalUser(){
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
