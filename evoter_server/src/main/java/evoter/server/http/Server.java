package evoter.server.http;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.springframework.http.HttpRequest;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.server.http.HttpConnection;
import org.apache.log4j.Logger;

/**
 * This is a main class to start evoter server </br>
 * The connection information is provided by {@link HttpConnection} bean loaded by {@link BeanDAOFactory} </br>
 * Create a {@link HttpServer} and handle multi-thread {@link HttpRequest} </br>
 * @author btdiem </br>
 *
 */
public class Server {

	static Logger log = Logger.getLogger(Server.class);
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
			
			System.out.println("Listening for connections..." + server.getAddress() + context.getPath());
			
			log.debug("Listening for connections...");
			log.debug(context.getPath());
			log.debug(server.getAddress());
			
			
		} catch (Exception e) {
			log.error(e);
		}


	}

}
