package evoter.mobile.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import evoter.mobile.abstracts.ItemDataAdapter;
import evoter.mobile.abstracts.ItemDataViewHolder;
import evoter.mobile.main.R;
import evoter.share.model.Subject;
import evoter.mobile.utils.EVoterMobileUtils;

/**
 * {@link SubjectAdapter} extend from {@link ItemDataAdapter} is adapter for ListView of {@link Subject}
 * Created by luongnv89 on 06/12/13.
 */
public class SubjectAdapter extends ItemDataAdapter implements Filterable {

	public SubjectAdapter(Context context) {
		super(context);
	}

	/**
	 * Get a View that displays the data at the specified position in the data
	 * set. You can either create a View manually or inflate it from an XML
	 * layout file. When the View is inflated, the parent View (GridView,
	 * ListView...) will apply default layout parameters unless you use
	 * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
	 * to specify a root view and to prevent attachment to the root.
	 * 
	 * @param position
	 *            The position of the item within the adapter's data set of the
	 *            item whose view we want.
	 * @param convertView
	 *            The old view to reuse, if possible. Note: You should check
	 *            that this view is non-null and of an appropriate type before
	 *            using. If it is not possible to convert this view to display
	 *            the correct data, this method can create a new view.
	 *            Heterogeneous lists can specify their number of view types, so
	 *            that this View is always of the right type (see
	 *            {@link #getViewTypeCount()} and {@link #getItemViewType(int)}
	 *            ).
	 * @param parent
	 *            The parent that this view will eventually be attached to
	 * @return A View corresponding to the data at the specified position.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SubjectViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_subject_view, null);
			holder = new SubjectViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (SubjectViewHolder) convertView.getTag();
		}

		holder.title = detail(convertView, R.id.tvSubname, listItemDataToPublic
				.get(position).getTitle());
		holder.creationDate = detail(
				convertView,
				R.id.tvSubDate,
				"Created: "
						+ EVoterMobileUtils.convertToString(((Subject) listItemDataToPublic
								.get(position)).getCreationDate()));
		return convertView;
	}

	private class SubjectViewHolder extends ItemDataViewHolder {
	}

}
