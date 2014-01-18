/**
 * 
 */
package evoter.share.model;

import java.io.Serializable;

/**
 * Updated by @author luongnv89 on 18-Jan-2014<br>
 * <li> Add user type values
 *
 * Created by @author btdiem
 *
 */
public class UserType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5893911091454051698L;
	
	public static final int SECRETARY =1;
	public static final int TEACHER =2;
	public static final int STUDENT =3;
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
