package evoter.server.http.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.AnswerDAO;
import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.QuestionDAO;
import evoter.server.http.URIUtils;
import evoter.server.model.Answer;
import evoter.server.model.Question;

public class QuestionRequest {

	//This value is updated when receiving a /send_question request 
	private static Map<Long,Long> mapSentQuestion = new HashMap<Long, Long>();
	

	@SuppressWarnings("unchecked")
	public static void doGetAll(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long sessionId = Long.parseLong(parameters.get(QuestionDAO.SESSION_ID));
		QuestionDAO questionDao = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
		List<Question> questionList = questionDao.findBySessionId(sessionId);
		if (questionList != null && !questionList.isEmpty()){
			
			JSONArray jsArray = new JSONArray();
			for (Question question : questionList){
				JSONObject object = question.toJSON();
				object.put("answers", getAnswersOfQuestion(question.getId()));
				jsArray.add(object.toJSONString());			
			}
			System.out.println("question:" + jsArray.toJSONString());
			URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
			
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}
		
	}

	@SuppressWarnings("unchecked")
	public static void doView(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long questionId = Long.parseLong(parameters.get(QuestionDAO.ID));
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
	public static String getAnswersOfQuestion(long questionId){
		
		List<Answer> answers = (List<Answer>) ((AnswerDAO)BeanDAOFactory.getBean(AnswerDAO.BEAN_NAME)).findByQuestionId(questionId);
		if (answers != null && !answers.isEmpty()){
			JSONArray arrays = new JSONArray();
			for (Answer answer : answers){
				arrays.add(answer.toJSON());
			}
			return arrays.toJSONString();
		}else{
			return null;
		}
		
	}
	public static void doSave(HttpExchange httpExchange,
			Map<String, String> parameters) {
		// TODO Auto-generated method stub
		
	}

	public static void doDelete(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long questionId = Long.parseLong(parameters.get(QuestionDAO.ID));
		QuestionDAO questionDao = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
		questionDao.deleteById(questionId);
		((AnswerDAO)BeanDAOFactory.getBean(AnswerDAO.BEAN_NAME)).deleteByQuestionId(questionId);
		URIUtils.writeSuccessResponse(httpExchange);
		
	}

	public static void doSend(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long questionId = Long.parseLong(parameters.get(QuestionDAO.ID));
		long sessionId = Long.parseLong(parameters.get(QuestionDAO.SESSION_ID));
		mapSentQuestion.put(sessionId, questionId);
	}

	@SuppressWarnings("unchecked")
	public static void doGetLatest(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long sessionId = Long.parseLong(parameters.get(QuestionDAO.SESSION_ID));
		if (mapSentQuestion.containsKey(sessionId)){
			long questionId = mapSentQuestion.get(sessionId);
			QuestionDAO questionDao = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
			Question question = questionDao.findById(questionId).get(0);
			question.toJSON().put("answers", getAnswersOfQuestion(question.getId()));
			URIUtils.writeResponse(question.toJSON().toJSONString(), httpExchange);
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}
		
	}

	public static void doStopSend(HttpExchange httpExchange,
			Map<String, String> parameters) {
		long sessionId = Long.parseLong(parameters.get(QuestionDAO.SESSION_ID));
		mapSentQuestion.remove(sessionId);
		URIUtils.writeSuccessResponse(httpExchange);
	} 

	
	
	
}
