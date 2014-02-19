package evoter.server.dao.test;


import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.dao.impl.SessionDAOImpl;
import evoter.share.dao.SessionDAO;
import evoter.share.model.Session;
/**
 * Make test cases for {@link SessionDAO} and {@link SessionDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestSessionDAO {
	/**
	 * Create a {@link SessionDAOImpl} instance
	 */
	@Autowired
	SessionDAO sessionDAO;
	
	/**
	 * Test for {@link SessionDAO#findAll()} </br>
	 * Select all records in session table </br>
	 */
	@Test
	@Rollback(false)
	public void test_findAll(){
		List<Session> sessions = sessionDAO.findAll(); 
		assertTrue(sessions.size() > 0);
	}
	/**
	 * Test for {@link SessionDAO#findById(long)} </br>
	 * Select session having id=1 and expect returning one record </br>
	 */
	@Test
	@Rollback(false)
	public void test_findById(){
		
		List<Session> sessions = sessionDAO.findById(3); 
		assertTrue(sessions.size() == 1);
	}
	/**
	 * Test for {@link SessionDAO#findBySubjectId(long)} </br>
	 * Select session having subject id=1 and expect returning a list of {@link Session} </br> 
	 */
	@Test
	@Rollback(false)
	public void test_findBySubjectId(){
		
		List<Session> sessions = sessionDAO.findBySubjectId(1); 
		assertTrue(sessions.size() > 0);
	}
	/**
	 * Test for {@link SessionDAO#findById(long)} </br>
	 * Search sessions having session name = "subject_1_session_1" and expect returning a list of {@link Session} </br> 
	 */
	@Test
	@Rollback(false)
	public void test_findByName(){
		
		List<Session> sessions = sessionDAO.findByName("subject_1_session_1"); 
		assertTrue(sessions.size() > 0);
	}
	/**
	 * Test {@link SessionDAO#findByCreationDate(Timestamp)} </br>
	 * Search sessions having creation date = "2013-12-28 12:50:24" and expect returning a list of {@link Session} </br> 
	 * Search sessions having creation date = "2014-10-09 22:39:24" and expect returing nothing </br> 
	 */
	@Test
	@Rollback(false)
	public void test_findByCreationDate() throws ParseException{
		
		List<Session> sessions = sessionDAO.
				findByCreationDate(Timestamp.valueOf("2013-12-28 12:50:24"));
		assertTrue("test_findByCreationDate - Case 1", sessions.size() > 0);
		
		sessions = sessionDAO.
				findByCreationDate(Timestamp.valueOf("2014-10-09 22:39:24"));
		assertTrue("test_findByCreationDate - Case 2", sessions.size() == 0);
	}	
	/**
	 * Test {@link SessionDAO#deleteByCreationDate(Timestamp)} </br>
	 * Delete sessions having creation date = "2013-12-28 12:50:24" 
	 * Search all sessions with this condition and expect returning an empty list</br> 
 
	 */
	@Test
	public void test_deleteByCreationDate() throws ParseException{
		
		Timestamp sqlDate = Timestamp.valueOf("2013-12-28 12:50:24");

		sessionDAO.deleteByCreationDate(sqlDate);
		List<Session> sessions = sessionDAO.findByCreationDate(sqlDate);
		assertTrue("test_deleteByCreationDate", sessions.size() == 0);
		
	}		
	/**
	 * Test {@link SessionDAO#findBySessionIsActive(Boolean)} </br>
	 * Search active sessions expect returning a list of {@link Session} </br> 
	 * Search inactive sessions and expect returning nothing </br> 
 
	 */
	@Test
	@Rollback(false)
	public void test_findBySessionIsActive(){
		
		boolean isActive = true;
		
		List<Session> sessions = sessionDAO.findBySessionIsActive(isActive);
		assertTrue("testFindBySessionIsActive - return a Session Object", sessions.size() == 1);
		
		sessions = sessionDAO.findBySessionIsActive(!isActive);
		assertTrue("test_findBySessionIsActive - return 8 Session Objects", sessions.size() == 8);
	}
	
	/**
	 * Test {@link SessionDAO#deleteBySessionIsActive(Boolean)} </br>
	 * Delete all active sessions
	 * Search active sessions and expect returning an empty list </br> 
	 * 
	 */
	@Test
	public void test_deleteBySessionIsActive(){
		
		boolean isActive = true;
		sessionDAO.deleteBySessionIsActive(isActive);
		List<Session> sessions = sessionDAO.findBySessionIsActive(isActive);
		assertTrue("test_deleteBySessionIsActive", sessions.size() == 0);
	}
	/**
	 * Test for {@link SessionDAO#deleteByProperty(String[], Object[])} </br>
	 * Delete active sessions having session name = "2013-12-28 12:50:24" and expect returning a list of {@link Session} </br> 
	 * Search these active sessions and expect returning nothing </br> 
 
	 */
	@Test
	public void test_deleteByProperty(){
		
		sessionDAO.deleteByProperty(
				new String[]{SessionDAO.ID, SessionDAO.NAME, SessionDAO.IS_ACTIVE}, 
				new Object[]{2, "subject_1_session_2", false});
		
		List<Session> sessions = sessionDAO.findByProperty(
				new String[]{SessionDAO.ID, SessionDAO.NAME, SessionDAO.IS_ACTIVE}, 
				new Object[]{2, "subject_1_session_2", false});
		
		assertTrue("test_deleteByProperty", sessions.size() == 0);
	}
	/**
	 * Test {@link SessionDAO#findBySessionUserId(Long)} </br>
	 * Search sessions having creator id = 1 and expect returning a list of {@link Session} </br> 
	 */
	@Test
	@Rollback(false)
	public void test_findByUserId(){
		
		List<Session> sessions = sessionDAO.findBySessionUserId(1);
		assertTrue("test_findByUserId", sessions.size() > 0);
	}
	/**
	 * Test {@link SessionDAO#deleteByUserId(Long)} </br>
	 * Delete sessions having user id=1</br> 
	 * Search these sessions and expect returning nothing </br> 
 
	 */
	@Test
	public void test_deleteByUserId(){
		
		sessionDAO.deleteByUserId(1);
		List<Session> sessions = sessionDAO.findBySessionUserId(1);
		assertTrue("test_deleteByUserId", sessions.size() == 0);
	}	
	

	/**
	 * Test for {@link SessionDAO#findByProperty(String[],Object[])} </br>
	 * Search active sessions having session id=1 and expect returning a list of {@link Session} </br> 
 	 */
	@Test
	@Rollback(false)
	public void test_findByByProperty(){
		
		List<Session> sessions = sessionDAO.findByProperty(
				new String[]{SessionDAO.ID, SessionDAO.IS_ACTIVE}, 
				new Object[]{2, 0}); 
		assertTrue(sessions.size() == 1);
	}
	
	/**
	 * Test for {@link SessionDAO#deleteById(long id)} </br>
	 * Delete sessions having id=2 </br> 
	 * Search these sessions and expect returning nothing </br> 
 
	 */
	@Test
	public void test_deleteById(){
		
		sessionDAO.deleteById(2);
		List<Session> sessions = sessionDAO.findById(2); 
		assertTrue(sessions.size() == 0);
	}	
	/**
	 * Test for {@link SessionDAO#deleteBySubjectId(long)} </br>
	 * Delete sessions having subject id=2</br> 
	 * Search these sessions and expect returning nothing </br> 
 
	 */
	@Test
	public void test_deleteBySubjectId(){
		
		sessionDAO.deleteBySubjectId(2);
		List<Session> sessions = sessionDAO.findBySubjectId(2); 
		assertTrue(sessions.size() == 0);
	}
	/**
	 * Test for {@link SessionDAO#deleteByName(String)} </br>
	 * Delete  sessions having session name = "subject_2_session_4"</br> 
	 * Search these sessions and expect returning nothing </br> 
 	 */
	@Test
	public void test_deleteByName(){
		
		String sessionName = "subject_2_session_4";
		sessionDAO.deleteByName(sessionName);
		List<Session> sessions = sessionDAO.findByName(sessionName); 
		assertTrue(sessions.size() == 0);
	}	
	
}
