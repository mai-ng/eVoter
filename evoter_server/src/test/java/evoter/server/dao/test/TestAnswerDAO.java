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

import evoter.server.dao.impl.AnswerDAOImpl;
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
	/**
	 * Create a {@link AnswerDAOImpl} instance
	 */
	@Autowired
	AnswerDAO answerDAO;
	
	/**
	 * Test for {@link AnswerDAO#findAll()} </br>
	 * Select all {@link Answer} in answer table </br>
	 * Expect returning a list of {@link Answer} </br>
	 */
	@Test
	@Rollback(false)
	public void test_findAll(){
		List<Answer> answers = answerDAO.findAll();
		assertTrue("test_findAll", answers.size()>0);
	}

	/**
	 * Test {@link AnswerDAO#findById(long)} </br>
	 * Search all {@link Answer} by id=4 in answer table </br>
	 * Expect returning a list of {@link Answer} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findById(){
		List<Answer> answers = answerDAO.findById(4);
		assertTrue("test_findById", answers.size() > 0);
	}
	/**
	 * Test {@link AnswerDAO#findByQuestionId(long)} </br>
	 * Search {@link Answer} by question id=4 in answer table </br>
	 * Expect returning a list of {@link Answer} </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void test_findByQuestionId(){
		List<Answer> answers = answerDAO.findByQuestionId(4);
		assertTrue("test_findByQuestionId", answers.size() > 0);
	}

	/**
	 * Test for {@link AnswerDAO#findByAnswerText(String)} </br>
	 * Search answers with answer text condition="Two hours." and expect returning not empty list </br>
	 * Search answers with answer text condition="anything else" and expect returning an empty list </br>
	 */
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
	 * Search answers answer ID=4 and question ID=3 in answer table</br>
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
	 * Delete an answer ID=8 in answer table and expect returning an empty list when searching answer ID=8 again </br>
	 */
	@Test
	public void test_deleteById(){

		answerDAO.deleteById(8);
		List<Answer> answers = answerDAO.findById(8);
		assertTrue("test_deleteById", answers.size() == 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteByQuestionId(long)} </br>
	 * Delete answers of question ID=4 in answer table and expect returning an empty list when searching all answers of question ID=4 </br>
	 */
	@Test
	public void test_deleteByQuestionId(){

		answerDAO.deleteByQuestionId(4);
		List<Answer> answers = answerDAO.findByQuestionId(4);
		assertTrue("test_deleteByQuestionId", answers.size() == 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteByAnswerText(String)} </br>
	 * Delete answers having answer text="My brother" in answer table and 
	 * expect returning an empty array when searching all answers with answer text="My brother" </br>
	 */
	@Test
	public void test_deleteByAnswerText(){

		String answerText = "My brother";
		answerDAO.deleteByAnswerText(answerText);
		List<Answer> answers = answerDAO.findByAnswerText(answerText);
		assertTrue("test_deleteByAnswerText", 0 == answers.size());		
	}
	/**
	 * Test for {@link AnswerDAO#insert(Answer)} </br>
	 * Create a new {@link Answer} and insert it to answer table </br>
	 * {@link AnswerDAO#insert(Answer)} will return a generated answer ID. 
	 * Expect returning a answer object when search answer with the generated id </br>
	 */
	@Test
	public void test_insert(){

		Answer answer = new Answer(4, "answer data for test");
		long id = answerDAO.insert(answer);
		List<Answer> answers = answerDAO.findById(id);
		assertTrue("test_insert", 1 == answers.size());
	}
	/**
	 * Test for {@link AnswerDAO#update(Answer)} </br>
	 * Change {@link Answer} by answer text of answer ID=2 </br>
	 * Expect the change are updated when searching answer ID=2;
	 */
	@Test
	public void test_update(){
		long answerId = 2;
		Answer answer = answerDAO.findById(answerId).get(0);
		answer.setAnswerText("new answer text");
		answer.setStatistics("1");
		
		answerDAO.update(answer);
		
		answer = answerDAO.findById(answerId).get(0);
		assertEquals("update() changes answer text", 
				answer.getAnswerText(), "new answer text");
		assertEquals("update() changes statistics value", 
				answer.getStatistics(), "1");
	}

}
