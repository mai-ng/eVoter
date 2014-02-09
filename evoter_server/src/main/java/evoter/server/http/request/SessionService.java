package evoter.server.http.request;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.net.httpserver.HttpExchange;

import evoter.server.http.URIUtils;
import evoter.server.http.request.interfaces.ISessionService;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Question;
import evoter.share.model.Session;
import evoter.share.model.SessionUser;
import evoter.share.model.Subject;
import evoter.share.model.User;
import evoter.share.model.UserType;
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

/**
 * Process all {@link Session} requests sent by client applications </br> 
 * 
 * @author btdiem
 *
 */
@Service
public class SessionService implements ISessionService{

	public static final String CREATOR = "CREATOR";
	
	//@Autowired
	private SessionDAO sessionDAO;
	//@Autowired
	private SessionUserDAO sessionUserDAO;
	//@Autowired
	private UserDAO userDAO;
	
	
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
	
	
	/**
	 * This method will select all {@link Session} of a specific {@link Subject} </br>
	 * and the result will be added to response to client application </br>
	 * There are two kind of requests. One is sent by teacher user and the other is sent by student user </br>
	 * The first selection is made from session table. if a null or empty value is returned, that means </br>
	 * the request is coming from student user application. So try to select all session in session_user table </br>
	 * and get all session object from returned value above </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server client application </br>
	 * @param parameters contains : </br>
	 * 	</li> SessionDAO.SUBJECT_ID </br>
	 * 	</li> {@link UserDAO#USER_KEY} </br>
	 */
	@SuppressWarnings("unchecked")
	public  void doGetAll(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		JSONArray response = new JSONArray();
		
		try{
			
			long subjectId = Long.parseLong((String)parameters.get(SessionDAO.SUBJECT_ID));
			long userId = Long.valueOf(UserValidation.
					getUserIdFromUserKey((String)parameters.get(UserDAO.USER_KEY)));
			
			//select all sessions in SESSION table in case user is teacher
			List<Session> sessions = sessionDAO.findByProperty(
					new String[]{SessionDAO.SUBJECT_ID, SessionDAO.USER_ID}, 
					new Long[]{subjectId, userId});
			
			//this is request is sent by student user
			if (sessions == null || sessions.isEmpty()){
				
				List<Session> sessionList = sessionDAO.findBySubjectId(subjectId);
				for (Session session : sessionList){
					List<SessionUser> sessionUsers = sessionUserDAO.
							findByProperty(new String[]{SessionUserDAO.SESSION_ID, SessionUserDAO.USER_ID}, 
											new Object[]{session.getId(), userId});
					
					if (sessionUsers != null && !sessionUsers.isEmpty())
						//get this session
						sessions.add(session);
				
				}
			}
			
			//find session creator
			for (Session ses : sessions){
				User creator = userDAO.findById(ses.getUserId()).get(0);
				JSONObject object = ses.toJSON(); 
				object.put(CREATOR, creator.getUserName());
				response.add(object);
				
			}
			URIUtils.writeResponse(response, httpExchange);
			System.out.println("sessions: " + response);
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}

	}

	/**
	 * Response client a {@link Session}  when receiving {@link URIRequest#VIEW_SESSION} request </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server client application </br>
	 * @param parameters request parameter map contains </br>
	 * 	</li> SessionDAO.ID </br>
	 *  </li> {@link UserDAO#USER_KEY} </br>
	 */
	@SuppressWarnings("unchecked")
	public  void doView(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long id = Long.parseLong((String)parameters.get(SessionDAO.ID));
		List<Session> sessions = sessionDAO.findById(id);
		JSONArray jsArray = new JSONArray();
		for (Session ses : sessions){
			User creator = userDAO.findById(ses.getUserId()).get(0);
			JSONObject object = ses.toJSON();
			object.put(CREATOR, creator.getUserName());
			jsArray.add(object);
		}
		URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
	}

	/**
	 * Change the status of {@link Session} to inactive </br>
	 * when receiving {@link URIRequest#ACTIVE_SESSION}</br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains : </br>
	 *  </li> SessionDAO.ID
	 *  </li> {@link UserDAO#USER_KEY}
	 */
	public  void doActive(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		updateStatus(httpExchange, parameters, true);
	}

