/**
 * 
 */
package evoter.share.model;

import java.io.Serializable;

/**
 * {@link ItemData} is an abstract class of those item: subject, session,
 * question.
 * 
 * @author luongnv89
 */
public abstract class ItemData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The id of item which is the item's id in database
	 */
	protected long id;
	
	/**
	 * The title of item which will be presented as the name of item in list
	 * item
	 */
	protected String title;
	
	/**
	 * 
	 */
	public ItemData() {
	}
	
	/**
	 * @param id
	 * @param title
	 */
	public ItemData(long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	/**
	 * Copy an object of item
	 * 
	 * @param cp
	 */
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
