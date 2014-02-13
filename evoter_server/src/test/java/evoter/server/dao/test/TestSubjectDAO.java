package evoter.server.dao.test;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import evoter.server.dao.impl.SubjectDAOImpl;
import evoter.share.dao.SubjectDAO;

import evoter.share.model.Subject;
/**
 * Make test cases for {@link SubjectDAO} and {@link SubjectDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestSubjectDAO {

	@Autowired
	SubjectDAO subjectDAO;
	/**
	 * Test for {@link SubjectDAO#findAll()} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindAll(){
		List<Subject> subjects = subjectDAO.findAll();
		assertTrue("testFindAll", subjects.size() > 0);
	}

	/**
	 * Test {@link SubjectDAO#findById(long)} </br>
	 * Search {@link Subject} by {@link subjectDAO#ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindById(){
		List<Subject> subjects = subjectDAO.findById(2);
		assertTrue("testFindById", subjects.size() == 1);
	}
	/**
	 * Test for {@link subjectDAO#findByTitle(String)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByTitle(){
		List<Subject> subjects = subjectDAO.findByTitle("Object Oriented Programming");
		assertTrue("testFindByTitle", subjects.size() > 0);
	}
	/**
	 * Test for {@link SubjectDAO#findByCreationDate(Timestamp)} </br>
	 */
	@Test
	public void testFindByCreationDate(){
		
		List<Subject> subjects = subjectDAO.
				findByCreationDate(Timestamp.valueOf("2013-12-28 12:50:24"));
		assertTrue("testFindByCreationDate", subjects.size() > 1);
	}
	/**
	 * Test {@link SubjectDAO#findByProperty(String[], Object[])} </br>
	 * Search {@link Subject} by {@link SubjectDAO#ID} and {@link SubjectDAO#TITLE} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindByProperty(){
		List<Subject> subjects = subjectDAO.findByProperty(
				new String[]{SubjectDAO.ID, SubjectDAO.TITLE}, 
				new Object[]{1, "Object Oriented Programming"});
		
		assertTrue("testFindByProperty", subjects.size() == 1);
	}
	/**
	 * Test {@link SubjectDAO#deleteByProperty(String[], Object[])} </br>
	 * 
	 */
	@Test
	public void testDeleteByProperty(){
		
		subjectDAO.deleteByProperty(
				new String[]{SubjectDAO.ID, SubjectDAO.TITLE}, 
				new Object[]{2,"Testing Metrics"});
		
		List<Subject> subjects = subjectDAO.findByProperty(
				new String[]{SubjectDAO.ID, SubjectDAO.TITLE}, 
				new Object[]{2,"Testing Metrics"});
		
		assertTrue("testDeleteByProperty", subjects.size() == 0);
	}	
	/**
	 * Test {@link SubjectDAO#deleteById(long)} </br>
	 */
	@Test
	public void testDeleteById(){
		
		subjectDAO.deleteById(1);
		List<Subject> subjects = subjectDAO.findById(1);
		assertTrue("testDeleteById", subjects.size() == 0);
	}
	/**
	 * Test {@link SubjectDAO#deleteByTitle(String)} </br>
	 */
	@Test
	public void testDeleteByTitle(){
		
		subjectDAO.deleteByTitle("Testing Metrics");
		List<Subject> subjects = subjectDAO.findByTitle("Testing Metrics");
		assertTrue("testDeleteByTitle", subjects.size() == 0);
	}	
	/**
	 * Test {@link SubjectDAO#deleteByCreationDate(Timestamp)} </br>
	 */
	@Test
	public void testDeleteByCreationDate(){
		
		subjectDAO.deleteByCreationDate(Timestamp.valueOf("2013-12-28 12:50:24"));
		List<Subject> subjects = subjectDAO.
				findByCreationDate(Timestamp.valueOf("2013-12-28 12:50:24"));
		assertTrue("testDeleteByCreationDate", subjects.size() == 0);
	}	

}
