package evoter.share.dao;

import java.util.List;
import evoter.share.model.UserSubject;
/**
 * This class is an interface mapping all properties in this class to fields
 *  of USER_SUBJECT table in the database </br>
 *  
 * @author btdiem </br>
 *
 */
public interface UserSubjectDAO {
	//user_id column
	public static final String USER_ID = "USER_ID";
	//subject_id column 
	public static final String SUBJECT_ID = "SUBJECT_ID";
	//table name
	public static final String TABLE_NAME = "USER_SUBJECT";
	//spring bean name
	public static final String BEAN_NAME = "userSubjectDAO";
	/**
	 * Insert a new record valued by {@link UserSubject} to user_subject table </br>
	 * @param us {@link UserSubject}
	 * @return a generated id of inserted record </br>
	 */
	public long insert(UserSubject us);

	/**
	 * Select all records in user_subject table </br>
	 * @return List of {@link UserSubject} if table is not empty </br>
	 * Otherwise, returning an empty List </br>
	 */
	public List<UserSubject> findAll();
	/**
	 * Select records in user_subject table that match the input properties and their values </br>
	 * @param propertyNames an array of column </br>
	 * @param propertyValues an array of column values </br>
	 * @return a list of {@link UserSubject} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */	
	public List<UserSubject> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Select records in user_subject table that have user_id column matching the given userId </br>
	 * @param userId user id value of {@link UserSubject}</br>
	 * @return a list of {@link UserSubject} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */	
	public List<UserSubject> findByUserId(long userId);
	/**
	 * Select records in user_subject table that have subject_id column matching the given subjectId </br>
	 * @param subjectId subject id value of {@link UserSubject}</br>
	 * @return a list of {@link UserSubject} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */		
	public List<UserSubject> findBySubjectId(long subjectId);
	/**
	 * Delete all records in user_subject table that match the input properties and their values
	 * @param propertyNames an array of column </br>
	 * @param propertyValues an array of column values </br>
	 */	
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Delete all records that have user_id column match the given user_id </br>
	 * @param userId user ID value of {@link UserSubject} </br>
	 */	
	public void deleteByUserId(long userId);
	/**
	 * Delete all records that have subject_id column match the given subjectId </br>
	 * @param subjectId subject ID value of {@link UserSubject} </br>
	 */	
	public void deleteBySubjectId(long subjectId);
	
	
}
