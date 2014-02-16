package evoter.server.http.request;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sun.net.httpserver.HttpExchange;

//import evoter.server.http.URIUtils;
import evoter.server.http.request.interfaces.ISubjectService;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.StatisticsDAO;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.model.UserSubject;
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

/**
 * Process all {@link Subject} requests sent by client applications </br>
 * 
 * @author btdiem
 *
 */
@Service
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class SubjectService implements ISubjectService{
	
	//@Autowired
	private QuestionSessionDAO questionSessionDAO;
	//@Autowired
	private SessionDAO sessionDAO;
	//@Autowired
	private SessionUserDAO sessionUserDAO;
	//@Autowired
	private StatisticsDAO statisticsDAO;
	//@Autowired
	private SubjectDAO subjectDAO;
	//@Autowired
	private UserDAO userDAO;
	//@Autowired
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
	@Override
	public  Object doView(Map<String,Object> parameters){
		
		try{
			
			
			long id = Long.valueOf((String)parameters.get(SubjectDAO.ID));
			Subject subject = (Subject)subjectDAO.findById(id).get(0);
			return subject.toJSON();
			//URIUtils.writeResponse(subject.toJSON(), exchange);
			
		}catch(Exception e){
			System.err.println(e);
			return URIRequest.FAILURE_MESSAGE;
			//URIUtils.writeFailureResponse(exchange);
		}

		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectRequest#doGetAll(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Rollback(false)
	public  Object doGetAll(Map<String,Object> parameters){
		
		try{
			
			//long id = Long.valueOf(parameters.get(UserSubjectDAO.USER_ID));
			String userKey = (String)parameters.get(UserDAO.USER_KEY);
			Long id = UserValidation.getUserIdFromUserKey(userKey);
			List<UserSubject> usList = userSubjectDAO.findByUserId(id);
			
			JSONArray response = new JSONArray();
			for (UserSubject us : usList){
				Subject subject = (Subject)subjectDAO.findById(us.getSubjectId()).get(0);
				response.add(subject.toJSON());
			}
			System.out.println("SUBJECT : " + response.toJSONString());
			//URIUtils.writeResponse(response.toJSONString(), exchange);
			return response;
			
		}catch(Exception e){
			System.err.println(e);
			//URIUtils.writeFailureResponse(exchange);
			return URIRequest.FAILURE_MESSAGE;
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
	@Override
	public  Object doDelete(Map<String,Object> parameters){
		
		try{
			
			long subjectId = Long.valueOf((String)parameters.get(SubjectDAO.ID));
			List<Subject> subject = subjectDAO.findById(subjectId);
			if (subject != null && !subject.isEmpty()){
				
				subjectDAO.deleteById(subjectId);
				return URIRequest.SUCCESS_MESSAGE;
				
			}else{
				
				return URIRequest.SUBJECT_NOT_EXIST_MESSAGE;
			}
			//get all session of subject in SESSION table
			//this is on delete cascade
/**			List<Session> sessionList = sessionDAO.findBySubjectId(subjectId);
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
*/				

			//URIUtils.writeSuccessResponse(exchange);
				
		}catch(Exception e){
		
			System.out.println("delete subject error : " + e);
			//URIUtils.writeFailureResponse(exchange);
			return URIRequest.FAILURE_MESSAGE;
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
	public  Object doSearch(Map<String,Object> parameters) {
		try{
			
			//remove user key out the search condition
			parameters.remove(UserDAO.USER_KEY);
			
			List<Subject> subjects = subjectDAO.
					findByProperty(parameters.keySet().toArray(new String[]{}), parameters.values().toArray());
			JSONArray response = new JSONArray();
			for (Subject subject : subjects){
				response.add(subject.toJSON());
			}
			//URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
			return response;
			
		}catch(Exception e){
			//URIUtils.writeFailureResponse(httpExchange);
			System.err.println(e);
			return URIRequest.FAILURE_MESSAGE;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectService#doGetUsersOfSubject(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object doGetUsersOfSubject(Map<String, Object> parameters) {
		
		JSONArray response = new JSONArray();
		try{
			
			long subjectId = Long.valueOf(parameters.get(SubjectDAO.ID).toString());
			List<UserSubject> userSubjectList = userSubjectDAO.
					findBySubjectId(subjectId);
			
			for (UserSubject userSubject : userSubjectList){
				List<User> userList = userDAO.findById(userSubject.getUserId());
				if (userList != null && !userList.isEmpty()){
					response.add(userList.get(0).toJSON());
				}
			}
			//URIUtils.writeResponse(response, httpExchange);
			return response;
			
		}catch(Exception e){
			
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectService#doEdit(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public Object doEdit(Map<String, Object> parameters) {
		
		try{
			
			long subjectId = Long.valueOf(parameters.get(SubjectDAO.ID).toString());
			String title = parameters.get(SubjectDAO.TITLE).toString();
			String[] mail_list = (String[])parameters.get(SubjectDAO.EMAIL_LIST);
			/**
			 * Search the subject if it exists in the database or not
			 */
			List<Subject> subjectList = subjectDAO.findById(subjectId);
			if (subjectList != null && !subjectList.isEmpty()){
				
				Subject subject = subjectList.get(0);
				subject.setTitle(title);
				
				subjectDAO.update(subject);
				
				/**
				 * Delete records of user_subject table of this subject
				 */
				userSubjectDAO.deleteBySubjectId(subjectId);
				/**
				 * Iterator email list, create new UserSubject records and insert them to user_subject table
				 */
				for (String email : mail_list){
					
					List<User> userList = userDAO.findByEmail(email);
					if (userList != null && !userList.isEmpty()){
						User user = userList.get(0);
						
						UserSubject userSubject = new UserSubject();
						userSubject.setSubjectId(subjectId);
						userSubject.setUserId(user.getId());
						
						userSubjectDAO.insert(userSubject);
					}
				}
				
				return URIRequest.SUCCESS_MESSAGE;
			}
			
			return URIRequest.SUBJECT_NOT_EXIST_MESSAGE;
			
		}catch(Exception e){
			
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectService#doCreate(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@Override
	public Object doCreate(Map<String, Object> parameters) {
		
		try{
			
			String title = parameters.get(SubjectDAO.TITLE).toString();
			Timestamp creationDate = Timestamp.valueOf(parameters.get(SubjectDAO.CREATION_DATE).toString());
			String[] mail_list = (String[])parameters.get(SubjectDAO.EMAIL_LIST);
			
			Subject subject = new Subject();
			subject.setCreationDate(creationDate);
			subject.setTitle(title);
			/**
			 * Insert a record to subject table and return a new id
			 */
			long subjectId = subjectDAO.insert(subject);
			/**
			 * Iterate email list and insert these records to user_subject table
			 */
			for (String email : mail_list){
				
				List<User> userList = userDAO.findByEmail(email);
				if (userList != null && !userList.isEmpty()){
					User user = userList.get(0);
					
					UserSubject userSubject = new UserSubject();
					userSubject.setSubjectId(subjectId);
					userSubject.setUserId(user.getId());
					
					userSubjectDAO.insert(userSubject);
				}
			}
			
			//URIUtils.writeSuccessResponse(httpExchange);
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			
			e.printStackTrace();
			//URIUtils.writeFailureResponse(httpExchange);
			return URIRequest.FAILURE_MESSAGE;
		}
	}
	
	
	
	
	
}
