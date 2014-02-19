package evoter.server.http.request;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


import evoter.server.dao.impl.AnswerDAOImpl;
import evoter.server.dao.impl.QuestionDAOImpl;
import evoter.server.dao.impl.QuestionSessionDAOImpl;
import evoter.server.dao.impl.QuestionTypeDAOImpl;
import evoter.server.dao.impl.UserDAOImpl;
import evoter.server.http.request.interfaces.IAnswerService;
import evoter.server.http.request.interfaces.IQuestionService;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.QuestionTypeDAO;
import evoter.share.dao.UserDAO;
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
	
	static Logger log = Logger.getLogger(QuestionService.class);
	/**
	 * Define getter/setter of {@link AnswerDAOImpl} bean
	 */
	private AnswerDAO answerDAO;
	/**
	 * Define getter/setter of {@link QuestionDAOImpl} bean 
	 */
	private QuestionDAO questionDAO;
	/**
	 * Define getter/setter of {@link QuestionSessionDAOImpl} bean
	 */
	private QuestionSessionDAO questionSessionDAO;
	/**
	 * Define getter/setter of {@link QuestionTypeDAOImpl} bean
	 */
	private QuestionTypeDAO questionTypeDAO;
	/**
	 * Define getter/setter of {@link UserDAOImpl} </br>
	 */
	private UserDAO userDAO;
	
	public IAnswerService getAnswerService() {
		return answerService;
	}

	public void setAnswerService(IAnswerService answerService) {
		this.answerService = answerService;
	}
	/**
	 * Define getter/setter of {@link IAnswerService} bean
	 */
	private IAnswerService answerService;
	
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

	/**
	 * Map has key is session ID and and value is Question that is sending  of the current session </br>
	 */
	private  Map<Long,Question> mapSentQuestion = new HashMap<Long, Question>();

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doGetAll(java.util.Map)
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
					
					JSONArray answers = (JSONArray)answerService.doGetAllAnswer(question.getId());//getAnswersOfQuestion(question.getId());
					
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
			return response;
			
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doView(java.util.Map)
	 */
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
					question.toJSON().put("answers", answerService.doGetAllAnswer(question.getId()));
					response.add(question.toJSON());
				}
			}
			return response;
			
		}catch(Exception e){
			System.err.println(e);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	/**
	 * 
	 * @param questionId
	 * @return a {@link JSONArray} 
	 *
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
		
		
	}*/
	
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doCreate(java.util.Map)
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
			/**
			 * If the question is yes/no, agree/disagree, there is no answer anymore
			 */
			if (parameters.containsKey(AnswerDAO.ANSWER_TEXT)){
				String[] answerTexts = (String[])parameters.get(AnswerDAO.ANSWER_TEXT);
				answerService.doCreate(questionId, answerTexts);
			}

			/**
			 * Create SessionQuestion object and insert it to QUESTION_SESSION table
			 */
			QuestionSession questionSession = new QuestionSession(questionId, sessionId);
			questionSessionDAO.insert(questionSession);
			
		}catch(Exception e){
			
			log.error(e);
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}
		
		return URIRequest.SUCCESS_MESSAGE;
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doDelete(java.util.Map)
	 */
	@Override
	public  Object doDelete(Map<String,Object> parameters) {
		
		long questionId = (Long.valueOf((String)parameters.get(QuestionDAO.ID)));
		
		try{

			questionDAO.deleteById(questionId);
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doSend(java.util.Map)
	 */
	@Override
	@Transactional
	@Rollback(true)
	public  Object doSend(Map<String,Object> parameters) {
		
		try{
			
			long questionId = (Long.valueOf((String)parameters.get(QuestionDAO.ID)));
			List<Question> questionList = questionDAO.findById(questionId);
			if (questionList != null && !questionList.isEmpty()){
				
				Question question = questionList.get(0);
				question.setStatus(1);
				questionDAO.update(question);
				
				long sessionId = (Long.valueOf((String)parameters.get(QuestionSessionDAO.SESSION_ID)));
				addSentQuestion(sessionId, question);
				
				return URIRequest.SUCCESS_MESSAGE;
			}
			return URIRequest.QUESTION_NOT_EXIST;

		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doGetLatest(java.util.Map)
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
				//long questionId = mapSentQuestion.get(sessionId);
				//Question question = questionDAO.findById(questionId).get(0);
				Question question = mapSentQuestion.get(sessionId);
				//RE-WORK 
				JSONObject object = question.toJSON();
				//object.put("answers", getAnswersOfQuestion(question.getId()));
				object.put("answers", answerService.doGetAllAnswer(question.getId()));
				response.add(object);
				
			}
			
			return response;
			
		}catch(Exception e){
			System.err.println(e);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doStopSend(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	@Rollback(false)
	public  Object doStopSend(Map<String,Object> parameters) {
		try{
			
			long sessionId = (Long.valueOf((String)parameters.get(QuestionSessionDAO.SESSION_ID)));
			
			Question question = removeSentQuestion(sessionId);
			question.setStatus(2);
			questionDAO.update(question);
			
			return URIRequest.SUCCESS_MESSAGE;

		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#doEdit(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public Object doEdit(Map<String, Object> parameters) {
		
		try{
			String questionText = (String)parameters.get(QuestionDAO.QUESTION_TEXT);
			long questionId = (Long.valueOf((String)parameters.get(QuestionDAO.ID)));

			List<Question> questions = questionDAO.findById(questionId);
			if (questions != null && !questions.isEmpty()){
				
				Question question = questions.get(0);
				question.setQuestionText(questionText);
				questionDAO.update(question);
			}
			
			/**
			 * Create Answer object and insert it to ANSWER table
			 */
			if (parameters.containsKey(AnswerDAO.ANSWER_TEXT)){
				String[] answerTexts = (String[]) parameters.get(AnswerDAO.ANSWER_TEXT);
				//delete the old answer
				answerService.doDelete(questionId);
				// add new one
				answerService.doCreate(questionId, answerTexts);

			}

				
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
		
		
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#addSentQuestion(long, long)
	 */
	@Override
	public void addSentQuestion(long sessionId, Question question) {
		
		mapSentQuestion.put(sessionId, question);
		
	}
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.IQuestionService#removeSentQuestion(long)
	 */
	@Override
	public Question removeSentQuestion(long sessionId) {
		
		return mapSentQuestion.remove(sessionId);
		
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
