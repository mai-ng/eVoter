package evoter.server.dao.test;


import static org.junit.Assert.assertEquals;
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

import evoter.server.dao.impl.QuestionTypeDAOImpl;
import evoter.share.dao.QuestionTypeDAO;
import evoter.share.model.QuestionType;

/**
 * Make test cases for {@link QuestionTypeDAO} and {@link QuestionTypeDAOImpl} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestQuestionTypeDAO {

	/**
	 * Create a {@link QuestionTypeDAOImpl} instance
	 */
	@Autowired
	QuestionTypeDAO questionTypeDAO;
	
	/**
	 * Test for {@link QuestionTypeDAO#findAll()} </br>
	 * Select all records in question_type table and expect returning a list {@link QuestionType} </br?
	 */
	@Test
	@Rollback(false)
	public void test_findAll(){
		List<QuestionType> types = questionTypeDAO.findAll(); 
		assertTrue(types.size() > 0);
	}
	/**
	 * Test for {@link QuestionTypeDAO#findById(long)} </br>
	 * Select question type record having id=3 and expect returing 1 record </br>
	 */
	@Test
	@Rollback(false)
	public void test_findById(){
		
		List<QuestionType> types = questionTypeDAO.findById(3); 
		assertTrue(types.size() == 1);
	}
	/**
	 * Test for {@link QuestionTypeDAO#findByQuestionTypeValue(String)} </br>
	 * Search {@link QuestionType} having value="Multi Radio Button" </br>
	 * Expect returning one record</br>
	 */
	@Test
	@Rollback(false)
	public void test_findByQuestionTypeValue(){
		
		List<QuestionType> types = questionTypeDAO.findByQuestionTypeValue("Multi Radio Button"); 
		assertTrue(types.size() == 1);
	}
	/**
	 * Test for {@link QuestionTypeDAO#findByProperty(String[],Object[])} </br>
	 * Search all {@link QuestionType} having question id=2 and value="Multi Radio Button" </br>
	 * Expect returning one record </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByByProperty(){
		
		List<QuestionType> types = questionTypeDAO.findByProperty(
				new String[]{QuestionTypeDAO.ID, QuestionTypeDAO.QUESTION_TYPE_VALUE}, 
				new Object[]{2, "Multi Radio Button"}); 
		assertTrue(types.size() == 1);
	}
	
	/**
	 * Test for {@link QuestionTypeDAO#deleteByProperty(String[], Object[])} </br>
	 * Delete all {@link QuestionType} having id=2 and value="Multi Radio Button" </br>
	 * Search records with these conditions and expect returning an empty list </br>
	 */
	@Test
	public void test_deleteByByProperty(){
		
		questionTypeDAO.deleteByProperty(
				new String[]{QuestionTypeDAO.ID, QuestionTypeDAO.QUESTION_TYPE_VALUE}, 
				new Object[]{2, "Multi Radio Button"});
		
		List<QuestionType> types = questionTypeDAO.findByProperty(
				new String[]{QuestionTypeDAO.ID, QuestionTypeDAO.QUESTION_TYPE_VALUE}, 
				new Object[]{2, "Multi Radio Button"}); 
		
		assertTrue(types.size() == 0);
	}
	/**
	 * Test for {@link QuestionTypeDAO#deleteById(long id)} </br>
	 * Delete all {@link QuestionType} having id=2 in question_type table</br>
	 * Search records with id=2 and expect returning an empty list </br> 
	 */
	@Test
	public void test_deleteById(){
		
		questionTypeDAO.deleteById(2);
		List<QuestionType> types = questionTypeDAO.findById(2); 
		assertTrue(types.size() == 0);
	}	
	/**
	 * Test for {@link QuestionTypeDAO#deleteByQuestionTypeValue(String)} </br>
	 * Delete all {@link QuestionType} having value="Multi Radio Button" in question_type table </br>
	 * Search records with these conditions and expect returning an empty list </br> 
	 */
	@Test
	public void test_deleteByQuestionTypeValue(){
		
		String text = "Multi Radio Button";
		questionTypeDAO.deleteByQuestionTypeValue(text);
		List<QuestionType> types = questionTypeDAO.findByQuestionTypeValue(text);
		assertTrue(types.size() == 0);
	}		
	/**
	 * Test for {@link QuestionTypeDAO#insert()} </br>
	 * Insert a {@link QuestionType} record to question_type table and returning a generated id </br>
	 * Select a record with the generated id and expect its properties are the same with created one </br>
	 */
	@Test
	public void test_insert(){
		
		QuestionType questionType = new QuestionType("question type for test");
		long id = questionTypeDAO.insert(questionType);
		List<QuestionType> types = questionTypeDAO.findById(id); 
		assertTrue(types.size() == 1);
		assertEquals(types.get(0).getQuestionTypeValue(), "question type for test");
	}	
	

}
