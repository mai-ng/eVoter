package evoter.server.http.request;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.impl.BeanDAOFactory;

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
	
	private static ISubjectRequest _this;
	
	private SubjectRequest(){}
	

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectRequest#doView(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	public  void doView(HttpExchange exchange, Map<String,Object> parameters){
		
		long id = Long.valueOf((String)parameters.get(SubjectDAO.ID));
		Subject subject = (Subject)((SubjectDAO) BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME)).findById(id).get(0);
		URIUtils.writeResponse(subject.toJSON(), exchange);
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectRequest#doGetAll(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public  void doGetAll(HttpExchange exchange, Map<String,Object> parameters){
		
		//long id = Long.valueOf(parameters.get(UserSubjectDAO.USER_ID));
		String userKey = (String)parameters.get(UserDAO.USER_KEY);
		Long id = UserValidation.getUserIdFromUserKey(userKey);
		UserSubjectDAO userSubjectDao = (UserSubjectDAO)BeanDAOFactory.getBean(UserSubjectDAO.BEAN_NAME);
		List<UserSubject> usList = userSubjectDao.findByUserId(id);
		
		JSONArray jsArray = new JSONArray();
		for (UserSubject us : usList){
			Subject subject = (Subject)((SubjectDAO) BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME)).findById(us.getSubjectId()).get(0);
			jsArray.add(subject.toJSON());
		}
		System.out.println("SUBJECT : " + jsArray.toJSONString());
		URIUtils.writeResponse(jsArray.toJSONString(), exchange);
		
	}
	
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
	 *  </li> {@link SubjectDAO.ID}
	 *  </li> {@link UserDAO#USER_KEY}
	 */
	public  void doDelete(HttpExchange exchange, Map<String,Object> parameters){
		
		try{
			
			long subjectId = Long.valueOf((String)parameters.get(SubjectDAO.ID));
			SubjectDAO subjectDAO = (SubjectDAO)BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME);
			UserSubjectDAO userSubjectDAO = (UserSubjectDAO)BeanDAOFactory.getBean(UserSubjectDAO.BEAN_NAME);
			SessionDAO sessionDAO = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
			SessionUserDAO sessionUserDAO = (SessionUserDAO)BeanDAOFactory.getBean(SessionUserDAO.BEAN_NAME);
			QuestionSessionDAO questionSessionDAO = (QuestionSessionDAO)BeanDAOFactory.getBean(QuestionSessionDAO.BEAN_NAME);
			StatisticsDAO statisticsDAO = (StatisticsDAO)BeanDAOFactory.getBean(StatisticsDAO.BEAN_NAME);
			
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

		SubjectDAO subjectDao = (SubjectDAO)BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME);
		List<Subject> subjects = subjectDao.findByProperty(parameters.keySet().toArray(new String[]{}), parameters.values().toArray());
		JSONArray jsArray = new JSONArray();
		for (Subject subject : subjects){
			jsArray.add(subject.toJSON());
		}
		URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
	}
	
	public static ISubjectRequest getInstance(){
		if(_this==null){
			_this = new SubjectRequest();
		}
		return _this;
	}

}
