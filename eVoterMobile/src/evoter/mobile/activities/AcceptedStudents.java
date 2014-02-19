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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import evoter.mobile.main.R;
import evoter.mobile.utils.CallBackMessage;
import evoter.share.dao.UserDAO;

/**
 * @author luongnv89
 */
public class AcceptedStudents extends EVoterActivity {
	ArrayList<String> listStudents;
	ListView lvStudent;
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
		ivTitleBarRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshData();
				
			}
		});
		mainMenu.setQuestionActivityMenu();
		mainMenu.getBtAcceptUsers().setVisibility(View.GONE);
		listStudents = new ArrayList<String>();
		
		lvStudent = (ListView) findViewById(R.id.lvSubjectStudents);
		studentAdapter = new ArrayAdapter<String>(AcceptedStudents.this, R.layout.user_item, listStudents);
		lvStudent.setAdapter(studentAdapter);
		ListView lvTeacher = (ListView) findViewById(R.id.lvSubjectProfessor);
		lvTeacher.setVisibility(View.GONE);
		TextView tv = (TextView) findViewById(R.id.tvListProfessors);
		tv.setVisibility(View.GONE);
		refreshData();
	}
	
	/**
	 * 
	 */
	public void refreshData() {
		EVoterRequestManager.getUsersOfSession(this);
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
			try {
				JSONArray array = new JSONArray(response);
				for (int i = 0; i < array.length(); i++) {
					JSONObject ob = array.getJSONObject(i);
					Log.i("Object: " + i, "User type: " + String.valueOf(ob.getLong(UserDAO.USER_TYPE_ID)));
					listStudents.add((listStudents.size() + 1) + ". " + ob.getString(UserDAO.FULL_NAME) + ": " + ob.getString(UserDAO.EMAIL));
				}
				Log.i("Total students: ", String.valueOf(listStudents.size()));
				studentAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			super.updateRequestCallBack(response, callBackMessage);
		}
	}
	
}
