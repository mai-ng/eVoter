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

	public static final String BEAN_NAME = "userService";
	/**
	 * Response client request {@link List} of {@link User} </br>
	 * @param httpExchange {@link HttpExchange} communicates between server and clients </br>
	 * @param parameter  possibly contains one or some or all of the values below  </br>
	 * @return An array of {@link User#toJSON()} if the search condition match the database </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link UserDAO#USER_TYPE_ID} </br>
	 * </li> {@link UserDAO#EMAIL} </br>
	 * </li> {@link UserDAO#FULL_NAME} </br>
	 * </li> {@link UserDAO#ID} </br>
	 * </li> {@link UserDAO#IS_APPROVED} </br>
	 * </li> {@link UserDAO#PASSWORD} </br>
	 * </li> {@value UserDAO#USER_NAME}
	 */
	public Object doGetAll(Map<String, Object> parameter);
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
	 *  @return SUCCESS message if creating {@link User} successfully </br>
	 *  @return FAILURE message if creating {@link User} failure </br>
	 */
	public Object doCreate(Map<String, Object> parameter);
	
	/**
	 * Update the changes of {@link User} to the database </br>
	 * @param parameter contains </br>
	 * {@link UserDAO#USER_KEY} </br> - mandatory
	 * {@link UserDAO#ID} </br> - mandatory
	 * {@link UserDAO#EMAIL} </br> - optional
	 * {@link UserDAO#FULL_NAME} </br> - optional
	 * {@link UserDAO#PASSWORD} </br> - optional
	 * {@link UserDAO#IS_APPROVED} </br> - optional
	 * {@link UserDAO#USER_NAME} </br> - optional
	 * {@value UserDAO#USER_TYPE_ID} </br> - optional
	 *  @return SUCCESS message if creating {@link User} successfully </br>
	 *  @return FAILURE message if creating {@link User} failure </br>
	 * @return USER DOES NOT EXIST message if user doesn't exist in the system </br>
	 */
	public Object doEdit(Map<String, Object> parameter);
	
	/**
	 * Remove {@link User} out the system </br>
	 * @param httpExchange {@link tHttpExchange} communicate between server and clients </br>
	 * @param parameter contains: </br>
	 *  {@link UserDAO#USER_KEY} </br>
	 *  {@link UserDAO#ID} </br>
	 * @return SUCCESS of user is deleted successfully </br>
	 * @return FAILURE if there is an error </br>
	 * @return USER DOES NOT EXIST message if user doesn't exist in the system </br>
   
	 */
	public Object doDelete(Map<String, Object> parameter);
	/**
	 * Change approve status of  {@link User} 
	 * @param httpExchange {@link HttpExchange} communicates between server and clients </br>
	 * @param parameter contains: </br>
	 * {@link UserDAO#USER_KEY} </br>
	 * {@link UserDAO#ID} </br>
	 * {@link UserDAO#IS_APPROVED} </br> a boolean value
	 * @return SUCCESS of approved status is change successfully </br>
	 * @return FAILURE if there is an error </br>
	 * @return USER DOES NOT EXIST message if user doesn't exist in the system </br>
	 */
	public Object doChangeApprove(Map<String, Object> parameter);
	
}
