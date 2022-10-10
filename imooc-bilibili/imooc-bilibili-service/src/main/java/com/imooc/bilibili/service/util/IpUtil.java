package com.imooc.bilibili.service.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * IP工具类
 */
public class IpUtil {

	public static String getIP(HttpServletRequest httpServletRequest) {
		String ip = httpServletRequest.getHeader("X-Forwarded-For");
		if (ip != null && !"".equals(ip.trim())) {
			int index = ip.indexOf(",");
			if (index != -1) {
				ip = ip.substring(0, index);
			}
			return ip;
		} else {
			ip = httpServletRequest.getHeader("X-Real-IP");
			if (ip == null || "".equals(ip.trim())) {
				ip = httpServletRequest.getRemoteAddr();
			}
			return ip;
		}
	}

	public static String getLocalAddress() {
		try {
			InetAddress candidateAddress = null;
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
			while (ifaces.hasMoreElements()) {
				NetworkInterface iface = ifaces.nextElement();
				Enumeration<InetAddress> inetAddrs = iface.getInetAddresses();

				while (inetAddrs.hasMoreElements()) {
					InetAddress inetAddr = inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {
						if (inetAddr.isSiteLocalAddress()) {
							return inetAddr.getHostAddress();
						}

						if (candidateAddress == null) {
							candidateAddress = inetAddr;
						}
					}
				}
			}
			if (candidateAddress != null) {
				return candidateAddress.getHostAddress();
			} else {
				InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
				return jdkSuppliedAddress.getHostAddress();
			}
		} catch (Exception var5) {
			return "unkown";
		}
	}
}