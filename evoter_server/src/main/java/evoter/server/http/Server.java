package evoter.server.http;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.server.http.HttpConnection;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			//System.out.println("IP:"+ip);
			HttpConnection http = (HttpConnection)BeanDAOFactory.getBean("httpConnection");
//			System.out.println("here");
			HttpServer server = HttpServer.create(new InetSocketAddress(http.getIp(),http.getPort()), 0);
			HttpContext  context = server.createContext("/"+http.getContext(), new ServerHandler());
			server.setExecutor(Executors.newCachedThreadPool()); // creates a default executor
			server.start();
			System.out.println("Listening for connections...");
			System.out.println(context.getPath());
			System.out.println(server.getAddress());
			
		} catch (Exception e) {
			System.err.println("exception: " + e);
		}


	}

}
