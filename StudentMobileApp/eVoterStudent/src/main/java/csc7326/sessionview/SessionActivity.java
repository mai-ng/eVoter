package csc7326.sessionview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import csc7326.mylectures.InternetChecking;
import csc7326.mylectures.R;
import csc7326.mylectures.SharedData;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SessionActivity extends Activity {

    ArrayList<SessionData> listSessions = new ArrayList<SessionData>();
    ListView listView;
    SessionBaseActivity sessionBaseAdapter;
    Context context;
    String sessionURL = "https://gdata.youtube.com/feeds/api/playlists/";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_sessions);
        context = this;
        sessionURL = sessionURL + SharedData.getSubjectData().getId() + "?alt=jsonc&v=2";
        listView = (ListView) findViewById(R.id.lvSession);
        sessionBaseAdapter = new SessionBaseActivity(listSessions, SessionActivity.this);
        listView.setAdapter(sessionBaseAdapter);
        LoadListSessions loadListSessions = new LoadListSessions();
        loadListSessions.execute();

    }

    /*
    * Order sessions by status and date
    * **/
    private void loadSessions() {
        listSessions.clear();
        listSessions = SharedData.getSubjectData().getListSession();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mnInfalter = getMenuInflater();
        mnInfalter.inflate(R.menu.student_session_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnExit:
                finish();
                return true;
            case R.id.mnListSessionReload:
                loadSessions();
                return true;
            case R.id.mnSubjecInfor:
                //Show the detail information of subject from database
                return true;
            case R.id.mnSubjecDelete:
                //Delete subject, only when the subject has finished!

                return true;
        }
        return false;
    }

    private class LoadListSessions extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setTitle("Loading list session");
            dialog.show();
            super.onPreExecute();
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param aVoid The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            super.onPostExecute(aVoid);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... params) {
            String content = InternetChecking.getData(sessionURL);
//            if(content!=null){
//                dialog.setTitle("Content not null!");
//            }
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                JSONArray array = jsonObject1.getJSONArray("items");
                for (int i = 0; i < array.length(); i++) {

                    JSONObject detailInfor = array.getJSONObject(i).getJSONObject("video");
                    listSessions.add(new SessionData(detailInfor.getString("id"), detailInfor.getString("title"), detailInfor.getString("uploader"), detailInfor.getString("viewCount"), detailInfor.getString("uploaded")));
                }

                sessionBaseAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}