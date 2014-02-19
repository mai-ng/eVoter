package evoter.server.dao.test;


import static org.junit.Assert.assertEquals;
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
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestQuestionDAO {
	
	/**
	 * Create an {@link QuestionDAOImpl} instance
	 */
	@Autowired
	QuestionDAO questionDAO;
	/**
	 * Test {@link QuestionDAO#findAll()} </br>
	 * Select all records in question table and expect returning a list of {@link Question} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findAll(){
		List<Question> questions = questionDAO.findAll();
		assertTrue("test_findAll", questions.size() > 0);
	}
	/**
	 * Test {@link QuestionDAO#findByProperty(String[], Object[])} </br>
	 * Search all questions having question ID=2 and question type ID=1 </br>
	 * Expect returning a list of {@link Question} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByProperty(){
		List<Question> questions = questionDAO.findByProperty(
				new String[]{QuestionDAO.ID, QuestionDAO.QUESTION_TYPE_ID}
				, new Object[]{2, 1});
		assertTrue("test_findByProperty", questions.size() > 0);
	}
	/**
	 * Test {@link QuestionDAO#findById(long)} </br>
	 * Search question having id=1 and expect returning returning 1 {@link Question} record </br>
	 * Search question having id=1000 and expect returning a empty list </br>
	 */
	@Test
	@Rollback(false)
	public void test_findById(){
		List<Question> questions = questionDAO.findById(1);
		assertTrue("testFindById - Case 1", questions.size() == 1);	
		questions = questionDAO.findById(10000);
		assertTrue("test_findById - Case 2", questions.size() == 0);
	}
	/**
	 * Test {@link QuestionDAO#findByUserId(long)} </br>
	 * Search all questions having user ID=1 and expect returning 1 List of {@link Question} object </br>
	 * Search all questions having user ID=89 and expect returning a empty list </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByUserId(){
		List<Question> questions = questionDAO.findByUserId(1);
		assertTrue("test_findByUserId - Case 1", questions.size() > 0);	
		questions = questionDAO.findByUserId(89);
		assertTrue("test_findByUserId - Case 2", questions.size() == 0);

	}
	/**
	 * Test {@link QuestionDAO#findByQuestionTypeId(long)} </br>
	 * Search all questions having question type ID=6 and expect returning 1 List of {@link Question} object </br>
	 * Search all questions having question type ID=89 and expect returning a empty list </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByQuestionTypeId(){
		List<Question> questions = questionDAO.findByQuestionTypeId(6);
		assertTrue("test_findByQuestionTypeId - Case 1", questions.size() > 0);	
		questions = questionDAO.findByQuestionTypeId(89);
		assertTrue("test_findByQuestionTypeId - Case 2", questions.size() == 0);

	}
	/**
	 * Test {@link QuestionDAO#findByCreationDate(Timestamp)} </br>
	 * Search all questions having creation date="2014-01-09 22:39:24" and expect returning 1 List of {@link Question} object </br>
	 * Search all questions having creation date="2014-10-09 22:39:24" and expect returning a empty list </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByCreationDate(){
		
		List<Question> questions = questionDAO.
				findByCreationDate(Timestamp.valueOf("2014-01-09 22:39:24"));
		assertTrue("test_findByCreationDate - Case 1", questions.size() > 0);
		
		questions = questionDAO.
				findByCreationDate(Timestamp.valueOf("2014-10-09 22:39:24"));
		assertTrue("test_findByCreationDate - Case 2", questions.size() == 0);
	}
	/**
	 * Test {@link QuestionDAO#findByParentId(long)} </br>
	 * Search all questions having parent ID=0 and expect returning 1 List of {@link Question} object </br>
	 * Search all questions having parent ID=3 and expect returning a empty list </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByParentId(){
		List<Question> questions = questionDAO.findByParentId(0);
		assertTrue("test_findByParentId - Case 1", questions.size() > 0);	
		questions = questionDAO.findByParentId(2);
		assertTrue("test_findByParentId - Case 2", questions.size() == 0);

	}	
	/**
	 * Test {@link QuestionDAO#findByQuestionText(String)} </br>
	 * Case 1: Expect returning 1 List of {@link Question} object </br>
	 * Case 2: Expect returning a empty list </br>
	 */
	@Test
	@Rollback(false)
	public void test_findByQuestionText(){
		List<Question> questions = questionDAO.findByQuestionText("Who are you waiting for?");
		assertTrue("test_findByQuestionText - Case 1", questions.size() > 0);	
		questions = questionDAO.findByQuestionText("anything else");
		assertTrue("test_findByQuestionText - Case 2", questions.size() == 0);

	}
	/**
	 * Test for {@link QuestionDAO#insert(Question)} </br>
	 * insert a new records returning a generated id </br>
	 * 
	 */
	@Test
	public void test_insert(){
		Question question = new Question(2, 1, "question test", new Timestamp(System.currentTimeMillis()), 0);
		long id = questionDAO.insert(question);
		List<Question> questions2 = questionDAO.findById(id);
		assertTrue(questions2.size() == 1);
	}
	/**
	 * Test for {@link QuestionDAO#deleteByProperty(String[], Object[])} </br>
	 * Delete all questions that have question id=5 and question type id=6 </br>
	 * Expect returning an empty array when searching all questions with these conditons </br>
	 */
	@Test
	public void test_deleteByProperty(){
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
	 * Delete all questions that have id=1 </br>
	 * Expect returning an empty array when searching all questions having id=1 </br>
	 */
	@Test
	public void test_deleteById(){
		
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
	 * Delete all questions that have user id=2 </br>
	 * Expect returning an empty array when searching all questions having user id=2 </br>
	 */
 
	@Test
	public void test_deleteByUserId(){
		questionDAO.deleteByUserId(2);
		List<Question> questions = questionDAO.findByUserId(2);
		assertTrue("test_deleteByUserId", questions.size() == 0);
	}
	/**
	 * Test for {@link QuestionDAO#deleteByCreationDate(Timestamp)} </br>
	 * Delete all questions that have creation date=2014-01-09 22:39:24 </br>
	 * Expect returning an empty array when searching all questions having creation date=2014-01-09 22:39:24 </br>
	 */
	@Test
	public void test_deleteByCreationDate(){
		
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
	 * Delete all questions that have question text=Interface can implement an interface? </br>
	 * Expect returning an empty array when searching all questions having question text=Interface can implement an interface?
	 * 
	 */
	@Test
	public void test_deleteByQuestionText(){
		
		String questionText = "Interface can implement an interface?";
		List<Question> questions = questionDAO.findByQuestionText(questionText);
		assertTrue(questions.size() > 0);
		questionDAO.deleteByQuestionText(questionText);
		questions = questionDAO.findByQuestionText(questionText);
		assertTrue(questions.size() == 0);
	}
	/**
	 * Test for {@link QuestionDAO#deleteByQuestionTypeId(long)} </br>
	 * Delete all questions that have question type ID=6 </br>
	 * Expect returning an empty array when searching all questions having question type ID=6
	 */
	@Test
	public void test_deleteByQuestionTypeId(){
		
		long questionTypeId = 6;
		List<Question> questions = questionDAO.findByQuestionTypeId(questionTypeId);
		assertTrue(questions.size() > 0);
		questionDAO.deleteByQuestionTypeId(questionTypeId);
		questions = questionDAO.findByQuestionTypeId(questionTypeId);
		assertTrue(questions.size() == 0);
	}	
	/**
	 * Test for {@link QuestionDAO#deleteByParentId(long)} </br>
	 * Delete all questions that have parent ID=0 </br>
	 * Expect returning an empty array when searching all questions having parent ID=0
	 */
	@Test
	public void test_deleteByParentId(){
		
		long parentId = 0;
		List<Question> questions = questionDAO.findByParentId(parentId);
		assertTrue(questions.size() > 0);
		questionDAO.deleteByQuestionTypeId(parentId);
		
		questions = questionDAO.findByQuestionTypeId(parentId);
		assertTrue(questions.size() == 0);
	}
	/**
	 * Test for {@link QuestionDAO#update(Question)}
	 * Change parent question ID, question text, creation date, status of question ID=1 </br>
	 * Expect the changes are updated when searching question ID=2 again </br>
	 */
	@Test
	public void test_update(){

		long questionId = 1;
		Question question = questionDAO.findById(questionId).get(0);
		question.setParentId(4);
		question.setQuestionText("new question text");
		question.setCreationDate(Timestamp.valueOf("2000-01-09 22:39:24"));
		question.setQuestionTypeId(3);
		question.setStatus(2);
		questionDAO.update(question);
		
		question = questionDAO.findById(questionId).get(0);
		assertEquals("update() changes parent id", question.getParentId(), 4);
		assertEquals("update() changes question text", question.getQuestionText(), "new question text");
		assertEquals("update() changes creation date", question.getCreationDate(), 
				Timestamp.valueOf("2000-01-09 22:39:24"));
		assertEquals("update() changes question type", question.getQuestionTypeId(), 3);
		assertEquals("update() changes question status", question.getStatus(), 2);
		
		
	}

}
