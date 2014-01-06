package csc7326.subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import csc7326.mylectures.R;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SubjectBaseAdapter extends BaseAdapter implements Filterable {

    ArrayList<SubjectData> listSubjects = new ArrayList<SubjectData>();
    ArrayList<SubjectData> listFilterSubjects = new ArrayList<SubjectData>();
    LayoutInflater inflater;
    Context context;
    private ValueFilter valueFilter;

    public SubjectBaseAdapter(ArrayList<SubjectData> listSubjects, Context context) {
        this.listSubjects = listSubjects;
        this.listFilterSubjects = listSubjects;
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
        return listSubjects.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public SubjectData getItem(int position) {
        return listSubjects.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubjectViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_subject_view, null);
            holder = new SubjectViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (SubjectViewHolder) convertView.getTag();
        }

        holder.subjectName = detail(convertView, R.id.tvSubname, listSubjects.get(position).getName());
        holder.nbSession = detail(convertView, R.id.tvNumSessions, String.valueOf(listSubjects.get(position).getNbSessions()) + " sessions");
        holder.createDate = detail(convertView, R.id.tvSubDate, "Created: " + listSubjects.get(position).getDate());
        return convertView;
    }

    private TextView detail(View view, int resId, String text) {
        TextView tv = (TextView) view.findViewById(resId);
        tv.setText(text);
        return tv;
    }

    /**
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     * <p/>
     * <p>This method is usually implemented by {@link android.widget.Adapter}
     * classes.</p>
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

    private class SubjectViewHolder {
        TextView subjectName;
        TextView nbSession;
        TextView createDate;
    }

    private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<SubjectData> filterList = new ArrayList<SubjectData>();
                for (int i = 0; i < listFilterSubjects.size(); i++) {
                    if ((listFilterSubjects.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        SubjectData subject = new SubjectData(listFilterSubjects.get(i));
                        filterList.add(subject);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = listFilterSubjects.size();
                results.values = listFilterSubjects;
            }
            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            listSubjects = (ArrayList<SubjectData>) results.values;
            notifyDataSetChanged();
        }
    }

    public void deleteItem(String subjectID) {
        for (int i = 0; i < listSubjects.size(); i++) {
            if (listSubjects.get(i).getId().equals(subjectID)) {
                listSubjects.remove(i);
                break;
            }
        }

        for (int i = 0; i < listFilterSubjects.size(); i++) {
            if (listFilterSubjects.get(i).getId().equals(subjectID)) {
                listFilterSubjects.remove(i);
                break;
            }
        }


    }
}
