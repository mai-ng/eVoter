package evoter.server.dao;

import java.util.List;

import evoter.server.model.UserType;

public interface UserTypeDAO {

	public static final String ID = "id";
	public static final String USER_TYPE_VALUE = "user_type_value";
	public static final String TABLE_NAME = "USER_TYPE";
	public static final String BEAN_NAME = "userTypeDAO";
	
	public List<UserType> findAll();
	public int insert(UserType userType); 
	public List<UserType> findById(long id);
	public List<UserType> findByUserTypeValue(String userTypeValue);
	public List<UserType> findByProperty(String[] propertyNames, Object[] propertyValues);
	public void deleteByProperty(String[] propertyNames, Object[] propertyValues);
	public void deleteById(long id);
	public void deleteByUserTypeValue(String userTypeValue);
	
}
