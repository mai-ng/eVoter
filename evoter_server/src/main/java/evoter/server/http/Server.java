package evoter.server.http;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.springframework.http.HttpRequest;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.server.http.HttpConnection;

/**
 * This is a main class to start evoter server </br>
 * The connection information is provided by {@link HttpConnection} bean loaded by {@link BeanDAOFactory} </br>
 * Create a {@link HttpServer} and handle mult-thread {@link HttpRequest} </br>
 * @author btdiem </br>
 *
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			HttpConnection http = (HttpConnection)BeanDAOFactory.getBean("httpConnection");
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
