package evoter.server.http.request;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.http.request.interfaces.IAccountService;
import evoter.server.http.request.interfaces.ISessionService;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Session;
import evoter.share.model.User;
/**
 * 
 * Test for {@link ISessionService} and {@link SessionService} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestSessionService {

	//username is used to create a teacher login case
	String teacher_username = "paul_gibson";
	//password is used to create a teacher login case
	String teacher_password = "12345678";
	//username is used to create a student login case	
	String student_username = "nvluong";
	//password is used to create a student login case
	String student_password = "12345678";
	
	Map<String, Object> parameters;
	/**
	 * Create an instance of {@link ISessionService} bean
	 */
	@Autowired
	ISessionService sessionService;
	/**
	 * Create an instance of {@link IAccountService} bean
	 */
	@Autowired
	IAccountService accountService;
	/**
	 * Create an instance of {@link SessionDAO} </br>
	 */
	@Autowired
	SessionDAO sessionDAO;
	
	@Before
	public void setUp() throws Exception {
		parameters = new HashMap<String, Object>();
	}

	@After
	public void tearDown() throws Exception {
		parameters = null;
	}

	/**
	 * Test for {@link ISessionService#doGetAll(Map)} </br>
	 * Select all sessions of subjectID=1 by a teacher user</br>
	 * Expect returning an {@link Session#toJSON()} array </br>
	 */
	@Test
	public void test_doGetAll_1(){
		
		long subjectId = 1;

		parameters.put(UserDAO.USER_NAME, teacher_username);
		parameters.put(UserDAO.PASSWORD, teacher_password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);

		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(SessionDAO.SUBJECT_ID, String.valueOf(subjectId));
		
		String expected_response = ""+
		"[{\"NAME\":\"subject_1_session_1\",\"USER_ID\":1,\"SUBJECT_ID\":1," +
		"\"ID\":1,\"CREATION_DATE\":\"2013-12-28 00:00:00.0\",\"IS_ACTIVE\":true}]";
				
		Object response = sessionService.doGetAll(parameters);
		assertEquals("doGetAll()", response.toString(), expected_response);
		
	}

	/**
	 * Test for {@link ISessionService#doGetAll(Map)} </br>
	 * Select all sessions of subjectID=1 by a student user</br>
	 * Expect returning an {@link Session#toJSON()} </br>
	 */
	@Test
	public void test_doGetAll_2(){
		

		long subjectId = 1;
		parameters.put(UserDAO.USER_NAME, student_username);
		parameters.put(UserDAO.PASSWORD, student_password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		parameters.put("userkey", userKey.get("userkey"));
		

		parameters.put(SessionDAO.SUBJECT_ID, String.valueOf(subjectId));
		
		String expected_response = ""+
		"[{\"NAME\":\"subject_1_session_1\",\"ACCEPT_STT\":false,\"USER_ID\":1," +
		"\"SUBJECT_ID\":1,\"ID\":1,\"CREATOR\":\"paul_gibson\",\"CREATION_DATE\":" +
		"\"2013-12-28 00:00:00.0\",\"IS_ACTIVE\":true}]";
		
		Object response = sessionService.doGetAll(parameters);
		assertEquals("doGetAll()", response.toString(), expected_response);
	}
	
	
	/**
	 * Test for {@link ISessionService#doView(Map)} </br>
	 * Select the information of sessionID=1 and expect return {@link Session#toJSON()} </br>
	 */
	@Test
	public void test_doView(){
		
		parameters.put(UserDAO.USER_NAME, student_username);
		parameters.put(UserDAO.PASSWORD, student_password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		parameters.remove(UserDAO.USER_NAME);
		parameters.remove(UserDAO.PASSWORD);
		
		parameters.put("userkey", userKey.get("userkey"));

		long sessionId = 1;
		String expected_response = ""+
				"[{\"NAME\":\"subject_1_session_1\",\"ACCEPT_STT\":false,\"USER_ID\":1,\"SUBJECT_ID\":1,\"ID\":1," +
				"\"CREATOR\":\"paul_gibson\",\"CREATION_DATE\":\"2013-12-28 00:00:00.0\",\"IS_ACTIVE\":true}]";
		parameters.put(SessionDAO.ID, String.valueOf(sessionId));
		Object response = sessionService.doView(parameters);
		assertEquals("doView", response.toString(), expected_response);
	}
	/**
	 * Test for {@link ISessionService#doActive(Map)} </br>
	 * Active a sessionID=2 and expect returning SUCCESS message and
	 * active value of this session is changed to true after getting back </br>
	 * 
	 */
	@Test
	public void test_doActive(){
		
		long sessionId = 2;
		parameters.put(SessionDAO.ID, String.valueOf(sessionId));
		Object response = sessionService.doActive(parameters);
		assertEquals("doActive", response.toString(), "SUCCESS");
		
		Session session = sessionDAO.findById(sessionId).get(0);
		assertTrue(session.isActive());
	}
	/**
	 * Test for {@link ISessionService#doInActive(Map)} </br>
	 * Active a sessionID=2 and expect returning SUCCESS message and
	 * active value of this session is changed to false after getting back </br>
	 */
	@Test
	public void test_doInActive(){
		
		long sessionId = 2;
		parameters.put(SessionDAO.ID, String.valueOf(sessionId));
		Object response = sessionService.doInActive(parameters);
		assertEquals("doInActive", response.toString(), "SUCCESS");
		
		Session session = sessionDAO.findById(sessionId).get(0);
		assertFalse(session.isActive());
	}
	/**
	 * Test for {@link ISessionService#doUpdate(Map)} </br>
	 * Change session name of sessionID=2 </br>
	 * Expect returning SUCCESS message and session name is changed after getting back </br>
	 */
	@Test
	public void test_doUpdate(){
		
		long sessionId = 2;
		String name = "data for testing";
		parameters.put(SessionDAO.ID, String.valueOf(sessionId));
		parameters.put(SessionDAO.NAME, name);
		Object response = sessionService.doUpdate(parameters);
		assertEquals("doUpdate", response.toString(), "SUCCESS");
		
		Session session = sessionDAO.findById(sessionId).get(0);
		assertEquals("session name is changed", session.getName(), name);
		
	}	
	/**
	 * Test for {@link ISessionService#doGetStudentsOfSession(Map)} </br>
	 * Get all accepted student users of sessionID=1   </br>
	 * Expect returning a {@link User#toJSON()} array </br>
	 */
	@Test
	public void test_doGetStudentsOfSession_1(){
		
		long sessionId = 1;
		boolean isAccepted = true;
		
		parameters.put(SessionUserDAO.SESSION_ID, String.valueOf(sessionId));
		parameters.put(SessionUserDAO.ACCEPT_SESSION, String.valueOf(isAccepted));
		String expected_response = ""+
		"[{\"user_type_id\":3,\"id\":4,\"passwd\":\"12345678\",\"approved\":true," +
		"\"username\":\"ntmai\",\"full_name\":\"\"," +
		"\"email_address\":\"nguyen.mai@telecom-sudparis.eu\"}]";
				
		Object response = sessionService.doGetStudentsOfSession(parameters);
		assertEquals("test_doGetStudentsOfSession return an array", 
				response.toString(), expected_response);
		
	}
	
	/**
	 * Test for {@link ISessionService#doGetStudentsOfSession(Map)} </br>
	 * Get all student users of sessionID=1 that  are not accepted yet</br>
	 * Expect returning a array of {@link User#toJSON()} </br>
	 */
	@Test
	public void test_doGetStudentsOfSession_2(){
		
		long sessionId = 1;
		boolean isAccepted = false;
		
		parameters.put(SessionUserDAO.SESSION_ID, String.valueOf(sessionId));
		parameters.put(SessionUserDAO.ACCEPT_SESSION, String.valueOf(isAccepted));
		String expected_response = ""+
		"[{\"user_type_id\":3,\"id\":3,\"passwd\":\"12345678\",\"approved\":true," +
		"\"username\":\"nvluong\",\"full_name\":\"\"," +
		"\"email_address\":\"nguyen.van@telecom-sudparis.eu\"}]";				
		
		Object response = sessionService.doGetStudentsOfSession(parameters);
		assertEquals("test_doGetStudentsOfSession return an array", 
				response.toString(), expected_response);
		
	}
	
}
