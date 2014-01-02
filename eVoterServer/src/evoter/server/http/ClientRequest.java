package evoter.server.http;

import java.util.List;

import sun.org.mozilla.javascript.internal.json.JsonParser;

import evoter.server.model.Answer;
import evoter.server.model.Question;
import evoter.server.model.Session;
import evoter.server.model.Subject;

/**
 * define client request
 * @author btdiem
 *
 */
public class ClientRequest {
	
	//for login
	/**
	 * request parameters:
	 * 	</li> user name
	 * 	</li> password
	 * response: 
	 * 	</li> boolean
	 */
	public static final String LOGIN="/login";
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
	public static final String ACTIVE_SESSION="/active_session";
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
	 * 	</li> {@link Session} object under {@link JsonParser} format
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
	
	
}
