package evoter.server.dao.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import evoter.share.dao.AnswerDAO;
import evoter.share.model.Answer;
/**
 * Make test cases for {@link AnswerDAO} </br>
 * @author btdiem </br>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestAnswerDAO {
	
	@Autowired
	AnswerDAO answerDAO;
	
	
	@Test
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
	@Transactional
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
	@Transactional
	@Rollback(false)
	public void testFindByQuestionId(){
		List<Answer> answers = answerDAO.findByQuestionId(4);
		assertTrue("testFindByQuestionId", answers.size() > 0);
	}

	@Test
	@Transactional
	@Rollback(false)
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
	@Transactional
	@Rollback(false)
	public void testFindByProperty(){
		List<Answer> answers = answerDAO.findByProperty(new String[]{AnswerDAO.ID, AnswerDAO.QUESTION_ID}, new Object[]{4,3});
		assertTrue("testFindByProperty", answers.size() > 0);
	}
	/**
	 * Test {@link AnswerDAO#deleteById(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteById(){
		List<Answer> answers = answerDAO.findAll();
		int count = answers.size();
		answerDAO.deleteById(8);
		answers = answerDAO.findAll();
		assertTrue("testDeleteById", count-1 == answers.size());
	}
	/**
	 * Test {@link AnswerDAO#deleteByQuestionId(long)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByQuestionId(){
		List<Answer> answers = answerDAO.findAll();
		int count = answers.size();
		answerDAO.deleteByQuestionId(4);
		answers = answerDAO.findAll();
		assertTrue("testDeleteByQuestionId", count > answers.size());
	}
	/**
	 * Test {@link AnswerDAO#deleteByAnswerText(String)} </br>
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteByAnswerText(){
		List<Answer> answers = answerDAO.findAll();
		int count = answers.size();
		answerDAO.deleteByAnswerText("My brother");
		answers = answerDAO.findAll();
		assertTrue("testDeleteByAnswerText", count > answers.size());		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInsert(){
		List<Answer> answers = answerDAO.findAll();
		int count = answers.size();
		Answer answer = new Answer(4, "answer data for test");
		answerDAO.insert(answer);
		answers = answerDAO.findAll();
		assertTrue("testInsert", count+1 == answers.size());
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
