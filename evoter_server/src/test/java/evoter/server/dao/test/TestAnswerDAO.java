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
import evoter.share.dao.AnswerDAO;
import evoter.share.model.Answer;
/**
 * Make test cases for {@link AnswerDAO} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class TestAnswerDAO {
	
	@Autowired
	AnswerDAO answerDAO;
	
	
	@Test
	@Rollback(false)
	public void test_findAll(){
		List<Answer> answers = answerDAO.findAll();
		assertTrue("test_findAll", answers.size()>0);
	}

	/**
	 * Test {@link AnswerDAO#deleteById(long)} </br>
	 * Search {@link Answer} by {@link AnswerDAO#ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findById(){
		List<Answer> answers = answerDAO.findById(4);
		assertTrue("test_findById", answers.size() > 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteById(long)} </br>
	 * Search {@link Answer} by {@link AnswerDAO#ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findByQuestionId(){
		List<Answer> answers = answerDAO.findByQuestionId(4);
		assertTrue("test_findByQuestionId", answers.size() > 0);
	}

	@Test
	@Transactional
	public void test_findByAnswerText(){
		List<Answer> answers = answerDAO.findByAnswerText("Two hours.");
		assertTrue("test_findByAnswerText", answers.size() > 0);
		answers = answerDAO.findByAnswerText("anything else");
		assertTrue("test_findByAnswerText", answers.size() == 0);
	}
	/**
	 * Test {@link AnswerDAO#findByProperty(String[], Object[])} </br>
	 * Search {@link Answer} by {@link AnswerDAO#ID} and {@link AnswerDAO#QUESTION_ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findByProperty(){
		List<Answer> answers = answerDAO.findByProperty(
				new String[]{AnswerDAO.ID, AnswerDAO.QUESTION_ID}, 
				new Object[]{4,3});
		assertTrue("test_findByProperty", answers.size() > 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteById(long)} </br>
	 */
	@Test
	public void test_deleteById(){

		answerDAO.deleteById(8);
		List<Answer> answers = answerDAO.findById(8);
		assertTrue("test_deleteById", answers.size() == 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteByQuestionId(long)} </br>
	 */
	@Test
	public void test_deleteByQuestionId(){

		answerDAO.deleteByQuestionId(4);
		List<Answer> answers = answerDAO.findByQuestionId(4);
		assertTrue("test_deleteByQuestionId", answers.size() == 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteByAnswerText(String)} </br>
	 */
	@Test
	public void test_deleteByAnswerText(){

		String answerText = "My brother";
		answerDAO.deleteByAnswerText(answerText);
		List<Answer> answers = answerDAO.findByAnswerText(answerText);
		assertTrue("test_deleteByAnswerText", 0 == answers.size());		
	}
	
	@Test
	public void test_insert(){

		Answer answer = new Answer(4, "answer data for test");
		long id = answerDAO.insert(answer);
		List<Answer> answers = answerDAO.findById(id);
		assertTrue("test_insert", 1 == answers.size());
	}
	/**
	 * Test for {@link AnswerDAO#update(Answer)} </br>
	 */
	@Test
	public void test_update(){
		long answerId = 2;
		Answer answer = answerDAO.findById(answerId).get(0);
		answer.setAnswerText("new answer text");
		answer.setStatistics("1");
		
		answerDAO.update(answer);
		assertEquals("update() changes answer text", 
				answer.getAnswerText(), "new answer text");
		assertEquals("update() changes statistics value", 
				answer.getStatistics(), "1");
	}

}
