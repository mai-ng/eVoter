package evoter.server.model;

import java.io.Serializable;
import java.util.Date;

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

	
	
	/**
	 * @param id
	 * @param subjectId
	 * @param name
	 * @param creationDate
	 * @param isActive
	 */
	public Session(long id, long subjectId, String name, Date creationDate,
			boolean isActive) {
		this.id = id;
		this.subjectId = subjectId;
		this.name = name;
		this.creationDate = creationDate;
		this.isActive = isActive;
	}

	public Session(Session cp) {
		this.id = cp.getId();
		this.subjectId = cp.getSubjectId();
		this.name = cp.getName();
		this.creationDate = cp.getCreationDate();
		this.isActive = cp.isActive();
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
	public String toJSON() {

		JSONObject obj = new JSONObject();
		obj.put(SessionDAO.ID, id);
		obj.put(SessionDAO.CREATION_DATE, creationDate);
		obj.put(SessionDAO.IS_ACTIVE, isActive);
		obj.put(SessionDAO.NAME, name);
		obj.put(SessionDAO.SUBJECT_ID, subjectId);
		return obj.toJSONString();

	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", subjectId=" + subjectId + ", name="
				+ name + ", creationDate=" + creationDate + ", isActive="
				+ isActive + "]";
	}

}
