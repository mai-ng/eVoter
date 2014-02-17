package evoter.share.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.json.simple.JSONObject;

import evoter.share.dao.SessionDAO;
/**
 * Mapping columns of session table to properties of this class </br>
 * 
 * @author btdiem </br>
 *
 */
public class Session extends ItemData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9112405387599457550L;

	private long subjectId;
	private Timestamp creationDate;
	private boolean isActive;
	private long userId;
	private String creatorName;
	
	public String getCreatorName(){
		return creatorName;
	}
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @param id
	 * @param subjectId
	 * @param name
	 * @param creationDate
	 * @param isActive
	 * @param userId user creates the session </br>
	 */
	public Session(long id, long subjectId, String name, Timestamp creationDate,
			boolean isActive, long userId, String creatorName) {
		super(id, name);
		this.subjectId = subjectId;
		this.creationDate = creationDate;
		this.isActive = isActive;
		this.userId = userId;
		this.creatorName = creatorName;
	}

   /**
	 * @param id
	 * @param subjectId
	 * @param name
	 * @param creationDate
	 * @param isActive
	 * @param userId user creates the session </br>
	 */
	public Session(long id, long subjectId, String name, Timestamp creationDate,
			boolean isActive, long userId) {
		super(id, name);
		this.subjectId = subjectId;
		this.creationDate = creationDate;
		this.isActive = isActive;
		this.userId = userId;
	}


	/**
	 * @param cp
	 */
	public Session(Session cp) {
		super(cp);
		this.subjectId = cp.getSubjectId();
		this.creationDate = cp.getCreationDate();
		this.isActive = cp.isActive();
		this.userId = cp.getUserId();
	}

	/**
	 * @param id
	 * @param title
	 */
	public Session(long id, String title) {
		super(id, title);
		// TODO Auto-generated constructor stub
	}

	public Session() {}

	public Session(long subjectId, String name, Timestamp creationDate,
			boolean isActive, long userId) {
		this.subjectId = subjectId;
		this.title = name;
		this.creationDate = creationDate;
		this.isActive = isActive;
		this.userId = userId;
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

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {

		JSONObject obj = new JSONObject();
		obj.put(SessionDAO.ID, id);
		obj.put(SessionDAO.CREATION_DATE, "" + creationDate + "");
		obj.put(SessionDAO.IS_ACTIVE, isActive);
		obj.put(SessionDAO.NAME, title);
		obj.put(SessionDAO.SUBJECT_ID, subjectId);
		obj.put(SessionDAO.USER_ID, userId);
		return obj;

	}

	@Override
	public String toString() {
		return "Session [subjectId=" + subjectId + ", creationDate="
				+ creationDate + ", isActive=" + isActive + ", userId="
				+ userId + "]";
	}


}
