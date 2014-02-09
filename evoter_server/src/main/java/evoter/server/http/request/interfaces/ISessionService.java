/**
 * 
 */
package evoter.server.http.request.interfaces;

import java.util.List;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Question;
import evoter.share.model.Session;
import evoter.share.model.SessionUser;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;

/**
 * @author btdiem
 *
 */
public interface ISessionService {

	public static final String BEAN_NAME = "sessionService";
	/**
	 * This method will select all {@link Session} of a specific {@link Subject} </br>
	 * and the result will be added to response to client application </br>
	 * There are two kind of requests. One is sent by teacher user and the other is sent by student user </br>
	 * The first selection is made from session table. if a null or empty value is returned, that means </br>
	 * the request is coming from student user application. So try to select all session in session_user table </br>
	 * and get all session object from returned value above </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server client application </br>
	 * @param parameters contains : </br>
	 * 	</li> SessionDAO.SUBJECT_ID
	 * 	</li> {@link UserDAO#USER_KEY}
	 */
	public void doGetAll(HttpExchange httpExchange, Map<String,Object> parameters) ;

	/**
	 * Response client a {@link Session}  when receiving {@link URIRequest#VIEW_SESSION} request </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server client application </br>
	 * @param parameters request parameter map contains </br>
	 * 	</li> SessionDAO.ID
	 */
	public void doView(HttpExchange httpExchange, Map<String,Object> parameters) ; 

	/**
	 * Change the status of {@link Session} to inactive </br>
	 * when receiving {@link URIRequest#ACTIVE_SESSION}</br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains : </br>
	 *  </li> SessionDAO.ID
	 *  </li> {@link UserDAO#USER_KEY}
	 */
	public void doActive(HttpExchange httpExchange, Map<String,Object> parameters) ;
	
	public void doAccept(HttpExchange httpExchange, Map<String,Object> parameters); 

	/**
	 * Update delete_indicator field of SESSION_USER table </br>
	 * when receiving {@link URIRequest#DELETE_SESSION} request from client application</br>
	 * to mark that this session is deleted by a user </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains : </br>
	 * 	</li> SessionUserDAO.SESSION_ID
	 *  </li> UserDAO.USER_KEY
	 */
	public void doDelete(HttpExchange httpExchange, Map<String,Object> parameters);
	/**
	 * Create a new {@link Question} object when receiving {@link URIRequest#CREATE_SESSION} </br>
	 * The order of steps are: </br>
	 * </li>Create a new {@link Session} and insert to SESSION table </br>
	 * </li>Create a new {@link SessionUser}  and insert to SESSION_USER table </br>
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains 
	 * 		</li> {@link SessionDAO#CREATION_DATE}
	 * 		</li> {@link SessionDAO#IS_ACTIVE}
	 * 		</li> {@link SessionDAO#NAME}
	 * 		</li> {@link SessionDAO#SUBJECT_ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 * 		</li> 
	 */
	public void doCreate(HttpExchange httpExchange, Map<String,Object> parameters); 

	/**
	 * Change the status of {@link Session} to inactive </br>
	 * when receiving {@link URIRequest#INACTIVE_SESSION}</br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains: </br>
	 * 		</li> {@link SessionDAO#ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 */
	public void doInActive(HttpExchange httpExchange, Map<String,Object> parameters) ;
	
	
	/**
	 * Update {@link SessionDAO#NAME} of {@link Session} </br> 
	 * when receiving {@link URIRequest#UPDATE_SESSION} </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains </br>
	 * 		</li> {@link SessionDAO#NAME}
	 * 		</li> {@link SessionDAO#ID} 
	 * 		</li> {@link UserDAO#USER_KEY}
	 */
	public  void doUpdate(HttpExchange httpExchange, Map<String,Object> parameters) ;
	
	/**
	 * Response clients a {@link List} of {@link User} accepted or not accept {@link Session} </br>
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link SessionUserDAO#ACCEPT_SESSION} </br>
	 * </li> {@link SessionUserDAO#SESSION_ID} </br>
	 */
	public void doGetStudentsOfSession(HttpExchange httpExchange, Map<String, Object> parameters);
}
