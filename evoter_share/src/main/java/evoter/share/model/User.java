package evoter.share.model;

import java.io.Serializable;

import org.json.simple.JSONObject;

import evoter.share.dao.UserDAO;
/**
 * Mapping columns of user table to properties of this class
 * 
 * @author btdiem </br>
 *
 */
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7626176031131119500L;
	private long id;
	private String userName;
	private String passWord;
	private long userTypeId;
	private String email;
	private String fullName;
	private boolean isApproved;
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public User(){}
	
	public User(String username, 
				String password, 
				String email,
				long userTypeId) {
		
		this.userName = username;
		this.passWord = password;
		this.email = email;
		this.userTypeId = userTypeId;
		this.fullName =null;
		this.isApproved = false;
	}
	public User(String username, 
			String password, 
			String email, 
			long userTypeId,
			String fullName,
			boolean isApproved){
		
		this(username, password, email, userTypeId);
		this.fullName = fullName;
		this.isApproved = isApproved;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public long getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord="
				+ passWord + ", userTypeId=" + userTypeId + ", email=" + email
				+ ", fullName=" + fullName + ", isApproved=" + isApproved + "]";
	}

	public String generateUserKey(long responseTime){
		return responseTime+"_"+id+"_"+userTypeId; 
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		
		JSONObject res = new JSONObject();
		res.put(UserDAO.ID, id);
		res.put(UserDAO.USER_NAME, userName);
		res.put(UserDAO.PASSWORD, passWord);
		res.put(UserDAO.USER_TYPE_ID, userTypeId);
		res.put(UserDAO.EMAIL, email);
		res.put(UserDAO.FULL_NAME, fullName);
		res.put(UserDAO.IS_APPROVED, isApproved);
		
		return res;
	}
	
	
}
