package com.reco.generate.utils;

/**
 * linux服务器的信息
 * @author zhangsl
 * @date 2018年10月23日
 */
public class LinuxServer {

	private String host;
	private String username;
	private String password;
	private Integer port;

	public LinuxServer(){}

	public LinuxServer(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}

	public LinuxServer(String host, String username, String password, int port) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.port = port;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LinuxServer [host=" + host + ", username=" + username
				+ ", password=" + password + "]";
	}
}
