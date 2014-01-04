package csc7326.session;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import csc7326.mylectures.EVoterSessionManager;
import csc7326.mylectures.InternetChecking;
import csc7326.mylectures.R;
import csc7326.mylectures.SharedData;
import csc7326.mylectures.Splash;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SessionActivity extends Activity {

    ArrayList<SessionData> listSessions = new ArrayList<SessionData>();
    ListView listView;
    SessionBaseAdapter sessionBaseAdapter;
    Context context;
    String sessionURL = "https://gdata.youtube.com/feeds/api/playlists/";
    EditText etSearch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_sessions);
        context = this;
        sessionURL = sessionURL + SharedData.getSubjectData().getId() + "?alt=jsonc&v=2";
        listView = (ListView) findViewById(R.id.lvSession);
        sessionBaseAdapter = new SessionBaseAdapter(listSessions, SessionActivity.this);
        listView.setAdapter(sessionBaseAdapter);
        LoadListSessions loadListSessions = new LoadListSessions();
        loadListSessions.execute();

        etSearch = (EditText) findViewById(R.id.etSessionSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                SessionActivity.this.sessionBaseAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SessionData selectedSession = (SessionData) parent.getItemAtPosition(position);
                Toast.makeText(SessionActivity.this, "Process item clicked for item: " + selectedSession.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SessionData selectedSession = (SessionData) parent.getItemAtPosition(position);
                Toast.makeText(SessionActivity.this, "Process item long clicked for item: " + selectedSession.getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

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
                Intent exitIntent = new Intent(this, Splash.class);
                exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                exitIntent.putExtra("Exit application", true);
                startActivity(exitIntent);
                finish();
                return true;
            case R.id.mnListSessionReload:
                LoadListSessions loadListSessions = new LoadListSessions();
                loadListSessions.execute();
                return true;
            case R.id.mnLogout:
                EVoterSessionManager eVoterSessionManager = new EVoterSessionManager(this);
                if (eVoterSessionManager.isLoggedIn()) {
                    eVoterSessionManager.logoutUser();
                }
                eVoterSessionManager.checkLogin();
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
            listSessions.clear();
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                JSONArray array = jsonObject1.getJSONArray("items");
                for (int i = 0; i < array.length(); i++) {

                    JSONObject detailInfor = array.getJSONObject(i).getJSONObject("video");
                    SessionData sessionData = new SessionData(detailInfor.getString("id"), detailInfor.getString("title"), detailInfor.getString("uploader"), detailInfor.getString("viewCount"), detailInfor.getString("uploaded"));
                    int count = Integer.parseInt(sessionData.getStatus());
                    if (count % 2 == 0)
                        sessionData.setStatus(count + " - Status: Wait for accepting");
                    else {
                        sessionData.setStatus(count + " - Status: accepted");
                    }
                    if (count % 7 == 0) sessionData.setActive(true);
                    else {
                        sessionData.setActive(false);
                    }
                    listSessions.add(sessionData);
                }

                sessionBaseAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}