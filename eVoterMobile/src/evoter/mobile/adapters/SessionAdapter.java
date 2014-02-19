package evoter.mobile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Filterable;
import android.widget.TextView;
import evoter.mobile.abstracts.ItemDataAdapter;
import evoter.mobile.abstracts.ItemDataViewHolder;
import evoter.mobile.main.EVoterShareMemory;
import evoter.mobile.main.R;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.model.Session;

/**
 * {@link SessionAdapter} extends from {@link ItemDataAdapter} is the adapter for show {@link Session}
 * Created by luongnv89 on 06/12/13.
 */
public class SessionAdapter extends ItemDataAdapter implements Filterable {

	public SessionAdapter(Context context) {
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
		SessionHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_session_view_student, null);
			holder = new SessionHolder();
			convertView.setTag(holder);
		} else {
			holder = (SessionHolder) convertView.getTag();
		}

		holder.title = detail(convertView, R.id.tvSesName, listItemDataToPublic
				.get(position).getTitle());
		holder.teacherName = detail(convertView, R.id.tvTeacher, ((Session) listItemDataToPublic.get(position)).getCreatorName());
		if(EVoterShareMemory.getCurrentUserType()==3){
			
		}
		holder.sessionStatus = detail(
				convertView,
				R.id.tvSessionStatus,
				((Session) listItemDataToPublic.get(position)).isActive() ? "Active"
						: "Inactive");
		if (((Session) listItemDataToPublic.get(position)).isActive()) {
			holder.sessionStatus.setTextColor(Color.RED);
			Animation animation = new TranslateAnimation(0, 480, 0, 0);
			// Animation animation = new AlphaAnimation(0.0f,1.0f);
			animation.setDuration(5000);
			// animation.setStartOffset(20);
			animation.setRepeatMode(Animation.REVERSE);
			animation.setRepeatCount(Animation.INFINITE);
			holder.sessionStatus.startAnimation(animation);
		}
		holder.creationDate = detail(convertView, R.id.tvSessDate,
				EVoterMobileUtils.convertToString(((Session) listItemDataToPublic
						.get(position)).getCreationDate()));
		return convertView;
	}

	private class SessionHolder extends ItemDataViewHolder {
		TextView teacherName;
		TextView sessionStatus;

	}
}
