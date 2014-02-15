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
	public void testFindAll(){
		List<Answer> answers = answerDAO.findAll();
		assertTrue("testFindAll", answers.size()>0);
	}

	/**
	 * Test {@link AnswerDAO#deleteById(long)} </br>
	 * Search {@link Answer} by {@link AnswerDAO#ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindById(){
		List<Answer> answers = answerDAO.findById(4);
		assertTrue("testFindById", answers.size() > 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteById(long)} </br>
	 * Search {@link Answer} by {@link AnswerDAO#ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindByQuestionId(){
		List<Answer> answers = answerDAO.findByQuestionId(4);
		assertTrue("testFindByQuestionId", answers.size() > 0);
	}

	@Test
	@Transactional
	public void testFindByAnswerText(){
		List<Answer> answers = answerDAO.findByAnswerText("Two hours.");
		assertTrue("testFindByAnswerText", answers.size() > 0);
		answers = answerDAO.findByAnswerText("anything else");
		assertTrue("testFindByAnswerText", answers.size() == 0);
	}
	/**
	 * Test {@link AnswerDAO#findByProperty(String[], Object[])} </br>
	 * Search {@link Answer} by {@link AnswerDAO#ID} and {@link AnswerDAO#QUESTION_ID} </br>
	 * Expect returning a not null value </br>
	 * 
	 */
	@Test
	@Rollback(false)
	public void testFindByProperty(){
		List<Answer> answers = answerDAO.findByProperty(new String[]{AnswerDAO.ID, AnswerDAO.QUESTION_ID}, new Object[]{4,3});
		assertTrue("testFindByProperty", answers.size() > 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteById(long)} </br>
	 */
	@Test
	public void testDeleteById(){

		answerDAO.deleteById(8);
		List<Answer> answers = answerDAO.findById(8);
		assertTrue("testDeleteById", answers.size() == 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteByQuestionId(long)} </br>
	 */
	@Test
	public void testDeleteByQuestionId(){

		answerDAO.deleteByQuestionId(4);
		List<Answer> answers = answerDAO.findByQuestionId(4);
		assertTrue("testDeleteByQuestionId", answers.size() == 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteByAnswerText(String)} </br>
	 */
	@Test
	public void testDeleteByAnswerText(){

		String answerText = "My brother";
		answerDAO.deleteByAnswerText(answerText);
		List<Answer> answers = answerDAO.findByAnswerText(answerText);
		assertTrue("testDeleteByAnswerText", 0 == answers.size());		
	}
	
	@Test
	public void testInsert(){

		Answer answer = new Answer(4, "answer data for test");
		long id = answerDAO.insert(answer);
		List<Answer> answers = answerDAO.findById(id);
		assertTrue("testInsert", 1 == answers.size());
	}
	/**
	 * Test for {@link AnswerDAO#update(Answer)} </br>
	 */
	@Test
	public void testUpdate(){
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
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//	
//		//test get DAO object
//		//AnswerDAO answerDao = (AnswerDAO)BeanDAOFactory.getBean("answerDAO");
//		//test find all
//		System.out.println(answerDAO.findAll());
//		//test find by property
//		System.out.println(answerDAO.findByProperty(new String[]{AnswerDAO.QUESTION_ID}, new Object[]{3}));
//		System.out.println(answerDAO.findByProperty(new String[]{AnswerDAO.QUESTION_ID, AnswerDAO.ID}, new Object[]{3,1}));
//		//test insert
//		Answer answer = new Answer();
//		answer.setQuestionId(2);
//		answer.setAnswerText("add a new answer");
//		System.out.println("the returned key : " + answerDAO.insert(answer));
//		//test delete by property
//		//answerDao.deleteByProperty(new String[]{AnswerDAO.ID, AnswerDAO.QUESTION_ID}, new Integer[]{6, 2});
//		
//
//	}

}
