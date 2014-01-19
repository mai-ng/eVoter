package evoter.server.http.request;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.impl.BeanDAOFactory;
import evoter.server.http.URIRequest;
import evoter.server.http.URIUtils;
import evoter.server.http.request.interfaces.IQuestionRequest;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.QuestionTypeDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.QuestionSession;
import evoter.share.utils.UserValidation;

/**
 * Process all {@link Question} requests sent by client applications </br>
 * 
 * @author btdiem </br>
 *
 */

public class QuestionRequest implements IQuestionRequest{
	
	private static IQuestionRequest _this;
	
	private QuestionRequest(){}

	//This value is updated when receiving a /send_question request 
	private  Map<Long,Long> mapSentQuestion = new HashMap<Long, Long>();

	/**
	 * This method will response a list of {@link Question} and {@link Answer} when server </br>
	 * receives request {@link URIRequest#GET_ALL_QUESTION} </br>
	 * 
	 * @param httpExchange </br>
	 * @param parameters contains : </br>
	 * 	</li> QuestionSessionDAO.SESSION_ID
	 *  </li> {@link UserDAO#USER_KEY}
	 */
	@SuppressWarnings("unchecked")
	public  void doGetAll(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long sessionId = Long.parseLong((String)parameters.get(QuestionSessionDAO.SESSION_ID));
		
		QuestionSessionDAO quesSesDao = (QuestionSessionDAO)BeanDAOFactory.getBean(QuestionSessionDAO.BEAN_NAME);
		
		List<QuestionSession> questionSessionList = quesSesDao.findBySessionId(sessionId);
		QuestionDAO questionDao = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
		JSONArray jsArray = new JSONArray();
		
		for (QuestionSession questionSession : questionSessionList){
			List<Question> questionList = questionDao.findByProperty(new String[]{QuestionDAO.ID, QuestionDAO.PARENT_ID}, new Object[]{questionSession.getQuestionId(), 0});//questionDao.findById(questionSession.getQuestionId());
			for (Question question : questionList){
				
				JSONObject object = question.toJSON();
				object.put(QuestionSessionDAO.SESSION_ID, questionSession.getSessionId());
				
				JSONArray answers = getAnswersOfQuestion(question.getId());
				
				List<Question> subQuestionList = questionDao.findByParentId(question.getId());
				if (subQuestionList != null && !subQuestionList.isEmpty()){
					JSONArray jsPart1 = new JSONArray();
					for (Question subQuestion : subQuestionList){
						jsPart1.add(subQuestion.toJSON());
					}
					object.put(Question.COL1, jsPart1);
					object.put(Question.COL2,  answers);
					
				}else{ //if
					object.put(Question.COL1,  answers);
				}
				jsArray.add(object);
			}
			
		}
		System.out.println("QUESTION : " + jsArray.toJSONString());
		URIUtils.writeResponse(jsArray, httpExchange);
		
	}

	@SuppressWarnings("unchecked")
	public  void doView(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long questionId = Long.parseLong((String)parameters.get(QuestionDAO.ID));
		QuestionDAO questionDao = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
		List<Question> questionList = questionDao.findById(questionId);
		if (questionList != null && !questionList.isEmpty()){
			JSONArray jsArray = new JSONArray();
			for (Question question : questionList){
				//JSONObject jsObject = question.toJSON();
				question.toJSON().put("answers", getAnswersOfQuestion(question.getId()));
				jsArray.add(question.toJSON().toJSONString());
			}
			URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
			
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}		
	}

