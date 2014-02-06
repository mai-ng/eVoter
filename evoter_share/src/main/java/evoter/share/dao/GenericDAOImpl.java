/**
 * 
 */
package evoter.share.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author btdiem
 *
 */
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> type;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    public T insert(T t) {
        this.em.persist(t);
        return t;
    }

    public void delete(Object id) {
        this.em.remove(this.em.getReference(type, id));
    }

    public T find(Object id) {
        return (T) this.em.find(type, id);
    }

    public T update(T t) {
        return this.em.merge(t);    
    }
}
