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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

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
		mainMenu.setQuestionActivityMenu();
		mainMenu.getBtAcceptUsers().setVisibility(View.GONE);
		listStudents = new ArrayList<String>();
		
		lvStudent = (ListView) findViewById(R.id.lvSubjectStudents);
		studentAdapter = new ArrayAdapter<String>(AcceptedStudents.this, R.layout.user_item, listStudents);
		lvStudent.setAdapter(studentAdapter);
		ListView lvTeacher = (ListView)findViewById(R.id.lvSubjectProfessor);
		lvTeacher.setVisibility(View.GONE);
		TextView tv = (TextView)findViewById(R.id.tvListProfessors);
		tv.setVisibility(View.GONE);
		loadListItemData();
	}
	
	/**
	 * 
	 */
	private void loadListItemData() {
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
//		parameters.put(SessionUserDAO.SESSION_ID, String.valueOf(sessionId));
//		parameters.put(SessionUserDAO.ACCEPT_SESSION, String.valueOf(isAccepted));
		params.add(SessionUserDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSessionID()));
		params.add(SessionUserDAO.ACCEPT_SESSION, String.valueOf(true));
		
		client.post(RequestConfig.getURL(URIRequest.ACCEPT_SESSION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
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
						
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								AcceptedStudents.this,
								"Cannot request to server!");
						Log.e("LoginTest", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
		
	}
	
}
