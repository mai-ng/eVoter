package evoter.server.http.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.http.URIRequest;
import evoter.server.http.URIUtils;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
/**
 * Process all user account requests sent by client applications
 * 
 * @author btdiem
 *
 */
public class AccountRequest {
	
	static List<String> userKeys = new ArrayList<String>();
//	private static final String USER_KEY = "userkey";
	
	/**
	 * Response clients a userkey generated from {@link UserDAO#USER_NAME} </br>
	 * and {@link UserDAO#PASSWORD} if username and password exist in database </br> 
	 * or response a null value if username and password do not exist in database </br>
	 * or response a @ {@link URIRequest#FAILURE_MESSAGE } if there is an{@link Exception} </br>    
	 * 
	 * @param exchange {@link HttpExchange} communicates between server and clients </br>
	 * @param parameters contains UserDAO.USER_NAME and UserDAO.PASSWORD </br>
	 */
	@SuppressWarnings("unchecked")
	public static void doLogin(HttpExchange exchange, Map<String,Object> parameters){
		
		String username = (String)parameters.get(UserDAO.USER_NAME);
		String password = (String)parameters.get(UserDAO.PASSWORD);

		try{
			
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
			
		}catch(Exception e){
			e.printStackTrace();
			
			URIUtils.writeFailureResponse(exchange);
		}

	}
	
	/**
	 * 
	 * @param parameters contaisn {@link UserDAO#USER_KEY} </br>
	 * @return true if {@link UserDAO#USER_KEY} exists, otherwise return false </br>
	 */
	public static boolean hasUserKey(Map<String,Object> parameters){
		
		return userKeys.contains(parameters.get(UserDAO.USER_KEY));
		//return true;
	}

	/**
	 * Response clients a {@link URIRequest#SUCCESS_MESSAGE} if userkey value exists </br>
	 * or response clients a {@link URIRequest#FAILURE_MESSAGE} if userkey does not exist </br>
	 * 
	 * @param exchange {@link HttpExchange} communicate between clients and server </br>
	 * @param parameters contains {@link UserDAO#USER_KEY} </br>
	 */
	public static void doLogout(HttpExchange exchange, Map<String,Object> parameters) {
		
		if (hasUserKey(parameters)){
			URIUtils.writeSuccessResponse(exchange);
		}else{
			URIUtils.writeFailureResponse(exchange);
		}
	}

	/**
	 * Check if email exists in the database and return a success or failure response </br> 
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains email address </br>
	 */
	public static void doResetPassword(HttpExchange httpExchange,
			Map<String, Object> parameters) {
		
		String email = (String)parameters.get(UserDAO.EMAIL);
		UserDAO userDAO = (UserDAO)BeanDAOFactory.getBean(UserDAO.BEAN_NAME);
		
		List<User> userList = userDAO.findByEmail(email);
		if (userList != null && !userList.isEmpty()){
			URIUtils.writeSuccessResponse(httpExchange);
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}
		
	}

	/**
	 * Response lients a {@link URIRequest#SUCCESS_MESSAGE} if user name and password are valid </br>
	 * and they do not exist in the database when receiving {@link URIRequest#LOGOUT} request from client applications</br>
	 * Otherwise, response a {@link URIRequest#FAILURE_MESSAGE} </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between clients and server </br>
	 * @param parameters contains: </br>
	 * 		</li> {@link UserDAO#USER_NAME}
	 * 		</li> {@link UserDAO#EMAIL}
	 * 		</li> {@link UserDAO#PASSWORD}
	 * 		</li> {@link UserDAO#USER_TYPE_ID}
	 * 
	 */
	public static void doRegister(HttpExchange httpExchange,
			Map<String, Object> parameters) {
		
		String username = (String)parameters.get(UserDAO.USER_NAME);
		String email = (String)parameters.get(UserDAO.EMAIL);
		long userTypeId = Long.valueOf((String)parameters.get(UserDAO.USER_TYPE_ID));
		
		UserDAO userDAO = (UserDAO)BeanDAOFactory.getBean(UserDAO.BEAN_NAME);
		//search database if this username exist
		List<User> checkUsername = userDAO.findByProperty(new String[]{UserDAO.USER_NAME, UserDAO.USER_TYPE_ID}
														, new Object[]{username, userTypeId});
		if (checkUsername != null && !checkUsername.isEmpty()){
			URIUtils.writeResponse(URIRequest.USER_EXIST_MESSAGE, httpExchange);
			return;
		}
		
		//search database if this email exists
		List<User> checkEmail = userDAO.findByProperty(new String[]{UserDAO.EMAIL, UserDAO.USER_TYPE_ID}
													, new Object[]{email, userTypeId});
		if (checkEmail != null && !checkEmail.isEmpty()){
			URIUtils.writeResponse(URIRequest.EMAIL_EXIST_MESSAGE, httpExchange);
			return;
		}
		
		//create User object and insert to database
		String password = (String)parameters.get(UserDAO.PASSWORD);
		User user = new User(username, password, email, userTypeId);
		try{
			userDAO.insert(user);
			URIUtils.writeSuccessResponse(httpExchange);
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}
		
	}

}
