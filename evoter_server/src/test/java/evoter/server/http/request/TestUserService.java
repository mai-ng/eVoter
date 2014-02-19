package evoter.server.http.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
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
import evoter.server.http.request.interfaces.IUserService;
import evoter.share.dao.UserDAO;
import evoter.share.model.User;
import evoter.share.model.UserType;
/**
 * 
 * Create test cases for {@link IUserService} and {@link UserService} </br>
 * For all test cases , there is always a login test cases created to generate userkey </br>
 * Userkey is verified when a request is coming except login, register account or reset password </br> 
 * Also, userkey is used to get user id in some test cases </br> 
 *  
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestUserService {
	//request parameter map
	Map<String, Object> parameters;
	//username is used to create a login case
	String username = "paul_gibson";
	//password is used to create a login case
	String password = "12345678";
	/**
	 * Create an instance of {@link IAccountService} 
	 */
	@Autowired
	IAccountService accountService;
	/**
	 * Create an instance of {@link IUserService}
	 */
	@Autowired
	IUserService userService;
	/**
	 * Create an instance of {@link UserDAO} </br>
	 */
	@Autowired
	UserDAO userDAO;
	
	@Before
	public void setUp() throws Exception {
		parameters = new HashMap<String, Object>();
	}

	@After
	public void tearDown() throws Exception {
		parameters = null;
	}

	/**
	 * Test for {@link IUserService#doGetAll(Map)} </br>
	 * Select all users who are teacher </br>
	 * Expect returning an {@link User#toJSON()} array </br>
	 */
	@Test
	public void test_doGetAll() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		//look for teacher users
		parameters.put(UserDAO.USER_KEY, userKey.get(UserDAO.USER_KEY));
		parameters.put(UserDAO.USER_TYPE_ID, UserType.TEACHER);
		
		Object response = userService.doGetAll(parameters);
		String expected_response=""+
		"[{\"user_type_id\":2,\"id\":1,\"passwd\":\"12345678\",\"approved\":true,\"username\":\"paul_gibson\"," +
		"\"full_name\":\"Paul Gibson\",\"email_address\":\"paul.gibson@telecom-sudparis.eu\"}," +
		"{\"user_type_id\":2,\"id\":2,\"passwd\":\"12345678\",\"approved\":true,\"username\":\"jean\"," +
		"\"full_name\":\"Jean Luc\",\"email_address\":\"jean.luc@telecom-sudparis.eu\"}," +
		"{\"user_type_id\":2,\"id\":22,\"passwd\":\"123456789\",\"approved\":false,\"username\":" +
		"\"tuan\",\"full_name\":\"Tuan\",\"email_address\":\"tuan@yahoo.com\"}]";		
		
		assertEquals("doGetAll", response.toString(), expected_response);
	}
	/**
	 * Test for {@link IUserService#doCreate(Map)} </br>
	 * Create an {@link User} with username, password, fullname, email and user type as input parameters
	 * Expect returning SUCCESS message </br>
	 */
	@Test
	public void test_doCreate_1(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		String _username = "user_abc";
		String _password = "1234567890";
		String _fullname = "Nguyen Van A";
		String _email = "abc@yahoo.com";
		long _usertype = 1;
		parameters.put(UserDAO.USER_NAME, _username);
		parameters.put(UserDAO.EMAIL, _email);
		parameters.put(UserDAO.PASSWORD, _password);
		parameters.put(UserDAO.FULL_NAME, _fullname);
		parameters.put(UserDAO.USER_TYPE_ID, String.valueOf(_usertype));
		
		Object response = userService.doCreate(parameters);
		assertEquals("doCreate", response.toString(), "SUCCESS");
		
	}
	
	/**
	 * Test for {@link IUserService#doCreate(Map)} </br>
	 * Create a {@link User} that has no an email in the input parameters </br>
	 * Expect returning FAILURE message </br>
	 */
	@Test
	public void test_doCreate_2(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		String _username = "user_abc";
		String _fullname = "Nguyen Van A";
		String _email = "abc@yahoo.com";
		long _usertype = 1;
		parameters.put(UserDAO.USER_NAME, _username);
		parameters.put(UserDAO.EMAIL, _email);
		parameters.put(UserDAO.FULL_NAME, _fullname);
		parameters.put(UserDAO.USER_TYPE_ID, String.valueOf(_usertype));
		
		Object response = userService.doCreate(parameters);
		assertEquals("doCreate", response.toString(), "FAILURE");
		
	}
	/**
	 * Test for {@link IUserService#doDelete(Map)} </br>
	 * Delete an user ID=1 and expect returning SUCCESS message </br>
	 * Search user ID=1 and expect returning an empty array </br>
	 */
	@Test
	public void test_doDelete_1(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(UserDAO.ID, "1");
		
		Object response = userService.doDelete(parameters);
		assertEquals("doDelete() returns SUCCESS message", 
				response.toString(), "SUCCESS");
		
		List<User> users = userDAO.findById(1);
		assertTrue(users.isEmpty());
	}
	/**
	 * Test for {@link IUserService#doDelete(Map)} </br>
	 * Delete a user ID=-1 that does not exist in the system </br>
	 * Expect returning USER DOES NOT EXIST message </br>
	 */
	@Test
	public void test_doDelete_2(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(UserDAO.ID, "-1");
		
		Object response = userService.doDelete(parameters);
		assertEquals("doDelete() returns USER DOES NOT EXIST message", 
				response.toString(), "USER DOES NOT EXIST");

	}
	/**
	 * Test for {@link IUserService#doDelete(Map)} </br>
	 * Send a delete_user request without valid input parameter </br>
	 * Expect returning FAILURE message </br>
	 */
	@Test
	public void test_doDelete_3(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		
		Object response = userService.doDelete(parameters);
		assertEquals("doDelete() returns USER DOES NOT EXIST message", 
				response.toString(), "FAILURE");

	}
	/**
	 * Test for {@link IUserService#doChangeApprove(Map)} </br>
	 * Change approved status of user ID=1 to false and expect returning SUCCESS message </br>
	 * Search user ID=1 again and expect its approved status is false </br>
	 */
	@Test
	public void test_doChangeApprove_1(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(UserDAO.ID, "1");
		parameters.put(UserDAO.IS_APPROVED,"0");
		
		Object response = userService.doChangeApprove(parameters);
//		System.out.println("response :" + response);
		assertEquals("doChangeApprove() returns SUCCESS", response.toString(), "SUCCESS");
		
		User user = userDAO.findById(1).get(0);
		assertFalse(user.isApproved());
		
	}
	/**
	 * Test for {@link IUserService#doChangeApprove(Map)} </br>
	 * Change approved status of user ID=-1 to false. This user does not exist in the system </br>
	 * Expect returning USER DOES NOT EXIST message </br>
	 */
	@Test
	public void test_doChangeApprove_2(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(UserDAO.ID, "-1");
		parameters.put(UserDAO.IS_APPROVED, "0");
		
		Object response = userService.doChangeApprove(parameters);
		assertEquals("doChangeApprove() returns USER DOES NOT EXIST",
				response.toString(), "USER DOES NOT EXIST");
	}
	/**
	 * Test for {@link IUserService#doChangeApprove(Map)} </br>
	 * Send a change_approved request with an invalid input parameter in the system </br>
	 * Expect returning FAILURE message </br>
	 */
	@Test
	public void test_doChangeApprove_3(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		
		Object response = userService.doChangeApprove(parameters);
		assertEquals("doChangeApprove() returns FAILURE", response.toString(), "FAILURE");
	}

	/**
	 * Test for {@link IUserService#doEdit(Map)} </br>
	 * Change approved status and full name of user ID=1 and expect returning SUCCESS message </br>
	 * Search user ID=1 again and expect the changes are updated </br>
	 */
	@Test
	public void test_doEdit_1(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(UserDAO.ID, "1");
		parameters.put(UserDAO.IS_APPROVED,"0");
		parameters.put(UserDAO.FULL_NAME,"full name");
		
		Object response = userService.doEdit(parameters);
		assertEquals("doEdit() returns SUCCESS", response.toString(), "SUCCESS");
		
		User user = userDAO.findById(1).get(0);
		assertFalse(user.isApproved());
		assertEquals(user.getFullName(), "full name");
		
	}
	/**
	 * Test for {@link IUserService#doEdit(Map)} </br>
	 * Change the information of user ID=-1 that does not exist </br>
	 * Expect returning USER DOES NOT EXIST message </br>
	 */
	@Test
	public void test_doEdit_2(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		parameters.put(UserDAO.ID, "-1");
		
		Object response = userService.doEdit(parameters);
		assertEquals("doEdit() returns USER DOES NOT EXIST",
				response.toString(), "USER DOES NOT EXIST");
	}
	/**
	 * Test for {@link IUserService#doEdit(Map)} </br>
	 * Send edit_user request with an invalid input parameter </br>
	 * Expect returning FAILURE message </br>
	 */
	@Test
	public void test_doEdit_3(){
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDAO.USER_NAME, username);
		map.put(UserDAO.PASSWORD, password);
		
		JSONObject userKey = (JSONObject)accountService.doLogin(map);
		parameters.put("userkey", userKey.get("userkey"));
		
		Object response = userService.doEdit(parameters);
		assertEquals("doEdit() returns FAILURE", response.toString(), "FAILURE");
	}

	

}
