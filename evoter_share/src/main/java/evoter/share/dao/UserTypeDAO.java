package evoter.share.dao;

import java.util.List;

import evoter.share.model.UserType;
/**
 * This class is an interface mapping all properties in this class to fields
 *  of USER_TYPE table in the database </br>
 *  
 * @author btdiem </br>
 *
 */
public interface UserTypeDAO {
	//id column
	public static final String ID = "id";
	//user_type_value column
	public static final String USER_TYPE_VALUE = "user_type_value";
	//table name
	public static final String TABLE_NAME = "USER_TYPE";
	//spring bean name
	public static final String BEAN_NAME = "userTypeDAO";
	/**
	 * Select all records in user_type table </br>
	 * @return List of {@link UserType} if table is not empty </br>
	 * Otherwise, returning an empty List </br>
	 */	
	public List<UserType> findAll();
	/**
	 * Insert a new record valued by {@link UserType} to user_type table </br>
	 * @param userType {@link UserType}
	 * @return a generated id of inserted record </br>
	 */
	public long insert(UserType userType); 
	/**
	 * Select records in user_type table that have id column matching the given id </br>
	 * @param id user type id value of {@link UserType}</br>
	 * @return a list of {@link UserType} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<UserType> findById(long id);
	/**
	 * Select records in user_type table that have user_type_value column matching the given userTypeValue </br>
	 * @param userTypeValue user type value of {@link UserType}</br>
	 * @return a list of {@link UserType} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<UserType> findByUserTypeValue(String userTypeValue);
	/**
	 * Select records in user_type table that match the input properties and their values </br>
	 * @param propertyNames an array of column </br>
	 * @param propertyValues an array of column values </br>
	 * @return a list of {@link UserType} if there are records returned </br>
	 * Otherwise, returning an empty list </br>
	 */	
	public List<UserType> findByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Delete all records in user_type table that match the input properties and their values
	 * @param propertyNames an array of column </br>
	 * @param propertyValues an array of column values </br>
	 */	
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	/**
	 * Delete all records that have id column match the given id </br>
	 * @param id user type ID value of {@link UserType} </br>
	 */	
	public void deleteById(long id);
	/**
	 * Delete all records that have user_type_value column match the given userTypeValue </br>
	 * @param id user type ID value of {@link UserType} </br>
	 */	
	public void deleteByUserTypeValue(String userTypeValue);
	
}
