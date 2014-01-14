package evoter.server.dao.test;

import evoter.server.dao.BeanDAOFactory;
import evoter.share.dao.QuestionTypeDAO;

import evoter.share.model.QuestionType;

public class TestQuestionTypeDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//get DAO object
		QuestionTypeDAO qeTypeDao = (QuestionTypeDAO)BeanDAOFactory.getBean("questionTypeDAO");
		//test insert
		QuestionType qeType = new QuestionType();
		qeType.setQuestionTypeValue("test question type");
		qeTypeDao.insert(qeType);

		//test find all
		System.out.println(qeTypeDao.findAll());
		//test find by id
		System.out.println(qeTypeDao.findById(1));
		//test find by question type value
		System.out.println(qeTypeDao.findByQuestionTypeValue("Agree/Disagree"));
		

		//test delete by Id
		qeTypeDao.deleteById(6);
		//test delete by question type value
		qeTypeDao.deleteByQuestionTypeValue("test question type");
		
	}

}
