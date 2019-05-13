package com.reco.generate.utils;

import ch.ethz.ssh2.SCPClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.Connection;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

public class SSHUtils {

    private static Logger logger = LoggerFactory.getLogger(SSHUtils.class);

    private static Connection connection = null;

    /**
     * 上传文件到远端
     *
     * @param active
     * @param remoteFile
     * @param fileType
     * @return
     */
    public static Boolean putFile(String active, String remoteFile, Integer fileType) {
        try {
            String localFilePath = Constant.getTempJspPath(active) + remoteFile;
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
                                scpClient.put(localFilePath, Constant.getRemoteJspPath(), "0644");
                                break;
                            case 2:
                                scpClient.put(localFilePath, Constant.getRemoteActImgPath(), "0644");
                                break;
                            case 3:
                                scpClient.put(localFilePath, Constant.getRemoteActIconPath(), "0644");
                                break;
                            case 4:
                                scpClient.put(localFilePath, Constant.getRemoteActFiconPath(), "0644");
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
            if(connection != null) {
                connection.close();
            }
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
            if (StringUtils.isNotBlank(Constant.getIp()) && null != Constant.getPort()) {
                connection = new Connection(Constant.getIp(), Constant.getPort());
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
            return connection.authenticateWithPassword(Constant.getUsername(), Constant.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
