package evoter.server.http.request;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.SubjectDAO;
import evoter.server.dao.UserDAO;
import evoter.server.dao.UserSubjectDAO;
import evoter.server.http.URIUtils;
import evoter.server.model.Subject;
import evoter.server.model.UserSubject;

public class SubjectRequest {
	
	
	public static void doView(HttpExchange exchange, Map<String,Object> parameters){
		
		long id = Long.valueOf((String)parameters.get(SubjectDAO.ID));
		Subject subject = (Subject)((SubjectDAO) BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME)).findById(id).get(0);
		URIUtils.writeResponse(subject.toJSON().toJSONString(), exchange);
		
	}
	
	@SuppressWarnings("unchecked")
	public static void doGetAll(HttpExchange exchange, Map<String,Object> parameters){
		
		//long id = Long.valueOf(parameters.get(UserSubjectDAO.USER_ID));
		String userKey = (String)parameters.get(UserDAO.USER_KEY);
		Long id = URIUtils.getUserIdFromUserKey(userKey);
		UserSubjectDAO userSubjectDao = (UserSubjectDAO)BeanDAOFactory.getBean(UserSubjectDAO.BEAN_NAME);
		List<UserSubject> usList = userSubjectDao.findByUserId(id);
		
		JSONArray jsArray = new JSONArray();
		for (UserSubject us : usList){
			Subject subject = (Subject)((SubjectDAO) BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME)).findById(us.getSubjectId()).get(0);
			jsArray.add(subject.toJSON().toJSONString());
		}
		System.out.println("SUBJECT : " + jsArray.toJSONString());
		URIUtils.writeResponse(jsArray.toJSONString(), exchange);
		
	}
	
	public static void doDelete(HttpExchange exchange, Map<String,Object> parameters){
		
		long subjectId = Long.valueOf((String)parameters.get(SubjectDAO.ID));
		SubjectDAO subjectDao = (SubjectDAO)BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME);
		try{
			
			subjectDao.deleteById(subjectId);
			URIUtils.writeResponse("SUCCESS", exchange);
			
		}catch(Exception e){
			
			URIUtils.writeResponse("FAILURE", exchange);
		}
		
		
	}

	@SuppressWarnings("unchecked")
	public static void doSearch(HttpExchange httpExchange,
			Map<String,Object> parameters) {

		SubjectDAO subjectDao = (SubjectDAO)BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME);
		List<Subject> subjects = subjectDao.findByProperty(parameters.keySet().toArray(new String[]{}), parameters.values().toArray());
		JSONArray jsArray = new JSONArray();
		for (Subject subject : subjects){
			jsArray.add(subject.toJSON().toJSONString());
		}
		URIUtils.writeResponse(jsArray.toJSONString(), httpExchange);
	}
	

}