	/**
	 * 
	 * @param questionId
	 * @return a {@link JSONArray} 
	 */
	@SuppressWarnings("unchecked")
	public  JSONArray getAnswersOfQuestion(long questionId){
		
		List<Answer> answers = (List<Answer>) ((AnswerDAO)BeanDAOFactory.getBean(AnswerDAO.BEAN_NAME))
				.findByQuestionId(questionId);
		if (answers != null && !answers.isEmpty()){
			JSONArray arrays = new JSONArray();
			for (Answer answer : answers){
				arrays.add(answer.toJSON());
			}
			return arrays;
		}else{
			return null;
		}
		
	}
	
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
	public  void doCreate(HttpExchange httpExchange,
			Map<String, Object> parameters) {
		
		String[] questionTexts = (String[])parameters.get(QuestionDAO.QUESTION_TEXT);
		long questionTypeId = Long.valueOf((String)parameters.get(QuestionDAO.QUESTION_TYPE_ID));
		Date creationDate = Date.valueOf((String)parameters.get(QuestionDAO.CREATION_DATE));
		String userKey = (String)parameters.get(UserDAO.USER_KEY);
		long userId = UserValidation.getUserIdFromUserKey(userKey);
		long sessionId = Long.valueOf((String)parameters.get(QuestionSessionDAO.SESSION_ID));
		String[] answerTexts = (String[])parameters.get(AnswerDAO.ANSWER_TEXT);
		
		try{
			
			QuestionDAO questionDao = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
			Question question = null;
			long parentId = 0;
			int index = 0;
			long questionId = 0;
			
			//insert the 1st question of array
			question = new Question(questionTypeId, 
					userId, 
					questionTexts[index++], 
					creationDate, 
					parentId);
			questionId =  questionDao.insert(question);
			/**
			 * if this is match type question, the inserted question id is parent id </br>
			 * continue inserting the sub questions to the database </br>
			 */
			if (questionTypeId == QuestionTypeDAO.MATCH){
				parentId = questionId;
				for (; index < questionTexts.length; index++){
					question = new Question(questionTypeId, 
							userId, 
							questionTexts[index++], 
							creationDate, 
							parentId);
					questionId =  questionDao.insert(question);
				}
			}
			/**
			 * Create Answer object and insert it to ANSWER table
			 */
			AnswerDAO answerDAO = (AnswerDAO)BeanDAOFactory.getBean(AnswerDAO.BEAN_NAME);
			for (String answerText : answerTexts){
				Answer answer = new Answer(questionId, answerText);
				answerDAO.insert(answer);
			}
			/**
			 * Create SessionQuestion object and insert it to QUESTION_SESSION table
			 */
			QuestionSession questionSession = new QuestionSession(questionId, sessionId);
			QuestionSessionDAO questionSessionDAO = (QuestionSessionDAO)BeanDAOFactory.getBean(QuestionSessionDAO.BEAN_NAME);
			questionSessionDAO.insert(questionSession);
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}
		
		URIUtils.writeSuccessResponse(httpExchange);
		
	}

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
	public  void doDelete(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long questionId = Long.parseLong((String)parameters.get(QuestionDAO.ID));
		
		try{
			
			QuestionDAO questionDAO = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
			AnswerDAO answerDAO = (AnswerDAO)BeanDAOFactory.getBean(AnswerDAO.BEAN_NAME);
			QuestionSessionDAO questionSessionDAO = (QuestionSessionDAO)BeanDAOFactory.getBean(QuestionSessionDAO.TABLE_NAME);
			
			// ANSWER table
			answerDAO.deleteByQuestionId(questionId);
			// QUESTION_SESSION table
			questionSessionDAO.deleteByQuestionId(questionId);
			// QUESTION table
			questionDAO.deleteById(questionId);
			
			URIUtils.writeSuccessResponse(httpExchange);
			
		}catch(Exception e){
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}
		
	}

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
	public  void doSend(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long questionId = Long.parseLong((String)parameters.get(QuestionDAO.ID));
		long sessionId = Long.parseLong((String)parameters.get(QuestionSessionDAO.SESSION_ID));
		mapSentQuestion.put(sessionId, questionId);
	}

	/**
	 * This request is sent by student application within a certain time </br>
	 * This request will receive a response if server receives a {@link URIRequest#SEARCH_QUESTION} </br>
	 * 
	 * @param httpExchange</br>
	 * @param parameters contains: </br>
	 * 	</li> QuestionSessionDAO.SESSION_ID
	 * 	</li> {@link UserDAO#USER_KEY}
	 */
	@SuppressWarnings("unchecked")
	public  void doGetLatest(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long sessionId = Long.parseLong((String)parameters.get(QuestionSessionDAO.SESSION_ID));
		if (mapSentQuestion.containsKey(sessionId)){
			long questionId = mapSentQuestion.get(sessionId);
			QuestionDAO questionDao = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
			Question question = questionDao.findById(questionId).get(0);
			//RE-WORK 
			question.toJSON().put("answers", getAnswersOfQuestion(question.getId()));
			URIUtils.writeResponse(question.toJSON().toJSONString(), httpExchange);
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}
		
	}

	/** 
	 * This method is called when teacher sends the request </br>
	 * {@link URIRequest#STOP_SEND_QUESTION} to server </br>
	 *  
	 * @param httpExchange </br>
	 * @param parameters contains : </br>
	 * 	</li> QuestionSessionDAO.SESSION_ID
	 */
	public  void doStopSend(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		long sessionId = Long.parseLong((String)parameters.get(QuestionSessionDAO.SESSION_ID));
		mapSentQuestion.remove(sessionId);
		URIUtils.writeSuccessResponse(httpExchange);
	}

	public static IQuestionRequest getInstance() {
		if (_this == null){
			_this = new QuestionRequest();
		}
		return _this;
	} 

	
	
	
}
