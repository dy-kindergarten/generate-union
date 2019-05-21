package com.reco.generate.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    private static String ip;

    private static Integer port;

    private static String username;

    private static String password;

    private static String remoteJspPath;

    private static String remoteActImgPath;

    private static String remoteActIconPath;

    private static String remoteActFiconPath;

    private static String remoteEnterIconPath;

    private static String tempResourcePath;

    private static String tempJspPathProd;

    private static String tempJspPathTest;

    private static String tempReportPath;

    private static String recommendUrl = "http://113.136.207.47/iptv_hd/welcome.jsp?socnew=";

    @Value("${spring.ssh.ipAddress}")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Value("${spring.ssh.port}")
    public void setPort(int port) {
        this.port = port;
    }

    @Value("${spring.ssh.username}")
    public void setUsername(String username) {
        this.username = username;
    }

    @Value("${spring.ssh.password}")
    public void setPassword(String password) {
        this.password = password;
    }

    @Value("${spring.file.remoteJspPath}")
    public void setRemotePath(String remoteJspPath) {
        this.remoteJspPath = remoteJspPath;
    }

    @Value("${spring.file.remoteJspPath}")
    public void setRemoteJspPath(String remoteJspPath) {
        this.remoteJspPath = remoteJspPath;
    }

    @Value("${spring.file.remoteActImgPath}")
    public void setRemoteActImgPath(String remoteActImgPath) {
        this.remoteActImgPath = remoteActImgPath;
    }

    @Value("${spring.file.remoteActIconPath}")
    public void setRemoteActIconPath(String remoteActIconPath) {
        this.remoteActIconPath = remoteActIconPath;
    }

    @Value("${spring.file.remoteActFiconPath}")
    public void setRemoteActFiconPath(String remoteActFiconPath) {
        this.remoteActFiconPath = remoteActFiconPath;
    }

    @Value("${spring.file.tempResourcePath}")
    public void setTempResourcePath(String tempResourcePath) {
        this.tempResourcePath = tempResourcePath;
    }

    @Value("${spring.file.tempJspPathProd}")
    public void setTempJspPathProd(String tempJspPathProd) {
        Constant.tempJspPathProd = tempJspPathProd;
    }

    @Value("${spring.file.tempJspPathTest}")
    public void setTempJspPathTest(String tempJspPathTest) {
        Constant.tempJspPathTest = tempJspPathTest;
    }

    @Value("${spring.file.tempReportPath}")
    public void setTempReportPath(String tempReportPath) {
        Constant.tempReportPath = tempReportPath;
    }

    @Value("${spring.file.remoteEnterIconPath}")
    public void setRemoteEnterIconPath(String remoteEnterIconPath) {
        Constant.remoteEnterIconPath = remoteEnterIconPath;
    }

    public static String getIp() {
        return ip;
    }

    public static Integer getPort() {
        return port;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getRemoteJspPath() {
        return remoteJspPath;
    }

    public static String getRemoteActImgPath() {
        return remoteActImgPath;
    }

    public static String getRemoteActIconPath() {
        return remoteActIconPath;
    }

    public static String getRemoteActFiconPath() {
        return remoteActFiconPath;
    }

    public static String getRemoteEnterIconPath() {
        return remoteEnterIconPath;
    }

    public static String getTempResourcePath() {
        return tempResourcePath;
    }

    public static String getTempJspPath(String active) {
        if(StringUtils.equalsIgnoreCase("prod", active)) {
            return tempJspPathProd;
        }
        return tempJspPathTest;
    }

    public static String getTempReportPath() {
        return tempReportPath;
    }

    public static String getRecommendUrl() {
        return recommendUrl;
    }
}
