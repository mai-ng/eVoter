package evoter.server.dao;

import java.util.List;

import evoter.server.model.SessionUser;

public interface SessionUserDAO {
	
	public static final String USER_ID = "USER_ID";
	public static final String SESSION_ID = "SESSION_ID";
	public static final String DELETE_INDICATOR = "DELETE_INDICATOR";
	public static final String ACCEPT_SESSION = "ACCEPT_STT";
	public static final String TABLE_NAME = "SESSION_USER";
	public static final String BEAN_NAME = "sessionUserDAO";
	
	public int insert (SessionUser SessionUser);
	public List<SessionUser> findAll();
	/**
	 * Search {@link SessionUser} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link SessionUser} </br>
	 */
	public List<SessionUser> findByProperty(String[] propertyNames, Object[] propertyValues);
	public List<SessionUser> findByUserId(long userId);
	public List<SessionUser> findBySessionId(long sessionId);
	public List<SessionUser> findByDeleteIndicator(boolean deleteIndicator);
	public List<SessionUser> findByAcceptSession(boolean acceptSession);
	/**
	 * 
	 * Delete {@link SessionUser} objects in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link SessionUser} </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	public void deleteByUserId(long userId);
	public void deleteBySessionId(long sessionId);
	public void deleteByDeleteIndicator(boolean deleteIndicator);
	public void deleteByAcceptSession(boolean acceptSession);
	public int update(SessionUser sessUser);

}
