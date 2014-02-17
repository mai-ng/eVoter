/**
 * 
 */
package evoter.server.http.request.interfaces;

import java.util.Map;

import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.QuestionSession;
import evoter.share.model.Session;
import evoter.share.utils.URIRequest;

/**
 * Interface define all methods to handle requests involving {@link Question} </br>
 * 
 * @author btdiem </br>
 *
 */
public interface IQuestionService {

	public static final String BEAN_NAME = "questionService";
	/**
	 * 
	 * Select all {@link Question} of given {@link Session} </br>
	 * @return {@link Question#toJSON()} array and {@link Answer#toJSON()} array </br>
	 * if session has question </br>
	 * @return an empty array if session has no question </br>
	 * @return failure message if there is an exception </br>
	 * 
	 * This method is called when receiving {@link URIRequest#GET_ALL_QUESTION} </br>
	 * 
	 * @param httpExchange </br>
	 * @param parameters contains : </br>
	 * 	</li> QuestionSessionDAO.SESSION_ID </br>
	 *  </li> {@link UserDAO#USER_KEY}
	 */
	public Object doGetAll(Map<String,Object> parameters) ;
	
	/**
	 * Select the information of the given question </br>
	 * @return an {@link Question#toJSON()} and {@link Answer#toJSON()} array </br> 
	 * if question can be found </br>
	 * @return an empty array if question does not created before </br>
	 * @return failure message if there is an exception </br>
	 * 
	 * This method is called when receiving {@link URIRequest#VIEW_QUESTION} </br>
	 * 
	 * @param parameters contains </br>
	 * {@link UserDAO#USER_KEY} </br>
	 * {@link QuestionDAO#ID} </br>
	 * 
	 */
	public Object doView(Map<String,Object> parameters) ;
	/**
	 * 
	 * @param questionId
	 * @return a {@link JSONArray} 
	 *
	public JSONArray getAnswersOfQuestion(long questionId); */
	
	
	/**
	 * 
	 * Create a {@link Question} object from input request parameter and insert to question table </br>
	 * Create an  {@link QuestionSession} object and insert to QUESTION_SESSION table </br>
	 * Create {@link Answer} object from input request parameter and insert to ANSWER table </br>
	 * @return success message if creating and inserting successfully </br>
	 * @return failure message if there is an exception </br>
	 * 
	 * This method is called when receiving {@link URIRequest#CREATE_QUESTION} </br>
	 * 
	 * @param parameters contains: </br>
	 * 	</li> {@link QuestionDAO#QUESTION_TEXT} is a string array
	 * 	</li> {@link QuestionDAO#QUESTION_TYPE_ID}
	 *  </li> {@link QuestionDAO#CREATION_DATE} 
	 *  </li> {@link UserDAO#USER_KEY}
	 *  </li> {@link QuestionSessionDAO#SESSION_ID};
	 *  </li> {@link AnswerDAO#ANSWER_TEXT}  is a string array
	 *  
	 */
	public Object doCreate(Map<String, Object> parameters) ;
	/**
	 * 
	 * Remove {@link Question} of input parameter out the question table</br>
	 * @return success message if  deleting successfully </br>
	 * @return failure message if there is an exception </br>
	 *
	 * This method is called when receiving {@link URIRequest#DELETE_QUESTION} </br>
	 * 
	 * @param parameters contains: </br>
	 * {@link QuestionDAO#ID}
	 * {@link UserDAO#USER_KEY}
	 */
	public Object doDelete(Map<String,Object> parameters) ;
	
	/**
	 * 
	 * Keep the SessionID and {@link Question} of input request parameter </br>
	 * Change status of question from 0 to 1 that means the question is sending status </br>
	 * @return success message if the given question of request parameter is found and change status of question successfully </br>
	 * @return "question does not exist" if the question could not be found </br>
	 * @return failure if there is an exception </br>
	 * 
	 * This method is called when receiving {@link URIRequest#SEARCH_QUESTION} </br>
	 * 
	 * @param parameters contains: </br>
	 * 	</li> {@link QuestionDAO#ID} : question id is sending
	 * 	</li> {@link QuestionSessionDAO#SESSION_ID} : current session ID
	 * 	</li> {@link UserDAO#USER_KEY}
	 */
	public Object doSend(Map<String,Object> parameters) ;

	/**
	 * 
	 * Send client the question that has sending status (status value is 1)
	 * @return an {@link Question#toJSON()} and {@link Answer#toJSON()} array </br>
	 * @return failure message if there is an exception </br>
	 * 
	 * 
	 * This method is called when receiving {@link URIRequest#GET_LATEST_QUESTION} </br>
	 * 
	 * @param parameters contains: </br>
	 * 	</li> {@link QuestionSessionDAO.SESSION_ID} </br>
	 * 	</li> {@link UserDAO#USER_KEY} </br>
	 */
	public Object doGetLatest(Map<String,Object> parameters);
	/** 
	 * Stop sending question to clients and change status of sending question from 1 to 2 </br>
	 * @return success message if there is no exception </br>
	 * Otherwise @return failure message </br>
	 * 
	 * This method is called when receiving {@link URIRequest#STOP_SEND_QUESTION} </br>
	 *  
	 * @param parameters contains : </br>
	 * 	 {@link QuestionSessionDAO.SESSION_ID} </br>
	 *   {@link UserDAO#USER_KEY} </br>
	 */
	public Object doStopSend(Map<String,Object> parameters) ;
	/**
	 * 
	 * Change question text value of given question from input parameter request </br>
	 * and update the changes to database </br>
	 * @return success message if there is no exception </br>
	 * Otherwise @return a failure message </br>
	 *  
	 * This method is called when receiving {@link URIRequest#UPDATE_QUESTION} </br>
	 * 
	 * @param parameters contains: </br>
	 * </li> {@link UserDAO#USER_KEY} </br>
	 * </li> {@link QuestionDAO#ID} </br>
	 * 	</li> {@link QuestionDAO#QUESTION_TEXT}</br>
	 */
	public Object doEdit(Map<String, Object> parameters);
	
	/**
	 * Keep question of sending question request of teacher user</br>
	 * @param sessionId the current session id</br>
	 * @param question  question is sent by client requests </br>
	 */
	public void addSentQuestion(long sessionId, Question question);
	/**
	 * Remove the sending question 

	 * @param sessionId the current active session </br>
	 * @return {@link Question} sending question </br>
	 */
	public Question removeSentQuestion(long sessionId);
	/**
	 * 
	 * @param sessionId id the current active session </br>
	 * @return true if there is an sending status question </br>
	 * @return false if there is no sending status question </br>
	 */
	public boolean canSendQuestion(long sessionId);
}
