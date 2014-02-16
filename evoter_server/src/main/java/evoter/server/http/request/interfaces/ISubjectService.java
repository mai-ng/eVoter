package evoter.server.http.request.interfaces;

import java.util.Map;
import com.sun.net.httpserver.HttpExchange;

import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.model.UserSubject;
import evoter.share.utils.URIRequest;

/**
 * @author btdiem
 *
 */
public interface ISubjectService {

	public static final String BEAN_NAME = "subjectService";
	/**
	 * Response client a {@link Subject} object when receiving {@link URIRequest#VIEW_SUBJECT} </br>
	 * 
	 * @param exchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains : </br>
	 *  </li> {@link SubjectDAO.ID} </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 */
	public Object doView(Map<String,Object> parameters);
	
	/**
	 * Response client all {@link Subject} of user when receiving {@link URIRequest#VIEW_SUBJECT} </br>
	 * @param exchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains : </br>
	 *  </li> {@link UserDAO.USER_KEY} </br>
	 */
	public  Object doGetAll(Map<String,Object> parameters);
	
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
	public Object doDelete(Map<String,Object> parameters);
	/**
	 * Response client a list of {@link Subject} matching search conditions when receiving </br>
	 * {@link URIRequest#SEARCH_SUBJECT} </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains </br>
	 *  </li> {@link SubjectDAO#TITLE} </br>
	 *  </li> {@link SubjectDAO#CREATION_DATE} </br>
	 */
	public Object  doSearch(Map<String,Object> parameters);
	
	/**
	 * Response client a list of {@link User} of a {@link Subject} </br>
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link SubjectDAO#ID} </br>
	 */
	public Object doGetUsersOfSubject(Map<String, Object> parameters);
	/**
	 * Update a the changes for {@link Subject} to database </br>
	 * Delete all records of this subject in user_subject table </br>
	 * Iterate email list, create {@link UserSubject} and insert new records to user_subject table </br>
	 * This method is called when receiving {@link URIRequest#UPDATE_SUBJECT} request </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link SubjectDAO#ID} </br>
	 * </li> {@link SubjectDAO#TITLE} </br>
	 *  {@link SubjectDAO#EMAIL_LIST} : an array of student and teacher email </br>
	 * 
	 */
	public Object doEdit(Map<String, Object> parameters);
	/**
	 * Insert a new {@link Subject} to subject table </br>
	 * Iterate user email list and insert these records to user_subject table </br> 
	 * This method is called when receiving {@link URIRequest#CREATE_SUBJECT} request</br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and clients </br>
	 * @param parameters contains: </br>
	 *  {@link UserDAO#USER_KEY} </br>
	 *  {@link SubjectDAO#TITLE} </br>
	 *  {@link SubjectDAO#CREATION_DATE} </br>
	 *  {@link SubjectDAO#EMAIL_LIST} : an array of student and teacher email </br>
	 *  
	 */
	public Object doCreate(Map<String, Object> parameters);
	
	
}
