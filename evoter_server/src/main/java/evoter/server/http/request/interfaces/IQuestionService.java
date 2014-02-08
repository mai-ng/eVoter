/**
 * 
 */
package evoter.server.http.request.interfaces;

import java.util.Map;
import org.json.simple.JSONArray;
import com.sun.net.httpserver.HttpExchange;

import evoter.server.http.URIRequest;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.QuestionSession;

/**
 * @author btdiem
 *
 */
public interface IQuestionService {

	public static final String BEAN_NAME = "questionRequest";
	/**
	 * This method will response a list of {@link Question} and {@link Answer} when server </br>
	 * receives request {@link URIRequest#GET_ALL_QUESTION} </br>
	 * 
	 * @param httpExchange </br>
	 * @param parameters contains : </br>
	 * 	</li> QuestionSessionDAO.SESSION_ID
	 *  </li> {@link UserDAO#USER_KEY}
	 */
	public void doGetAll(HttpExchange httpExchange, Map<String,Object> parameters) ;
	
	public void doView(HttpExchange httpExchange, Map<String,Object> parameters) ;
	/**
	 * 
	 * @param questionId
	 * @return a {@link JSONArray} 
	 */
	public JSONArray getAnswersOfQuestion(long questionId);
	/**
	 * Insert {@link Question} object to QUESTION table </br>
	 * Insert {@link QuestionSession} object to QUESTION_SESSION table </br>
	 * Insert {@link Answer} object to ANSWER table </br>
	 * 
	 * 
	 * @param httpExchange
	 * @param parameters contains: </br>
	 * 	</li> {@link QuestionDAO#QUESTION_TEXT} is a string array
	 * 	</li> {@link QuestionDAO#QUESTION_TYPE_ID}
	 *  </li> {@link QuestionDAO#CREATION_DATE} 
	 *  </li> {@link UserDAO#USER_KEY}
	 *  </li> {@link QuestionSessionDAO#SESSION_ID};
	 *  </li> {@link AnswerDAO#ANSWER_TEXT}  is a string array
	 *  
	 */
	public void doCreate(HttpExchange httpExchange, Map<String, Object> parameters) ;
	/**
	 * Delete {@link Answer} in ANSWER table </br>
	 * Delete {@link Statistics} in STATISTICS table </br>
	 * Delete {@link QuestionSession} in QUESTION_SESSION table </br>
	 * Delete {@link Question} in QUESTION table</br>
	 * 
	 * @param httpExchange
	 * @param parameters contains: </br>
	 * 	{@link QuestionDAO#ID}
	 */
	public void doDelete(HttpExchange httpExchange, Map<String,Object> parameters) ;
	/**
	 * When receiving {@link URIRequest#SEND_QUESTION} from teacher application </br>
	 * keep the questionId and sessionId of request and wait for request {@link URIRequest#GET_LATEST_QUESTION} </br>
	 * from student application </br>
	 * @param httpExchange </br>
	 * @param parameters contains: </br>
	 * 	</li> {@link QuestionDAO#ID} : questionID
	 * 	</li> {@link QuestionSessionDAO#SESSION_ID} : current session ID
	 * 	</li> {@link UserDAO#USER_KEY}
	 */
	public void doSend(HttpExchange httpExchange, Map<String,Object> parameters) ;

	/**
	 * This request is sent by student application within a certain time </br>
	 * This request will receive a response if server receives a {@link URIRequest#SEARCH_QUESTION} </br>
	 * 
	 * @param httpExchange</br>
	 * @param parameters contains: </br>
	 * 	</li> QuestionSessionDAO.SESSION_ID
	 * 	</li> {@link UserDAO#USER_KEY}
	 */
	public void doGetLatest(HttpExchange httpExchange, Map<String,Object> parameters);
	/** 
	 * This method is called when teacher sends the request </br>
	 * {@link URIRequest#STOP_SEND_QUESTION} to server </br>
	 *  
	 * @param httpExchange </br>
	 * @param parameters contains : </br>
	 * 	</li> QuestionSessionDAO.SESSION_ID
	 */
	public void doStopSend(HttpExchange httpExchange, Map<String,Object> parameters) ;
}
