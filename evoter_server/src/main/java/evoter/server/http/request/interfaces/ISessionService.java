/**
 * 
 */
package evoter.server.http.request.interfaces;


import java.util.Map;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Session;
import evoter.share.model.SessionUser;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;

/**
 * @author btdiem
 *
 */
public interface ISessionService {

	public static final String BEAN_NAME = "sessionService";
	/**
	 * This method will select all {@link Session} of a given subject of parameter request </br>
	 * There are two kind of requests. One is sent by teacher user and the other is sent by student user </br>
	 * Teacher user request will receive all sessions created by him </br>
	 * Student user request will receive all sessions invited </br>
	 * 
	 * This method is called when receiving {@link URIRequest#GET_ALL_SESSION} </br>
	 * 
	 * @return {@link Session#toJSON()} array if subject has session. Otherwise, returning an empty array </br>
	 * @return failure message if there is an exception </br>
	 * 
	 * @param parameters contains : </br>
	 * 	</li> {@link SessionDAO.SUBJECT_ID}
	 * 	</li> {@link UserDAO#USER_KEY}
	 */
	public Object doGetAll(Map<String,Object> parameters) ;

	/**
	 * Get a {@link Session} that has the same sessionID in parameter request </br>
	 * This method is called when receiving {@link URIRequest#VIEW_QUESTION} </br>
	 * 
	 * @return {@link Session#toJSON()} if session is found otherwise, returning an empty array </br>
	 * @return failure message if there is an exception </br>
	 * 
	 * @param parameters request parameter map contains </br>
	 * {@link SessionDAO.ID} </br>
	 * {@link UserDAO#USER_KEY} </br>
	 */
	public Object doView(Map<String,Object> parameters) ; 

	/**
	 * Change the status of given session to active and update the changes to session table </br>
	 * @return success message if there is no exception </br>
	 * Otherwise, @return failure message </br>
	 * this method is called when receiving {@link URIRequest#ACTIVE_SESSION}</br>
	 * 
	 * @param parameters contains : </br>
	 *  </li> {@link SessionDAO.ID} </br>
	 *  </li> {@link UserDAO#USER_KEY}
	 */
	public Object doActive(Map<String,Object> parameters) ;
	/**
	 * Change status of {@link Session} to accepted and set the changes to session table </br>
	 * @return success message if there is no exception </br>
	 * Otherwise, @return failure message </br>
     * This method is called when receiving {@link URIRequest#ACCEPT_SESSION} </br>
     * 
	 * @param parameters contains:
	 * {@link UserDAO#USER_KEY} </br>
	 * {@link SessionDAO#ID} </br>
	 */
	public Object doAccept(Map<String,Object> parameters); 

	/**
	 * 
	 * Change delete_indicator of user with the session from o to 1 </br>
	 * That means this session becomes invisible with user </br>
	 * @return success message if there is no exception. Otherwise </br>
	 * @return failure message
	 * 
	 * This method is called when receiving {@link URIRequest#DELETE_SESSION} </br>
	 * 
	 * @param parameters contains : </br>
	 * 	</li> {@link SessionUserDAO.SESSION_ID} </br>
	 *  </li> {@link UserDAO.USER_KEY} </br>
	 */
	public Object doDelete(Map<String,Object> parameters);
	/**
	 * 
	 * Create a new {@link Session} object from values of parameter request </br>
	 * Add new {@link Session} to session table </br>
	 * Add a new {@link SessionUser} record to session_user table</br>
	 * Add two questions:excited and difficult to calculate real time statistics </br>
	 * 
	 * @return success message if there is no exception. </br>
	 * Otherwise, @return a failure message </br>
	 * 
	 * This method is called when receiving {@link URIRequest#CREATE_SESSION}</br>
	 * 
	 * @param parameters contains </br>
	 * 		</li> {@link SessionDAO#CREATION_DATE} </br>
	 * 		</li> {@link SessionDAO#IS_ACTIVE} </br>
	 * 		</li> {@link SessionDAO#NAME} </br>
	 * 		</li> {@link SessionDAO#SUBJECT_ID} </br>
	 * 		</li> {@link UserDAO#USER_KEY} </br>
	 */
	public Object doCreate(Map<String,Object> parameters); 

	/**
	 * Change the status of the give session to inactive </br>
	 * Set the change to session table </br>
	 * @return success message if there is no exception. </br>
	 * Otherwise, @return a failure message </br>
	 * 
	 * This method is called when receiving {@link URIRequest#INACTIVE_SESSION} </br>
	 * @param parameters contains: </br>
	 * 		</li> {@link SessionDAO#ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 */
	public Object doInActive(Map<String,Object> parameters) ;
	/**
	 * Change name of session from parameter request and set the change to session table </br>
	 * @return success message if there is no exception. </br>
	 * Otherwise, @return a failure message </br>
	 * 
	 * this method is called when receiving {@link URIRequest#UPDATE_SESSION} </br>
	 * 
	 * @param parameters contains </br>
	 * 		</li> {@link SessionDAO#NAME} </br>
	 * 		</li> {@link SessionDAO#ID} </br>
	 * 		</li> {@link UserDAO#USER_KEY} </br>
	 */
	public  Object doUpdate(Map<String,Object> parameters) ;
	
	/**
	 * 
	 * Select all student {@link User} that invite to the given {@link Session} </br>
	 * @return {@link User#toJSON()} array if session has user. Otherwise, returning an empty array </br>
     * This method is called when receiving {@link URIRequest#GET_ALL_STUDENT} </br>
     * 
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link SessionUserDAO#ACCEPT_SESSION} </br>
	 * </li> {@link SessionUserDAO#SESSION_ID} </br>
	 */
	public Object doGetStudentsOfSession(Map<String, Object> parameters);
}
