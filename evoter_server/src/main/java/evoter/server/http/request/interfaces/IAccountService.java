package evoter.server.http.request.interfaces;

import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

import evoter.server.http.URIRequest;
import evoter.share.dao.UserDAO;

/**
 * 
 * @author btdiem
 *
 */
public interface IAccountService {

	public static final String BEAN_NAME = "accountRequest";
	/**
	 * Response clients a userkey generated from {@link UserDAO#USER_NAME} </br>
	 * and {@link UserDAO#PASSWORD} if username and password exist in database </br> 
	 * or response a null value if username and password do not exist in database </br>
	 * or response a @ {@link URIRequest#FAILURE_MESSAGE } if there is an{@link Exception} </br>    
	 * 
	 * @param exchange {@link HttpExchange} communicates between server and clients </br>
	 * @param parameters contains {@link UserDAO.USER_NAME} and {@link UserDAO.PASSWORD} </br>
	 */
	public void doLogin(HttpExchange exchange, Map<String,Object> parameters);	

	/**
	 * Response clients a {@link URIRequest#SUCCESS_MESSAGE} if userkey value exists </br>
	 * or response clients a {@link URIRequest#FAILURE_MESSAGE} if userkey does not exist </br>
	 * 
	 * @param exchange {@link HttpExchange} communicate between clients and server </br>
	 * @param parameters contains {@link UserDAO#USER_KEY} </br>
	 */
	public void doLogout(HttpExchange exchange, Map<String,Object> parameters);

	/**
	 * Check if email exists in the database and return a success or failure response </br> 
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains email address </br>
	 */
	public void doResetPassword(HttpExchange httpExchange, Map<String, Object> parameters) ;
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
	public void doRegister(HttpExchange httpExchange, Map<String, Object> parameters);
	
	/**
	 * 
	 * @param parameters contaisn {@link UserDAO#USER_KEY} </br>
	 * @return true if {@link UserDAO#USER_KEY} exists, otherwise return false </br>
	 */
	public  boolean hasUserKey(Map<String,Object> parameters);

}