	public  void doAccept(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
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

			URIUtils.writeSuccessResponse(httpExchange);
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
			
		}
	
	}

	/**
	 * Update delete_indicator field of SESSION_USER table </br>
	 * when receiving {@link URIRequest#DELETE_SESSION} request from client application</br>
	 * to mark that this session is deleted by a user </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains : </br>
	 * 	</li> SessionUserDAO.SESSION_ID
	 *  </li> UserDAO.USER_KEY
	 */
	public  void doDelete(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		long sessionId = Long.parseLong((String)parameters.get(SessionUserDAO.SESSION_ID));
		String userKey = (String)parameters.get(UserDAO.USER_KEY);
		Long userId = Long.valueOf(UserValidation.getUserIdFromUserKey(userKey));
		List<SessionUser> sessUserList = sessionUserDAO.findByProperty(new String[]{SessionUserDAO.SESSION_ID, SessionUserDAO.USER_ID}, new Object[]{sessionId, userId});
		if (sessUserList != null && !sessUserList.isEmpty()){
			
			for (SessionUser sessUser : sessUserList){
				sessUser.setDeleteIndicator(true);
				sessionUserDAO.update(sessUser);
			}
			URIUtils.writeSuccessResponse(httpExchange);
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}

	}

	/**
	 * Create a new {@link Question} object when receiving {@link URIRequest#CREATE_SESSION} </br>
	 * The order of steps are: </br>
	 * </li>Create a new {@link Session} and insert to SESSION table </br>
	 * </li>Create a new {@link SessionUser}  and insert to SESSION_USER table </br>
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains 
	 * 		</li> {@link SessionDAO#CREATION_DATE}
	 * 		</li> {@link SessionDAO#IS_ACTIVE}
	 * 		</li> {@link SessionDAO#NAME}
	 * 		</li> {@link SessionDAO#SUBJECT_ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 * 		</li> 
	 */
	public  void doCreate(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		String creattionDate = (String)parameters.get(SessionDAO.CREATION_DATE);
		String isActive = (String)parameters.get(SessionDAO.IS_ACTIVE);
		String sessionName = (String)parameters.get(SessionDAO.NAME);
		String subjectId = (String)parameters.get(SessionDAO.SUBJECT_ID);
		long userId = Long.valueOf(UserValidation.getUserIdFromUserKey((String)parameters.get(UserDAO.USER_KEY)));
		Session session = new Session(Long.valueOf(subjectId), 
										sessionName, 
										Timestamp.valueOf(creattionDate), 
										Boolean.valueOf(isActive),
										userId);
		try{
		
			long sessionId = sessionDAO.insert(session);
			//create SessionUser object and insert to Database
//			String userKey = (String)parameters.get(UserDAO.USER_KEY);
			//Long userId = UserValidation.getUserIdFromUserKey(userKey);
			SessionUser sessionUser = new SessionUser(userId, sessionId, false, false);
			sessionUserDAO.insert(sessionUser);
			
		}catch(Exception e){
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}
		
		URIUtils.writeSuccessResponse(httpExchange);
		
	}

	/**
	 * Change the status of {@link Session} to inactive </br>
	 * when receiving {@link URIRequest#INACTIVE_SESSION}</br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains: </br>
	 * 		</li> {@link SessionDAO#ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 */
	public  void doInActive(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		updateStatus(httpExchange, parameters, false);
		
	}
	
	/**
	 * Update the session status when session changes from active to inactive or </br>
	 * from inactive to active </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains: </br>
	 * 		</li> {@link SessionDAO#ID}
	 * 		</li> {@link UserDAO#USER_KEY}
	 * @param isActive true if the current session is activated. False if the current value is in-activated </br>
	 */
	public  void updateStatus(HttpExchange httpExchange,
			Map<String,Object> parameters, boolean isActive){
		
		Long sessionId = Long.valueOf((String)parameters.get(SessionDAO.ID));
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
	 * Update {@link SessionDAO#NAME} of {@link Session} </br> 
	 * when receiving {@link URIRequest#UPDATE_SESSION} </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains </br>
	 * 		</li> {@link SessionDAO#NAME}
	 * 		</li> {@link SessionDAO#ID} 
	 * 		</li> {@link UserDAO#USER_KEY}
	 */
	public  void doUpdate(HttpExchange httpExchange,
			Map<String,Object> parameters) {
		
		String sessionName = (String)parameters.get(SessionDAO.NAME);
		Long sessionId = Long.valueOf((String)parameters.get(SessionDAO.ID));
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

	/*
	 * (non-Javadoc)
	 * @see evoter.server.http.request.interfaces.ISessionService#doGetStudentsOfSession(com.sun.net.httpserver.HttpExchange, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doGetStudentsOfSession(HttpExchange httpExchange,
			Map<String, Object> parameters) {
		
		JSONArray response = new JSONArray();
		try{
			
			long sessionId = Long.valueOf(parameters.get(SessionUserDAO.SESSION_ID).toString());
			boolean acceptSession = Boolean.valueOf(parameters.get(SessionUserDAO.ACCEPT_SESSION).toString());

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
				
			}//for
			URIUtils.writeResponse(response, httpExchange);
			
		}catch(Exception e){
			
			e.printStackTrace();
			URIUtils.writeFailureResponse(httpExchange);
		}

	}
	
	
	
}
