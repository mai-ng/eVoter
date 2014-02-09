/**
 * 
 */
package evoter.server.http.request.interfaces;

import java.util.List;
import java.util.Map;


import com.sun.net.httpserver.HttpExchange;

import evoter.share.dao.UserDAO;
import evoter.share.model.User;


/**
 * 
 * This class is an interface of User </br>
 * @author btdiem </br>
 *
 */
public interface IUserService {

	/**
	 * Response client request {@link List} of {@link User} </br>
	 * @param httpExchange {@link HttpExchange} communicates between server and clients </br>
	 * @param parameter  possibly contains one or some or all of the values below  </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link UserDAO#USER_TYPE_ID} </br>
	 * </li> {@link UserDAO#EMAIL} </br>
	 * </li> {@link UserDAO#FULL_NAME} </br>
	 * </li> {@link UserDAO#ID} </br>
	 * </li> {@link UserDAO#IS_APPROVED} </br>
	 * </li> {@link UserDAO#PASSWORD} </br>
	 * </li> {@value UserDAO#USER_NAME}
	 */
	public void doGetAll(HttpExchange httpExchange, Map<String, Object> parameter);
	/**
	 * Insert {@link User} to the {@link Database} </br> 
	 * @param httpExchange {@link HttpExchange} communicate between server and clients </br>
	 * @param parameter contains : </br>
	 * 	{@link UserDAO#USER_KEY} </br>
	 * 	{@link UserDAO#USER_TYPE_ID} </br>
	 * 	{@link UserDAO#EMAIL} </br>
	 * 	{@link UserDAO#IS_APPROVED} : true or false </br>
	 *  {@link UserDAO#USER_NAME} </br>
	 *  {@link UserDAO#PASSWORD} </br>
	 *  {@link UserDAO#FULL_NAME} </br>
	 */
	public void doCreate(HttpExchange httpExchange, Map<String, Object> parameter);
	
	public void doEdit(HttpExchange httpExchange, Map<String, Object> parameter);
	
	/**
	 * Remove {@link User} out the system </br>
	 * @param httpExchange {@link tHttpExchange} communicate between server and clients </br>
	 * @param parameter contains: </br>
	 *  {@link UserDAO#USER_KEY} </br>
	 *   {@link UserDAO#ID} </br>
	 */
	public void doDelete(HttpExchange httpExchange, Map<String, Object> parameter);
	/**
	 * Change approve status of  {@link User} 
	 * @param httpExchange {@link HttpExchange} communicates between server and clients </br>
	 * @param parameter contains: </br>
	 * {@link UserDAO#USER_KEY} </br>
	 * {@link UserDAO#ID} </br>
	 * {@link UserDAO#IS_APPROVED} </br> a boolean value
	 */
	public void doApprove(HttpExchange httpExchange, Map<String, Object> parameter);
	
}
