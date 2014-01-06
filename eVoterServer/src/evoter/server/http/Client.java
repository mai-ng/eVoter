package evoter.server.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import sun.net.www.http.HttpClient;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			HttpClient client = new HttpClient(new URL("http://192.168.102.14:1000/evoter"), null, 0);
			
			System.out.println("isAlive " + client.serverIsOpen());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
