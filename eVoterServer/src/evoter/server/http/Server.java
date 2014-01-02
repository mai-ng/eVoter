package evoter.server.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import evoter.server.dao.BeanDAOFactory;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			//System.out.println("IP:"+ip);
			
			HttpConnection http = (HttpConnection)BeanDAOFactory.getBean("httpConnection");
			HttpServer server = HttpServer.create(new InetSocketAddress(http.getIp(),http.getPort()), 0);
			server.createContext("/"+http.getContext(), new ServerHandler());
			server.setExecutor(Executors.newCachedThreadPool()); // creates a default executor
			server.start();
			System.out.println("Listening for connections...");
			System.out.println(server.getAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
