package evoter.share.dao;
import java.sql.Timestamp;
import java.util.List;
import evoter.share.model.Subject;
/**
 * 
 * @author btdiem
 *
 */
public interface SubjectDAO {

	public static final String ID = "ID";
	public static final String TITLE = "TITLE";
	public static final String CREATION_DATE = "CREATION_DATE";
	public static final String TABLE_NAME = "SUBJECT";
	public static final String BEAN_NAME = "subjectDAO";
	
	public long insert (Subject subject);
	public List<Subject> findAll();
	/**
	 * Search {@link Subject} object in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Subject} </br>
	 */
	public List<Subject> findByProperty(String[] propertyName, Object[] propertyValue);
	public List<Subject> findById(long id);
	public List<Subject> findByTitle(String title);
	public List<Subject> findByCreationDate(Timestamp date);
	/**
	 * 
	 * Delete {@link Subject} objects in the database by input properties and their values </br> 
	 * The length of property name array should have the same length with array of property value </br>
	 * @param propertyName array of property name </br>
	 * @param propertyValue array of property value </br>
	 * @return {@link List} of {@link Subject} </br>
	 */
	public void deleteByProperty(String[] propertyNames, Object [] propertyValues);
	public void deleteById(long id);
	public void deleteByTitle(String title);
	public void deleteByCreationDate(Timestamp date);
	/**
	 * Update the changed values of {@link Subject} to the database </br> 
	 * @param subject {@link Subject} is changed
	 * @return a positive integer if updating successfully </br>
	 */
	public int update(Subject subject);
}
