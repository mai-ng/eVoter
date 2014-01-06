package evoter.server.http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.QuestionDAO;
import evoter.server.http.URIUtils;
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
				jsArray.add(question.toJSONString());
			}
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
				jsArray.add(question.toJSONString());
			}
			URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
			
		}else{
			URIUtils.writeFailureResponse(httpExchange);
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
		URIUtils.writeSuccessResponse(httpExchange);
		
	}

	public static void doSend(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long questionId = Long.parseLong(parameters.get(QuestionDAO.ID));
		long sessionId = Long.parseLong(parameters.get(QuestionDAO.SESSION_ID));
		mapSentQuestion.put(sessionId, questionId);
	}

	public static void doGetLatest(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long sessionId = Long.parseLong(parameters.get(QuestionDAO.SESSION_ID));
		if (mapSentQuestion.containsKey(sessionId)){
			long questionId = mapSentQuestion.get(sessionId);
			QuestionDAO questionDao = (QuestionDAO)BeanDAOFactory.getBean(QuestionDAO.BEAN_NAME);
			Question question = questionDao.findById(questionId).get(0);
			URIUtils.writeResponse(question.toJSONString(), httpExchange);
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
