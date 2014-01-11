package evoter.server.model;

import java.io.Serializable;
import java.sql.Date;

import org.json.simple.JSONObject;
import evoter.server.dao.SessionDAO;

public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9112405387599457550L;

	private long id;
	private long subjectId;
	private String name;
	private Date creationDate;
	private boolean isActive;
	
	public Session(){}
	
	public Session(long subjectId, String name, Date creationDate, boolean isActive){
		this.subjectId = subjectId;
		this.name = name;
		this.creationDate = creationDate;
		this.isActive = isActive;
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(){

		JSONObject obj = new JSONObject();
		obj.put(SessionDAO.ID, id);
		obj.put(SessionDAO.CREATION_DATE, creationDate);
		obj.put(SessionDAO.IS_ACTIVE, isActive);
		obj.put(SessionDAO.NAME, name);
		obj.put(SessionDAO.SUBJECT_ID, subjectId);
		return obj;

	}
	@Override
	public String toString() {
		return "Session [id=" + id + ", subjectId=" + subjectId + ", name="
				+ name + ", creationDate=" + creationDate + ", isActive="
				+ isActive + "]";
	}
	
}
