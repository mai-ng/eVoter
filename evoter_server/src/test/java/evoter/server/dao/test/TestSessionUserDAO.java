package evoter.server.dao.test;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.share.dao.SessionUserDAO;
import evoter.share.model.SessionUser;
/**
 * Make test cases for {@link SessionUserDAO} and {@link SessionUserDAOImpl}</br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestSessionUserDAO {

	/**
	 * Create a {@link SessionUserDAO} instance
	 */
	@Autowired
	SessionUserDAO sessionUserDAO;
	
	/**
	 * Test for {@link SessionUserDAO#findAll()} </br>
	 * Select all records in session_user table </br>
	 */
	@Test
	@Rollback(false)
	public void test_findAll(){
		List<SessionUser> sessionUsers = sessionUserDAO.findAll(); 
		assertTrue(sessionUsers.size() > 0);
	}
	/**
	 * Test for {@link SessionUserDAO#deleteByProperty(String[], Object[])} </br>
	 * Delete all {@link SessionUser} having user ID=3 and session ID=1 </br>
	 * Search all {@link SessionUser} with these conditions and expect returning an empty list </br> 
	 */
	@Test
	@Rollback(true)
	public void test_deleteByProperty(){
		
		sessionUserDAO.deleteByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1});
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1});
		
		assertTrue("test_deleteByProperty", sessionUsers.size() == 0);
	}
	/**
	 * Test {@link SessionUserDAO#findByUserId(Long)} </br>
	 * Select all {@link SessionUser} having user ID=1 </br>
	 * Expect returing a list of {@link SessionUser} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByUserId(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByUserId(1);
		assertTrue("test_findByUserId", sessionUsers.size() > 0);
	}
	/**
	 * Test {@link SessionUserDAO#findBySessionId(Long)} </br>
	 * Select all {@link SessionUser} having session id=1 </br>
	 * Expect returing a list of {@link SessionUser}
	 */
	@Test
	@Rollback(false)
	public void test_findBySessionId(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findBySessionId(1);
		assertTrue("test_findBySessionId", sessionUsers.size() > 0);
	}
	/**
	 * Test {@link SessionUserDAO#findByDeleteIndicator(Boolean)} </br>
	 * Select all {@link SessionUser} having delete indication=true </br>
	 * expect returing a list of {@link SessionUser} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByDeleteIndicator(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByDeleteIndicator(true);
		assertTrue("test_findByDeleteIndicator", sessionUsers.size() > 0);
	}
	/**
	 * Test {@link SessionUserDAO#deleteByDeleteIndicator(Boolean)} </br>
	 * Delete all {@link SessionUser} having delete indication=true </br>
	 * Search all {@link SessionUser} with this condition and expect returning an empty list </br>
	 */
	@Test
	public void test_deleteByDeleteIndicator(){
		
		sessionUserDAO.deleteByDeleteIndicator(true);
		List<SessionUser> sessionUsers = sessionUserDAO.findByDeleteIndicator(true);
		assertTrue("test_deleteByDeleteIndicator", sessionUsers.size() == 0);
	}	
	/**
	 * Test {@link SessionUserDAO#findByAcceptSession(Boolean)} </br>
	 * Search all {@link SessionUser} having accepted value = true </br>
	 * Expect returing a list of {@link SessionUser} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByAcceptSession(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByAcceptSession(false);
		assertTrue("test_findByAcceptSession", sessionUsers.size() > 0);
	}
	/**
	 * Test {@link SessionUserDAO#deleteByAcceptSession(Boolean)} </br>
	 * Delete all {@link SessionUser} having delete indicator=true in session_user table </br>
	 * Search {@link SessionUser} records with this condition and expect returing true </br>
	 */
	@Test
	public void test_deleteByAcceptSession(){
		
		sessionUserDAO.deleteByAcceptSession(false);
		List<SessionUser> sessionUsers = sessionUserDAO.findByAcceptSession(false);
		assertTrue("test_deleteByAcceptSession", sessionUsers.size() == 0);
	}	
	/**
	 * Test {@link SessionUserDAO#deleteByUserId(Long)} </br>
	 * Delete all {@link SessionUser} having user ID=1 in session_user table </br>
	 * Search all {@link SessionUser} with this condition and expect returning an empty list </br> 
	 */
	@Test
	public void test_deleteByUserId(){
		
		sessionUserDAO.deleteByUserId(1);
		List<SessionUser> sessionUsers = sessionUserDAO.findByUserId(1);
		assertTrue("test_deleteByUserId", sessionUsers.size() == 0);
	}	
	/**
	 * Test {@link SessionUserDAO#findBySessionId(Long)} </br>
	 * Delete all {@link SessionUser} having session id=1 in session_user table </br>
	 * Search {@link SessionUser} with this condition and expect returning an empty array </br>
	 */
	@Test
	public void test_deleteBySessionId(){
		
		sessionUserDAO.deleteBySessionId(1);
		List<SessionUser> sessionUsers = sessionUserDAO.findBySessionId(1);
		assertTrue("test_deleteBySessionId", sessionUsers.size() == 0);
	}	

	/**
	 * Test for {@link SessionUserDAO#findByProperty(String[],Object[])} </br>
	 * Select all {@link SessionUser} having user ID=3 and session Id=1 in session_user table</br>
	 * Expect returning a list of {@link SessionUser} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByByProperty(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1}); 
		assertTrue(sessionUsers.size() == 1);
	}
	/**
	 * Test for {@link SessionUserDAO#update(SessionUser)} </br>
	 * Change accepted value to true of {@link SessionUser} having user ID=3 and session ID=1 </br>
	 * 
	 */
	@Test
	public void test_update(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1}); 
		assertTrue(sessionUsers.size() == 1);
		
		SessionUser sessionUser = sessionUsers.get(0);
		boolean acceptSession = true;
		sessionUser.setAcceptSession(acceptSession);
		sessionUserDAO.update(sessionUser);

		sessionUsers = sessionUserDAO.findByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1}); 
		assertTrue("test_update", sessionUsers.get(0).isAcceptSession());
		
	}
		
}
