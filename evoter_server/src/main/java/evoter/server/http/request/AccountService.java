package evoter.server.http.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.dao.impl.UserDAOImpl;
import evoter.server.http.request.interfaces.IAccountService;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;
/**
 * Process all user account requests sent by client applications </br>
 * This class is an implementation of {@link IAccountService} </br>
 * @author btdiem</br>
 *
 */
@Service
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class AccountService implements IAccountService{
	
	static Logger log = Logger.getLogger(AccountService.class);
	
	 List<String> userKeys = new ArrayList<String>();
	 //It is used for testing requests that have not yet implemented
//	 String userKeyemp = "temp";
	 private AccountService(){
//		 userKeys.add(userKeyemp);
//		 userKeys.add("1333_1_2");
//		 userKeys.add("1333_3_3");
	 }
	
	 /**
	  * get/set a {@link UserDAOImpl} instance  
	  */
	 private UserDAO userDAO;

	
	 public void setUserDAO(UserDAO userDAO){
		 this.userDAO = userDAO;
	 }
	 public UserDAO getUserDAO(){
		 return this.userDAO;
	 }
	
	 
	
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAccountService#doLogin(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public  Object doLogin(Map<String,Object> parameters){
		

		try{
			String username = (String)parameters.get(UserDAO.USER_NAME);
			String password = (String)parameters.get(UserDAO.PASSWORD);

			JSONObject response = new JSONObject();
			
			List<User> users = userDAO.findByProperty(
					new String[]{UserDAO.USER_NAME, UserDAO.PASSWORD}, 
					new Object[]{username, password});
			String userKey = null;
			if (users != null && !users.isEmpty()){
				
				//System.out.println("user exists in db");
				userKey = users.get(0).generateUserKey(System.currentTimeMillis());
				addUserKey(userKey);
				response.put(UserDAO.USER_KEY, userKey);
				return response;
				
			}
				
			return URIRequest.USER_NOT_EXIST_MESSAGE;
			
			
			//URIUtils.writeResponse(object, exchange);
			
		}catch(Exception e){
			
			log.error(e);
			
			return URIRequest.FAILURE_MESSAGE;
			//URIUtils.writeFailureResponse(exchange);
		}

	}
	

	public  boolean hasUserKey(String userKey){
		
		return userKeys.contains(userKey);
		//return true;
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAccountService#doLogout(java.util.Map)
	 */
	@Override
	public  Object doLogout(Map<String,Object> parameters) {
		
		try{
			
			if (hasUserKey((String)parameters.get(UserDAO.USER_KEY))){
				
				userKeys.remove(parameters.get(UserDAO.USER_KEY));
				
				return URIRequest.SUCCESS_MESSAGE;
				
			}
			return URIRequest.USER_NOT_EXIST_MESSAGE;
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}


	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAccountService#doResetPassword(java.util.Map)
	 */
	@Override
	public  Object doResetPassword(Map<String, Object> parameters) {
		
		try{

			String email = (String)parameters.get(UserDAO.EMAIL);
			//UserDAO userDAO = (UserDAO)BeanDAOFactory.getBean(UserDAO.BEAN_NAME);
			
			List<User> userList = userDAO.findByEmail(email);
			if (userList != null && !userList.isEmpty()){
				
				return URIRequest.EMAIL_EXIST_MESSAGE;
				
			}
			return URIRequest.EMAIL_NOT_EXIST_MESSAGE;
			
		}catch(Exception e){
			
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAccountService#doRegister(java.util.Map)
	 */
	@Override
	public  Object doRegister(Map<String, Object> parameters) {
		
		try{
			
			String username = (String)parameters.get(UserDAO.USER_NAME);
			String email = (String)parameters.get(UserDAO.EMAIL);
			long userTypeId =(Long.valueOf((String)parameters.get(UserDAO.USER_TYPE_ID)));
			
			//search database if this username exist
			List<User> checkUsername = userDAO.findByProperty(new String[]{UserDAO.USER_NAME, UserDAO.USER_TYPE_ID}
															, new Object[]{username, userTypeId});
			if (checkUsername != null && !checkUsername.isEmpty())
				return URIRequest.USER_EXIST_MESSAGE;
			
			//search database if this email exists
			List<User> checkEmail = userDAO.findByProperty(new String[]{UserDAO.EMAIL, UserDAO.USER_TYPE_ID}
														, new Object[]{email, userTypeId});
			if (checkEmail != null && !checkEmail.isEmpty())
				return URIRequest.EMAIL_EXIST_MESSAGE;
			
			//create User object and insert to database
			String password = (String)parameters.get(UserDAO.PASSWORD);
			User user = new User(username, password, email, userTypeId);
			userDAO.insert(user);

			return URIRequest.SUCCESS_MESSAGE;
				
		}catch(Exception e){

			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
			
		}
		
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAccountService#addUserKey(java.lang.String)
	 */
	@Override
	public void addUserKey(String userKey) {
		userKeys.add(userKey);
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IAccountService#removeUserKey(java.lang.String)
	 */
	@Override
	public void removeUserKey(String userKey) {
		userKeys.remove(userKey);
	}
	
	
	
	

}
