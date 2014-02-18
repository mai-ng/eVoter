/**
 * 
 */
package evoter.mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.utils.URIRequest;

/**
 * <br>
 * Update by @author luongnv89 on 12-Feb-2014:<br>
 * <li>Completed send edit session request to server <br>
 * Created by @author luongnv89
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
		this.tvTitleBarContent.setText(EVoterShareMemory
				.getCurrentSessionName());
		mainMenu.setSessionActivityMenu();
		
		etTitle = (EditText) findViewById(R.id.etNewSessionName);
		etTitle.setText(EVoterShareMemory.getCurrentSessionName());
		btSave = (Button) findViewById(R.id.btNewSessionSave);
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EVoterRequestManager.editSession(etTitle.getText().toString(), EVoterShareMemory.getCurrentSession().getId(), EditSessionActivity.this);
				finish();
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
	
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.NewSessionActivity#updateRequestCallBack(java
	 * .lang.String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.EDIT_SESSION_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(EditSessionActivity.this, "Session is updated!");
				EVoterShareMemory.getPreviousContext().refreshData();
				finish();
			} else {
				EVoterMobileUtils.showeVoterToast(EditSessionActivity.this, "Cannot update session!");
			}
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
	}
	
}
