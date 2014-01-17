/**
 * 
 */
package evoter.share.model;

import java.io.Serializable;

/**
 * @author btdiem
 *
 */
public class UserType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5893911091454051698L;
	private long id;
	private String userTypeValue;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserTypeValue() {
		return userTypeValue;
	}
	public void setUserTypeValue(String userTypeValue) {
		this.userTypeValue = userTypeValue;
	}
	@Override
	public String toString() {
		return "UserType [id=" + id + ", userTypeValue=" + userTypeValue + "]";
	}
	
	

}
