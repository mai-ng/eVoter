package csc7326.subjectview;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import csc7326.mylectures.InternetChecking;
import csc7326.mylectures.R;
import csc7326.mylectures.SharedData;
import csc7326.sessionview.SessionData;

/**
 * Created by luongnv89 on 05/12/13.
 */
public class SubjectActivity extends Activity {

    ListView listSubView;
    ArrayList<SubjectData> listSubjects = new ArrayList<SubjectData>();
    SubjectBaseAdapter subjectBaseAdapter;
    Context context;
    String subjectsURL;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_subjects);
        context = this;
        subjectsURL="https://gdata.youtube.com/feeds/api/users/chatonaudiobooks/playlists?v=2&alt=jsonc";
        listSubView = (ListView) findViewById(R.id.lvSubjects);
        subjectBaseAdapter = new SubjectBaseAdapter(listSubjects, SubjectActivity.this);
        listSubView.setAdapter(subjectBaseAdapter);
        LoadListSubject loadListSubject = new LoadListSubject();
        loadListSubject.execute();
        listSubView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedData.setSubjectData(listSubjects.get(position));
                startActivity(new Intent("android.intent.action.SESSION"));
            }
        });
    }

    /**
     * Load subjects from data
     */
    private void loadSubjects(){
        //Delete old data
        listSubjects.clear();
        if(!InternetChecking.isConnected(this)){
            Dialog error = new Dialog(this);
            error.setTitle("Cannot connect to network!!!");
            error.show();
        }else{

//        if(content!=null){

//        }
//        else{
//            for (int i = 0; i < 20; i++) {
//                String id = String.valueOf(i);
//                ArrayList<SessionData> listSession = new ArrayList<SessionData>();
//                ArrayList<String> listStatus = new ArrayList<String>();
//                listStatus.add(SessionStatus.WAITING_FOR_JOINING);
//                listStatus.add(SessionStatus.WAITING_FOR_ACCEPTACE);
//                listStatus.add(SessionStatus.FINISHED);
//                listStatus.add(SessionStatus.RUNNING);
//                for (int j = 0; j < 5 + i; j++) {
//                    String ids = String.valueOf(j);
//
//                    Random ran = new Random();
//                    int sttIndex = ran.nextInt(listStatus.size());
//                    listSession.add(new SessionData(ids, "Session " + ids, "Teacher " + ids, listStatus.get(sttIndex), "Created: 06/12/2013"));
//                }
//                listSubjects.add(new SubjectData(id, "Subject name " + id, "05/12/13", listSession));
//            }
//        }
//        subjectBaseAdapter.notifyDataSetChanged();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mnInfalter = getMenuInflater();
        mnInfalter.inflate(R.menu.student_subject_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnExit:
                finish();
                return true;
            case R.id.mnListSubjectReload:
                loadSubjects();
                return true;
        }
        return false;
    }

    private class LoadListSubject extends AsyncTask<Void,Void,Void>{
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
//            if(content!=null){
//                dialog.setTitle("Content not null!");
//            }
            try{
                JSONObject jsonObject = new JSONObject(content);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                JSONArray array = jsonObject1.getJSONArray("items");
                for(int i=0;i<array.length();i++){
                    JSONObject items = array.getJSONObject(i);
                    listSubjects.add(new SubjectData(items.getString("id"),items.getString("title"),items.getString("updated"),new ArrayList<SessionData>()));

                }

                subjectBaseAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}