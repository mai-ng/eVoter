package evoter.server.http.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.UserDAO;
import evoter.server.http.URIUtils;

public class AccountRequest {
	
	static List<String> userKeys = new ArrayList<String>();
	private static final String USER_KEY = "userkey";
	
	public static void doLogin(HttpExchange exchange, Map<String,String> parameters){
		
		String username = parameters.get(UserDAO.USER_NAME);
		String password = parameters.get(UserDAO.PASSWORD);
		UserDAO userDao = (UserDAO)BeanDAOFactory.getBean(UserDAO.BEAN_NAME);
		if (!userDao.findByProperty(new String[]{UserDAO.USER_NAME, UserDAO.PASSWORD}, 
				new Object[]{username, password}).isEmpty()){
			System.out.println("user exists in db");
			//generate user key
			URIUtils.writeResponse("TRUE", exchange);
			//write response
		}else{
			
			URIUtils.writeResponse("FALSE", exchange);
		}
		
	}
	
	public static boolean hasUserKey(Map<String,String> parameters){
		
		//return userKeys.contains(parameters.get(USER_KEY));
		return true;
	}

	public static void doLogout(HttpExchange httpExchange, Map<String, String> parameters) {
		// TODO Auto-generated method stub
		
	}

}
