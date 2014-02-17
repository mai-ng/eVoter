/**
 * 
 */
package evoter.server.http.request.interfaces;

import java.util.Map;

import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;


/**
 * 
 * Define methods to handle the coming request involving User </br>
 * @author btdiem </br>
 *
 */
public interface IUserService {

	public static final String BEAN_NAME = "userService";
	/**
	 * 
	 * Select all {@link User} matching conditions of the input parameter </br>
	 * @return {@link User#toJSON()} if user list can be found </br>
	 * Otherwise, returning an empty array </br>
	 * @return failure message if there is an exception </br>
	 * This method is called when receiving {@link URIRequest#GET_ALL_USER} </br>
	 * 
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
	 * Create and insert a new {@link User} from values of the input parameter to database </br>
     * @return success message if there is no exception. Otherwise, returning a failure message </br>
     * 
     * This method is called when receiving {@link URIRequest#CREATE_USER} </br>
     * 
	 * @param parameter contains : </br>
	 * 	{@link UserDAO#USER_KEY} </br>
	 * 	{@link UserDAO#USER_TYPE_ID} </br>
	 * 	{@link UserDAO#EMAIL} </br>
	 * 	{@link UserDAO#IS_APPROVED} : true or false </br>
	 *  {@link UserDAO#USER_NAME} </br>
	 *  {@link UserDAO#PASSWORD} </br>
	 *  {@link UserDAO#FULL_NAME} </br>
	 */
	public Object doCreate(Map<String, Object> parameter);
	
	/**
	 * 
	 * Update the changes of {@link User} from the input parameter </br>
     * @return success message if there is no exception. Otherwise, returning a failure message </br>
     * @return "user does not exist" if there is no user found from the given userId </br> 
     * This method is called when receiving {@link URIRequest#EDIT_USER} </br>
 
 	 * @param parameter contains </br>
	 * {@link UserDAO#USER_KEY} </br> - mandatory
	 * {@link UserDAO#ID} </br> - mandatory
	 * {@link UserDAO#EMAIL} </br> - optional
	 * {@link UserDAO#FULL_NAME} </br> - optional
	 * {@link UserDAO#PASSWORD} </br> - optional
	 * {@link UserDAO#IS_APPROVED} </br> - optional
	 * {@link UserDAO#USER_NAME} </br> - optional
	 * {@value UserDAO#USER_TYPE_ID} </br> - optional
	 * 
	 */
	public Object doEdit(Map<String, Object> parameter);
	
	/**
	 * Delete {@link User} having then same userId value in the input parameter </br>
     * @return success message if there is no exception. Otherwise, returning a failure message </br>
     * @return "user does not exist" if there is no user found from the given userId </br>
     * 
     * This method is called when receiving {@link URIRequest#DELETE_USER} </br>
     *  
	 * @param parameter contains: </br>
	 *  {@link UserDAO#USER_KEY} </br>
	 *  {@link UserDAO#ID} </br>
	 */
	public Object doDelete(Map<String, Object> parameter);
	/**
	 * Change approve status of  {@link User} and set the change to database </br>
	 * @return SUCCESS of approved status is change successfully </br>
	 * @return FAILURE if there is an error </br>
	 * @return USER DOES NOT EXIST message if user doesn't exist in the system </br
	 * 
	 * @param parameter contains: </br>
	 * {@link UserDAO#USER_KEY} </br>
	 * {@link UserDAO#ID} </br>
	 * {@link UserDAO#IS_APPROVED} </br> a boolean value
>
	 */
	public Object doChangeApprove(Map<String, Object> parameter);
	
}
