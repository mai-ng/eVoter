package evoter.server.http.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.UserDAO;
import evoter.server.http.URIUtils;
import evoter.server.model.User;

public class AccountRequest {
	
	static List<String> userKeys = new ArrayList<String>();
//	private static final String USER_KEY = "userkey";
	
	@SuppressWarnings("unchecked")
	public static void doLogin(HttpExchange exchange, Map<String,String> parameters){
		
		String username = parameters.get(UserDAO.USER_NAME);
		String password = parameters.get(UserDAO.PASSWORD);
		UserDAO userDao = (UserDAO)BeanDAOFactory.getBean(UserDAO.BEAN_NAME);
		List<User> users = userDao.findByProperty(new String[]{UserDAO.USER_NAME, UserDAO.PASSWORD}, 
				new Object[]{username, password});
		String userKey = null;
		if (users != null && !users.isEmpty()){
			userKey = users.get(0).generateUserKey(System.currentTimeMillis());
			userKeys.add(userKey);
			System.out.println("user exists in db");
			
		}
		JSONObject object = new JSONObject();
		object.put(UserDAO.USER_KEY, userKey);
		URIUtils.writeResponse(object.toJSONString(), exchange);
		
//		if (userDao.findByProperty(new String[]{UserDAO.USER_NAME, UserDAO.PASSWORD}, 
//				new Object[]{username, password}).isEmpty() == false){
//			System.out.println("user exists in db");
//			//generate user key
//			URIUtils.writeResponse("TRUE", exchange);
//			//write response
//		}else{
//			
//			URIUtils.writeResponse("FALSE", exchange);
//		}
		
	}
	
	
	public static boolean hasUserKey(Map<String,String> parameters){
		
		return userKeys.contains(parameters.get(UserDAO.USER_KEY));
		//return true;
	}

	public static void doLogout(HttpExchange exchange, Map<String, String> parameters) {
		
		if (hasUserKey(parameters)){
			URIUtils.writeSuccessResponse(exchange);
		}else{
			URIUtils.writeFailureResponse(exchange);
		}
	}

}
