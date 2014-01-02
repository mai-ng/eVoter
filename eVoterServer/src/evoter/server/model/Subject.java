package evoter.server.model;

import java.io.Serializable;
import java.sql.Date;

public class Subject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private long id;
	private String title;
	private Date creationDate;
	
	
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

	
	
}
