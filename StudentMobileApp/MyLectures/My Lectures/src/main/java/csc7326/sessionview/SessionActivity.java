package csc7326.sessionview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import csc7326.mylectures.SharedData;
import csc7326.mylectures.R;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SessionActivity extends Activity {

    ArrayList<SessionData> listSessions = new ArrayList<SessionData>();
    ListView listView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_sessions);
        loadSessions();
        listView = (ListView) findViewById(R.id.lvSession);
        listView.setAdapter(new SessionBaseActivity(listSessions, SessionActivity.this));
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
}