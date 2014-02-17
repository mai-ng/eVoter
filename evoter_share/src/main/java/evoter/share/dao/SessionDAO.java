package evoter.share.dao;

import java.sql.Timestamp;
import java.util.List;

import evoter.share.model.Session;
/**
 * This class is an interface mapping all properties in this class to fields
 *  of SESSION table in the database </br>

 * @author btdiem </br>
 *
 */
public interface SessionDAO {
	//id column
	public static final String ID = "ID";
	//name column
	public static final String NAME = "NAME";
	//subject id column
	public static final String SUBJECT_ID = "SUBJECT_ID";
	//creation_date column
	public static final String CREATION_DATE = "CREATION_DATE";
	//is_active column
	public static final String IS_ACTIVE = "IS_ACTIVE";
	//user_id column
	public static final String USER_ID = "USER_ID";
	//table name
	public static final String TABLE_NAME = "SESSION";
	//spring bean name
	public static final String BEAN_NAME = "sessionDAO";
	
	/**
	 * Insert a new {@link Session} to database </br>
	 * @param session </br>
	 * @return a generated id of inserted record </br>
	 */
	public long insert (Session session);
	/**
	 * Select all records of session table
	 * @return a List of {@link Session} if there are records returned </br>
	 * Otherwise, returning an empty array </br>
	 */
	public List<Session> findAll();
	/**
	 * Update new values of {@link Session} to database </br>
	 * @param session
	 * @return a positive integer if updating successfully </br>
	 * Otherwise, returning a negative integer </br>
	 */
	public int update(Session session);
	/**
	 * Search {@link Session} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Session} </br>
	 */
	public List<Session> findByProperty(String[] propertyName, Object[] propertyValue);
	/**
	 * Select a record in session table that has sessionId matching the given id </br>
	 * @param id sessionId
	 * @return a {@link List} of {@link Session} with size == 1  </br>
	 * @return an empty list if there is no record returned </br>
	 */
	public List<Session> findById(long id);
	/**
	 * Select records in session table that has subjectID matching the given subjectId </br>
	 * @param subjectId subject ID of session </br>
	 * @return a {@link List} of {@link Session} if there are records returned  </br>
	 * Otherwise, return an empty list </br>
	 */
	public List<Session> findBySubjectId(long subjectId);
	/**
	 * Select records in session table that has session name matching the given name </br>
	 * @param name session name of session </br>
	 * @return a {@link List} of {@link Session} if there are records returned  </br>
	 * Otherwise, return an empty list </br>
	 */	
	public List<Session> findByName(String name);
	/**
	 * Select  records in session table that has creation_date matching the given date </br>
	 * @param date creation date of session </br>
	 * @return a {@link List} of {@link Session} if there are records returned  </br>
	 * Otherwise, return an empty list </br>
	 */		
	public List<Session> findByCreationDate(Timestamp date);
	/**
	 * Select records in session table that has active status matching the given active </br>
	 * @param isActive active status of session </br>
	 * @return a {@link List} of {@link Session} if there are records returned  </br>
	 * Otherwise, return an empty list </br>
	 */		
	public List<Session> findBySessionIsActive(boolean isActive);
	/**
	 * Select record sin session table that has userId matching the given userId </br>
	 * @param userId userId of session </br>
	 * @return a {@link List} of {@link Session} if there are records returned  </br>
	 * Otherwise, return an empty list </br>
	 */		
	public List<Session> findBySessionUserId(long userId);
	/**
	 * 
	 * Delete all records in session table that have values of column matching the input condition</br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	/**
	 * 
	 * Delete all records in session table that have column ID matching the given id</br> 
	 * @param id session ID value</br>
	 */
	public void deleteById(long id);
	/**
	 * 
	 * Delete all records in session table that have column subject_id matching the given subject id</br> 
	 * @param subjectId subjectId value of session</br>
	 */	
	public void deleteBySubjectId(long subjectId);
	/**
	 * 
	 * Delete all records in session table that have column name matching the given name</br> 
	 * @param name session name value</br>
	 */	
	public void deleteByName(String name);
	/**
	 * 
	 * Delete all records in session table that have column creation_date matching the given date</br> 
	 * @param date creation_date value of session</br>
	 */	
	public void deleteByCreationDate(Timestamp date);
	/**
	 * 
	 * Delete all records in session table that have column is_active matching the given active value</br> 
	 * @param isActive active status of session </br>
	 */	
	public void deleteBySessionIsActive(boolean isActive);
	/**
	 * 
	 * Delete all records in session table that have column user_id matching the given user_id</br> 
	 * @param userId userId value of session </br>
	 */	
	public void deleteByUserId(long userId);
}
