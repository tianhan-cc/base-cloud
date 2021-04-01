package com.tianhan.cloud.gateway.utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Author NieAnTai
 * @Date 2021/4/1 11:49 上午
 * @Version 1.0
 * @Email nieat@foxmail.com
 * @Description 获取访问IP
 **/
public class ObtainRemoteIp {
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";
    private static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";
    private static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    private final Build build;

    private ObtainRemoteIp(Build build) {
        this.build = build;
    }

    public static Build create() {
        return new Build();
    }

    public String obtainIp() {
        String ipAddress;
        HttpHeaders headers = build.getHeaders();
        InetSocketAddress socket = build.getSocket();
        try {
            // 1.根据常见的代理服务器转发的请求ip存放协议，从请求头获取原始请求ip。值类似于203.98.182.163, 203.98.182.163
            ipAddress = headers.getFirst(HEADER_X_FORWARDED_FOR);
            if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = headers.getFirst(HEADER_PROXY_CLIENT_IP);
            }
            if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = headers.getFirst(HEADER_WL_PROXY_CLIENT_IP);
            }
            // 2.如果没有转发的ip，则取当前通信的请求端的ip
            if (StringUtils.isBlank(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                if (socket != null) {
                    ipAddress = socket.getAddress().getHostAddress();
                }
                // 如果是127.0.0.1，则取本地真实ip
                if (LOCALHOST.equals(ipAddress)) {
                    ipAddress = getLocalIpAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***"
            if (ipAddress != null) {
                ipAddress = ipAddress.split(SEPARATOR)[0].trim();
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress == null ? "" : ipAddress;
    }

    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (!(netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp())) {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception ignore) {
        }
        return "";
    }

    public static class Build {
        private HttpHeaders headers;
        private InetSocketAddress socket;

        private Build() {
        }

        public Build headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        public Build inetSocket(InetSocketAddress socket) {
            this.socket = socket;
            return this;
        }

        public ObtainRemoteIp build() {
            return new ObtainRemoteIp(this);
        }

        public HttpHeaders getHeaders() {
            return this.headers;
        }

        public InetSocketAddress getSocket() {
            return this.socket;
        }
    }
}
