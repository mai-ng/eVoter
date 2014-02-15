/**
 * 
 */
package evoter.mobile.activities;

import java.sql.Timestamp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

/**<br>Update by @author luongnv89 on 12-Feb-2014:<br>
 * <li> Completed send create session request to server 
 * <br>Created by @author luongnv89 on 30-Jun-2014 <br>
 * Manage creating a new session in a subject
 */
public class NewSessionActivity extends EVoterActivity {
	
	EditText etTitle;
	ListView listView;
	Button btAddQuestion;
	Button btSave;
	Button btCancel;
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.new_session);
		
		this.tvTitleBarContent.setText(EVoterShareMemory
				.getCurrentSubjectName());
		mainMenu.setSessionActivityMenu();
		mainMenu.getBtNewSession().setVisibility(View.GONE);
		
		etTitle = (EditText) findViewById(R.id.etNewSessionName);
		btSave = (Button) findViewById(R.id.btNewSessionSave);
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (etTitle.getText().toString().equals("")) {
					EVoterMobileUtils.showeVoterToast(NewSessionActivity.this, "Session title cannot be empty!");
				}
				else {
					
					createSessionRequest();
					finish();
				}
			}
		});
		btCancel = (Button) findViewById(R.id.btNewSessionCancel);
		btCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 
	 */
	private void createSessionRequest() {
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		params.add(SessionDAO.CREATION_DATE, EVoterMobileUtils.convertToString(timeStamp));
		params.add(SessionDAO.IS_ACTIVE, String.valueOf(false));
		params.add(SessionDAO.NAME, etTitle.getText().toString());
		params.add(SessionDAO.SUBJECT_ID, String.valueOf(EVoterShareMemory.getCurrentSubjectID()));
		
		client.post(RequestConfig.getURL(URIRequest.CREATE_SESSION), params,
				new AsyncHttpResponseHandler() {
					// Request successfully - client receive a response
					@Override
					public void onSuccess(String response) {
						Log.i("Response", response);
						if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
							EVoterMobileUtils.showeVoterToast(NewSessionActivity.this, "A new session is created!");
						} else {
							EVoterMobileUtils.showeVoterToast(NewSessionActivity.this, "Cannot create new session!");
						}
					}
					
					//Login fail
					@Override
					public void onFailure(Throwable error,
							String content) {
						EVoterMobileUtils.showeVoterToast(
								NewSessionActivity.this,
								"Cannot request to server!");
						Log.e("Create new session", "onFailure error : "
								+ error.toString() + "content : "
								+ content);
					}
				});
	}
	
}
