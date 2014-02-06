/**
 * 
 */
package evoter.share.dao;
/**
 * @author btdiem
 *
 */
public interface GenericDAO<T> {
	
	public T insert(T t);
	
    public void delete(Object id);

    public T find(Object id);

    public T update(T t); 
    
/*	public List<T> findAll();
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
*/	

}
