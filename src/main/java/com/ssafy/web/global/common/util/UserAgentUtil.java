package com.ssafy.web.global.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class UserAgentUtil {
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FOR_WARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getBrowser(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");

		String browser = null;
		if (agent != null) {
			if (agent.contains("Trident")) {
				browser = "MSIE";
			} else if (agent.contains("Chrome")) {
				browser = "Chrome";
			} else if (agent.contains("Opera")) {
				browser = "Opera";
			} else if (agent.contains("iPhone") && agent.contains("Mobile")) {
				browser = "iPhone";
			} else if (agent.contains("Android") && agent.contains("Mobile")) {
				browser = "Android";
			}
		}
		return browser;
	}

	public static String getOs(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		String os = null;
		if (agent.contains("NT 6.0"))
			os = "Windows Vista/Server 2008";
		else if (agent.contains("NT 5.2"))
			os = "Windows Server 2003";
		else if (agent.contains("NT 5.1"))
			os = "Windows XP";
		else if (agent.contains("NT 5.0"))
			os = "Windows 2000";
		else if (agent.contains("NT"))
			os = "Windows NT";
		else if (agent.contains("9x 4.90"))
			os = "Windows Me";
		else if (agent.contains("98"))
			os = "Windows 98";
		else if (agent.contains("95"))
			os = "Windows 95";
		else if (agent.contains("Win16"))
			os = "Windows 3.x";
		else if (agent.contains("Windows"))
			os = "Windows";
		else if (agent.contains("Linux"))
			os = "Linux";
		else if (agent.contains("Macintosh"))
			os = "Macintosh";
		else
			os = "";
		return os;
	}

	public static String getWebType(HttpServletRequest request) {
		String filter = "iphone|ipod|android|windows ce|blackberry|symbian|windows phone|webos|opera mini|opera mobi|polaris|iemobile|lgtelecom|nokia|sonyericsson|lg|samsung";
		String[] filters = filter.split("\\|");
		String webType = "";

		for (String tmp : filters) {
			if (request.getHeader("User-Agent").toLowerCase().contains(tmp)) {
				webType = "MOBILE";
				break;
			} else {
				webType = "PC";
			}
		}
		return webType;

	}
}
