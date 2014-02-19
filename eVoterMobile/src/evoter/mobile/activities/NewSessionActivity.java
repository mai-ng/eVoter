/**
 * 
 */
package evoter.mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.utils.URIRequest;

/**
 * <br>
 * Update by @author luongnv89 on 12-Feb-2014:<br>
 * <li>Completed send create session request to server <br>
 * Created by @author luongnv89 on 30-Jun-2014 <br>
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
		
		initComponents();
	}


	/**
	 * Init components of {@link NewSessionActivity}
	 */
	private void initComponents() {
		etTitle = (EditText) findViewById(R.id.etNewSessionName);
		btSave = (Button) findViewById(R.id.btNewSessionSave);
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (etTitle.getText().toString().equals("")) {
					EVoterMobileUtils.showeVoterToast(NewSessionActivity.this, "Session title cannot be empty!");
				}
				else {
					EVoterRequestManager.createSessionRequest(etTitle.getText().toString(), EVoterShareMemory.getCurrentSubject().getId(), NewSessionActivity.this);
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
	
	
	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupContentMainMenu()
	 */
	@Override
	protected void setupContentMainMenu() {
		// TODO Auto-generated method stub
		super.setupContentMainMenu();
	}


	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		// TODO Auto-generated method stub
		super.setupTitleBar();
		tvTitleBarContent.setText(EVoterShareMemory.getCurrentSubjectName());
		
	}


	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupMainMenu()
	 */
	@Override
	protected void setupMainMenu() {
		// TODO Auto-generated method stub
		super.setupMainMenu();
	}


	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.CREATE_SESSION_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(NewSessionActivity.this, "A new session is created!");
				finish();
			} else {
				EVoterMobileUtils.showeVoterToast(NewSessionActivity.this, "Cannot create new session: " + response);
			}
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
	}

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		EVoterShareMemory.getPreviousContext().refeshContent();
	}
	
	
}
