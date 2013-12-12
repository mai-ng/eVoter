package csc7326.sessionview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import csc7326.enums.SessionStatus;
import csc7326.mylectures.R;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SessionBaseActivity extends BaseAdapter {

    ArrayList<SessionData> listSessions;
    Context context;
    LayoutInflater inflater;

    public SessionBaseActivity(ArrayList<SessionData> listSessions, Context context) {
        this.listSessions = listSessions;
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
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public SessionData getItem(int position) {
        return listSessions.get(position);
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
        SessionHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_session_view, null);
            holder = new SessionHolder();
            convertView.setTag(holder);
        } else {
            holder = (SessionHolder) convertView.getTag();
        }

        String stt = listSessions.get(position).getStatus();
        holder.sessionName = detail(convertView, R.id.tvSesName, listSessions.get(position).getName());
        holder.teacherName = detail(convertView, R.id.tvTeacher, listSessions.get(position).getTeacherName());
        holder.sessionStatus = detail(convertView, R.id.tvSessionStatus, listSessions.get(position).getStatus());
        holder.sessionDate = detail(convertView, R.id.tvSessDate, listSessions.get(position).getCreateDate());
        holder.btAction = (Button) convertView.findViewById(R.id.btSessionAction);
        //holder.btDelete = (Button) convertView.findViewById(R.id.btDeleteSession);
        //Check status of session to set label for button action here
        if (stt.equals(SessionStatus.FINISHED)) {
            holder.btAction.setText("History");
        } else if (stt.equals(SessionStatus.RUNNING)) {
            holder.btAction.setText("Resume");
        }
        //Check join if the session is started by teacher
        else if (stt.equals(SessionStatus.WAITING_FOR_JOINING)) {
            //Only enable if the session is running
            holder.btAction.setText("Join");
        } else if (stt.equals(SessionStatus.WAITING_FOR_ACCEPTACE)) {
            holder.btAction.setText("Accept");
        }


        return convertView;
    }

    private TextView detail(View view, int resId, String text) {
        TextView tv = (TextView) view.findViewById(resId);
        tv.setText(text);
        return tv;
    }


    private class SessionHolder {
        TextView sessionName;
        TextView teacherName;
        TextView sessionStatus;
        TextView sessionDate;
        Button btAction;
       // Button btDelete;

    }
}
