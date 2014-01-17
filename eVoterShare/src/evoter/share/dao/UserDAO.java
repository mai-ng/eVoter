package evoter.share.dao;

import java.util.List;

import evoter.share.model.User;
/**
 * 
 * @author btdiem
 *
 */
public interface UserDAO {
	
	public static final String USER_TYPE_ID = "user_type_id";
	public static final String EMAIL = "email_address";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "passwd";
	public static final String ID = "id";
	public static final String TABLE_NAME = "USER";
	public static final String BEAN_NAME = "userDAO";
	public static final String USER_KEY = "userkey";
	
	public int insert(User user);
	public List<User> findAll();
	public List<User> findById(long id);
	public List<User> findByUserName(String userName);
	public List<User> findByUserTypeId(long userTypeId);
	public List<User> findByEmail(String email);
	public List<User> findByPassword(String password);
	public List<User> findByProperty(String[] propertyNames, Object[] propertyValues);
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	public void deleteById(long id);
	public void deleteByUserName(String userName);
	public void deleteByUserTypeId(long userTypeId);
	public void deleteByEmail(String email);
	public void deleteByPassword(String password);
	
	
	

}
