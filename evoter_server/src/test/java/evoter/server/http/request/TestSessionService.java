package evoter.server.http.request;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

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

	Map<String, Object> parameters;
	
	@Autowired
	ISessionService sessionService;
	@Autowired
	IAccountService accountService;
	
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
	 * Select all sessions by a teacher </br>
	 */
	@Test
	public void test_doGetAll_1(){
		
		long subjectId = 1;
		//user type is teacher
		String userKey = "1333_1_2";
		parameters.put(UserDAO.USER_KEY, userKey);
		parameters.put(SessionDAO.SUBJECT_ID, String.valueOf(subjectId));
		
		String expected_response = ""+
		"[{\"NAME\":\"subject_1_session_1\",\"USER_ID\":1,\"SUBJECT_ID\":1,\"ID\":1," +
		"\"CREATOR\":\"paul_gibson\",\"CREATION_DATE\":\"2013-12-28 00:00:00.0\"," +
		"\"IS_ACTIVE\":true},{\"NAME\":\"subject_1_session_2\",\"USER_ID\":1,\"SUBJECT_ID\":1," +
		"\"ID\":2,\"CREATOR\":\"paul_gibson\",\"CREATION_DATE\":\"2013-12-28 12:50:24.0\"," +
		"\"IS_ACTIVE\":false},{\"NAME\":\"subject_1_session_3\",\"USER_ID\":1,\"SUBJECT_ID\":1," +
		"\"ID\":3,\"CREATOR\":\"paul_gibson\",\"CREATION_DATE\":\"2013-12-28 12:50:24.0\",\"IS_ACTIVE\":false}]";
		
		accountService.addUserKey(userKey);
		Object response = sessionService.doGetAll(parameters);
		assertEquals("doGetAll()", response.toString(), expected_response);
		
	}

	/**
	 * Test for {@link ISessionService#doGetAll(Map)} </br>
	 * Select all sessions by a student </br>
	 */
	@Test
	public void test_doGetAll_2(){
		
		long subjectId = 2;
		//user type is teacher
		String userKey = "1333_3_3";
		parameters.put(UserDAO.USER_KEY, userKey);
		parameters.put(SessionDAO.SUBJECT_ID, String.valueOf(subjectId));
		
		String expected_response = ""+
		"[{\"NAME\":\"subject_2_session_5\",\"USER_ID\":2,\"SUBJECT_ID\":2,\"ID\":5," +
		"\"CREATOR\":\"jean\",\"CREATION_DATE\":\"2013-12-28 12:50:24.0\",\"IS_ACTIVE\":false}]";

		Object response = sessionService.doGetAll(parameters);
		assertEquals("doGetAll()", response.toString(), expected_response);
	}
	/**
	 * Test for {@link ISessionService#doView(Map)} </br>
	 */
	@Test
	public void test_doView(){
		
		long sessionId = 1;
		String expected_response = ""+
				"[{\"NAME\":\"subject_1_session_1\",\"USER_ID\":1,\"SUBJECT_ID\":1,\"ID\":1," +
				"\"CREATOR\":\"paul_gibson\",\"CREATION_DATE\":\"2013-12-28 00:00:00.0\",\"IS_ACTIVE\":true}]";
		parameters.put(SessionDAO.ID, String.valueOf(sessionId));
		Object response = sessionService.doView(parameters);
		assertEquals("doView", response.toString(), expected_response);
	}
	/**
	 * Test for {@link ISessionService#doActive(Map)} </br>
	 * Expect returning SUCCESS message </br>
	 */
	@Test
	public void test_doActive(){
		
		long sessionId = 2;
		parameters.put(SessionDAO.ID, String.valueOf(sessionId));
		Object response = sessionService.doActive(parameters);
		assertEquals("doActive", response.toString(), "SUCCESS");
	}
	/**
	 * Test for {@link ISessionService#doInActive(Map)} </br>
	 * Expect returning SUCCESS message </br>
	 */
	@Test
	public void test_doInActive(){
		
		long sessionId = 2;
		parameters.put(SessionDAO.ID, String.valueOf(sessionId));
		Object response = sessionService.doInActive(parameters);
		assertEquals("doInActive", response.toString(), "SUCCESS");
	}
	/**
	 * Test for {@link ISessionService#doUpdate(Map)} </br>
	 * Expect returning SUCCESS message </br>
	 */
	@Test
	public void test_doUpdate(){
		
		long sessionId = 2;
		String name = "data for testing";
		parameters.put(SessionDAO.ID, String.valueOf(sessionId));
		parameters.put(SessionDAO.NAME, name);
		Object response = sessionService.doUpdate(parameters);
		assertEquals("doActive", response.toString(), "SUCCESS");
	}	
	/**
	 * Test for {@link ISessionService#doGetStudentsOfSession(Map)} </br>
	 * Get all student users that accepted </br>
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
	 * Get all student users that  are not accepted yet</br>
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
