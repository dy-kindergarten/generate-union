package com.reco.generate.utils;

import com.jcraft.jsch.*;
import lombok.Cleanup;

import java.io.*;
import java.util.Properties;

public class JschConnect {

    private Session session;

    public JschConnect(LinuxServer server) throws JSchException {
        openSession(server.getHost(), server.getUsername(),
                server.getPassword(), server.getPort());
    }

    public JschConnect(String host, String username, String password) throws JSchException {
        openSession(host, username, password, 22);
    }

    public JschConnect(String host, String username, String password, Integer port) throws JSchException {
        openSession(host, username, password, port);
    }

    /**
     * 开启session
     *
     * @param host
     * @param username
     * @param password
     * @return
     * @throws JSchException
     */
    private Session openSession(String host, String username, String password, Integer port)
            throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(username, host);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        session.setPassword(password);
        session.setPort(port);
        session.connect(300000);
        return session;
    }

    /**
     * 上传本地文件到远程linux上 使用sftp上传,完成后关闭channel
     *
     * @param localFile  本地war文件全路径
     * @param remoteFile 远端目录路径
     * @return 上传成功返回true, 否则false
     * @throws Exception
     */
    public boolean transfer(String localFile, String remoteFile) throws Exception {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        String parent = getParentPath(remoteFile);
        try {
            sftp.stat(parent);//检测是否文件夹存在,如果不存在则抛出异常
        } catch (Exception e) {
            System.out.println("parent==>" + parent);
            executeCommand("mkdir -p " + parent);
        }
        SftpProgressImpl sftpProgressMonitorImpl = new SftpProgressImpl();
        sftp.put(localFile, remoteFile, sftpProgressMonitorImpl);
        sftp.disconnect();
        return sftpProgressMonitorImpl.isSuccess();
    }

    public boolean transfer(InputStream is, String remoteFile) throws Exception {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        String parent = getParentPath(remoteFile);
        try {
            sftp.stat(parent);//检测是否文件夹存在,如果不存在则抛出异常
        } catch (Exception e) {
            executeCommand("mkdir -p " + parent);
        }
        SftpProgressImpl sftpProgressMonitorImpl = new SftpProgressImpl();
        sftp.put(is, remoteFile, sftpProgressMonitorImpl);
        sftp.disconnect();
        return sftpProgressMonitorImpl.isSuccess();
    }

    /**
     * 执行命令,完成后立刻关闭pipe
     *
     * @param command
     * @throws Exception
     */
    public void executeCommand(String command) throws Exception {
        ChannelShell shell = (ChannelShell) session.openChannel("shell");
        shell.connect();
        //System.out.println("Sending commands...");
        PrintStream out = new PrintStream(shell.getOutputStream());
        out.println(command);
        out.println("exit");
        out.flush();
        readChannelOutput(shell);
        //System.out.println("Finished sending commands!");
        shell.disconnect();
    }

    /**
     * 读取shell的实时输出
     *
     * @param channel
     * @throws Exception
     */
    private void readChannelOutput(Channel channel) throws Exception {
        byte[] buffer = new byte[1024];
        InputStream in = channel.getInputStream();
        while (true) {
            while (in.available() > 0) {
                int i = in.read(buffer, 0, 1024);
                if (i < 0) break;
                //System.out.print(new String(buffer, 0, i));
            }
            if (channel.isClosed()) break;
            Thread.sleep(1000);
        }
    }

    /**
     * 功能描述:
     * 〈执行命令,完成后立刻关闭pipe 并返回结果〉
     *
     * @param command 执行语句
     * @return : java.lang.String
     * @author : wangpq
     * @date : 2019/8/18 14:51
     */
    public String executeCommandResult(String command) throws Exception {
        ChannelShell shell = (ChannelShell) session.openChannel("shell");
        shell.connect();
        PrintStream out = new PrintStream(shell.getOutputStream());
        out.println(command);
        out.println("exit");
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(shell.getInputStream()));
        String result;
        while ((result = in.readLine()) != null) {
            if (result.contains(command) && result.contains("#")) {
                result = in.readLine();
                break;
            }
        }
        shell.disconnect();
        return result;
    }

    private static String getParentPath(String path) {
        if (path.endsWith("/")) {
            throw new IllegalArgumentException("远端路径[" + path + "]有误,这似乎不是一个合法的文件路径!");
        }
        int pos = path.lastIndexOf("/");
        return path.substring(0, pos);
    }

    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    public void downFile(String ftpFilePath, String optPutFile) throws Exception {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        @Cleanup InputStream inputStream = sftp.get(ftpFilePath);
        @Cleanup OutputStream output = new FileOutputStream(optPutFile);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buf)) > 0) {
            output.write(buf, 0, bytesRead);
        }
        if(null != sftp) {
            sftp.disconnect();
        }
    }
}
