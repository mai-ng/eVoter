package csc7326.subjectview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

import csc7326.enums.SessionStatus;
import csc7326.mylectures.R;
import csc7326.mylectures.SharedData;
import csc7326.sessionview.SessionData;

/**
 * Created by luongnv89 on 05/12/13.
 */
public class SubjectActivity extends Activity {

    ListView listSubView;
    ArrayList<SubjectData> listSubjects = new ArrayList<SubjectData>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_subjects);

        listSubView = (ListView) findViewById(R.id.lvSubjects);
        loadSubjects();
        listSubView.setAdapter(new SubjectBaseAdapter(listSubjects, SubjectActivity.this));
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
    private void loadSubjects() {
        //Delete old data
        listSubjects.clear();
        for (int i = 0; i < 20; i++) {
            String id = String.valueOf(i);
            ArrayList<SessionData> listSession = new ArrayList<SessionData>();
            ArrayList<String> listStatus = new ArrayList<String>();
            listStatus.add(SessionStatus.WAITING_FOR_JOINING);
            listStatus.add(SessionStatus.WAITING_FOR_ACCEPTACE);
            listStatus.add(SessionStatus.FINISHED);
            listStatus.add(SessionStatus.RUNNING);
            for (int j = 0; j < 5 + i; j++) {
                String ids = String.valueOf(j);

                Random ran = new Random();
                int sttIndex = ran.nextInt(listStatus.size());
                listSession.add(new SessionData(ids, "Session " + ids, "Teacher " + ids, listStatus.get(sttIndex), "Created: 06/12/2013"));
            }
            listSubjects.add(new SubjectData(id, "Subject name " + id, "05/12/13", listSession));
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
}