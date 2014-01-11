package evoter.server.http.request;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.SessionDAO;
import evoter.server.dao.SessionUserDAO;
import evoter.server.dao.UserDAO;
import evoter.server.http.URIUtils;
import evoter.server.model.Session;
import evoter.server.model.SessionUser;

public class SessionRequest {

	@SuppressWarnings("unchecked")
	public static void doGetAll(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long subjectId = Long.parseLong((String)parameters.get(SessionDAO.SUBJECT_ID));
		SessionDAO sesDao = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
		List<Session> sessions = sesDao.findBySubjectId(subjectId);
		JSONArray jsArray = new JSONArray();
		for (Session ses : sessions){
			jsArray.add(ses.toJSON().toJSONString());
		}
		URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
	}

	@SuppressWarnings("unchecked")
	public static void doView(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long id = Long.parseLong((String)parameters.get(SessionDAO.ID));
		SessionDAO sesDao = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
		List<Session> sessions = sesDao.findById(id);
		JSONArray jsArray = new JSONArray();
		for (Session ses : sessions){
			jsArray.add(ses.toJSON().toJSONString());
		}
		URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
	}


	public static void doActive(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		updateStatus(httpExchange, parameters, true);
	}

	public static void doAccept(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long sessionId = Long.parseLong((String)parameters.get(SessionUserDAO.SESSION_ID));
		String userKey = (String)parameters.get(UserDAO.USER_KEY);
		Long userId = Long.valueOf(URIUtils.getUserIdFromUserKey(userKey));
		
		SessionUserDAO sesUserDao = (SessionUserDAO)BeanDAOFactory.getBean(SessionUserDAO.BEAN_NAME);
		List<SessionUser> sessUserList = sesUserDao.findByProperty(new String[]{SessionUserDAO.SESSION_ID, SessionUserDAO.USER_ID}, new Object[]{sessionId, userId});
		if (sessUserList != null && !sessUserList.isEmpty()){
			
			for (SessionUser sessUser : sessUserList){
				sessUser.setAcceptSession(true);
				sesUserDao.update(sessUser);
			}
			URIUtils.writeSuccessResponse(httpExchange);
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}
	
	}

	/**
	 * Now only update delete_indicator of SESSION_USER table </br>
	 * 
	 * @param httpExchange
	 * @param parameters
	 */
	public static void doDelete(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long sessionId = Long.parseLong((String)parameters.get(SessionUserDAO.SESSION_ID));
		String userKey = (String)parameters.get(UserDAO.USER_KEY);
		Long userId = Long.valueOf(URIUtils.getUserIdFromUserKey(userKey));
		SessionUserDAO sesUserDao = (SessionUserDAO)BeanDAOFactory.getBean(SessionUserDAO.BEAN_NAME);
		List<SessionUser> sessUserList = sesUserDao.findByProperty(new String[]{SessionUserDAO.SESSION_ID, SessionUserDAO.USER_ID}, new Object[]{sessionId, userId});
		if (sessUserList != null && !sessUserList.isEmpty()){
			
			for (SessionUser sessUser : sessUserList){
				sessUser.setDeleteIndicator(true);
				sesUserDao.update(sessUser);
			}
			URIUtils.writeSuccessResponse(httpExchange);
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}

	}

	/**
	 * Insert new {@link Session} object to Session table
	 * Insert new {@link SessionUser} object to SessionUser table
	 * @param httpExchange
	 * @param parameters contains 
	 * 		</li> {@link SessionDAO#CREATION_DATE}
	 * 		</li> {@link SessionDAO#IS_ACTIVE}
	 * 		</li> {@link SessionDAO#NAME}
	 * 		</li> {@link SessionDAO#SUBJECT_ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 */
	public static void doCreate(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		String creattionDate = (String)parameters.get(SessionDAO.CREATION_DATE);
		String isActive = (String)parameters.get(SessionDAO.IS_ACTIVE);
		String sessionName = (String)parameters.get(SessionDAO.NAME);
		String subjectId = (String)parameters.get(SessionDAO.SUBJECT_ID);
		Session session = new Session(Long.valueOf(subjectId), 
										sessionName, 
										Date.valueOf(creattionDate), 
										Boolean.valueOf(isActive));
		SessionDAO sessionDAO = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
		try{
		
			long sessionId = sessionDAO.insert(session);
			//create SessionUser object and insert to Database
			String userKey = (String)parameters.get(UserDAO.USER_KEY);
			Long userId = URIUtils.getUserIdFromUserKey(userKey);
			SessionUser sessionUser = new SessionUser(userId, sessionId, false, false);
			SessionUserDAO sessionUserDAO = (SessionUserDAO)BeanDAOFactory.getBean(SessionUserDAO.BEAN_NAME);
			sessionUserDAO.insert(sessionUser);
			
		}catch(Exception e){
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}
		
		URIUtils.writeSuccessResponse(httpExchange);
		
	}

	/**
	 * 
	 * @param httpExchange
	 * @param parameters contains: </br>
	 * 		</li> {@link SessionDAO#ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 */
	public static void doInActive(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		updateStatus(httpExchange, parameters, false);
		
	}
	
	/**
	 * 
	 * @param httpExchange
	 * @param parameters
	 * @param parameters contains: </br>
	 * 		</li> {@link SessionDAO#ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 * @param isActive true if the current session is activated. False if the current value is in-activated </br>
	 */
	public static void updateStatus(HttpExchange httpExchange,
			Map<String,Object> parameters, boolean isActive){
		
		Long sessionId = Long.valueOf((String)parameters.get(SessionDAO.ID));
		SessionDAO sessionDAO = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
		try{

			List<Session> sessions = sessionDAO.findById(sessionId);
			Session session = sessions.get(0);
			session.setActive(isActive);
			sessionDAO.update(session);
			
			URIUtils.writeSuccessResponse(httpExchange);
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
			
		}
		
	}

	
	/**
	 * 
	 * @param httpExchange
	 * @param parameters contains </br>
	 * 		</li> {@link SessionDAO#NAME}
	 * 		</li> {@link SessionDAO#ID} 
	 * 		</li> {@link UserDAO#USER_KEY}
	 */
	public static void doUpdate(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		String sessionName = (String)parameters.get(SessionDAO.NAME);
		Long sessionId = Long.valueOf((String)parameters.get(SessionDAO.ID));
		SessionDAO sessionDAO = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
		try{
			List<Session> sessionList = sessionDAO.findById(sessionId);
			Session session = sessionList.get(0);
			session.setName(sessionName);
			sessionDAO.update(session);
			
			URIUtils.writeSuccessResponse(httpExchange);
		}catch(Exception ex){
			ex.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}
		
	}
	
	
	
	


}
