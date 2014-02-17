package evoter.share.dao;

import java.util.List;

import evoter.share.model.User;
/**
 * 
 * This class is an interface mapping all properties in this class to fields
 *  of USER table in the database </br>
 *  
 * @author btdiem
 *
 */
public interface UserDAO {
	//user_type_id column
	public static final String USER_TYPE_ID = "user_type_id";
	//email column
	public static final String EMAIL = "email_address";
	//user_name column
	public static final String USER_NAME = "username";
	//password column
	public static final String PASSWORD = "passwd";
	//id column 
	public static final String ID = "id";
	//table name
	public static final String TABLE_NAME = "USER";
	//spring bean name
	public static final String BEAN_NAME = "userDAO";
	//is used when get/put userkey value in the request parameter
	public static final String USER_KEY = "userkey";
	//full_name column
	public static final String FULL_NAME = "full_name";
	//is_approved colum
	public static final String IS_APPROVED = "approved";
	
	/**
	 * Create a records from the given {@link User} </br> 
	 * @param user
	 * @return a positive integer if there is no exception </br>
	 * Otherwise, returning a negative integer </br>
	 */
	public int insert(User user);
	/**
	 * Select all records in user table </br>
	 * @return List of {@link User} if table is not empty </br>
	 * Otherwise, returning an empty List </br>
	 */
	public List<User> findAll();
	/**
	 * Select records in user table that have id column matching the given id </br>
	 * @param id user ID
	 * @return a list of {@link User} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<User> findById(long id);
	/**
	 * Select records in user table that have user_name column matching the given userName </br>
	 * @param id userName user name of {@link User} </br>
	 * @return a list of {@link User} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */	
	public List<User> findByUserName(String userName);
	/**
	 * Select records in user table that have user_type_id column matching the given userTypeId </br>
	 * @param userTypeId user type value of {@link User}</br>
	 * @return a list of {@link User} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */	
	public List<User> findByUserTypeId(long userTypeId);
	/**
	 * Select records in user table that have email column matching the given email </br>
	 * @param id user ID
	 * @return a list of {@link User} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<User> findByEmail(String email);
	/**
	 * Select records in user table that have full_name column matching the given fullName </br>
	 * @param fullName full name of {@link User}</br>
	 * @return a list of {@link User} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<User> findByFullName(String fullName);
	/**
	 * Select records in user table that have is_approved column matching the given isApproved </br>
	 * @param isApproved approved status of {@link User}</br>
	 * @return a list of {@link User} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<User> findByApproved(boolean isApproved);
	/**
	 * Select records in user table that match the input properties and their values </br>
	 * @param propertyNames an array of column </br>
	 * @param propertyValues an array of column values </br>
	 * @return a list of {@link User} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<User> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Delete all records in user table that match the input properties and their values
	 * @param propertyNames an array of column </br>
	 * @param propertyValues an array of column values </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Delete all records that have id column match the given id </br>
	 * @param id user ID value of {@link User} </br>
	 */
	public void deleteById(long id);
	/**
	 * Delete all records that have user_name column match the given userName  </br>
	 * @param userName user name value of {@link User}</br>
	 */	
	public void deleteByUserName(String userName);

	/**
	 * Delete all records that have user_type_id column match the given userTypeId  </br>
	 * @param userTypeId user type value of {@link User}</br>
	 */
	public void deleteByUserTypeId(long userTypeId);
	/**
	 * Delete all records that have email column match the given email  </br>
	 * @param email email value of {@link User}</br>
	 */
	public void deleteByEmail(String email);
	/**
	 * Delete all records that have full_name column match the given fullName  </br>
	 * @param fullName full name value of {@link User}</br>
	 */
	public void deleteByFullName(String fullName);
	/**
	 * Delete all records that have is_approved column match the given isApproved  </br>
	 * @param isApproved approved status of {@link User}</br>
	 */
	public void deleteByApproved(boolean isApproved);
	/**
	 * 
	 * @param user
	 * @return a positive integer if there is no exception </br>
	 * Otherwise returning a negative integer </br>
	 */
	public int update(User user);
	
	
	

}
