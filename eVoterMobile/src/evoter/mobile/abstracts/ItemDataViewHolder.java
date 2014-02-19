/**
 * 
 */
package evoter.mobile.abstracts;

import evoter.share.model.ItemData;
import android.widget.TextView;

/**
 * Holder view of an {@link ItemData}
 * @author luongnv89
 *
 */
public abstract class ItemDataViewHolder {
	/**
	 * Title to show
	 */
	public TextView title;
	/**
	 * Create date to show
	 */
	public TextView creationDate;

}
