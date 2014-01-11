package evoter.mobile.model;

import java.io.Serializable;
import java.util.Date;

import org.json.simple.JSONObject;

import evoter.server.dao.SessionDAO;

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

	public Session(Session cp) {
		super(cp);
		this.subjectId = cp.getSubjectId();
		this.creationDate = cp.getCreationDate();
		this.isActive = cp.isActive();
	}

	public boolean isActive() {
		return isActive;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@SuppressWarnings("unchecked")
	public String toJSON() {

		JSONObject obj = new JSONObject();
		obj.put(SessionDAO.ID, id);
		obj.put(SessionDAO.CREATION_DATE, creationDate);
		obj.put(SessionDAO.IS_ACTIVE, isActive);
		obj.put(SessionDAO.NAME, getTitle());
		obj.put(SessionDAO.SUBJECT_ID, subjectId);
		return obj.toJSONString();

	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", subjectId=" + subjectId + ", name="
				+ getTitle() + ", creationDate=" + creationDate + ", isActive="
				+ isActive + "]";
	}

}
