package com.reco.generate.utils;

import ch.ethz.ssh2.SCPClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import ch.ethz.ssh2.Connection;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class SSHUtils {

    private static Logger logger = LoggerFactory.getLogger(SSHUtils.class);

    private static String ip;

    private static Integer port;

    private static String username;

    private static String password;

    private static String remoteJspPath;

    private static String remoteActImgPath;

    private static String remoteActIconPath;

    private static String remoteActFiconPath;

    private static String tempPath;

    private static Connection connection = null;

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

    @Value("${spring.file.tempPath}")
    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    /**
     * 上传文件到远端
     *
     * @param remoteFile
     * @param fileType
     * @return
     */
    public static Boolean putFile(String remoteFile, Integer fileType) {
        try {
            String localFilePath = tempPath + remoteFile;
            File locafile = new File(localFilePath);
            if (locafile.exists()) {
                connection = getInstance();
                if (null != connection) {
                    connection.connect();
                    boolean isAuthed = isAuthedWithPassword();
                    if (isAuthed) {
                        SCPClient scpClient = connection.createSCPClient();
                        switch (fileType) {
                            case 1:
                                scpClient.put(localFilePath, remoteJspPath, "0644");
                                break;
                            case 2:
                                scpClient.put(localFilePath, remoteActImgPath, "0644");
                                break;
                            case 3:
                                scpClient.put(localFilePath, remoteActIconPath, "0644");
                                break;
                            case 4:
                                scpClient.put(localFilePath, remoteActFiconPath, "0644");
                                break;
                            default:
                                break;
                        }
                        logger.info("========= 上传成功 ==========");
                        return true;
                    } else {
                        logger.info("========= 认证失败 ==========");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

    /**
     * 获取连接
     *
     * @return
     */
    private static Connection getInstance() {
        if (connection == null) {
            if (StringUtils.isNotBlank(ip) && null != port) {
                connection = new Connection(ip, port);
            }
        }
        return connection;
    }

    /**
     * 登录验证
     *
     * @return
     */
    private static boolean isAuthedWithPassword() {
        try {
            return connection.authenticateWithPassword(username, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
