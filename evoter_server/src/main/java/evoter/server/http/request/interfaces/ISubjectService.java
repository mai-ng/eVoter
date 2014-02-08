package evoter.server.http.request.interfaces;

import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.utils.URIRequest;

/**
 * @author btdiem
 *
 */
public interface ISubjectService {

	public static final String BEAN_NAME = "subjectRequest";
	/**
	 * Response client a {@link Subject} object when receiving {@link URIRequest#VIEW_SUBJECT} </br>
	 * 
	 * @param exchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains : </br>
	 *  </li> {@link SubjectDAO.ID} </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 */
	public void doView(HttpExchange exchange, Map<String,Object> parameters);
	
	/**
	 * Response client all {@link Subject} of user when receiving {@link URIRequest#VIEW_SUBJECT} </br>
	 * @param exchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains : </br>
	 *  </li> {@link UserDAO.USER_KEY} </br>
	 */
	public  void doGetAll(HttpExchange exchange, Map<String,Object> parameters);
	
	/**
	 * When delete a subject: </br>
	 *  </li> delete subject in SUBJECT table </br>
	 *  </li> delete subject in USER_SUBJECT table </br>
	 *  </li> delete all sessions of this subject in SESSION table </br>
	 *  </li> delete all sessions of this subject in SESSION_USER table </br>
	 *  </li> delete all sessions in QUESTION_SESSION table </br>
	 *  </li> delete all sessions in STATISTICS table </br>
	 * 
	 * Remove {@link Subject} out database and response client a {@link URIRequest#SUCCESS_MESSAGE} </br>
	 * when receiving {@link URIRequest#DELETE_SUBJECT} </br>
	 * 
	 * @param exchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains </br>
	 *  </li> {@link SubjectDAO.ID} </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 */
	public void doDelete(HttpExchange exchange, Map<String,Object> parameters);
	/**
	 * Response client a list of {@link Subject} matching search conditions when receiving </br>
	 * {@link URIRequest#SEARCH_SUBJECT} </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains </br>
	 *  </li> {@link SubjectDAO#TITLE} </br>
	 *  </li> {@link SubjectDAO#CREATION_DATE} </br>
	 */
	public void doSearch(HttpExchange httpExchange, Map<String,Object> parameters);
	
	/**
	 * Response client a list of {@link User} of a {@link Subject} </br>
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link SubjectDAO#ID} </br>
	 */
	public void doGetUsersOfSubject(HttpExchange httpExchange, Map<String, Object> parameters);
	/**
	 * Update a {@link Subject} to database </br>
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link SubjectDAO#ID} </br>
	 * </li> {@link SubjectDAO#TITLE} </br>
	 * </li> {@link SubjectDAO#CREATION_DATE} </br>
	 */
	public void doEdit(HttpExchange httpExchange, Map<String, Object> parameters);
	
}
