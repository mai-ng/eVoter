/**
 * 
 */
package evoter.mobile.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.objects.RuntimeEVoterManager;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;

/**<br>Update by @author luongnv89 on 12-Feb-2014:<br>
 * <li> Completed send edit session request to server
 * <br>Created by @author luongnv89
 */
public class EditSessionActivity extends NewSessionActivity {
	
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.NewSessionActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.tvTitleBarContent.setText(RuntimeEVoterManager
				.getCurrentSessionName());
		mainMenu.setSessionActivityMenu();
		
		etTitle = (EditText) findViewById(R.id.etNewSessionName);
		etTitle.setText(RuntimeEVoterManager.getCurrentSessionName());
		btSave = (Button) findViewById(R.id.btNewSessionSave);
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (etTitle.getText().toString().equals("")) {
					EVoterMobileUtils.showeVoterToast(EditSessionActivity.this, "Session title cannot be empty!");
				}
				else {
					
					RequestParams params = new RequestParams();
					params.add(UserDAO.USER_KEY, RuntimeEVoterManager.getUSER_KEY());
					params.add(SessionDAO.NAME, etTitle.getText().toString());
					params.add(SessionDAO.ID, String.valueOf(RuntimeEVoterManager.getCurrentSessionID()));
					
					client.post(RequestConfig.getURL(URIRequest.UPDATE_SESSION), params,
							new AsyncHttpResponseHandler() {
								// Request successfully - client receive a response
								@Override
								public void onSuccess(String response) {
									Log.i("Response", response);
									if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
										EVoterMobileUtils.showeVoterToast(EditSessionActivity.this, "Session is updated!");
									} else {
										EVoterMobileUtils.showeVoterToast(EditSessionActivity.this, "Cannot update session!");
									}
								}
								
								//Login fail
								@Override
								public void onFailure(Throwable error,
										String content) {
									EVoterMobileUtils.showeVoterToast(
											EditSessionActivity.this,
											"Cannot request to server!");
									Log.e("Create new session", "onFailure error : "
											+ error.toString() + "content : "
											+ content);
								}
							});
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
	
}
