package evoter.mobile.activities;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import evoter.mobile.adapters.SessionAdapter;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.model.ItemData;
import evoter.share.model.Session;
import evoter.share.utils.URIRequest;

/**
 * Update by @author luongnv89 on 04-Feb-2014 <br>
 * <li>Add edit session activity Update by @author luongnv89 on 24-Jan-2014 <br>
 * <li>Edited Session constructor method - add creatorName Created by luongnv89
 * on 06/12/13.
 */
public class SessionActivity extends ItemDataActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	/* (non-Javadoc)
	 * @see evoter.mobile.activities.ItemDataActivity#initComponent()
	 */
	@Override
	public void initComponent() {
		super.initComponent();
		adapter = new SessionAdapter(SessionActivity.this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Session selectedSession = (Session) parent
						.getItemAtPosition(position);
				EVoterShareMemory.setCurrentSession(selectedSession);
				ActivityManager.startQuestionActivity(SessionActivity.this);
			}
		});
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Session selectedSession = (Session) parent
						.getItemAtPosition(position);
				EVoterShareMemory.setCurrentSession(selectedSession);
				longClickSessionAction(selectedSession.getId());
				return true;
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
		mainMenu.getBtUserOfSubject().setVisibility(View.VISIBLE);
		
	}




	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		// TODO Auto-generated method stub
		super.setupTitleBar();
		tvTitleBarContent.setText(EVoterShareMemory.getCurrentSubject().getTitle());
	}


	/**
	 * @param response
	 */
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.DELETE_SESSION_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.SUCCESS_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(SessionActivity.this,
						"Deleted session: " + EVoterShareMemory.getCurrentSession().getTitle());
				adapter.deleteItem(EVoterShareMemory.getCurrentSession().getId());
				adapter.notifyDataSetChanged();
			}
			else {
				EVoterMobileUtils.showeVoterToast(SessionActivity.this,
						"Cannot delete session: " + response);
			}
		} else if (callBackMessage.equals(CallBackMessage.GET_ALL_SESSION_EVOTER_REQUEST)) {
				
				ArrayList<ItemData> listSession = EVoterMobileUtils.parserToSessionArray(response);
				if (listSession.isEmpty()) {
					EVoterMobileUtils.showeVoterToast(SessionActivity.this,
							"There isn't any session!");
				}else{
					EVoterShareMemory.resetListAcceptedSessions();
					adapter.updateList(listSession);
				}
			
		} else {
			super.updateRequestCallBack(response, callBackMessage);
		}
	}
	
	/**
	 * @param selectedSession
	 */
	private void longClickSessionAction(final long sessionID) {
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("Session: " + EVoterShareMemory.getCurrentSessionName())
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton(R.string.edit_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						ActivityManager.startEditSessionActivity(SessionActivity.this);
					}
				})
				.setNegativeButton(R.string.delete_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						EVoterRequestManager.deleteSession(sessionID, SessionActivity.this);
					}
				}).show();
	}

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		EVoterRequestManager.getAllSession(SessionActivity.this, EVoterShareMemory.getCurrentSubject().getId());
	}
	
}