package evoter.share.dao;
import java.sql.Timestamp;
import java.util.List;
import evoter.share.model.Subject;
/**
 * 
 *
 * This class is an interface mapping all properties in this class to fields
 * of SUBJECT table in the database </br>

 * @author btdiem </br>
 *
 */
public interface SubjectDAO {
	
	//id column
	public static final String ID = "ID";
	//title column
	public static final String TITLE = "TITLE";
	//creation_date column
	public static final String CREATION_DATE = "CREATION_DATE";
	//table name
	public static final String TABLE_NAME = "SUBJECT";
	//spring bean name
	public static final String BEAN_NAME = "subjectDAO";
	//email list is used when inserting email list of user for a subject
	public static final String EMAIL_LIST="email_list";
	
	/**
	 * Insert a {@link Subject} to subject table </br>
	 * @param subject
	 * @return a generated id of insert record </br>
	 */
	public long insert (Subject subject);
	/**
	 * Select all records of subject table </br>
	 * @return a list of {@link Subject} if table is not empty </br>
	 * Otherwise, returning an empty list </br>
	 */
	public List<Subject> findAll();
	/**
	 * Select all records in subject table that match the input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Subject} if there are records returned </br>
	 * Otherwise, returning an empty List </br>
	 */
	public List<Subject> findByProperty(String[] propertyName, Object[] propertyValue);
	/**
	 * Select a record in subject table that has id column matching the given id value </br>
	 * @param id subject ID
	 * @return a {@link List} of subject with only 1 element if there is a record returned</br>
	 * Otherwise, returning an empty List </br>
	 */
	public List<Subject> findById(long id);
	/**
	 * Select records in subject table that has title column matching the given title value </br>
	 * @param title subject title value
	 * @return a {@link List} of subject  if there are records returned</br>
	 * Otherwise, returning an empty List </br>
	 */

	public List<Subject> findByTitle(String title);
	/**
	 * Select records in subject table that has creation_date column matching the given creation_date value </br>
	 * @param date creation date of subject
	 * @return a {@link List} of subject  if there are records returned</br>
	 * Otherwise, returning an empty List </br>
	 */	
	public List<Subject> findByCreationDate(Timestamp date);
	/**
	 * 
	 * Delete all records in subject table matching the input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	/**
	 * Delete a record in subject table that has id column matching the given id </br>
	 * @param id subject ID value </br>
	 */
	public void deleteById(long id);
	/**
	 * 	Delete all records that have title column matching the given title value
	 * @param title subject title value </br>
	 */
	public void deleteByTitle(String title);
	/**
	 * Delete all records that have creation_date column matching the given date </br>
	 * @param date creation date of subject </br>
	 */
	public void deleteByCreationDate(Timestamp date);
	/**
	 * Update the changed values of {@link Subject} to subject table </br> 
	 * @param subject {@link Subject} is changed
	 * @return a positive integer if updating successfully </br>
	 */
	public int update(Subject subject);
}
