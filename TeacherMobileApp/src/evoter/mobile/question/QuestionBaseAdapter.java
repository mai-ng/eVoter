/**
 * 
 */
package evoter.mobile.question;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import evoter.mobile.main.EVoterSessionManager;
import evoter.mobile.main.R;
import evoter.server.model.Question;

/**
 * @author luongnv89
 * 
 */
public class QuestionBaseAdapter extends BaseAdapter implements Filterable {

	ArrayList<Question> listQuestionToPublic;
	ArrayList<Question> listQuestionToFilter;

	Context context;
	LayoutInflater inflater;
	private QuestionValueFilter valueFilter;

	public QuestionBaseAdapter(ArrayList<Question> listQuestion, Context context) {
		this.listQuestionToPublic = listQuestion;
		this.listQuestionToFilter = listQuestion;
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
		return listQuestionToPublic.size();
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
		return listQuestionToPublic.get(position);
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		QuestionHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.question_view_item, null);
			holder = new QuestionHolder();
			convertView.setTag(holder);
		} else {
			holder = (QuestionHolder) convertView.getTag();
		}

		holder.auestionText = detail(convertView, R.id.tvQuestionShortDescript,
				listQuestionToPublic.get(position).getQuestionText());
		holder.btAction = (Button) convertView
				.findViewById(R.id.btSessionAction);
		holder.btAction.setText("Send");
		if (!EVoterSessionManager.getCurrentSessionStatus()) {
			holder.btAction.setTextColor(Color.GRAY);
			holder.btAction.setClickable(false);
		} else {
			holder.btAction.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// TODO: Send question for student and move to statistic
					// view

					Toast.makeText(
							context,
							"Send question for student and move to statistic view: "
									+ listQuestionToPublic.get(position)
											.getQuestionText(),
							Toast.LENGTH_LONG).show();

				}
			}
			);
		}
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
		TextView auestionText;
		Button btAction;
	}

	private class QuestionValueFilter extends Filter {

		// Invoked in a worker thread to filter the data according to the
		// constraint.
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				ArrayList<Question> filterList = new ArrayList<Question>();
				for (int i = 0; i < listQuestionToFilter.size(); i++) {
					if ((listQuestionToFilter.get(i).getQuestionText()
							.toUpperCase()).contains(constraint.toString()
							.toUpperCase())) {
						Question subject = new Question(
								listQuestionToFilter.get(i));
						filterList.add(subject);
					}
				}
				results.count = filterList.size();
				results.values = filterList;
			} else {
				results.count = listQuestionToFilter.size();
				results.values = listQuestionToFilter;
			}
			return results;
		}

		// Invoked in the UI thread to publish the filtering results in the user
		// interface.
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			listQuestionToPublic = (ArrayList<Question>) results.values;
			notifyDataSetChanged();
		}
	}
}
