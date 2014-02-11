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

import evoter.server.dao.impl.QuestionTypeDAOImpl;
import evoter.share.dao.QuestionTypeDAO;
import evoter.share.model.QuestionType;

/**
 * Make test cases for {@link QuestionTypeDAO} and {@link QuestionTypeDAOImpl} </br>
 * @author btdiem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestQuestionTypeDAO {

	@Autowired
	QuestionTypeDAO questionTypeDAO;
	
	/**
	 * Test for {@link QuestionTypeDAO#findAll()} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindAll(){
		List<QuestionType> types = questionTypeDAO.findAll(); 
		assertTrue(types.size() > 0);
	}
	/**
	 * Test for {@link QuestionTypeDAO#findById(long)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindById(){
		
		List<QuestionType> types = questionTypeDAO.findById(3); 
		assertTrue(types.size() == 1);
	}
	/**
	 * Test for {@link QuestionTypeDAO#findByQuestionTypeValue(String)} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByQuestionTypeValue(){
		
		List<QuestionType> types = questionTypeDAO.findByQuestionTypeValue("Multi Radio Button"); 
		assertTrue(types.size() == 1);
	}
	/**
	 * Test for {@link QuestionTypeDAO#findByProperty(String[],Object[])} </br>
	 */
	@Test
	@Rollback(false)
	public void testFindByByProperty(){
		
		List<QuestionType> types = questionTypeDAO.findByProperty(
				new String[]{QuestionTypeDAO.ID, QuestionTypeDAO.QUESTION_TYPE_VALUE}, 
				new Object[]{2, "Multi Radio Button"}); 
		assertTrue(types.size() == 1);
	}
	
	/**
	 * Test for {@link QuestionTypeDAO#deleteByProperty(String[], Object[])} </br>
	 */
	@Test
	public void testDeleteByByProperty(){
		
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
	 */
	@Test
	public void testDeleteById(){
		
		questionTypeDAO.deleteById(2);
		List<QuestionType> types = questionTypeDAO.findById(2); 
		assertTrue(types.size() == 0);
	}	
	/**
	 * Test for {@link QuestionTypeDAO#deleteByQuestionTypeValue(String)} </br>
	 */
	@Test
	public void testDeleteByQuestionTypeValue(){
		
		String text = "Multi Radio Button";
		questionTypeDAO.deleteByQuestionTypeValue(text);
		List<QuestionType> types = questionTypeDAO.findByQuestionTypeValue(text);
		assertTrue(types.size() == 0);
	}		
	/**
	 * Test for {@link QuestionTypeDAO#insert()} </br>
	 */
	@Test
	public void testInsert(){
		
		QuestionType questionType = new QuestionType("question type for test");
		long id = questionTypeDAO.insert(questionType);
		List<QuestionType> types = questionTypeDAO.findById(id); 
		assertTrue(types.size() == 1);
	}	
	

}
