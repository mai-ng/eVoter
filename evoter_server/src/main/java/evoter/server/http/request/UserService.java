/**
 * 
 */
package evoter.server.http.request;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.http.request.interfaces.IUserService;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;

/**
 * This class is an implementation of {@link IUserService} </br>
 * 
 * @author btdiem </br>
 *
 */

@Service
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class UserService implements IUserService {

//	@Autowired
	private UserDAO userDAO;
	
	
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
	public Object doGetAll(Map<String, Object> parameter) {
		
		JSONArray response = new JSONArray();
		try{
			
			parameter.remove(UserDAO.USER_KEY);
			String[] keys = new String[parameter.keySet().size()];
			keys = parameter.keySet().toArray(keys);
			Object[] values = new Object[parameter.values().size()];
			values = parameter.values().toArray(values);
//			System.out.println("keys: " + keys.length);
//			System.out.println("values: " + values.length);
			
			List<User> userList = userDAO.findByProperty(keys, values);
			for (User user : userList){
				response.add(user.toJSON());
			}//for
			
			//URIUtils.writeResponse(response, httpExchange);
			return response;
			
		}catch(Exception e){
			
			//e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IUserService#doCreate(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public Object doCreate(Map<String, Object> parameter) {
		
		try{
			
			User user = new User();
			if (parameter.containsKey(UserDAO.USER_TYPE_ID)){
				user.setUserTypeId(Long.valueOf((String)
						parameter.get(UserDAO.USER_TYPE_ID)));
			}
			if (parameter.containsKey(UserDAO.FULL_NAME)){
				user.setFullName((String)parameter.get(UserDAO.FULL_NAME));						
			}
			if(parameter.containsKey(UserDAO.EMAIL)){
				user.setEmail((String)parameter.get(UserDAO.EMAIL));
			}
			if(parameter.containsKey(UserDAO.USER_NAME)){
				user.setUserName((String)parameter.get(UserDAO.USER_NAME));
			}
			if(parameter.containsKey(UserDAO.PASSWORD)){
				user.setPassWord((String)parameter.get(UserDAO.PASSWORD));
			}
			if(parameter.containsKey(UserDAO.IS_APPROVED)){
				user.setApproved(Boolean.valueOf((String)parameter.get(UserDAO.IS_APPROVED)));
			}
			userDAO.insert(user);
			
			//URIUtils.writeSuccessResponse(httpExchange);
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}

		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.
	 * 	IUserService#doEdit(java.util.Map)
	 */
	@Override
	public Object doEdit(Map<String, Object> parameter) {

		try{
			
			long userId = Long.valueOf((String)parameter.get(UserDAO.ID));
			List<User> users = userDAO.findById(userId);
			if (users != null && !users.isEmpty()){
				
				User user = users.get(0);
				if (parameter.containsKey(UserDAO.USER_TYPE_ID)){
					user.setUserTypeId(Long.valueOf((String)
							parameter.get(UserDAO.USER_TYPE_ID)));
				}
				if (parameter.containsKey(UserDAO.FULL_NAME)){
					user.setFullName((String)parameter.get(UserDAO.FULL_NAME));						
				}
				if(parameter.containsKey(UserDAO.EMAIL)){
					user.setEmail((String)parameter.get(UserDAO.EMAIL));
				}
				if(parameter.containsKey(UserDAO.USER_NAME)){
					user.setUserName((String)parameter.get(UserDAO.USER_NAME));
				}
				if(parameter.containsKey(UserDAO.PASSWORD)){
					user.setPassWord((String)parameter.get(UserDAO.PASSWORD));
				}
				if(parameter.containsKey(UserDAO.IS_APPROVED)){
					user.setApproved(Boolean.valueOf((String)parameter.get(UserDAO.IS_APPROVED)));
				}
				userDAO.update(user);
				return URIRequest.SUCCESS_MESSAGE;
			}

			return URIRequest.USER_NOT_EXIST_MESSAGE;
			
		}catch(Exception e){
			
			e.printStackTrace();
			return URIRequest.FAILURE_MESSAGE;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IUserService#doDelete(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public Object doDelete(Map<String, Object> parameter) {
		
		try{
			
			long userId = Long.valueOf((String)parameter.get(UserDAO.ID));
			List<User> userList = userDAO.findById(userId);
			if (userList != null && !userList.isEmpty()){
				userDAO.deleteById(userId);
				return URIRequest.SUCCESS_MESSAGE;
			}
			
			return URIRequest.USER_NOT_EXIST_MESSAGE;
			//URIUtils.writeSuccessResponse(httpExchange);
		}catch(Exception e){
			
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IUserService#doApprove(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public Object doChangeApprove(Map<String, Object> parameter) {
		
		try{
			
			long userId = Long.valueOf((String)parameter.get(UserDAO.ID));
			boolean isApproved = Boolean.valueOf((String)parameter.get(UserDAO.IS_APPROVED));
			
			System.out.println("userId " + userId);
			System.out.println("isApproved " + isApproved);
			
			List<User> userList = userDAO.findById(userId);
			if (userList != null && !userList.isEmpty()){
				
				User user = userList.get(0);
				user.setApproved(isApproved);
				userDAO.update(user);
				//URIUtils.writeSuccessResponse(httpExchange);
				return URIRequest.SUCCESS_MESSAGE;
				
			}
			return URIRequest.USER_NOT_EXIST_MESSAGE;
				//URIUtils.writeResponse("User does not exist", httpExchange);
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}

		
	}

}
