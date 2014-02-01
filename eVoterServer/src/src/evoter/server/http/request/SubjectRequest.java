package evoter.server.http.request;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import com.sun.net.httpserver.HttpExchange;

import evoter.server.http.URIRequest;
import evoter.server.http.URIUtils;
import evoter.server.http.request.interfaces.ISubjectRequest;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.StatisticsDAO;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.Session;
import evoter.share.model.Subject;
import evoter.share.model.UserSubject;
import evoter.share.utils.UserValidation;

/**
 * Process all {@link Subject} requests sent by client applications </br>
 * 
 * @author btdiem
 *
 */
public class SubjectRequest implements ISubjectRequest{
	

	private QuestionSessionDAO questionSessionDAO;
	private SessionDAO sessionDAO;
	private SessionUserDAO sessionUserDAO;
	private StatisticsDAO statisticsDAO;
	private SubjectDAO subjectDAO;
	private UserDAO userDAO;
	private UserSubjectDAO userSubjectDAO;
	
	

	public QuestionSessionDAO getQuestionSessionDAO() {
		return questionSessionDAO;
	}

	public void setQuestionSessionDAO(QuestionSessionDAO questionSessionDAO) {
		this.questionSessionDAO = questionSessionDAO;
	}

	public SessionDAO getSessionDAO() {
		return sessionDAO;
	}

	public void setSessionDAO(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}

	public SessionUserDAO getSessionUserDAO() {
		return sessionUserDAO;
	}

	public void setSessionUserDAO(SessionUserDAO sessionUserDAO) {
		this.sessionUserDAO = sessionUserDAO;
	}

	public StatisticsDAO getStatisticsDAO() {
		return statisticsDAO;
	}

	public void setStatisticsDAO(StatisticsDAO statisticsDAO) {
		this.statisticsDAO = statisticsDAO;
	}

	public SubjectDAO getSubjectDAO() {
		return subjectDAO;
	}

	public void setSubjectDAO(SubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserSubjectDAO getUserSubjectDAO() {
		return userSubjectDAO;
	}

	public void setUserSubjectDAO(UserSubjectDAO userSubjectDAO) {
		this.userSubjectDAO = userSubjectDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectRequest#doView(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	public  void doView(HttpExchange exchange, Map<String,Object> parameters){
		
		try{
			
			long id = Long.valueOf((String)parameters.get(SubjectDAO.ID));
			Subject subject = (Subject)subjectDAO.findById(id).get(0);
			URIUtils.writeResponse(subject.toJSON(), exchange);
			
		}catch(Exception e){
			System.err.println(e);
			URIUtils.writeFailureResponse(exchange);
		}

		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectRequest#doGetAll(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public  void doGetAll(HttpExchange exchange, Map<String,Object> parameters){
		
		try{
			
			//long id = Long.valueOf(parameters.get(UserSubjectDAO.USER_ID));
			String userKey = (String)parameters.get(UserDAO.USER_KEY);
			Long id = UserValidation.getUserIdFromUserKey(userKey);
			List<UserSubject> usList = userSubjectDAO.findByUserId(id);
			
			JSONArray jsArray = new JSONArray();
			for (UserSubject us : usList){
				Subject subject = (Subject)subjectDAO.findById(us.getSubjectId()).get(0);
				jsArray.add(subject.toJSON());
			}
			System.out.println("SUBJECT : " + jsArray.toJSONString());
			URIUtils.writeResponse(jsArray.toJSONString(), exchange);
			
		}catch(Exception e){
			System.err.println(e);
			URIUtils.writeFailureResponse(exchange);
		}

		
	}
	
	/**
	 * When deleting a subject: </br>
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
	 *  </li> {@link SubjectDAO.ID}
	 *  </li> {@link UserDAO#USER_KEY}
	 *  TESTED
	 */
	public  void doDelete(HttpExchange exchange, Map<String,Object> parameters){
		
		try{
			
			long subjectId = Long.valueOf((String)parameters.get(SubjectDAO.ID));
			
//			List<UserSubject> userSubjectList = userSubjectDAO.findBySubjectId(subjectId);
//			for (UserSubject userSubject : userSubjectList){
			//get all session of subject in SESSION table
			List<Session> sessionList = sessionDAO.findBySubjectId(subjectId);
			for (Session session : sessionList){
				// for each session
				//    delete all session records in SESSION_USER table
				//    delete all session records in STATISTICS table
				//    delete all session records in SESSION_QUESTION table
				//  
				sessionUserDAO.deleteBySessionId(session.getId());
				statisticsDAO.deleteBySessionId(session.getId());
				questionSessionDAO.deleteBySessionId(session.getId());
			}
			sessionDAO.deleteBySubjectId(subjectId);
			//remove session records in USER_SUBJECT table ==>ok
			userSubjectDAO.deleteBySubjectId(subjectId);	
			subjectDAO.deleteById(subjectId);
			
			URIUtils.writeSuccessResponse(exchange);
				
		}catch(Exception e){
		
			System.out.println("delete subject error : " + e);
			URIUtils.writeFailureResponse(exchange);
		}
		
	}

	/**
	 * Response client a list of {@link Subject} matching search conditions when receiving </br>
	 * {@link URIRequest#SEARCH_SUBJECT} </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains </br>
	 *  </li> {@link SubjectDAO#TITLE} </br>
	 *  </li> {@link SubjectDAO#CREATION_DATE} </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 */
	@SuppressWarnings("unchecked")
	public  void doSearch(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		try{
			
			List<Subject> subjects = subjectDAO.findByProperty(parameters.keySet().toArray(new String[]{}), parameters.values().toArray());
			JSONArray jsArray = new JSONArray();
			for (Subject subject : subjects){
				jsArray.add(subject.toJSON());
			}
			URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
			
		}catch(Exception e){
			URIUtils.writeFailureResponse(httpExchange);
			System.err.println(e);
		}

	}
	
}
