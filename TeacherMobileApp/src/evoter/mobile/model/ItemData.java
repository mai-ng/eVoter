/**
 * 
 */
package evoter.mobile.model;

import java.io.Serializable;


/**
 * @author luongnv89
 * 
 */
public abstract class ItemData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected long id;

	protected String title;

	/**
	 * @param id
	 * @param title
	 */
	public ItemData(long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public ItemData(ItemData cp) {
		this.id = cp.getId();
		this.title = cp.getTitle();
	}

	
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

}
