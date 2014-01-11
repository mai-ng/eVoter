package evoter.mobile.model;

import java.util.Date;

import org.json.simple.JSONObject;

import evoter.server.dao.SubjectDAO;

public class Subject extends ItemData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date creationDate;

	/**
	 * @param id
	 * @param title
	 * @param creationDate
	 */
	public Subject(long id, String title, Date creationDate) {
		super(id, title);
		this.creationDate = creationDate;
	}

	/**
	 * 
	 */
	public Subject(Subject copy) {
		super(copy);
		this.creationDate = copy.getCreationDate();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", title=" + title + ", creationDate="
				+ creationDate + "]";
	}

	@SuppressWarnings("unchecked")
	public String toJSON() {

		JSONObject obj = new JSONObject();
		obj.put(SubjectDAO.ID, id);
		obj.put(SubjectDAO.TITLE, title);
		obj.put(SubjectDAO.CREATION_DATE, creationDate);
		return obj.toJSONString();

	}

}
