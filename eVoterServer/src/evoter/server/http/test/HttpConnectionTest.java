package evoter.server.http.test;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.http.HttpConnection;

public class HttpConnectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		HttpConnection http = (HttpConnection)BeanDAOFactory.getBean("httpConnection");
		System.out.println(http.getContext());
		System.out.println(http.getIp());
		System.out.println(http.getPort());

	}

}
