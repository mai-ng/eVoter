package evoter.share.model;

import java.io.Serializable;
import java.sql.Date;

import org.json.simple.JSONObject;

import evoter.share.dao.SubjectDAO;

/**
 * 
 * @author btdiem
 * 
 */
public class Subject extends ItemData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String title;
	private Date creationDate;

	public Subject() {
	}

	/**
	 * @param id
	 * @param title
	 * @param creationDate
	 */
	public Subject(long id, String title, Date creationDate) {
		super(id, title);
		this.creationDate = creationDate;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", title=" + title + ", creationDate="
				+ creationDate + "]";
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {

		JSONObject obj = new JSONObject();
		obj.put(SubjectDAO.ID, id);
		obj.put(SubjectDAO.TITLE, title);
		obj.put(SubjectDAO.CREATION_DATE, creationDate);
		return obj;

	}

}
