/**
 * 
 */
package evoter.share.model;
import java.io.Serializable;

/**
 * Updated by @author luongnv89 on 18-Jan-2014<br>
 * <li> Add user type values
 *
 * Mapping columns of user_type table to properties of this class </br>
 * @author btdiem </br>
 *
 */
public class UserType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5893911091454051698L;
	
	public static final long SECRETARY =1;
	public static final long TEACHER =2;
	public static final long STUDENT =3;
	private long id;
	private String userTypeValue;
	
	public UserType(){}
	
	public UserType(String userTypeValue){
		this.userTypeValue = userTypeValue;
	}
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
