package com.reco.generate.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class SSHUtils {

    private static Logger logger = LoggerFactory.getLogger(SSHUtils.class);

    private static Connection connection = null;

    /**
     * 上传文件到远端
     *
     * @param localFilePath
     * @param remoteFilePath
     * @return
     */
    public static Boolean putFile(String localFilePath, String remoteFilePath) {
        try {
            if (connectAndAuthenticate()) {
                File localFile = new File(localFilePath);
                if (localFile.exists()) {
                    SCPClient scpClient = connection.createSCPClient();
                    scpClient.put(localFilePath, remoteFilePath, "0644");
                    logger.info("========= 上传成功 ==========");
                    return true;
                }
            } else {
                logger.info("========= 认证失败 ==========");
            }
        } catch (Exception ex) {
            logger.info("上传时发生异常，" + ex.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return false;
    }

    /**
     * 创建文件夹并上传文件
     *
     * @param localPath
     * @param fileNames
     * @param remotePath
     * @param dirName
     * @return
     */
    public static Boolean mkdirAndPutFile(String localPath, List<String> fileNames, String remotePath, String dirName) {
        try {
            if (connectAndAuthenticate()) {
                File path = new File(remotePath);
                path.setWritable(true);
                String remoteFilePath = mkdir(remotePath, dirName);
                for (String fileName : fileNames) {
                    String localFilePath = localPath + fileName;
                    File localFile = new File(localFilePath);
                    if (localFile.exists()) {
                        SCPClient scpClient = connection.createSCPClient();
                        scpClient.put(localFilePath, remoteFilePath, "0644");
                    }
                }
                logger.info("========= 上传成功 ==========");
                return true;
            } else {
                logger.info("========= 认证失败 ==========");
            }
        } catch (Exception ex) {
            logger.info("上传时发生异常，" + ex.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return false;
    }

    /**
     * 执行命令
     *
     * @param command
     * @return
     */
    public static Boolean execCommand(String command) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = null;
        StringBuffer stdout = new StringBuffer();
        try {
            Session session = connection.openSession();
            session.execCommand(command);
            is = new StreamGobbler(session.getStdout());
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                stdout.append(line);
            }
            if (StringUtils.isBlank(stdout.toString())) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @param dirName
     * @return
     */
    private static String mkdir(String path, String dirName) {
        String command = "cd " + path + "; mkdir " + dirName;
        execCommand(command);
        return path + dirName;
    }

    /**
     * 创建连接并认证
     *
     * @return
     * @throws IOException
     */
    private static Boolean connectAndAuthenticate() throws IOException {
        connection = getInstance();
        if (null != connection) {
            connection.connect();
            return connection.authenticateWithPassword(Constant.getUsername(), Constant.getPassword());
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
}
