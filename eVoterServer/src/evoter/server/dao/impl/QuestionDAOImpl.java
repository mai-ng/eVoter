package evoter.server.dao.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import evoter.server.dao.QuestionDAO;
import evoter.server.model.Question;
import evoter.server.model.mapper.QuestionRowMapper;


public class QuestionDAOImpl extends JdbcDaoSupport implements QuestionDAO {

	@Override
	public int insert(Question question) {
		
		String sql = "INSERT INTO " + TABLE_NAME + "(" 
		+ USER_ID 
		+ "," + QUESTION_TYPE_ID 
		+ ","+ QUESTION_TEXT 
		+ "," + CREATION_DATE 
		+ "," + PARENT_ID + ")" 
		+ " VALUES(?,?,?,?,?)";
		return getJdbcTemplate().update(sql, 
				new Object[]{	question.getUserId(),
								question.getQuestionTypeId(), 
								question.getQuestionText(),
								question.getCreationDate(),
								question.getParentId()});
	}

	@Override
	public List<Question> findAll() {
		
		String sql = "SELECT * FROM " + TABLE_NAME ;
		return getJdbcTemplate().query(sql, new QuestionRowMapper());

	}

	@Override
	public List<Question> findByProperty(String[] propertyNames,
			Object[] propertyValues) {
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			
			if (i<len-1)
				sql += " AND ";
		}
		return getJdbcTemplate().query(sql, propertyValues, new QuestionRowMapper());
		
	}

	@Override
	public List<Question> findById(long id) {
		
		return findByProperty(new String[]{ID}, new Long[]{id});
	}

	@Override
	public List<Question> findByUserId(long userId) {
		
		return findByProperty(new String[]{USER_ID}, new Long[]{userId});	
		
	}

	@Override
	public List<Question> findByQuestionText(String questionText) {
		
		return findByProperty(new String[]{QUESTION_TEXT}, new String[]{questionText});
		
	}

	@Override
	public List<Question> findByQuestionTypeId(long questionTypeId) {
		
		return findByProperty(new String[]{QUESTION_TYPE_ID}, new Long[]{questionTypeId});
		
	}

	@Override
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues) {
		
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE ";
		int len = propertyNames.length;
		for (int i=0; i<len; i++){
			sql += propertyNames[i] + "=? ";
			if (i<len-1)
				sql += " AND ";
		}
		getJdbcTemplate().update(sql, propertyValues);
	}

	@Override
	public void deleteById(long id) {
		deleteByProperty(new String[]{ID}, new Long[]{id});
	}

	@Override
	public void deleteByUserId(long userId) {
		deleteByProperty(new String[]{USER_ID}, new Long[]{userId});
	}

	@Override
	public void deleteByQuestionText(String questionText) {
		
		deleteByProperty(new String[]{QUESTION_TEXT}, new String[]{questionText});
		
	}

	@Override
	public void deleteByQuestionTypeId(long questionTypeId) {
		
		deleteByProperty(new String[]{QUESTION_TYPE_ID}, new Long[]{questionTypeId});
		
	}

	@Override
	public List<Question> findByCreationDate(Date creationDate) {
		
		return findByProperty(new String[]{CREATION_DATE}, new Object[]{creationDate});
	}

	@Override
	public void deleteByCreationDate(Date creationDate) {
		
		deleteByProperty(new String[]{CREATION_DATE}, new Object[]{creationDate});
		
	}

	@Override
	public List<Question> findByParentId(long parentId) {
		
		return findByProperty(new String[]{PARENT_ID}, new Long[]{parentId});
	}

	@Override
	public void deleteByParentId(long parentId) {
		
		deleteByProperty(new String[]{PARENT_ID}, new Long[]{parentId});
	}

}
