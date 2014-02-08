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
import org.springframework.transaction.annotation.Transactional;


import evoter.server.dao.impl.QuestionDAOImpl;
import evoter.share.dao.QuestionDAO;
import evoter.share.model.Question;
/**
 * Make test cases for {@link QuestionDAO} and {@link QuestionDAOImpl} </br>
 * 
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestQuestionDAO {
	
	@Autowired
	QuestionDAO questionDAO;
	/**
	 * Test {@link QuestionDAO#findAll()} </br>
	 * Expect returning a list of {@link Question} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindAll(){
		List<Question> questions = questionDAO.findAll();
		assertTrue("testFindAll", questions.size() > 0);
	}
	/**
	 * Test {@link QuestionDAO#findByProperty(String[], Object[])} </br>
	 * Expect returning a list of {@link Question} </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByProperty(){
		List<Question> questions = questionDAO.findByProperty(
				new String[]{QuestionDAO.ID, QuestionDAO.QUESTION_TYPE_ID}, new Object[]{2, 1});
		assertTrue("testFindByProperty", questions.size() > 0);
	}
	/**
	 * Test {@link QuestionDAO#findById(long)} </br>
	 * Case 1: Expect returning 1 {@link Question} object </br>
	 * Case 2: Expect returning a empty list </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindById(){
		List<Question> questions = questionDAO.findById(1);
		assertTrue("testFindById - Case 1", questions.size() == 1);	
		questions = questionDAO.findById(10000);
		assertTrue("testFindById - Case 2", questions.size() == 0);
	}
	/**
	 * Test {@link QuestionDAO#findByUserId(long)} </br>
	 * Case 1: Expect returning 1 List of {@link Question} object </br>
	 * Case 2: Expect returning a empty list </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByUserId(){
		List<Question> questions = questionDAO.findByUserId(1);
		assertTrue("testFindByUserId - Case 1", questions.size() > 0);	
		questions = questionDAO.findByUserId(89);
		assertTrue("testFindByUserId - Case 2", questions.size() == 0);

	}
	/**
	 * Test {@link QuestionDAO#findByQuestionTypeId(long)} </br>
	 * Case 1: Expect returning 1 List of {@link Question} object </br>
	 * Case 2: Expect returning a empty list </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByQuestionTypeId(){
		List<Question> questions = questionDAO.findByQuestionTypeId(6);
		assertTrue("testFindByQuestionTypeId - Case 1", questions.size() > 0);	
		questions = questionDAO.findByQuestionTypeId(89);
		assertTrue("testFindByQuestionTypeId - Case 2", questions.size() == 0);

	}
	/**
	 * Test {@link QuestionDAO#findByCreationDate(Timestamp)} </br>
	 * Case 1: Expect returning 1 List of {@link Question} object </br>
	 * Case 2: Expect returning a empty list </br>
	 * @throws ParseException 
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByCreationDate() throws ParseException{
		
		List<Question> questions = questionDAO.
				findByCreationDate(Timestamp.valueOf("2014-01-09 22:39:24"));
		assertTrue("testFindByCreationDate - Case 1", questions.size() > 0);
		
		questions = questionDAO.
				findByCreationDate(Timestamp.valueOf("2014-10-09 22:39:24"));
		assertTrue("testFindByCreationDate - Case 2", questions.size() == 0);
	}
	/**
	 * Test {@link QuestionDAO#findByParentId(long)} </br>
	 * Case 1: Expect returning 1 List of {@link Question} object </br>
	 * Case 2: Expect returning a empty list </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByParentId(){
		List<Question> questions = questionDAO.findByParentId(0);
		assertTrue("testFindByParentId - Case 1", questions.size() > 0);	
		questions = questionDAO.findByParentId(2);
		assertTrue("testFindByParentId - Case 2", questions.size() == 0);

	}	
	/**
	 * Test {@link QuestionDAO#findByQuestionText(String)} </br>
	 * Case 1: Expect returning 1 List of {@link Question} object </br>
	 * Case 2: Expect returning a empty list </br>
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void testFindByQuestionText(){
		List<Question> questions = questionDAO.findByQuestionText("Who are you waiting for?");
		assertTrue("findByQuestionText - Case 1", questions.size() > 0);	
		questions = questionDAO.findByQuestionText("anything else");
		assertTrue("findByQuestionText - Case 2", questions.size() == 0);

	}
	/**
	 * Test for {@link QuestionDAO#insert(Question)} </br>
	 * Expect the number of records of QUESTION table inscreasing 1 </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testInsert(){
		Question question = new Question(2, 1, "question test", new Timestamp(System.currentTimeMillis()), 0);
		long id = questionDAO.insert(question);
		List<Question> questions2 = questionDAO.findById(id);
		assertTrue(questions2.size() == 1);
	}
	/**
	 * Test for {@link QuestionDAO#deleteByProperty(String[], Object[])} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByProperty(){
		questionDAO.deleteByProperty(new String[]{
				QuestionDAO.ID, QuestionDAO.QUESTION_TYPE_ID}, 
				new Object[]{5, 6});
		List<Question> questions = questionDAO.findByProperty(new String[]{
				QuestionDAO.ID, QuestionDAO.QUESTION_TYPE_ID}, 
				new Object[]{5, 6});
		assertTrue(questions.size() == 0);
	}
	/**
	 * Test for {@link QuestionDAO#findById(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteById(){
		Question question = new Question(2, 1, "question test", 
				new Timestamp(System.currentTimeMillis()), 0);
		long id = questionDAO.insert(question);
		List<Question> questions = questionDAO.findById(id);
		assertTrue(questions.size() == 1);
		
		questionDAO.deleteById(id);
		questions = questionDAO.findById(id);
		assertTrue(questions.size() == 0);
	}
	/**
	 * Test for {@link QuestionDAO#deleteByUserId(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByUserId(){
		questionDAO.deleteByUserId(2);
		List<Question> questions = questionDAO.findByUserId(2);
		assertTrue("testDeleteByUserId", questions.size() == 0);
	}
	/**
	 * Test for {@link QuestionDAO#deleteByCreationDate(Timestamp)} </br>
	 * @throws ParseException 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByCreationDate() throws ParseException{
		
		Timestamp sqlDate = Timestamp.valueOf("2014-01-09 22:39:24");
		
		List<Question> questions = questionDAO.
				findByCreationDate(sqlDate);
		assertTrue(questions.size() > 0);
		questionDAO.deleteByCreationDate(sqlDate);
		questions = questionDAO.findByCreationDate(sqlDate);
		assertTrue(questions.size() == 0);
	}
	/**
	 * Test for {@link QuestionDAO#deleteByQuestionText(String)} </br>
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByQuestionText(){
		
		String questionText = "Interface can implement an interface?";
		List<Question> questions = questionDAO.findByQuestionText(questionText);
		assertTrue(questions.size() > 0);
		questionDAO.deleteByQuestionText(questionText);
		questions = questionDAO.findByQuestionText(questionText);
		assertTrue(questions.size() == 0);
	}
	/**
	 * Test for {@link QuestionDAO#deleteByQuestionTypeId(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByQuestionTypeId(){
		
		long questionTypeId = 6;
		List<Question> questions = questionDAO.findByQuestionTypeId(questionTypeId);
		assertTrue(questions.size() > 0);
		questionDAO.deleteByQuestionTypeId(questionTypeId);
		questions = questionDAO.findByQuestionTypeId(questionTypeId);
		assertTrue(questions.size() == 0);
	}	
	/**
	 * Test for {@link QuestionDAO#deleteByParentId(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByParentId(){
		
		long parentId = 0;
		List<Question> questions = questionDAO.findByParentId(parentId);
		assertTrue(questions.size() > 0);
		questionDAO.deleteByQuestionTypeId(parentId);
		questions = questionDAO.findByQuestionTypeId(parentId);
		assertTrue(questions.size() == 0);
	}	

}
