/**
 * 
 */
package evoter.mobile.item;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import evoter.mobile.model.ItemData;

/**
 * @author luongnv89
 * 
 */
public abstract class ItemDataAdapter extends BaseAdapter implements Filterable {

	protected ArrayList<ItemData> listItemDataToPublic = new ArrayList<ItemData>();
	protected ArrayList<ItemData> listItemDataForFiltering = new ArrayList<ItemData>();
	protected LayoutInflater inflater;
	protected Context context;
	protected ValueFilter valueFilter;

	public ItemDataAdapter(ArrayList<ItemData> listSubjects, Context context) {
		this.listItemDataToPublic = listSubjects;
		this.listItemDataToPublic = listSubjects;
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItemDataToPublic.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItemDataToPublic.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return listItemDataToPublic.get(position).getId();
	}

	protected TextView detail(View view, int resId, String text) {
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

			valueFilter = new ValueFilter();
		}

		return valueFilter;
	}

	private class ValueFilter extends Filter {

		// Invoked in a worker thread to filter the data according to the
		// constraint.
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint != null && constraint.length() > 0) {
				ArrayList<ItemData> filterList = new ArrayList<ItemData>();
				for (int i = 0; i < listItemDataForFiltering.size(); i++) {
					if (listItemDataForFiltering.get(i).getTitle()
							.contains(constraint)) {
						filterList.add(listItemDataForFiltering.get(i));
					}
				}
				results.count = filterList.size();
				results.values = filterList;
			} else {
				results.count = listItemDataForFiltering.size();
				results.values = listItemDataForFiltering;
			}
			return results;
		}

		// Invoked in the UI thread to publish the filtering results in the user
		// interface.
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			listItemDataToPublic = (ArrayList<ItemData>) results.values;
			notifyDataSetChanged();
		}
	}

	public void deleteItem(long subjectID) {
		for (int i = 0; i < listItemDataToPublic.size(); i++) {
			if (listItemDataToPublic.get(i).getId() == subjectID) {
				listItemDataToPublic.remove(i);
				break;
			}
		}

		for (int i = 0; i < listItemDataForFiltering.size(); i++) {
			if (listItemDataForFiltering.get(i).getId() == subjectID) {
				listItemDataForFiltering.remove(i);
				break;
			}
		}
		notifyDataSetChanged();
	}

	public void updateList(ArrayList<ItemData> list) {
		listItemDataToPublic = list;
		listItemDataForFiltering = list;
		notifyDataSetChanged();
	}

}
