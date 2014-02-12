package evoter.server.http.request;

import static org.junit.Assert.assertEquals;

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
import evoter.server.http.request.interfaces.ISubjectService;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.Subject;
import evoter.share.model.User;
/**
 * Test for {@link ISubjectService} and {@link SubjectService} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestSubjectService {

	Map<String, Object> parameters;
	@Autowired
	ISubjectService subjectService;
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
	 * Test for {@link ISubjectService#doView(Map)} </br>
	 * Expect returning {@link Subject#toJSON()} </br>
	 */
	@Test
	public void test_doView_1(){
		
		String expected_response=""+
		"{\"ID\":1,\"CREATION_DATE\":\"2013-12-28 12:50:24.0\"," +
		"\"TITLE\":\"Object Oriented Programming\"}";
		
		long subjectId = 1;
		String username = "paul_gibson";
		String password = "12345678";
		
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(SubjectDAO.ID, String.valueOf(subjectId));
		
		Object response = subjectService.doView(parameters);
		assertEquals("doView", response.toString(), expected_response);
		
	}
	/**
	 * Test for {@link ISubjectService#doView(Map)} </br>
	 * Expect returning FAILURE message</br>
	 */
	@Test
	public void test_doView_2(){
		
		long subjectId = 10000;
		String username = "paul_gibson";
		String password = "12345678";
		
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		parameters.put("userkey", userKey.get("userkey"));
		
		parameters.put(SubjectDAO.ID, String.valueOf(subjectId));
		Object response = subjectService.doView(parameters);
		assertEquals("doView", response.toString(), "FAILURE");
		
	}
	/**
	 * Test for {@link ISubjectService#doGetAll(Map)} </br>
	 * Select all {@link Subject} created by user </br>
	 * Expecting returning a array of {@link Subject#toJSON()} </br>
	 */
	@Test
	public void test_doGetAll(){
		
		String username = "paul_gibson";
		String password = "12345678";
		
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		parameters.put("userkey", userKey.get("userkey"));
		
		String expected_response=""+
		"[{\"ID\":1,\"CREATION_DATE\":\"2013-12-28 12:50:24.0\",\"TITLE\":\"Object Oriented Programming\"}," +
		"{\"ID\":3,\"CREATION_DATE\":\"2013-12-28 12:50:24.0\",\"TITLE\":\"Software Engineering\"}]";
		
		Object response = subjectService.doGetAll(parameters);
		assertEquals("doGetAll", expected_response, response.toString());
		
	}
//	/**
//	 * Test for {@link ISubjectService#doGetAll(Map)} </br>
//	 * Select all {@link Subject} created by user who did not exist in the system</br>
//	 * Expecting returning empty array </br>
//	 */
//	@Test
//	public void test_doGetAll_2(){
//		
//		String username = "someone else";
//		String password = "12345678";
//		
//		parameters.put(UserDAO.USER_NAME, username);
//		parameters.put(UserDAO.PASSWORD, password);
//		
//		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
//		parameters.put("userkey", userKey.get("userkey"));
//		
//		Object response = subjectService.doGetAll(parameters);
//		assertEquals("doGetAll", "[]", response.toString());
//		
//	}
	/**
	 * Test for {@link ISubjectService#doDelete(Map)} </br>
	 * Expect returning SUCCESS message </br>
	 */
	@Test
	public void test_doDelete_1(){
		
		long subjectId = 1;
		//String userKey = "123456789_1_2";
		String username = "paul_gibson";
		String password = "12345678";
		
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(SubjectDAO.ID, String.valueOf(subjectId));
		
		Object response = subjectService.doDelete(parameters);
		
		assertEquals("doDelete", response.toString(), "SUCCESS");
		//accountService.addUserKey(userKey);
		//parameters
	}
	/**
	 * Test for {@link ISubjectService#doDelete(Map)} </br>
	 * Expect returning SUBJECT DOES NOT EXIST message </br>
	 */
	@Test
	public void test_doDelete_2(){
		
		long subjectId = 10000;
		//String userKey = "123456789_1_2";
		String username = "paul_gibson";
		String password = "12345678";
		
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(SubjectDAO.ID, String.valueOf(subjectId));
		
		Object response = subjectService.doDelete(parameters);
		
		assertEquals("doDelete", response.toString(), "SUBJECT DOES NOT EXIST");
	}
	/**
	 * Test for {@link ISubjectService#doSearch(Map)} </br>
	 * Expect returning a Array of {@link Subject#toJSON()} </br>
	 */
	@Test
	public void test_doSearch(){

		String username = "paul_gibson";
		String password = "12345678";

		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);

		parameters.remove(UserDAO.USER_NAME);
		parameters.remove(UserDAO.PASSWORD);
		
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(SubjectDAO.CREATION_DATE, "2013-12-28 12:50:24");

		String expected_response=""+
		"[{\"ID\":1,\"CREATION_DATE\":\"2013-12-28 12:50:24.0\",\"TITLE\":\"Object Oriented Programming\"}," +
		"{\"ID\":2,\"CREATION_DATE\":\"2013-12-28 12:50:24.0\",\"TITLE\":\"Testing Metrics\"}," +
		"{\"ID\":3,\"CREATION_DATE\":\"2013-12-28 12:50:24.0\",\"TITLE\":\"Software Engineering\"}]";
		

		Object response = subjectService.doSearch(parameters);
		
		assertEquals("doSearch", response.toString(), expected_response);
		
	}
	/**
	 * Test for {@link ISubjectService#doGetUsersOfSubject(Map)} </br>
	 * Expect returning an array of {@link User#toJSON()} </br>
	 * 
	 */
	@Test
	public void do_doGetUsersOfSubject(){
		
		String username = "paul_gibson";
		String password = "12345678";

		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.PASSWORD, password);
		JSONObject userKey = (JSONObject)accountService.doLogin(parameters);

		parameters.remove(UserDAO.USER_NAME);
		parameters.remove(UserDAO.PASSWORD);
		
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(SubjectDAO.ID, 3);

		Object response = subjectService.doGetUsersOfSubject(parameters);
		String expected_response = ""+
		"[{\"user_type_id\":2,\"id\":1,\"passwd\":\"12345678\",\"approved\":true," +
		"\"username\":\"paul_gibson\",\"full_name\":\"Paul Gibson\",\"email_address\":" +
		"\"paul.gibson@telecom-sudparis.eu\"},{\"user_type_id\":3,\"id\":3,\"passwd\":" +
		"\"12345678\",\"approved\":true,\"username\":\"nvluong\",\"full_name\":\"\"," +
		"\"email_address\":\"nguyen.van@telecom-sudparis.eu\"},{\"user_type_id\":3," +
		"\"id\":4,\"passwd\":\"12345678\",\"approved\":true,\"username\":\"ntmai\"," +
		"\"full_name\":\"\",\"email_address\":\"nguyen.mai@telecom-sudparis.eu\"}]";
		
		assertEquals("do_doGetUsersOfSubject", response.toString(), expected_response);
	}
	

}
