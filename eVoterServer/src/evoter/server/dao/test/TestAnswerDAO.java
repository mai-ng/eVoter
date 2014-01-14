package evoter.server.dao.test;

import evoter.server.dao.BeanDAOFactory;
import evoter.share.dao.AnswerDAO;
import evoter.share.model.Answer;

public class TestAnswerDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		//test get DAO object
		AnswerDAO answerDao = (AnswerDAO)BeanDAOFactory.getBean("answerDAO");
		//test find all
		System.out.println(answerDao.findAll());
		//test find by property
		System.out.println(answerDao.findByProperty(new String[]{AnswerDAO.QUESTION_ID}, new Object[]{3}));
		System.out.println(answerDao.findByProperty(new String[]{AnswerDAO.QUESTION_ID, AnswerDAO.ID}, new Object[]{3,1}));
		//test insert
		Answer answer = new Answer();
		answer.setQuestionId(2);
		answer.setAnswerText("add a new answer");
		System.out.println("the returned key : " + answerDao.insert(answer));
		//test delete by property
		//answerDao.deleteByProperty(new String[]{AnswerDAO.ID, AnswerDAO.QUESTION_ID}, new Integer[]{6, 2});
		

	}

}
