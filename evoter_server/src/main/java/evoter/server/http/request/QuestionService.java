package evoter.server.http.request;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;

import evoter.server.http.request.interfaces.IQuestionService;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.QuestionTypeDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.QuestionSession;
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

/**
 * Process all {@link Question} requests sent by client applications </br>
 * 
 * @author btdiem </br>
 *
 */
@Service
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class QuestionService implements IQuestionService{
	
	//@Autowired
	private AnswerDAO answerDAO;
	//@Autowired
	private QuestionDAO questionDAO;
	//@Autowired
	private QuestionSessionDAO questionSessionDAO;
	//@Autowired
	private QuestionTypeDAO questionTypeDAO;
	//@Autowired
	private UserDAO userDAO;
	
	public AnswerDAO getAnswerDAO() {
		return answerDAO;
	}

	public void setAnswerDAO(AnswerDAO answerDAO) {
		this.answerDAO = answerDAO;
	}

	public QuestionDAO getQuestionDAO() {
		return questionDAO;
	}

	public void setQuestionDAO(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	public QuestionSessionDAO getQuestionSessionDAO() {
		return questionSessionDAO;
	}

	public void setQuestionSessionDAO(QuestionSessionDAO questionSessionDAO) {
		this.questionSessionDAO = questionSessionDAO;
	}

	public QuestionTypeDAO getQuestionTypeDAO() {
		return questionTypeDAO;
	}

	public void setQuestionTypeDAO(QuestionTypeDAO questionTypeDAO) {
		this.questionTypeDAO = questionTypeDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	//This value is updated when receiving a /send_question request 
	private  Map<Long,Long> mapSentQuestion = new HashMap<Long, Long>();

	/**
	 * This method will response a list of {@link Question} and {@link Answer} when server </br>
	 * receives request {@link URIRequest#GET_ALL_QUESTION} </br>
	 * 
	 * @param httpExchange </br>
	 * @param parameters contains : </br>
	 * 	</li> QuestionSessionDAO.SESSION_ID </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 */
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Rollback(false)
	public  Object doGetAll(Map<String,Object> parameters) {
		
		try{
			
			long sessionId = (Long.valueOf((String)parameters.get(QuestionSessionDAO.SESSION_ID)));
			List<QuestionSession> questionSessionList = questionSessionDAO.findBySessionId(sessionId);
			JSONArray response = new JSONArray();
			
			for (QuestionSession questionSession : questionSessionList){
				List<Question> questionList = questionDAO.
						findByProperty(
								new String[]{QuestionDAO.ID, QuestionDAO.PARENT_ID}, 
								new Object[]{questionSession.getQuestionId(), 0});//questionDao.findById(questionSession.getQuestionId());
				for (Question question : questionList){
					
					JSONObject object = question.toJSON();
					object.put(QuestionSessionDAO.SESSION_ID, questionSession.getSessionId());
					
					JSONArray answers = getAnswersOfQuestion(question.getId());
					
					List<Question> subQuestionList = questionDAO.findByParentId(question.getId());
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
					response.add(object);
				}
				
			}
			System.out.println("QUESTION : " + response.toJSONString());
			//URIUtils.writeResponse(jsArray, httpExchange);
			return response;
			
		}catch(Exception e){
			
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Rollback(false)
	public  Object doView(Map<String,Object> parameters) {
		
		try{
			long questionId = (Long.valueOf((String)parameters.get(QuestionDAO.ID)));
			List<Question> questionList = questionDAO.findById(questionId);
			JSONArray response = new JSONArray();
			if (questionList != null && !questionList.isEmpty()){
				
				for (Question question : questionList){
					//JSONObject jsObject = question.toJSON();
					question.toJSON().put("answers", getAnswersOfQuestion(question.getId()));
					response.add(question.toJSON());
				}
			}
			//URIUtils.writeResponse(jsArray, httpExchange);
			return response;
			
		}catch(Exception e){
			System.err.println(e);
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	/**
	 * 
	 * @param questionId
	 * @return a {@link JSONArray} 
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Rollback(false)
	public  JSONArray getAnswersOfQuestion(long questionId){
		
		JSONArray arrays = new JSONArray();
		List<Answer> answers = (List<Answer>)answerDAO.findByQuestionId(questionId);
		if (answers != null && !answers.isEmpty()){
			
			for (Answer answer : answers){
				arrays.add(answer.toJSON());
			}
			
		}
		return arrays;
		
		
	}
	
	/**
	 * Insert {@link Question} object to QUESTION table </br>
	 * Insert {@link QuestionSession} object to QUESTION_SESSION table </br>
	 * Insert {@link Answer} object to ANSWER table </br>
	 * 
	 * 
	 * @param httpExchange
	 * @param parameters contains: </br>
	 * 	</li> {@link QuestionDAO#QUESTION_TEXT} is a string [] </br>
	 * 	</li> {@link QuestionDAO#QUESTION_TYPE_ID} </br>
	 *  </li> {@link QuestionDAO#CREATION_DATE} </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 *  </li> {@link QuestionSessionDAO#SESSION_ID} </br>
	 *  </li> {@link AnswerDAO#ANSWER_TEXT}  is a string[] </br>
	 *  
	 */
	@Override
	public  Object doCreate(Map<String, Object> parameters) {
		
		try{
			
			String[] questionTexts = (String[])parameters.get(QuestionDAO.QUESTION_TEXT);
			long questionTypeId = (Long.valueOf((String)parameters.get(QuestionDAO.QUESTION_TYPE_ID)));
			Timestamp creationDate = Timestamp.valueOf((String)parameters.get(QuestionDAO.CREATION_DATE));
			String userKey = (String)parameters.get(UserDAO.USER_KEY);
			long userId = UserValidation.getUserIdFromUserKey(userKey);
			long sessionId = (Long.valueOf((String)parameters.get(QuestionSessionDAO.SESSION_ID)));
			String[] answerTexts = null;
			/**
			 * If the question is yes/no, agree/disagree, there is no answer anymore
			 */
			if (parameters.containsKey(AnswerDAO.ANSWER_TEXT)){
				answerTexts = (String[])parameters.get(AnswerDAO.ANSWER_TEXT);
			}

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
			questionId =  questionDAO.insert(question);
			/**
			 * if this is match type question, this question is a parent </br>
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
					questionDAO.insert(question);
				}
			}
			/**
			 * Create Answer object and insert it to ANSWER table
			 */
			if (answerTexts != null){
				for (String answerText : answerTexts){
					Answer answer = new Answer(questionId, answerText);
					answerDAO.insert(answer);
				}
			}

			/**
			 * Create SessionQuestion object and insert it to QUESTION_SESSION table
			 */
			QuestionSession questionSession = new QuestionSession(questionId, sessionId);
			questionSessionDAO.insert(questionSession);
			
		}catch(Exception e){
			
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}
		
		return URIRequest.SUCCESS_MESSAGE;
		//URIUtils.writeSuccessResponse(httpExchange);
		
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
	
	
	@Override
	public  Object doDelete(Map<String,Object> parameters) {
		
		long questionId = (Long.valueOf((String)parameters.get(QuestionDAO.ID)));
		
		try{
			// ANSWER table
//			answerDAO.deleteByQuestionId(questionId);
			// QUESTION_SESSION table
//			questionSessionDAO.deleteByQuestionId(questionId);
			// QUESTION table
			questionDAO.deleteById(questionId);
			
			return URIRequest.SUCCESS_MESSAGE;
			//URIUtils.writeSuccessResponse(httpExchange);
			
		}catch(Exception e){
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
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
	@Override
	@Rollback(false)
	public  Object doSend(Map<String,Object> parameters) {
		
		try{
			
			long questionId = (Long.valueOf((String)parameters.get(QuestionDAO.ID)));
			long sessionId = (Long.valueOf((String)parameters.get(QuestionSessionDAO.SESSION_ID)));
			//mapSentQuestion.put(sessionId, questionId);
			addSentQuestion(sessionId, questionId);
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			
			e.printStackTrace();
			return URIRequest.FAILURE_MESSAGE;
		}
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
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Rollback(false)
	public  Object doGetLatest(Map<String, Object> parameters) {
		
		try{
			
			JSONArray response = new JSONArray();
			long sessionId = (Long.valueOf((String)parameters.get(QuestionSessionDAO.SESSION_ID)));
			//if (mapSentQuestion.containsKey(sessionId)){
			if (canSendQuestion(sessionId)){
				long questionId = mapSentQuestion.get(sessionId);
				Question question = questionDAO.findById(questionId).get(0);
				//RE-WORK 
				JSONObject object = question.toJSON();
				object.put("answers", getAnswersOfQuestion(question.getId()));
				response.add(object);
				
			}
			
			//URIUtils.writeResponse(response, httpExchange);
			return response;
			
		}catch(Exception e){
			System.err.println(e);
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
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
	
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doStopSend(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	@Rollback(false)
	public  Object doStopSend(Map<String,Object> parameters) {
		try{
			
			long sessionId = (Long.valueOf((String)parameters.get(QuestionSessionDAO.SESSION_ID)));
			//mapSentQuestion.remove(sessionId);
			removeSentQuestion(sessionId);
			//URIUtils.writeSuccessResponse(httpExchange);
			return URIRequest.SUCCESS_MESSAGE;

		}catch(Exception e){
			
			e.printStackTrace();
			return URIRequest.FAILURE_MESSAGE;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doEdit(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public Object doEdit(Map<String, Object> parameters) {
		
		String questionText = (String)parameters.get(QuestionDAO.QUESTION_TEXT);
		long questionId = (Long.valueOf((String)parameters.get(QuestionDAO.ID)));
		
		try{
			
			List<Question> questions = questionDAO.findById(questionId);
			if (questions != null && !questions.isEmpty()){
				
				Question question = questions.get(0);
				question.setQuestionText(questionText);
				questionDAO.update(question);
			}
			//URIUtils.writeSuccessResponse(httpExchange);
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			
			e.printStackTrace();
			return URIRequest.FAILURE_MESSAGE;
			//URIUtils.writeFailureResponse(httpExchange);
		}
		
		
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#addSentQuestion(long, long)
	 */
	@Override
	public void addSentQuestion(long sessionId, long questionId) {
		
		mapSentQuestion.put(sessionId, questionId);
		
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#removeSentQuestion(long)
	 */
	@Override
	public void removeSentQuestion(long sessionId) {
		
		mapSentQuestion.remove(sessionId);
		
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#canSendQuestion(long)
	 */
	@Override
	public boolean canSendQuestion(long sessionId) {
		
		return mapSentQuestion.containsKey(sessionId);
	}


	
	
	
}
