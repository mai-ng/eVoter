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

import evoter.server.dao.impl.UserSubjectDAOImpl;
import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.UserSubject;
/**
 * Make test cases for {@link UserSubjectDAO} and {@link UserSubjectDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestUserSubjectDAO {

	/**
	 * Create {@link UserSubjectDAOImpl} instance
	 */
	@Autowired
	UserSubjectDAO userSubjectDAO;
	/**
	 * Test for {@link UserSubjectDAO#findAll()} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findAll(){
		List<UserSubject> userSubjects = userSubjectDAO.findAll();
		assertTrue("test_findAll", userSubjects.size() > 0);
	}
	/**
	 * Test for {@link UserSubjectDAO#insert(UserSubject)} </br>
	 */
	@Test
	public void test_insert(){
		UserSubject userSubject = new UserSubject();
		userSubject.setUserId(1);
		userSubject.setSubjectId(2);
		
		userSubjectDAO.insert(userSubject);
		List<UserSubject> userSubjects = userSubjectDAO.findByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID}, 
				new Object[]{1, 2});
		
		assertTrue("test_insert", userSubjects.size() == 1);
	}	

	/**
	 * Test for {@link UserSubjectDAO#findByUserId(long)} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findByUserId(){
		List<UserSubject> userSubjects = userSubjectDAO.findByUserId(2);
		assertTrue("test_findByUserId", userSubjects.size() > 0);
	}
	/**
	 * Test for {@link UserSubjectDAO#findBySubjectId(Long)} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findBySubjectId(){
		List<UserSubject> userSubjects = userSubjectDAO.findBySubjectId(2);
		assertTrue("test_findBySubjectId", userSubjects.size() > 0);
	}
	
	/**
	 * Test {@link UserSubjectDAO#findByProperty(String[], Object[])} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findByProperty(){
		List<UserSubject> userSubjects = userSubjectDAO.findByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID}, 
				new Object[]{1, 3});
		
		assertTrue("test_findByProperty", userSubjects.size() == 1);
	}
	/**
	 * Test {@link UserSubjectDAO#deleteByProperty(String[], Object[])} </br>
	 * 
	 */
	@Test
	public void test_deleteByProperty(){
		
		userSubjectDAO.deleteByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID}, 
				new Object[]{1, 3});
		
		List<UserSubject> userSubjects = userSubjectDAO.findByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID}, 
				new Object[]{1, 3});
		
		assertTrue("test_deleteByProperty", userSubjects.size() == 0);
	}	
	/**
	 * Test {@link UserSubjectDAO#deleteByUserId(long)} </br>
	 */
	@Test
	public void test_deleteByUserId(){
		
		userSubjectDAO.deleteByUserId(1);
		List<UserSubject> userSubjects = userSubjectDAO.findByUserId(1);
		assertTrue("test_deleteByUserId", userSubjects.size() == 0);
	}
	/**
	 * Test {@link UserSubjectDAO#deleteBySubjectId(long)} </br>
	 */
	@Test
	public void test_deleteSubjectId(){
		
		userSubjectDAO.deleteBySubjectId(2);
		List<UserSubject> userSubjects = userSubjectDAO.findBySubjectId(2);
		assertTrue("test_deleteById", userSubjects.size() == 0);
	}	

}
