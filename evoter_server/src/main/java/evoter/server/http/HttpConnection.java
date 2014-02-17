package evoter.server.http;

import org.springframework.stereotype.Repository;
/**
 * 
 * This class is spring beans. its properties are values used for server connection </br>
 * such as context path, ip addess and port </br>
 * @author btdiem </br>
 *
 */
@Repository("httpConnection")
public class HttpConnection {
	
	/**
	 * Sever context path
	 */
	private String context;
	/**
	 * server ip address
	 */
	private String ip;
	/**
	 * server port
	 */
	private int port;
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "HttpConnection [context=" + context + ", ip=" + ip + ", port="
				+ port + "]";
	}
	
	

}
