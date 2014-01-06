package evoter.server.http.request;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.SessionDAO;
import evoter.server.dao.SessionUserDAO;
import evoter.server.http.URIUtils;
import evoter.server.model.Session;
import evoter.server.model.SessionUser;

public class SessionRequest {

	@SuppressWarnings("unchecked")
	public static void doGetAll(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long subjectId = Long.parseLong(parameters.get(SessionDAO.SUBJECT_ID));
		SessionDAO sesDao = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
		List<Session> sessions = sesDao.findBySubjectId(subjectId);
		JSONArray jsArray = new JSONArray();
		for (Session ses : sessions){
			jsArray.add(ses.toJSONString());
		}
		URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
	}

	@SuppressWarnings("unchecked")
	public static void doView(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long id = Long.parseLong(parameters.get(SessionDAO.ID));
		SessionDAO sesDao = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
		List<Session> sessions = sesDao.findById(id);
		JSONArray jsArray = new JSONArray();
		for (Session ses : sessions){
			jsArray.add(ses.toJSONString());
		}
		URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
	}

	public static void doCreate(HttpExchange httpExchange,
			Map<String, String> parameters) {
		// TODO Auto-generated method stub
		
	}

	public static void doActive(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long id = Long.parseLong(parameters.get(SessionDAO.ID));
		SessionDAO sesDao = (SessionDAO)BeanDAOFactory.getBean(SessionDAO.BEAN_NAME);
		List<Session> sessions = sesDao.findById(id);
		if (sessions != null && !sessions.isEmpty()){
			Session session = sessions.get(0);
			session.setActive(true);
			sesDao.update(session);
			URIUtils.writeSuccessResponse(httpExchange);
		}else{
			URIUtils.writeFailureResponse(httpExchange);
		}
	}

	public static void doAccept(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long sessionId = Long.parseLong(parameters.get(SessionUserDAO.SESSION_ID));
		long userId = Long.parseLong(parameters.get(SessionUserDAO.USER_ID));
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

	public static void doDelete(HttpExchange httpExchange,
			Map<String, String> parameters) {
		
		long sessionId = Long.parseLong(parameters.get(SessionUserDAO.SESSION_ID));
		long userId = Long.parseLong(parameters.get(SessionUserDAO.USER_ID));
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
	
	


}
