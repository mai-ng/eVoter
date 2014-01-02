package evoter.server.model;

import java.io.Serializable;
import java.sql.Date;

public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9112405387599457550L;

	private long id;
	private long subjectId;
	private String name;
	private Date creationDate;
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
	@Override
	public String toString() {
		return "Session [id=" + id + ", subjectId=" + subjectId + ", name="
				+ name + ", creationDate=" + creationDate + "]";
	}
	
	
	
}
