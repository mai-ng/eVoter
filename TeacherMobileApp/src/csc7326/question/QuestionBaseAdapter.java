/**
 * 
 */
package csc7326.question;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import csc7326.main.R;
import csc7326.utils.Utils;
import evoter.server.model.Question;

/**
 * @author luongnv89
 *
 */
public class QuestionBaseAdapter extends BaseAdapter implements Filterable{

	ArrayList<Question> listSessions;
	ArrayList<Question> listSessionsFilters;

	Context context;
	LayoutInflater inflater;
	private QuestionValueFilter valueFilter;

	public QuestionBaseAdapter(ArrayList<Question> listSessions, Context context) {
		this.listSessions = listSessions;
		this.listSessionsFilters = listSessions;
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
	}

	/**
	 * How many items are in the data set represented by this Adapter.
	 * 
	 * @return Count of items.
	 */
	@Override
	public int getCount() {
		return listSessions.size();
	}

	/**
	 * Get the data item associated with the specified position in the data set.
	 * 
	 * @param position
	 *            Position of the item whose data we want within the adapter's
	 *            data set.
	 * @return The data at the specified position.
	 */
	@Override
	public Question getItem(int position) {
		return listSessions.get(position);
	}

	/**
	 * Get the row id associated with the specified position in the list.
	 * 
	 * @param position
	 *            The position of the item within the adapter's data set whose
	 *            row id we want.
	 * @return The id of the item at the specified position.
	 */
	@Override
	public long getItemId(int position) {
		return position;
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
		QuestionHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.student_session_view, null);
			holder = new QuestionHolder();
			convertView.setTag(holder);
		} else {
			holder = (QuestionHolder) convertView.getTag();
		}

		holder.sessionName = detail(convertView, R.id.tvSesName, listSessions
				.get(position).getName());
		holder.teacherName = detail(convertView, R.id.tvTeacher, "Unknown");
		holder.sessionStatus = detail(convertView, R.id.tvSessionStatus,
				listSessions.get(position).isActive() ? "Active" : "Inactive");
		if (listSessions.get(position).isActive()) {
			holder.sessionStatus.setTextColor(Color.RED);
			Animation animation = new TranslateAnimation(0, 480, 0, 0);
			// Animation animation = new AlphaAnimation(0.0f,1.0f);
			animation.setDuration(5000);
			// animation.setStartOffset(20);
			animation.setRepeatMode(Animation.REVERSE);
			animation.setRepeatCount(Animation.INFINITE);
			holder.sessionStatus.startAnimation(animation);
		}
		holder.sessionDate = detail(convertView, R.id.tvSessDate,
				Utils.convertToString(listSessions.get(position)
						.getCreationDate()));
		return convertView;
	}

	private TextView detail(View view, int resId, String text) {
		TextView tv = (TextView) view.findViewById(resId);
		tv.setText(text);
		return tv;
	}

	/**
	 * <p>
	 * Returns a filter that can be used to constrain data with a filtering
	 * pattern.
	 * </p>
	 * <p/>
	 * <p>
	 * This method is usually implemented by {@link android.widget.Adapter}
	 * classes.
	 * </p>
	 * 
	 * @return a filter used to constrain data
	 */
	@Override
	public Filter getFilter() {
		if (valueFilter == null) {

			valueFilter = new QuestionValueFilter();
		}

		return valueFilter;
	}

	private class QuestionHolder {
		TextView sessionName;
		TextView teacherName;
		TextView sessionStatus;
		TextView sessionDate;

	}

	private class QuestionValueFilter extends Filter {

		// Invoked in a worker thread to filter the data according to the
		// constraint.
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				ArrayList<Question> filterList = new ArrayList<Question>();
				for (int i = 0; i < listSessionsFilters.size(); i++) {
					if ((listSessionsFilters.get(i).getName().toUpperCase())
							.contains(constraint.toString().toUpperCase())) {
						Question subject = new Question(
								listSessionsFilters.get(i));
						filterList.add(subject);
					}
				}
				results.count = filterList.size();
				results.values = filterList;
			} else {
				results.count = listSessionsFilters.size();
				results.values = listSessionsFilters;
			}
			return results;
		}

		// Invoked in the UI thread to publish the filtering results in the user
		// interface.
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			listSessions = (ArrayList<Question>) results.values;
			notifyDataSetChanged();
		}
	}
}
