package evoter.server.dao.test;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import evoter.share.dao.SessionUserDAO;
import evoter.share.model.SessionUser;
/**
 * Make test cases for {@link SessionUserDAO} and {@link SessionUserDAOImpl}</br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestSessionUserDAO {

	@Autowired
	SessionUserDAO sessionUserDAO;
	
	/**
	 * Test for {@link SessionUserDAO#findAll()} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindAll(){
		List<SessionUser> sessionUsers = sessionUserDAO.findAll(); 
		assertTrue(sessionUsers.size() > 0);
	}
	/**
	 * Test for {@link SessionUserDAO#deleteByProperty(String[], Object[])} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByProperty(){
		
		sessionUserDAO.deleteByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1});
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1});
		
		assertTrue("testDeleteByProperty", sessionUsers.size() == 0);
	}
	/**
	 * Test {@link SessionUserDAO#findByUserId(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByUserId(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByUserId(1);
		assertTrue("testFindByUserId", sessionUsers.size() > 0);
	}
	/**
	 * Test {@link SessionUserDAO#findBySessionId(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindBySessionId(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findBySessionId(1);
		assertTrue("testFindBySessionId", sessionUsers.size() > 0);
	}
	/**
	 * Test {@link SessionUserDAO#findByDeleteIndicator(Boolean)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByDeleteIndicator(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByDeleteIndicator(true);
		assertTrue("testFindByDeleteIndicator", sessionUsers.size() > 0);
	}
	/**
	 * Test {@link SessionUserDAO#deleteByDeleteIndicator(Boolean)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByDeleteIndicator(){
		
		sessionUserDAO.deleteByDeleteIndicator(true);
		List<SessionUser> sessionUsers = sessionUserDAO.findByDeleteIndicator(true);
		assertTrue("testDeleteByDeleteIndicator", sessionUsers.size() == 0);
	}	
	/**
	 * Test {@link SessionUserDAO#findByAcceptSession(Boolean)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByAcceptSession(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByAcceptSession(false);
		assertTrue("testFindByAcceptSession", sessionUsers.size() > 0);
	}
	/**
	 * Test {@link SessionUserDAO#deleteByAcceptSession(Boolean)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByAcceptSession(){
		
		sessionUserDAO.deleteByAcceptSession(false);
		List<SessionUser> sessionUsers = sessionUserDAO.findByAcceptSession(false);
		assertTrue("testDeleteByAcceptSession", sessionUsers.size() == 0);
	}	
	/**
	 * Test {@link SessionUserDAO#deleteByUserId(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByUserId(){
		
		sessionUserDAO.deleteByUserId(1);
		List<SessionUser> sessionUsers = sessionUserDAO.findByUserId(1);
		assertTrue("testDeleteByUserId", sessionUsers.size() == 0);
	}	
	/**
	 * Test {@link SessionUserDAO#findBySessionId(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBySessionId(){
		
		sessionUserDAO.deleteBySessionId(1);
		List<SessionUser> sessionUsers = sessionUserDAO.findBySessionId(1);
		assertTrue("testDeleteBySessionId", sessionUsers.size() == 0);
	}	

	/**
	 * Test for {@link SessionUserDAO#findByProperty(String[],Object[])} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByByProperty(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1}); 
		assertTrue(sessionUsers.size() == 1);
	}
	/**
	 * Test for {@link SessionUserDAO#update(SessionUser)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate(){
		
		List<SessionUser> sessionUsers = sessionUserDAO.findByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1}); 
		assertTrue(sessionUsers.size() == 1);
		
		SessionUser sessionUser = sessionUsers.get(0);
		boolean acceptSession = sessionUser.isAcceptSession();
		sessionUser.setAcceptSession(!acceptSession);
		sessionUserDAO.update(sessionUser);

		sessionUsers = sessionUserDAO.findByProperty(
				new String[]{SessionUserDAO.USER_ID, SessionUserDAO.SESSION_ID}, 
				new Object[]{3, 1}); 
		assertTrue("testUpdate", sessionUsers.get(0).isAcceptSession() == !acceptSession);
		
	}
		
}
