package evoter.server.http.request;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.dao.impl.QuestionSessionDAOImpl;
import evoter.server.http.request.interfaces.ISubjectService;
import evoter.share.dao.QuestionSessionDAO;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.model.UserSubject;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

/**
 * Process all {@link Subject} requests sent by client applications </br>
 * This class is an implementation of {@link ISubjectService} </br>
 * @author btdiem </br>
 *
 */
@Service
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class SubjectService implements ISubjectService{
	
	static Logger log = Logger.getLogger(SubjectService.class);
	/**
	 * Define getter/setter for {@link QuestionSessionDAOImpl} bean
	 */
	private QuestionSessionDAO questionSessionDAO;
	/**
	 * Define getter/setter for {@link SessionDAOImpl} bean
	 */
	private SessionDAO sessionDAO;
	/**
	 * Define getter/setter for {@link SessionUserDAOImpl} bean
	 */
	private SessionUserDAO sessionUserDAO;

	/**
	 * Define getter/setter for {@link SubjectDAOImpl} bean
	 */
	private SubjectDAO subjectDAO;
	/**
	 * Define getter/setter for {@link UserDAOImpl} bean
	 */
	private UserDAO userDAO;
	/**
	 * Define getter/setter for {@link UserSubjectDAOImpl} bean
	 */
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
			
			long subjectId = Long.valueOf((String)parameters.get(SubjectDAO.ID));
			List<Subject> subjectList = subjectDAO.findById(subjectId);
			if (subjectList != null && !subjectList.isEmpty()){
				
				Subject subject = subjectList.get(0);
				return subject.toJSON();
			}
			
			
			return URIRequest.SUBJECT_NOT_EXIST_MESSAGE;
			
		}catch(Exception e){
			System.err.println(e);
			return URIRequest.FAILURE_MESSAGE;
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
			long userTypeId = UserValidation.getUserTypeIdFromUserKey(userKey);
			List<Subject> subjectList = new ArrayList<Subject>();
			/**
			 * if user type is secrectary, select all subjects
			 * Otherwise, select subject of requested user
			 */
			
			if (userTypeId == UserType.SECRETARY){
				subjectList = subjectDAO.findAll();
			}else{
				Long id = UserValidation.getUserIdFromUserKey(userKey);
				List<UserSubject> userSubjectList = userSubjectDAO.findByUserId(id);
				for (UserSubject userSubject : userSubjectList){
					Subject subject = (Subject)subjectDAO.findById(userSubject.getSubjectId()).get(0);
					subjectList.add(subject);
				}

			}
			JSONArray response = new JSONArray();
			for (Subject subject : subjectList){
				response.add(subject.toJSON());
			}
			
//			System.out.println("SUBJECT : " + response.toJSONString());
			return response;
			
		}catch(Exception e){
			System.err.println(e);
			return URIRequest.FAILURE_MESSAGE;
		}

		
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectService#doDelete(java.util.Map)
	 */
	@Override
	public  Object doDelete(Map<String,Object> parameters){
		
		try{
			
			long subjectId = Long.valueOf((String)parameters.get(SubjectDAO.ID));
			List<Subject> subject = subjectDAO.findById(subjectId);
			if (subject != null && !subject.isEmpty()){
				
				subjectDAO.deleteById(subjectId);
				return URIRequest.SUCCESS_MESSAGE;
				
			}
				
			return URIRequest.SUBJECT_NOT_EXIST_MESSAGE;
				
		}catch(Exception e){
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISubjectService#doSearch(java.util.Map)
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
			return response;
			
		}catch(Exception e){
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
			return response;
			
		}catch(Exception e){
			
			log.error(e);
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
			
			log.error(e);
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
			
			return URIRequest.SUCCESS_MESSAGE;
			
		}catch(Exception e){
			
			log.error(e);
			return URIRequest.FAILURE_MESSAGE;
		}
	}
	
	
	
	
	
}
