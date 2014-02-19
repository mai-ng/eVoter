/**
 * 
 */
package evoter.mobile.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.main.EVoterRequestManager;
import evoter.mobile.main.EVoterShareMemory;
import evoter.mobile.main.R;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;

/**
 * Show email of all student who accepted to join current session
 * 
 * @author luongnv89
 */
public class AcceptedStudentsActivity extends EVoterActivity {
	/**
	 * List email of student
	 */
	ArrayList<String> listStudents;
	/**
	 * List view to show the list email
	 */
	ListView lvStudent;
	/**
	 * Adapter of list view
	 */
	ArrayAdapter<String> studentAdapter;
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_users);
		initComponents();
	}
	
	/**
	 * Init components of {@link AcceptedStudentsActivity}
	 */
	private void initComponents() {
		listStudents = new ArrayList<String>();
		lvStudent = (ListView) findViewById(R.id.lvSubjectStudents);
		studentAdapter = new ArrayAdapter<String>(AcceptedStudentsActivity.this, R.layout.user_item, listStudents);
		lvStudent.setAdapter(studentAdapter);
		ListView lvTeacher = (ListView) findViewById(R.id.lvSubjectProfessor);
		lvTeacher.setVisibility(View.GONE);
		TextView tv = (TextView) findViewById(R.id.tvListProfessors);
		tv.setVisibility(View.GONE);
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupContentMainMenu()
	 */
	@Override
	protected void setupContentMainMenu() {
		// TODO Auto-generated method stub
		super.setupContentMainMenu();
		setQuestionActivityMenu();
		mainMenu.getBtAcceptedStudent().setVisibility(View.GONE);
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		// TODO Auto-generated method stub
		super.setupTitleBar();
		tvTitleBarContent.setText(EVoterShareMemory.getCurrentSession().getTitle());
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.GET_LIST_ACCEPTED_STUDENT_EVOTER_REQUEST)) {
			Log.i("Get accepted users:", response);
			JSONArray array;
			try {
				array = new JSONArray(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array = null;
			}
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject ob;
					try {
						ob = array.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						ob = null;
					}
					if (ob != null) {
						String user = EVoterMobileUtils.parserJSONToUser(ob);
						if (user.contains("STUDENT_")) {
							listStudents.add((listStudents.size() + 1) + ". " + user.replace("STUDENT_", ""));
						}
					}
				}
				Log.i("Total students: ", String.valueOf(listStudents.size()));
				studentAdapter.notifyDataSetChanged();
			}
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		EVoterRequestManager.getUsersOfSession(this);
	}
	
}
