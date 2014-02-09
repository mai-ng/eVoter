/**
 * 
 */
package evoter.server.http.request;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.net.httpserver.HttpExchange;


import evoter.server.http.URIUtils;
import evoter.server.http.request.interfaces.IUserService;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;

/**
 * This class is an implementation of {@link IUserService} </br>
 * 
 * @author btdiem </br>
 *
 */
@Service
public class UserService implements IUserService {

	//@Autowired
	UserDAO userDAO;
	
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IUserService#doGetAll(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doGetAll(HttpExchange httpExchange,
			Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		
		JSONArray response = new JSONArray();
		try{
			
			parameter.remove(UserDAO.USER_KEY);
			String[] keys = parameter.keySet().toArray(new String[]{});
			Object[] values = parameter.values().toArray(new Object[]{});
			
			List<User> userList = userDAO.findByProperty(keys, values);
			for (User user : userList){
				response.add(user.toJSON());
			}//for
			
			URIUtils.writeResponse(response, httpExchange);
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IUserService#doCreate(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public void doCreate(HttpExchange httpExchange,
			Map<String, Object> parameter) {
		
		try{
			
			long userTypeId = Long.valueOf((String)parameter.get(UserDAO.USER_TYPE_ID));
			String fullName = (String)parameter.get(UserDAO.FULL_NAME);
			String email = (String)parameter.get(UserDAO.EMAIL);
			String userName = (String)parameter.get(UserDAO.USER_NAME);
			String password = (String)parameter.get(UserDAO.PASSWORD);
			
			User user = new User(userName, password, email, userTypeId, fullName, false);
			userDAO.insert(user);
			
			URIUtils.writeSuccessResponse(httpExchange);
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}

		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IUserService#doEdit(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public void doEdit(HttpExchange httpExchange, Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IUserService#doDelete(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public void doDelete(HttpExchange httpExchange,
			Map<String, Object> parameter) {
		
		try{
			
			long userId = Long.valueOf((String)parameter.get(UserDAO.ID));
			userDAO.deleteById(userId);
			
			URIUtils.writeSuccessResponse(httpExchange);

		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IUserService#doApprove(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public void doApprove(HttpExchange httpExchange,
			Map<String, Object> parameter) {
		
		try{
			
			long userId = Long.valueOf((String)parameter.get(UserDAO.ID));
			boolean isApproved = Boolean.valueOf((String)parameter.get(UserDAO.IS_APPROVED));
			
			List<User> userList = userDAO.findById(userId);
			if (userList != null && !userList.isEmpty()){
				
				User user = userList.get(0);
				user.setApproved(isApproved);
				
				URIUtils.writeSuccessResponse(httpExchange);
				
			}else{
				
				URIUtils.writeResponse("User does not exist", httpExchange);
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}

		
	}

}
