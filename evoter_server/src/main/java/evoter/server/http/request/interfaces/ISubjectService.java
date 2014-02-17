package evoter.server.http.request.interfaces;

import java.util.List;
import java.util.Map;

import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.model.UserSubject;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;

/**
 * 
 * Define methods to handle the coming requests involving {@link Subject} </br>
 * @author btdiem </br>
 *
 */
public interface ISubjectService {

	public static final String BEAN_NAME = "subjectService";
	/**
	 * 
	 * Select {@link Subject} by subjectID of parameter request </br>
	 * @return {@link Subject#toJSON()} if subjectID can be found. Otherwise,</br>
	 * return "subject does not exist" message </br>
	 * @return failure message if there is an exception </br>
	 * This method is called when receiving {@link URIRequest#VIEW_SUBJECT} </br>
	 * 
	 * @param parameters contains : </br>
	 *  </li> {@link SubjectDAO.ID} </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 */
	public Object doView(Map<String,Object> parameters);
	
	/**
     * 
	 * If user type is {@link UserType#SECRETARY}, select all {@link Subject} created by all teacher users </br>
	 * Otherwise, select only subjects  requested by {@link User} </br>
     * @return {@link User#toJSON()} array if subject list can be found. </br>
     * Otherwise, returning an empty array </br>
     * @return failure message if there is an exception </br>
     * 
	 * This method is called when receiving {@link URIRequest#VIEW_SUBJECT} </br>
	 * @param parameters contains : </br>
	 *  </li> {@link UserDAO.USER_KEY} </br>
	 */
	public  Object doGetAll(Map<String,Object> parameters);
	
	/**
	 * 
	 * Delete given subject from database and its reference </br>
	 * @return success message if there is no exception. </br>
	 * Otherwise, returning failure message </br>
	 * When delete a subject: </br>
	 *  </li> delete subject in SUBJECT table </br>
	 *  </li> delete subjects in USER_SUBJECT table </br>
	 *  </li> delete all sessions of this subject in SESSION table </br>
	 *  </li> delete all sessions of this subject in SESSION_USER table </br>
	 *  </li> delete all sessions in QUESTION_SESSION table </br>
	 *  
	 * This method is called when receiving {@link URIRequest#DELETE_SUBJECT} </br>
	 * 
	 * @param parameters contains </br>
	 *  </li> {@link SubjectDAO.ID} </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 */
	public Object doDelete(Map<String,Object> parameters);
	/**
	 * 
	 * Select a {@link List} of {@link Subject} matching conditions of parameter request </br>
	 * @return {@link Subject#toJSON()} array if subject list can be found </br>
	 * Otherwise, returning an empty array </br>
	 * @return failure message if there is an exception </br>
	 * 
	 * This method is called when receiving {@link URIRequest#SEARCH_SUBJECT} </br>
	 * 
	 * @param parameters contains </br>
	 *  </li> {@link SubjectDAO#TITLE} </br>
	 *  </li> {@link SubjectDAO#CREATION_DATE} </br>
	 */
	public Object  doSearch(Map<String,Object> parameters);
	
	/**
	 * 
	 * Select all users of {@link Subject} </br>
	 * @return {@link User#toJSON()} if user list can be found </br>
	 * Otherwise, returning an empty array </br>
	 * @return failure message if there is an exception </br>
	 * This method is called when receiving {@link URIRequest#GET_ALL_USERS_OF_SUBJECT} </br>
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link SubjectDAO#ID} </br>
	 */
	public Object doGetUsersOfSubject(Map<String, Object> parameters);
	/**
	 * Update a the changes for {@link Subject} to database </br>
	 * Delete all records of this subject in user_subject table </br>
	 * Iterate email list, create {@link UserSubject} and insert new records to user_subject table </br>
	 * 
	 * This method is called when receiving {@link URIRequest#UPDATE_SUBJECT}  </br>
	 * 
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link SubjectDAO#ID} </br>
	 * </li> {@link SubjectDAO#TITLE} </br>
	 *  {@link SubjectDAO#EMAIL_LIST} : an array of student and teacher email </br>
	 * 
	 */
	public Object doEdit(Map<String, Object> parameters);
	/**
	 * Create new {@link Subject} from the input parameter </br>
	 * Insert a new {@link Subject} to subject table </br>
	 * Iterate user email list and insert these records to user_subject table </br>
	 *  
	 * This method is called when receiving {@link URIRequest#CREATE_SUBJECT} </br>
	 * 
	 * @param parameters contains: </br>
	 *  {@link UserDAO#USER_KEY} </br>
	 *  {@link SubjectDAO#TITLE} </br>
	 *  {@link SubjectDAO#CREATION_DATE} </br>
	 *  {@link SubjectDAO#EMAIL_LIST} : an array of student and teacher email </br>
	 *  
	 */
	public Object doCreate(Map<String, Object> parameters);
	
	
}
