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

import evoter.server.dao.impl.UserTypeDAOImpl;
import evoter.share.dao.UserTypeDAO;
import evoter.share.model.UserType;

/**
 * Make test cases for {@link UserTypeDAO} and {@link UserTypeDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestUserTypeDAO{

	@Autowired
	UserTypeDAO userTypeDAO;
	
	@Test
	public void testFindAll(){
		List<UserType> userTypes = userTypeDAO.findAll();
		assertTrue("testFindAll", userTypes.size() > 0);
	}

	/**
	 * Test {@link UserTypeDAO#findById(long)} </br>
	 * Search {@link UserType} by {@link UserTypeDAO#ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindById(){
		List<UserType> userTypes = userTypeDAO.findById(2);
		assertTrue("testFindById", userTypes.size() == 1);
	}
	/**
	 * Test for {@link UserTypeDAO#findByUserTypeValue(String)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByUserTypeValue(){
		List<UserType> userTypes = userTypeDAO.findByUserTypeValue("Teacher");
		assertTrue("testFindByUserTypeValue", userTypes.size() == 1);
	}
	/**
	 * Test {@link UserTypeDAO#findByProperty(String[], Object[])} </br>
	 * Search {@link UserType} by {@link UserTypeDAO#ID} and {@link UserTypeDAO#USER_TYPE_VALUE} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindByProperty(){
		List<UserType> userTypes = userTypeDAO.findByProperty(
				new String[]{UserTypeDAO.ID, UserTypeDAO.USER_TYPE_VALUE}, 
				new Object[]{2,"Teacher"});
		
		assertTrue("testFindByProperty", userTypes.size() == 1);
	}
	/**
	 * Test {@link UserTypeDAO#deleteByProperty(String[], Object[])} </br>
	 * 
	 */
	@Test
	public void testDeleteByProperty(){
		
		userTypeDAO.deleteByProperty(
				new String[]{UserTypeDAO.ID, UserTypeDAO.USER_TYPE_VALUE}, 
				new Object[]{2,"Teacher"});
		
		List<UserType> userTypes = userTypeDAO.findByProperty(
				new String[]{UserTypeDAO.ID, UserTypeDAO.USER_TYPE_VALUE}, 
				new Object[]{2,"Teacher"});
		
		assertTrue("testDeleteByProperty", userTypes.size() == 0);
	}	
	/**
	 * Test {@link UserTypeDAO#deleteById(long)} </br>
	 */
	@Test
	public void testDeleteById(){
		
		userTypeDAO.deleteById(1);
		List<UserType> userTypes = userTypeDAO.findById(1);
		assertTrue("testDeleteById", userTypes.size() == 0);
	}

	/**
	 * Test {@link UserTypeDAO#deleteByUserTypeValue(String)} </br>
	 */
	@Test
	public void testDeleteByUserTypeValue(){
		
		String userTypeValue = "Teacher";
		userTypeDAO.deleteByUserTypeValue(userTypeValue);
		
		List<UserType> userTypes = userTypeDAO.findByUserTypeValue(userTypeValue);
		assertTrue("testDeleteByUserTypeValue", userTypes.size() == 0);		
	}
	
	@Test
	public void testInsert(){
		
		UserType insert = new UserType("user type value for testing");
		long id = userTypeDAO.insert(insert);
		List<UserType> userTypes = userTypeDAO.findById(id);
		assertTrue(userTypes.size() == 1);
	}


}
