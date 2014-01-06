package csc7326.subject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import csc7326.session.SessionData;

/**
 * Created by luongnv89 on 05/12/13.
 */
public class SubjectActivity extends Activity {

    ListView listSubView;
    ArrayList<SubjectData> listSubjects = new ArrayList<SubjectData>();
    SubjectBaseAdapter subjectBaseAdapter;
    Context context;
    String subjectsURL;
    EditText etSearch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_subjects);
        context = this;

        subjectsURL = "https://gdata.youtube.com/feeds/api/users/chatonaudiobooks/playlists?v=2&alt=jsonc";
        listSubView = (ListView) findViewById(R.id.lvSubjects);
        subjectBaseAdapter = new SubjectBaseAdapter(listSubjects, SubjectActivity.this);
        listSubView.setAdapter(subjectBaseAdapter);
        LoadListSubject loadListSubject = new LoadListSubject();
        loadListSubject.execute();
        listSubView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedData.setSubjectData((SubjectData) parent.getItemAtPosition(position));
                startActivity(new Intent("android.intent.action.SESSION"));
            }
        });

        listSubView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(SubjectActivity.this);
                final SubjectData subject = (SubjectData) parent.getItemAtPosition(position);
                dialog.setContentView(R.layout.subject_dialog);
                dialog.setTitle("Subject Action");

                Button btSbjDelete = (Button) dialog.findViewById(R.id.btSubjectDeleting);

                btSbjDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SubjectActivity.this, "Deleted subject: " + subject.getName(), Toast.LENGTH_LONG).show();
                        subjectBaseAdapter.deleteItem(subject.getId());
                        subjectBaseAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                Button btSbjDetail = (Button) dialog.findViewById(R.id.btSubjectDetail);
                btSbjDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SubjectActivity.this, "Not implemented yet!!!" + subject.getName(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            }
        });


        etSearch = (EditText) findViewById(R.id.etSubjectSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                SubjectActivity.this.subjectBaseAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mnInfalter = getMenuInflater();
        mnInfalter.inflate(R.menu.student_subject_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnListSubjectReload:
                LoadListSubject loadListSubject = new LoadListSubject();
                loadListSubject.execute();
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

    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    @Override
    public void onBackPressed() {
        Intent exitIntent = new Intent(this, Splash.class);
        exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        exitIntent.putExtra("Exit application", true);
        startActivity(exitIntent);
        finish();
    }

    private class LoadListSubject extends AsyncTask<Void, Void, Void> {
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
            dialog.setTitle("Loading list subjects");
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
            String content = InternetChecking.getData(subjectsURL);
            listSubjects.clear();
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                JSONArray array = jsonObject1.getJSONArray("items");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject items = array.getJSONObject(i);
                    SubjectData subject = new SubjectData(items.getString("id"), items.getString("title"), items.getString("updated"));
                    subject.setNbSessions(Integer.parseInt(items.getString("size")));
                    listSubjects.add(subject);

                }

                subjectBaseAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }

}