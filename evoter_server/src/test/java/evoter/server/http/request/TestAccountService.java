package evoter.server.http.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.http.request.interfaces.IAccountService;
import evoter.share.dao.UserDAO;
/**
 * Make test cases for {@link IAccountService} and {@link AccountService} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestAccountService {

	Map<String, Object> parameters;
	@Before
	public void setUp(){
		parameters = new HashMap<String, Object>();
	}
	@After
	public void tearDown(){
		parameters = null;
	}
	/**
	 * Create a {@link IAccountService} instance
	 */
	@Autowired
	IAccountService accountService;
	/**
	 * Test for {@link IAccountService#doLogin(Map)} </br>
	 * Call login request with a valid username and password 
	 * and expect returning a not null response </br>
	 */
	@Test
	@Rollback(false)
	public void test_doLogin_1(){
		
		parameters.put(UserDAO.USER_NAME, "btdiem");
		parameters.put(UserDAO.PASSWORD, "1234567890");
		
		Object output = accountService.doLogin(parameters);
		assertTrue("test_doLogin_1", output instanceof JSONObject);
		assertTrue("test_doLogin_1", !((JSONObject)output).isEmpty()); 
		
	}
	/**
	 * Test for {@link IAccountService#doLogin(Map)} </br>
	 * Call login request with a username that does not exist in the system and
	 * expect returning "USER DOES NOT EXIST" message
	 */
	@Test
	@Rollback(false)
	public void test_doLogin_2(){
		
		parameters.put(UserDAO.USER_NAME, "someone");
		parameters.put(UserDAO.PASSWORD, "something");
		
		Object output = accountService.doLogin(parameters);
		assertEquals("test_doLogin_2", output, "USER DOES NOT EXIST"); 
		
	}
	/**
	 * Test for {@link IAccountService#hasUserKey(String)} </br>
	 * Create a userKey and add it by calling {@link IAccountService#addUserKey(String)} 
	 * and expect it exist in the system</br>
	 * 
	 * Create an userkey without adding it to the system and expect it does not exist </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_hasUserKey(){
		
		String userKey = "1923242434_3_3";
		accountService.addUserKey(userKey);
		assertTrue("test_hasUserKey() returns true", 
				accountService.hasUserKey(userKey));
		
		String userKey1 = "userkey_not_added";
		assertFalse("test_hasUserKey() returns false", 
				accountService.hasUserKey(userKey1));
	}
	/**
	 * Test for {@link IAccountService#doLogout(Map)} </br>
	 * Create a userKey and add it by calling {@link IAccountService#addUserKey(String)} 
	 * Expect returning a success message and userkey is removed after logging out the system</br> 
	 */
	@Test
	@Rollback(false)
	public void test_doLogout_1(){
		
		String userKey = "1923242434_3_3";
		accountService.addUserKey(userKey);
		parameters.put(UserDAO.USER_KEY, userKey);
		
		assertTrue("userkey exists ", accountService.hasUserKey(userKey));
		Object response = accountService.doLogout(parameters);
		assertEquals("doLogout() returns success message", response,"SUCCESS");
		assertFalse("userkey has been removed ", accountService.hasUserKey(userKey));
		
	}
	/**
	 * Test for {@link IAccountService#doLogout(Map)} </br>
	 * Create a userkey and its userid gets from userkey does not exist in the system </br>
	 * Expect returning a USER DOES NOT EXIST message </br>
	 */
	@Test
	@Rollback(false)
	public void test_doLogout_2(){
		
		String userKey = "1923242434_3_3";
		parameters.put(UserDAO.USER_KEY, userKey);
		
		assertFalse("userkey does not exist ", accountService.hasUserKey(userKey));
		Object response = accountService.doLogout(parameters);
		assertEquals("doLogout() returns USER DOES NOT EXIST message", response,"USER DOES NOT EXIST");

	}
	/**
	 * Test for {@link IAccountService#doResetPassword(Map)} </br>
	 * Expect returning a EMAIL EXISTS message </br>
	 */
	@Test
	@Rollback(false)
	public void test_doResetPassword_1(){
		
		String email = "diemth@gmail.com";
		parameters.put(UserDAO.EMAIL, email);
		
		Object response = accountService.doResetPassword(parameters);
		assertTrue("doResetPassword() returns an EMAIL EXISTS message", 
				response.equals("EMAIL EXISTS"));
		
	}
	/**
	 * Test for {@link IAccountService#doResetPassword(Map)} </br>
	 * Expect returning a EMAIL DOES NOT EXIST message </br>
	 */
	@Test
	@Rollback(false)
	public void test_doResetPassword_2(){
		
		String email = "email_not_found@gmail.com";
		parameters.put(UserDAO.EMAIL, email);
		
		Object response = accountService.doResetPassword(parameters);
		System.out.println(response);
		assertTrue("doResetPassword() returns a EMAIL DOES NOT EXIST message",
				response.equals("EMAIL DOES NOT EXIST"));
		
	}	
	/**
	 * Test for {@link IAccountService#doRegister(Map)} </br>
	 * Create a user that has username existing in the system </br>
	 * Expect returning a "USER EXISTS ALREADY" message </br>
	 */
	@Test
	public void test_doRegister_1(){
		
		String username = "btdiem";
		String userTypeId = "3";
		String email = "diem.bt@telecom-sudparis.eu";
		String password = "12345xyz";
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.USER_TYPE_ID, userTypeId);
		parameters.put(UserDAO.EMAIL, email);
		parameters.put(UserDAO.PASSWORD, password);
		Object response = accountService.doRegister(parameters);
		System.out.println(response);
		assertTrue("doRegister() returns USER EXISTS ALREADY message",
				response.equals("USER EXISTS ALREADY"));
	}
	/**
	 * Test for {@link IAccountService#doRegister(Map)} </br>
	 * Create an user that has email address existing in the system </br>
	 * Expect returning a "EMAIL EXISTS" message </br>
	 */
	@Test
	public void test_doRegister_2(){
		
		String username = "new_user";
		String userTypeId = "3";
		String email = "diem.bt@telecom-sudparis.eu";
		String password = "12345xyz";
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.USER_TYPE_ID, userTypeId);
		parameters.put(UserDAO.EMAIL, email);
		parameters.put(UserDAO.PASSWORD, password);
		Object response = accountService.doRegister(parameters);
		assertTrue("doRegister() returns EMAIL EXISTS message", 
				response.equals("EMAIL EXISTS"));

	}
	/**
	 * Test for {@link IAccountService#doRegister(Map)} </br>
	 * Create a user that has username and email not existing in the system </br>
	 * Expect returning a "SUCCESS" message </br>
	 */
	@Test
	public void test_doRegister_3(){
		
		String username = "new_user";
		String userTypeId = "3";
		String email = "new_email@telecom-sudparis.eu";
		String password = "12345xyz";
		parameters.put(UserDAO.USER_NAME, username);
		parameters.put(UserDAO.USER_TYPE_ID, userTypeId);
		parameters.put(UserDAO.EMAIL, email);
		parameters.put(UserDAO.PASSWORD, password);
		Object response = accountService.doRegister(parameters);
		System.out.println(response);
		assertTrue("doRegister() returns SUCCESS message",
				response.equals("SUCCESS"));

	}
	
	
	

}
