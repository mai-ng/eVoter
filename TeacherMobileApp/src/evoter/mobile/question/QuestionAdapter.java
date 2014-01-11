/**
 * 
 */
package evoter.mobile.question;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.Toast;
import evoter.mobile.item.ItemDataAdapter;
import evoter.mobile.item.ItemDataViewHolder;
import evoter.mobile.main.R;
import evoter.mobile.main.RuntimeEVoterManager;
import evoter.mobile.model.ItemData;

/**
 * @author luongnv89
 * 
 */
public class QuestionAdapter extends ItemDataAdapter implements Filterable {

	/**
	 * @param listSubjects
	 * @param context
	 */
	public QuestionAdapter(ArrayList<ItemData> listSubjects, Context context) {
		super(listSubjects, context);
		// TODO Auto-generated constructor stub
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

		holder.title = detail(convertView, R.id.tvQuestionShortDescript,
				listItemDataToPublic.get(position).getTitle());
		holder.btAction = (Button) convertView
				.findViewById(R.id.btSessionAction);
		holder.btAction.setText("Send");
		if (!RuntimeEVoterManager.getCurrentSessionStatus()) {
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
									+ listItemDataToPublic.get(position)
											.getTitle(),
							Toast.LENGTH_LONG).show();

				}
			}
			);
		}
		return convertView;
	}


	private class QuestionHolder extends ItemDataViewHolder{
		Button btAction;
	}
}
