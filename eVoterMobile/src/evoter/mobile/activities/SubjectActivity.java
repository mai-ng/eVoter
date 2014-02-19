package evoter.mobile.activities;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.abstracts.ItemDataActivity;
import evoter.mobile.adapters.SubjectAdapter;
import evoter.mobile.main.ActivityManager;
import evoter.mobile.main.EVoterRequestManager;
import evoter.mobile.main.EVoterShareMemory;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.model.ItemData;
import evoter.share.model.Subject;

/**
 * <br>
 * Updated by @author luongnv89 on 30-Jan-2014:<br>
 * <li>Updated back button press -> call {@link EVoterActivity#exit()} method <br>
 * Updated by @author luongnv89 on 26-Jan-2014:<br>
 * <br><li>Updated back button press -> show dialog to confirm exiting
 * application. <br>
 * Updated by @author luongnv89 on 19-Jun-2014:<br>
 * <br><li>Using {@link DialogInfor} for long click event instead of
 * {@link Dialog} <br>
 * Updated by @author btdiem on 08-Jan-2014 : </br></li>update
 * loadListSubjects() method: </li>remove
 * parameters </li>add userKey to parameter map when sending request to server <br>
 * Created by @author nvluong on 05-Dec-2013</br>
 */
public class SubjectActivity extends ItemDataActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.ItemDataActivity#initComponent()
	 */
	@Override
	public void initComponent() {
		// TODO Auto-generated method stub
		super.initComponent();
		adapter = new SubjectAdapter(SubjectActivity.this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Subject currentSubject = ((Subject) parent.getItemAtPosition(position));
				EVoterShareMemory.setCurrentSubject(currentSubject);
				ActivityManager.startSessionActivity(SubjectActivity.this);
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		// TODO Auto-generated method stub
		super.setupTitleBar();
		tvTitleBarContent.setText(EVoterShareMemory.getCurrentUserName());
	}
	
	/**
	 * Called when the activity has detected the user's press of the back key.
	 * The default implementation simply finishes the current activity, but you
	 * can override this to do whatever you want.
	 */
	@Override
	public void onBackPressed() {
		exit();
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.GET_ALL_SUBJECT_EVOTER_REQUEST)) {
			ArrayList<ItemData> listSubjects = EVoterMobileUtils.parserSubjectArray(response);
			
			if (listSubjects.isEmpty()) {
				EVoterMobileUtils.showeVoterToast(SubjectActivity.this, "There isn't any subject!");
			} else {
				adapter.updateList(listSubjects);
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
		EVoterRequestManager.getListSubject(this);
	}
	
}