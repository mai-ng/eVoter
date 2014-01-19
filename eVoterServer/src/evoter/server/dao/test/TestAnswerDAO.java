package evoter.server.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//import evoter.server.dao.BeanDAOFactory;
import evoter.share.dao.AnswerDAO;
//import evoter.share.model.Answer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"applicationContext.xml"})
@TransactionConfiguration(transactionManager="txManager", defaultRollback=true)

public class TestAnswerDAO {
	
	@Autowired
	AnswerDAO answerDAO;
	
	
	@Test
	public void testFindAll(){
		
		System.out.println(answerDAO.findAll());
	}
	
//	@Transactional
	@Rollback(true)
	@Test
	public void testDeleteByQuestionId(){
		answerDAO.deleteByQuestionId(5);
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
