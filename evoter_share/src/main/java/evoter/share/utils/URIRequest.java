package evoter.share.utils;

import java.util.List;

import org.json.simple.JSONObject;


import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.Session;
import evoter.share.model.Subject;

/**
 * define client request
 * @author btdiem
 *
 */
public class URIRequest {
	
	public static final String SUCCESS_MESSAGE = "SUCCESS";
	public static final String FAILURE_MESSAGE = "FAILURE";
	public static final String USER_EXIST_MESSAGE = "USER EXISTS ALREADY";
	public static final String USER_NOT_EXIST_MESSAGE = "USER DOES NOT EXIST";
	public static final String EMAIL_EXIST_MESSAGE = "EMAIL EXISTS ";
	public static final String EMAIL_NOT_EXIST_MESSAGE = "EMAIL DOES NOT EXIST.";

	
	//for login
	/**
	 * request parameters:
	 * 	</li> user name
	 * 	</li> password
	 * response: 
	 * 	</li> boolean
	 */
	public static final String LOGIN="/login";
	//for reset password
	/**
	 * request parameters:
	 * 	</li> email
	 * 	</li> 
	 * response: 
	 * 	</li> boolean
	 */
	public static final String RESET_PASSWORD="/reset_password";	
	//for account registration
	/**
	 * request parameters:
	 * 	</li> email
	 * 	</li> 
	 * response: 
	 * 	</li> boolean
	 */
	public static final String REGISTER="/register";
	//for login
	/**
	 * request parameters:
	 * 	</li> user key
	 * response: 
	 * 	</li> boolean
	 */
	public static final String LOGOUT="/logout";	
	//for subject management
	/**
	 * request parameter 
	 * 	</li> user key
	 * response
	 * 	</li> {@link List} of {@link Subject} 
	 */
	public static final String GET_ALL_SUBJECT="/get_all_subject";
	/**
	 * request parameter 
	 * 	</li> user key
	 *  </li> subject id
	 * response
	 * 	</li> {@link Subject} 
	 */
	public static final String VIEW_SUBJECT="/view_subject";
	/**
	 * request parameter  </br>
	 * 	</li> user key
	 * 	</li> subject id
	 * response </br>
	 * 	</li>Boolean 
	 */
	public static final String DELETE_SUBJECT="/delete_subject";
	/**
	 * request parameter 
	 * 	</li> user key
	 *  </li> subject title
	 *  </li> creation date
	 * response
	 * 	</li> {@link List} of {@link Subject} 
	 */
	public static final String SEARCH_SUBJECT="/search_subject";
	
	public static final String UPDATE_SUBJECT = "/update_subject";
	
	
	//for session management
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> subject id
	 * response </br>
	 * 	</li> {@link List} of {@link Session}  
	 */
	public static final String GET_ALL_SESSION="/get_all_session";
	
	
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 * response </br>
	 * 	</li> {@link Session}  
	 */
	public static final String VIEW_SESSION="/view_session";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 * response </br>
	 * 	</li> Boolean  
	 */
	public static final String CREATE_SESSION="/create_session";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 * response </br>
	 * 	</li> Boolean  
	 */
	public static final String ACTIVE_SESSION="/active_session";//start a session
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 * response </br>
	 * 	</li> Boolean  
	 */
	public static final String INACTIVE_SESSION="/inactive_session";//start a session
	
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 *  </i> user id
	 * response </br>
	 * 	</li> Boolean  
	 */
	public static final String ACCEPT_SESSION="/accept_session";//accept a session	
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 * response </br>
	 * 	</li> Boolean  
	 */
	public static final String DELETE_SESSION="/delete_session";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> {@link Session} object under {@link JSONObject} format
	 * response </br>
	 * 	</li> {@link List} of {@link Session}  
	 */
	public static final String UPDATE_SESSION="/update_session";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session name
	 *  </li> creation date
	 *  </li> isActive
	 * response </br>
	 * 	</li> {@link List} of {@link Session}  
	 */
	public static final String SEARCH_SESSION="/search_session";
	//for question management
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 * response </br>
	 * 	</li> {@link List} of {@link Question}  
	 */
	public static final String GET_ALL_QUESTION="/get_all_question";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> question id
	 * response </br>
	 * 	</li> {@link Question}  
	 */
	public static final String VIEW_QUESTION="/view_question";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> {@link Question} object under JSon format
	 *  </li> session id
	 * response </br>
	 * 	</li> {@link Boolean}  
	 */
	public static final String CREATE_QUESTION="/create_question";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> question id
	 * response </br>
	 * 	</li> {@link Boolean}  
	 */
	public static final String DELETE_QUESTION="/delete_question";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> question id
	 *  </li> {@link Question} object under Json format
	 * response </br>
	 * 	</li> {@link Boolean}  
	 */
	public static final String UPDATE_QUESTION="/update_question";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> question id
	 *  </li> session id
	 * response </br>
	 * 	</li> {@link Boolean}  
	 */
	public static final String SEND_QUESTION="/send_question";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 *  </li> session id
	 * response </br>
	 * 	</li> {@link Boolean}  
	 */
	public static final String GET_QUESTION="/get_question";	
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> question id
	 *  </li> session id
	 * response </br>
	 * 	</li> {@link Boolean}  
	 */
	public static final String STOP_SEND_QUESTION="/stop_send_question";	
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> question type
	 *  </li> question text
	 * response </br>
	 * 	</li> {@link List} of {@link Question}  
	 */
	public static final String SEARCH_QUESTION="/search_question";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 * response </br>
	 * 	</li> {@link Question}  
	 */
	public static final String GET_LATEST_QUESTION="/get_latest_question";
	/**
	 * request parameter </br>
	 * 	</li> user key
	 * 	</li> session id
	 *  </li> question id
	 * response </br>
	 * 	</li> {@link Answer}  
	 */
	public static final String GET_STATISTICS="/get_statistics";
	/**
	 * Select all users of 1 subject
	 */
	public static final String GET_ALL_USERS_OF_SUBJECT="/get_allusers_ofsubject";
	
	
}
