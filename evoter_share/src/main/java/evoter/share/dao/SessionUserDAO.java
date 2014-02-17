package evoter.share.dao;

import java.util.List;

import evoter.share.model.SessionUser;
/**
 *
 * This class is an interface mapping all properties in this class to fields
 * of SESSION_USER table in the database </br>
 * 
 * @author btdiem </br>
 *
 */
public interface SessionUserDAO {
	//user_id column
	public static final String USER_ID = "USER_ID";
	//session_id column
	public static final String SESSION_ID = "SESSION_ID";
	//delete_indicator column
	public static final String DELETE_INDICATOR = "DELETE_INDICATOR";
	//accept_session column
	public static final String ACCEPT_SESSION = "ACCEPT_STT";
	//table name
	public static final String TABLE_NAME = "SESSION_USER";
	//spring bean name
	public static final String BEAN_NAME = "sessionUserDAO";
	
	/**
	 * 
	 * Insert a new records of {@link SessionUser} to session_user table </br>
	 * 
	 * @param SessionUser
	 * @return a positive integer if there is no exception </br>
	 * Otherwise, returning a negative integer </br>
	 */
	public int insert (SessionUser SessionUser);
	/**
	 * Select all records of session_user table
	 * @return a List of {@link SessionUser} if there are records returned </br>
	 * Otherwise, returning an empty array </br>
	 */	
	public List<SessionUser> findAll();
	/**
	 * Select all records  in session_user table that match the input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link SessionUser} if there are records returned</br>
	 * Otherwise, returning an empty List </br>
	 */
	public List<SessionUser> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Select all records in session_user that has user_id column match the given userId value	
	 * @param userId userId value of {@link SessionUser} </br>
	 * @return a {@link List} of {@link SessionUser} if there are some records returned </br>
	 * Otherwise, returning an empty List</br>
	 */
	public List<SessionUser> findByUserId(long userId);
	/**
	 * Select all records in session_user that has session_id column match the given sessionId value	
	 * @param session session ID value of {@link SessionUser} </br>
	 * @return a {@link List} of {@link SessionUser} if there are some records returned </br>
	 * Otherwise, returning an empty List</br>
	 */	
	public List<SessionUser> findBySessionId(long sessionId);
	/**
	 * Select all records in session_user that has delete_indicator column match the given deleteIndicator value	
	 * @param deleteIndicator delete_indicator value of {@link SessionUser} </br>
	 * @return a {@link List} of {@link SessionUser} if there are some records returned </br>
	 * Otherwise, returning an empty List</br>
	 */	
	public List<SessionUser> findByDeleteIndicator(boolean deleteIndicator);
	/**
	 * Select all records in session_user that has accept_stt column match the given acceptSession value	
	 * @param acceptSession accept_stt value of {@link SessionUser} </br>
	 * @return a {@link List} of {@link SessionUser} if there are some records returned </br>
	 * Otherwise, returning an empty List</br>
	 */	
	public List<SessionUser> findByAcceptSession(boolean acceptSession);
	/**
	 * 
	 * Delete all records in session_user table that match the input properties and their values </br> 

	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>

	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	/**
	 * Delete all records that have user_id column matching the given user_id value </br> 	
	 * @param userId user_id value of {@link SessionUser} </br>
	 */
	public void deleteByUserId(long userId);
	/**
	 * Delete all records that have session_id column matching the given sessionId value </br> 	
	 * @param sessionId session_id value of {@link SessionUser} </br>
	 */	
	public void deleteBySessionId(long sessionId);
	/**
	 * Delete all records that have delete_indicator column matching the given deleteIndicator value </br> 	
	 * @param deleteIndicator delete_indicator value of {@link SessionUser} </br>
	 */
	public void deleteByDeleteIndicator(boolean deleteIndicator);
	/**
	 * Delete all records that have accept_stt column matching the given acceptSession value </br> 	
	 * @param acceptSession accept_stt value of {@link SessionUser} </br>
	 */	
	public void deleteByAcceptSession(boolean acceptSession);
	/**
	 * Update {@link SessionUser} for record of session_user table
	 * @return a positive integer if there is no exception </br>
	 * Otherwise, returning a negative integer </br>
	 * 
	 * @param sessUser {@link SessionUser} </br>
	 */
	public int update(SessionUser sessUser);

}
