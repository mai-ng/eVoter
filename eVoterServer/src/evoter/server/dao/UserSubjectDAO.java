package evoter.server.dao;

import java.util.List;

import evoter.server.model.UserSubject;

public interface UserSubjectDAO {

	public static final String USER_ID = "USER_ID";
	public static final String SUBJECT_ID = "SUBJECT_ID";
	public static final String TABLE_NAME = "USER_SUBJECT";
	
	public int insert(UserSubject us);
	public List<UserSubject> findAll();
	public List<UserSubject> findByProperty(String[] propertyNames, Object[] propertyValues);
	public List<UserSubject> findByUserId(long userId);
	public List<UserSubject> findBySubjectId(long subjectId);
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	public void deleteByUserId(long userId);
	public void deleteBySubjectId(long subjectId);
	
	
}
