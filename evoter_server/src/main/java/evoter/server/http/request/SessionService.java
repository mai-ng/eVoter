package evoter.server.http.request;

import java.sql.Timestamp;
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
import evoter.server.dao.impl.SessionDAOImpl;
import evoter.server.dao.impl.SessionUserDAOImpl;
import evoter.server.dao.impl.UserDAOImpl;
import evoter.server.dao.impl.UserSubjectDAOImpl;
import evoter.server.http.request.interfaces.ISessionService;
import evoter.share.dao.AnswerDAO;
import evoter.share.dao.QuestionDAO;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.Answer;
import evoter.share.model.Question;
import evoter.share.model.QuestionSession;
import evoter.share.model.QuestionType;
import evoter.share.model.Session;
import evoter.share.model.SessionUser;
import evoter.share.model.User;
import evoter.share.model.UserSubject;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

/**
 * Process all {@link Session} requests sent by client applications </br> 
 * This class is an implementation of {@link ISessionService} </br>
 * @author btdiem </br>
 *
 */
@Service
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class SessionService implements ISessionService{
	
	static Logger log = Logger.getLogger(SessionService.class);
	public static final String CREATOR = "CREATOR";
	
	/**
	 * Define getter/setter of {@link SessionDAOImpl} bean
	 */
	private SessionDAO sessionDAO;
	/**
	 * Define getter/setter of {@link SessionUserDAOImpl} bean
	 */
	private SessionUserDAO sessionUserDAO;
	/**
	 * Define getter/setter of {@link UserDAOImpl} bean
	 */
	private UserDAO userDAO;
	/**
	 * Define getter/setter of {@link UserSubjectDAOImpl} bean
	 */
	private UserSubjectDAO userSubjectDAO;
	/**
	 * Define getter/setter of {@link QuestionDAOImpl} bean
	 */
	private QuestionDAO questionDAO;
	/**
	 * Define getter/setter of {@link QuestionSessionDAOImpl} bean
	 */
	private QuestionSessionDAO questionSessionDAO;
	/**
	 * Define getter/setter of {@link AnswerDAOImpl} bean
	 */
	private AnswerDAO answerDAO;
	


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

	public UserSubjectDAO getUserSubjectDAO() {
		return userSubjectDAO;
	}

	public void setUserSubjectDAO(UserSubjectDAO userSubjectDAO) {
		this.userSubjectDAO = userSubjectDAO;
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

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doGetAll(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Rollback(false)
	public  Object doGetAll(Map<String,Object> parameters) {
		
		JSONArray response = new JSONArray();
		
		try{
			
			long subjectId = (Long.valueOf((String)parameters.get(SessionDAO.SUBJECT_ID)));
			long userId = Long.valueOf(UserValidation.
					getUserIdFromUserKey((String)parameters.get(UserDAO.USER_KEY)));
			
			//select only available sessions of requested subject
			List<SessionUser> sessionUserList = sessionUserDAO.
					findByProperty(new String[]{SessionUserDAO.USER_ID, 
												SessionUserDAO.DELETE_INDICATOR}, 
								   new Object[]{userId, false});
			
			for (SessionUser sessionUser : sessionUserList){
				
				List<Session> sessionList = sessionDAO.
						findByProperty(new String[]{
								SessionDAO.SUBJECT_ID, SessionDAO.ID}, 
								new Long[]{subjectId, sessionUser.getSessionId()});
				
				for(Session session : sessionList){
					JSONObject jsSession = session.toJSON();
					
					//if the session creator is different from user session, add creator to response
					if (sessionUser.getUserId() != session.getUserId()){
						User creator = userDAO.findById(session.getUserId()).get(0);
						jsSession.put(SessionUserDAO.ACCEPT_SESSION, sessionUser.isAcceptSession());
						jsSession.put(CREATOR, creator.getUserName());
					}
					response.add(jsSession);
				}
				
			}//for
			
			//System.out.println("sessions: " + response);
			return response;
			
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doView(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Rollback(false)
	public  Object doView(Map<String,Object> parameters) {
		try{
			
			long id = Long.valueOf((String)parameters.get(SessionDAO.ID));
			String userKey = (String)parameters.get(UserDAO.USER_KEY);
			long userId = UserValidation.getUserIdFromUserKey(userKey);
			
			List<Session> sessions = sessionDAO.findById(id);
			JSONArray response = new JSONArray();
			for (Session ses : sessions){
				User creator = userDAO.findById(ses.getUserId()).get(0);
				SessionUser sessionUser = sessionUserDAO.
						findByProperty(new String[]{SessionUserDAO.SESSION_ID, SessionUserDAO.USER_ID}, 
									   new Object[]{id, userId}).get(0);
				JSONObject object = ses.toJSON();
				object.put(CREATOR, creator.getUserName());
				object.put(SessionUserDAO.ACCEPT_SESSION, sessionUser.isAcceptSession());
				response.add(object);
			}
			return response;
			
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}

	}


	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doActive(java.util.Map)
	 */
	@Override
	public  Object doActive(Map<String,Object> parameters) {
		
		return updateStatus(parameters, true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doAccept(java.util.Map)
	 */
	@Override
	public  Object doAccept(Map<String,Object> parameters) {
		
		try{
			
			long sessionId = Long.parseLong((String)parameters.get(SessionUserDAO.SESSION_ID));
			String userKey = (String)parameters.get(UserDAO.USER_KEY);
			Long userId = Long.valueOf(UserValidation.getUserIdFromUserKey(userKey));
			
			List<SessionUser> sessUserList = sessionUserDAO.
					findByProperty(new String[]{SessionUserDAO.SESSION_ID, SessionUserDAO.USER_ID}, 
									new Object[]{sessionId, userId});
			if (sessUserList != null && !sessUserList.isEmpty()){
				
				for (SessionUser sessUser : sessUserList){
					sessUser.setAcceptSession(true);
					sessionUserDAO.update(sessUser);
				}
				
			}
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
			
		}
	
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doDelete(java.util.Map)
	 */
	@Override
	public  Object doDelete(Map<String,Object> parameters) {
		
		try{
			
			long sessionId = Long.parseLong((String)parameters.get(SessionUserDAO.SESSION_ID));
			String userKey = (String)parameters.get(UserDAO.USER_KEY);
			Long userId = Long.valueOf(UserValidation.getUserIdFromUserKey(userKey));
			
			List<SessionUser> sessUserList = sessionUserDAO.findByProperty(
					new String[]{SessionUserDAO.SESSION_ID, SessionUserDAO.USER_ID}, 
					new Object[]{sessionId, userId});
			
			if (sessUserList != null){
				
				for (SessionUser sessUser : sessUserList){
					sessUser.setDeleteIndicator(true);
					sessionUserDAO.update(sessUser);
				}
				//URIUtils.writeSuccessResponse(httpExchange);
				return URIRequest.SUCCESS_MESSAGE;
			}
			
			return URIRequest.FAILURE_MESSAGE;
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}


	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doCreate(java.util.Map)
	 */
	@Override
	public  Object doCreate(Map<String,Object> parameters) {
		
		try{
			
			Timestamp creationDate = Timestamp.valueOf((String)parameters.get(SessionDAO.CREATION_DATE));
			boolean isActive = Boolean.valueOf((String)parameters.get(SessionDAO.IS_ACTIVE));
			String sessionName = (String)parameters.get(SessionDAO.NAME);
			long subjectId = Long.valueOf((String)parameters.get(SessionDAO.SUBJECT_ID));
			//this is session owner
			long userId = Long.valueOf(UserValidation.getUserIdFromUserKey
					((String)parameters.get(UserDAO.USER_KEY)));
			
			Session session = new Session(subjectId, sessionName, 
										creationDate,isActive, userId);
			/**
			 * insert a record for session creator to session table
			 */
			long sessionId = sessionDAO.insert(session);
			/**
			 * select all users of this subject and insert them to session_user_table
			 * if users are  teacher, accept_session status is true
			 * if users are student, accept_session status is false
			 */
			List<UserSubject> userSubjectList = userSubjectDAO.findBySubjectId(subjectId);
			for (UserSubject userSubject : userSubjectList){
				SessionUser sessionUser = new SessionUser(userSubject.getUserId(), sessionId, false, false);
				if (userSubject.getUserId() == userId){
					sessionUser.setAcceptSession(true);
				}
				sessionUserDAO.insert(sessionUser);
			}
			
			//create 2 slider questions EXCITED and DIFFICULT with the status is 1

			createQuestion("EXCITED", sessionId, userId);
			
			createQuestion("DIFFICULT", sessionId, userId);
			
		}catch(Exception e){
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
		
		return URIRequest.SUCCESS_MESSAGE;
		
	}
	/**
	 * Create a specific {@link Question}  for this session </br>
	 * Create an slider answer with default value is 10 for this question </br>
	 * 
	 * @param questionText question content </br>
	 * @param sessionId the current active session </br>
	 * @param userId user creates session </br>
	 */
	public void createQuestion(String questionText, long sessionId, long userId){
		
		Question question = new Question(QuestionType.SLIDER
				, userId
				, questionText
				, new Timestamp(System.currentTimeMillis())
				, 0);
		question.setStatus(1);
		long questionId = questionDAO.insert(question);
		QuestionSession questionSession = new QuestionSession(questionId, sessionId);
		questionSessionDAO.insert(questionSession);
		//create an answer with default statistics value = 10
		Answer answer = new Answer(questionId, "slider");
		answer.setStatistics("10");
		answerDAO.insert(answer);
		

	}


	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doInActive(java.util.Map)
	 */
	@Override
	public  Object doInActive(Map<String,Object> parameters) {
		
		return updateStatus(parameters, false);
		
	}
	
	/**
	 * Update the session status when session changes from active to inactive or </br>
	 * from inactive to active </br>
	 * 
	 * @param parameters contains: </br>
	 * 		</li> {@link SessionDAO#ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 * @param isActive true if the current session is activated. False if the current value is in-activated </br>
	 */
	public  Object updateStatus(Map<String,Object> parameters, boolean isActive){
		
		
		try{
			Long sessionId = Long.valueOf((String)parameters.get(SessionDAO.ID));
			List<Session> sessions = sessionDAO.findById(sessionId);
			Session session = sessions.get(0);
			session.setActive(isActive);
			sessionDAO.update(session);
			
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
			
		}
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doUpdate(java.util.Map)
	 */
	@Override
	public  Object doUpdate(Map<String,Object> parameters) {
		
		try{
			String sessionName = (String)parameters.get(SessionDAO.NAME);
			Long sessionId = Long.valueOf((String)parameters.get(SessionDAO.ID));

			List<Session> sessionList = sessionDAO.findById(sessionId);
			Session session = sessionList.get(0);
			session.setName(sessionName);
			sessionDAO.update(session);
			
			//URIUtils.writeSuccessResponse(httpExchange);
			return URIRequest.SUCCESS_MESSAGE;
		}catch(Exception ex){
			
			ex.printStackTrace();
			return URIRequest.FAILURE_MESSAGE;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doGetStudentsOfSession(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Rollback(false)
	public Object doGetStudentsOfSession(Map<String, Object> parameters) {
		
		
		try{
			JSONArray response = new JSONArray();
			long sessionId = Long.valueOf((String)parameters.get(SessionUserDAO.SESSION_ID));
			boolean acceptSession = Boolean.valueOf((String)parameters.get(SessionUserDAO.ACCEPT_SESSION));

			List<SessionUser> sessionUserList = sessionUserDAO.findByProperty(
					new String[]{SessionUserDAO.SESSION_ID, SessionUserDAO.ACCEPT_SESSION}, 
					new Object[]{sessionId, acceptSession});
			
			for (SessionUser sessionUser : sessionUserList){
				List<User> userList = userDAO.findByProperty(
						new String[]{UserDAO.ID, UserDAO.USER_TYPE_ID}, 
						new Object[]{sessionUser.getUserId(), UserType.STUDENT});
				for (User user : userList){
					response.add(user.toJSON());
				}
				
			}
			System.out.println("response: " + response);//for
			return response;
			
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}

	}
	
	
	
}
