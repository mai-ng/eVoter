package evoter.server.http;

public class HttpConnection {
	
	private String context;
	private String ip;
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
