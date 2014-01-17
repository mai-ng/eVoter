package evoter.share.model;

import java.io.Serializable;
import java.sql.Date;

import org.json.simple.JSONObject;

import evoter.share.dao.SessionDAO;

public class Session extends ItemData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9112405387599457550L;

	private long subjectId;
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
		super(id, name);
		this.subjectId = subjectId;
		this.creationDate = creationDate;
		this.isActive = isActive;
	}

	/**
	 * @param cp
	 */
	public Session(Session cp) {
		super(cp);
		this.subjectId = cp.getSubjectId();
		this.creationDate = cp.getCreationDate();
		this.isActive = cp.isActive();
	}

	/**
	 * @param id
	 * @param title
	 */
	public Session(long id, String title) {
		super(id, title);
		// TODO Auto-generated constructor stub
	}

	public Session() {
	}

	public Session(long subjectId, String name, Date creationDate,
			boolean isActive) {
		this.subjectId = subjectId;
		this.title = name;
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
		return title;
	}

	public void setName(String name) {
		this.title = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {

		JSONObject obj = new JSONObject();
		obj.put(SessionDAO.ID, id);
		obj.put(SessionDAO.CREATION_DATE, creationDate);
		obj.put(SessionDAO.IS_ACTIVE, isActive);
		obj.put(SessionDAO.NAME, title);
		obj.put(SessionDAO.SUBJECT_ID, subjectId);
		return obj;

	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", subjectId=" + subjectId + ", name="
				+ title + ", creationDate=" + creationDate + ", isActive="
				+ isActive + "]";
	}

}
