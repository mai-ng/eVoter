package evoter.server.dao.test;

import evoter.server.dao.BeanDAOFactory;
import evoter.server.dao.QuestionDAO;

public class TestQuestionDAO {
	
	public static void main(String [] args){
		
		//test get DAO object
		QuestionDAO qeDao = (QuestionDAO)BeanDAOFactory.getBean("questionDAO");
		//test find all
		System.out.println(qeDao.findAll());
		//test find by question id
		System.out.println(qeDao.findByProperty(new String[]{QuestionDAO.ID, QuestionDAO.QUESTION_TYPE_ID}, new Object[]{2, 3}));
	}

}
