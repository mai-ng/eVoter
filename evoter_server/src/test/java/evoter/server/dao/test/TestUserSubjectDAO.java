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

import evoter.share.dao.UserSubjectDAO;
import evoter.share.model.UserSubject;
/**
 * Make test cases for {@link UserSubjectDAO} and {@link UserSubjectDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestUserSubjectDAO {

	@Autowired
	UserSubjectDAO userSubjectDAO;
	/**
	 * Test for {@link UserSubjectDAO#findAll()} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindAll(){
		List<UserSubject> userSubjects = userSubjectDAO.findAll();
		assertTrue("testFindAll", userSubjects.size() > 0);
	}
	/**
	 * Test for {@link UserSubjectDAO#insert(UserSubject)} </br>
	 */
	@Test
	public void testInsert(){
		UserSubject userSubject = new UserSubject();
		userSubject.setUserId(1);
		userSubject.setSubjectId(2);
		
		userSubjectDAO.insert(userSubject);
		List<UserSubject> userSubjects = userSubjectDAO.findByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID}, 
				new Object[]{1, 2});
		
		assertTrue("testInsert", userSubjects.size() == 1);
	}	

	/**
	 * Test for {@link UserSubjectDAO#findByUserId(long)} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindByUserId(){
		List<UserSubject> userSubjects = userSubjectDAO.findByUserId(2);
		assertTrue("testFindByUserId", userSubjects.size() > 0);
	}
	/**
	 * Test for {@link UserSubjectDAO#findBySubjectId(Long)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindBySubjectId(){
		List<UserSubject> userSubjects = userSubjectDAO.findBySubjectId(2);
		assertTrue("testFindBySubjectId", userSubjects.size() > 0);
	}
	
	/**
	 * Test {@link UserSubjectDAO#findByProperty(String[], Object[])} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindByProperty(){
		List<UserSubject> userSubjects = userSubjectDAO.findByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID}, 
				new Object[]{1, 3});
		
		assertTrue("testFindByProperty", userSubjects.size() == 1);
	}
	/**
	 * Test {@link UserSubjectDAO#deleteByProperty(String[], Object[])} </br>
	 * 
	 */
	@Test
	public void testDeleteByProperty(){
		
		userSubjectDAO.deleteByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID}, 
				new Object[]{1, 3});
		
		List<UserSubject> userSubjects = userSubjectDAO.findByProperty(
				new String[]{UserSubjectDAO.USER_ID, UserSubjectDAO.SUBJECT_ID}, 
				new Object[]{1, 3});
		
		assertTrue("testDeleteByProperty", userSubjects.size() == 0);
	}	
	/**
	 * Test {@link UserSubjectDAO#deleteByUserId(long)} </br>
	 */
	@Test
	public void testDeleteByUserId(){
		
		userSubjectDAO.deleteByUserId(1);
		List<UserSubject> userSubjects = userSubjectDAO.findByUserId(1);
		assertTrue("testDeleteByUserId", userSubjects.size() == 0);
	}
	/**
	 * Test {@link UserSubjectDAO#deleteBySubjectId(long)} </br>
	 */
	@Test
	public void testDeleteSubjectId(){
		
		userSubjectDAO.deleteBySubjectId(2);
		List<UserSubject> userSubjects = userSubjectDAO.findBySubjectId(2);
		assertTrue("testDeleteById", userSubjects.size() == 0);
	}	

}
