package evoter.server.http.request;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import com.sun.net.httpserver.HttpExchange;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.http.URIRequest;
import evoter.server.http.URIUtils;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.Subject;
import evoter.share.model.UserSubject;
import evoter.share.utils.UserValidation;

/**
 * Process all {@link Subject} requests sent by client applications </br>
 * 
 * @author btdiem
 *
 */
public class SubjectRequest {
	
	
	/**
	 * Response client a {@link Subject} object when receiving {@link URIRequest#VIEW_SUBJECT} </br>
	 * 
	 * @param exchange {@link HttpExchange} communicates between server and client </br>
	 * @param parameters contains : </br>
	 *  </li> SubjectDAO.ID
	 *  </li> {@link UserDAO#USER_KEY}
	 */
	public static void doView(HttpExchange exchange, Map<String,Object> parameters){
		
		long id = Long.valueOf((String)parameters.get(SubjectDAO.ID));
		Subject subject = (Subject)((SubjectDAO) BeanDAOFactory.getBean(SubjectDAO.BEAN_NAME)).findById(id).get(0);
		URIUtils.writeResponse(subject.toJSON().toJSONString(), exchange);
		
	}
	
	/**
	 * Response client all {@link Subject} of user when receiving {@link URIRequest#VIEW_SUBJECT} </br>
	 * @param exchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains : </br>
	 *  </li> UserDAO.USER_KEY
	 */
	@SuppressWarnings("unchecked")
	public static void doGetAll(HttpExchange exchange, Map<String,Object> parameters){
		
		//long id = Long.valueOf(parameters.get(UserSubjectDAO.USER_ID));
		String userKey = (String)parameters.get(UserDAO.USER_KEY);
		Long id = UserValidation.getUserIdFromUserKey(userKey);
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
	
	/**
	 * Remove {@link Subject} out database and response client a {@link URIRequest#SUCCESS_MESSAGE} </br>
	 * when receiving {@link URIRequest#DELETE_SUBJECT} </br>
	 * 
	 * @param exchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains </br>
	 *  </li> SubjectDAO.ID
	 *  </li> {@link UserDAO#USER_KEY}
	 */
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

	/**
	 * Response client a list of {@link Subject} matching search conditions when receiving </br>
	 * {@link URIRequest#SEARCH_SUBJECT} </br>
	 * 
	 * @param httpExchange {@link HttpExchange} communicates between client and server </br>
	 * @param parameters contains </br>
	 *  </li> {@link SubjectDAO#TITLE}
	 *  </li> {@link SubjectDAO#CREATION_DATE}
	 */
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
