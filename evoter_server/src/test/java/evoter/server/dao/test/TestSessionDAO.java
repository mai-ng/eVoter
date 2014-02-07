package evoter.server.dao.test;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestSessionDAO {
	
	@Autowired
	SessionDAO sessionDAO;
	
	/**
	 * Test for {@link SessionDAO#findAll()} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindAll(){
		List<Session> sessions = sessionDAO.findAll(); 
		assertTrue(sessions.size() > 0);
	}
	/**
	 * Test for {@link SessionDAO#findById(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindById(){
		
		List<Session> sessions = sessionDAO.findById(3); 
		assertTrue(sessions.size() == 1);
	}
	/**
	 * Test for {@link SessionDAO#findBySubjectId(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindBySubjectId(){
		
		List<Session> sessions = sessionDAO.findBySubjectId(1); 
		assertTrue(sessions.size() > 0);
	}
	/**
	 * Test for {@link SessionDAO#findById(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByName(){
		
		List<Session> sessions = sessionDAO.findByName("subject_1_session_1"); 
		assertTrue(sessions.size() > 0);
	}
	/**
	 * Test {@link SessionDAO#findByCreationDate(Timestamp)} </br>
	 * Case 1: Expect returning 1 List of {@link Session} object </br>
	 * Case 2: Expect returning a empty list </br>
	 * @throws ParseException 
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByCreationDate() throws ParseException{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = dateFormat.parse("2013-12-28 12:50:24");
		Timestamp sqlDate = new Timestamp(date.getTime());

		List<Session> sessions = sessionDAO.findByCreationDate(sqlDate);
		assertTrue("testFindByCreationDate - Case 1", sessions.size() > 0);
		
		date = dateFormat.parse("2014-10-09 22:39:24");
		sqlDate = new Timestamp(date.getTime());
		sessions = sessionDAO.findByCreationDate(sqlDate);
		assertTrue("testFindByCreationDate - Case 2", sessions.size() == 0);
	}	
	/**
	 * Test {@link SessionDAO#deleteByCreationDate(Timestamp)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByCreationDate() throws ParseException{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = dateFormat.parse("2013-12-28 12:50:24");
		Timestamp sqlDate = new Timestamp(date.getTime());

		sessionDAO.deleteByCreationDate(sqlDate);
		List<Session> sessions = sessionDAO.findByCreationDate(sqlDate);
		assertTrue("testDeleteByCreationDate", sessions.size() == 0);
		
	}		
	/**
	 * Test {@link SessionDAO#findBySessionIsActive(Boolean)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindBySessionIsActive(){
		
		boolean isActive = true;
		
		List<Session> sessions = sessionDAO.findBySessionIsActive(isActive);
		assertTrue("testFindBySessionIsActive - return a Session Object", sessions.size() == 1);
		
		sessions = sessionDAO.findBySessionIsActive(!isActive);
		assertTrue("testFindBySessionIsActive - return 8 Session Objects", sessions.size() == 8);
	}
	
	/**
	 * Test {@link SessionDAO#deleteBySessionIsActive(Boolean)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBySessionIsActive(){
		
		boolean isActive = true;
		sessionDAO.deleteBySessionIsActive(isActive);
		List<Session> sessions = sessionDAO.findBySessionIsActive(isActive);
		assertTrue("testDeleteBySessionIsActive", sessions.size() == 0);
	}
	/**
	 * Test for {@link SessionDAO#deleteByProperty(String[], Object[])} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByProperty(){
		
		sessionDAO.deleteByProperty(
				new String[]{SessionDAO.ID, SessionDAO.NAME, SessionDAO.IS_ACTIVE}, 
				new Object[]{2, "subject_1_session_2", false});
		
		List<Session> sessions = sessionDAO.findByProperty(
				new String[]{SessionDAO.ID, SessionDAO.NAME, SessionDAO.IS_ACTIVE}, 
				new Object[]{2, "subject_1_session_2", false});
		
		assertTrue("testDeleteByProperty", sessions.size() == 0);
	}
	/**
	 * Test {@link SessionDAO#findBySessionUserId(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByUserId(){
		
		List<Session> sessions = sessionDAO.findBySessionUserId(1);
		assertTrue("testFindByUserId", sessions.size() > 0);
	}
	/**
	 * Test {@link SessionDAO#deleteByUserId(Long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByUserId(){
		
		sessionDAO.deleteByUserId(1);
		List<Session> sessions = sessionDAO.findBySessionUserId(1);
		assertTrue("testDeleteByUserId", sessions.size() == 0);
	}	
	

	/**
	 * Test for {@link SessionDAO#findByProperty(String[],Object[])} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByByProperty(){
		
		List<Session> sessions = sessionDAO.findByProperty(
				new String[]{SessionDAO.ID, SessionDAO.IS_ACTIVE}, 
				new Object[]{2, 0}); 
		assertTrue(sessions.size() == 1);
	}
	
	/**
	 * Test for {@link SessionDAO#deleteById(long id)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteById(){
		
		sessionDAO.deleteById(2);
		List<Session> sessions = sessionDAO.findById(2); 
		assertTrue(sessions.size() == 0);
	}	
	/**
	 * Test for {@link SessionDAO#deleteBySubjectId(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBySubjectId(){
		
		sessionDAO.deleteBySubjectId(2);
		List<Session> sessions = sessionDAO.findBySubjectId(2); 
		assertTrue(sessions.size() == 0);
	}
	/**
	 * Test for {@link SessionDAO#deleteByName(String)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByName(){
		
		String sessionName = "subject_2_session_4";
		sessionDAO.deleteByName(sessionName);
		List<Session> sessions = sessionDAO.findByName(sessionName); 
		assertTrue(sessions.size() == 0);
	}	
	
}
