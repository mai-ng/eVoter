/**
 * 
 */
package evoter.mobile.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;

/**
 * <br>
 * Updated by @author luongnv89 on 12-Feb-2014:<br>
 * <li>Completed loadListItemData() method <br>
 * Created by @author luongnv89
 */
public class SubjectUserActivity extends EVoterActivity {
	
	ListView lvTeacher;
	ListView lvStudent;
	ArrayList<String> listTeachers;
	ArrayList<String> listStudents;
	ArrayAdapter<String> teacherAdapter;
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
	 * 
	 */
	private void initComponents() {
		listTeachers = new ArrayList<String>();
		listStudents = new ArrayList<String>();
		
		lvTeacher = (ListView) findViewById(R.id.lvSubjectProfessor);
		teacherAdapter = new ArrayAdapter<String>(SubjectUserActivity.this, R.layout.user_item, listTeachers);
		lvTeacher.setAdapter(teacherAdapter);
		lvStudent = (ListView) findViewById(R.id.lvSubjectStudents);
		studentAdapter = new ArrayAdapter<String>(SubjectUserActivity.this, R.layout.user_item, listStudents);
		lvStudent.setAdapter(studentAdapter);
	}
	
	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		super.setupTitleBar();
		tvTitleBarContent.setText(EVoterShareMemory.getCurrentSubject().getTitle());
		ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listStudents.clear();
				listTeachers.clear();
				refeshContent();
			}
		});
		
	}



	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupContentMainMenu()
	 */
	@Override
	protected void setupContentMainMenu() {
		// TODO Auto-generated method stub
		super.setupContentMainMenu();
	}


	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.GET_USER_OF_SUBJECT_EVOTER_REQUEST)) {
				JSONArray array = null;
			try {
				array = new JSONArray(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array = null;
			}
			
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject ob = null;
					try {
						ob = array.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						ob = null;
					}
					if(ob!=null){
						String user = EVoterMobileUtils.parserJSONToUser(ob);
						if (user.contains("TEACHER_")) {
							listTeachers.add((listTeachers.size()+1)+". "+user.replace("TEACHER_", ""));
						} else if (user.contains("STUDENT_")) {
							listStudents.add((listStudents.size()+1)+". "+user.replace("STUDENT_", ""));
						}
					}
				}
			}
			studentAdapter.notifyDataSetChanged();
			teacherAdapter.notifyDataSetChanged();
			
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
	}

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		EVoterRequestManager.getUserOfSubject(this, EVoterShareMemory.getCurrentSubject().getId());
	}
	
}
