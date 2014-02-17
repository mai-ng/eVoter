package evoter.mobile.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.adapters.SessionAdapter;
import evoter.mobile.objects.DialogInfor;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.SessionDAO;
import evoter.share.dao.SessionUserDAO;
import evoter.share.dao.UserDAO;
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
		// Set title bar content is the subject of session
		this.tvTitleBarContent.setText(EVoterShareMemory
				.getCurrentSubjectName());
		
		mainMenu.setSessionActivityMenu();
		adapter = new SessionAdapter(SessionActivity.this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Session selectedSession = (Session) parent
						.getItemAtPosition(position);
				EVoterShareMemory.setCurrentSession(selectedSession);
				EVoterShareMemory.setPreviousContext(SessionActivity.this);
				Intent questionActivity = new Intent(SessionActivity.this,QuestionActivity.class);
				startActivity(questionActivity);
			}
		});
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Session selectedSession = (Session) parent
						.getItemAtPosition(position);
				EVoterShareMemory.setCurrentSession(selectedSession);
				longClickSessionAction();
				return true;
			}
		});
		
		mainMenu.getBtNewSession().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newSessionIntent = new Intent(SessionActivity.this, NewSessionActivity.class);
				EVoterShareMemory.setPreviousContext(SessionActivity.this);
				startActivity(newSessionIntent);
				mainMenu.dismiss();
			}
		});
		
	}
	
	public void refreshActivity() {
		RequestParams params = new RequestParams();
		params.add(SessionDAO.SUBJECT_ID,
				String.valueOf(EVoterShareMemory.getCurrentSubject().getId()));
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		Log.i("SUBJECT_ID",
				String.valueOf(EVoterShareMemory.getCurrentSubjectID()));
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_SESSION), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						parserListSessionFromResponse(response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
		
	}
	
	/**
	 * @param selectedSession
	 */
	private void longClickSessionAction() {
		final DialogInfor dialog = new DialogInfor(
				SessionActivity.this, "Session");
		dialog.setMessageContent(EVoterShareMemory.getCurrentSession().getTitle());
		dialog.getBtOK().setText("Edit");
		dialog.getBtOK().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("SESSION LONG ITEM CLICK", "Edit session" + EVoterShareMemory.getCurrentSession().getTitle());
				dialog.dismiss();
				Intent editSession = new Intent(SessionActivity.this, EditSessionActivity.class);
				EVoterShareMemory.setPreviousContext(SessionActivity.this);
				startActivity(editSession);
			}
		});
		
		dialog.getBtKO().setText("Delete");
		dialog.getBtKO().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteSessionRequest();
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	/**
	 * @param response
	 */
	private void parserListSessionFromResponse(String response) {
		try {
			EVoterShareMemory.resetListAcceptedSessions();
			ArrayList<ItemData> listSession = new ArrayList<ItemData>();
			JSONArray array = EVoterMobileUtils.getJSONArray(response);
			for (int i = 0; i < array.length(); i++) {
				Session session = EVoterMobileUtils.parserSession(array.getJSONObject(i));
				if (session != null)
					listSession.add(session);
			}
			if (listSession.isEmpty()) {
				EVoterMobileUtils.showeVoterToast(SessionActivity.this,
						"There isn't any session!");
			}
			adapter.updateList(listSession);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * @param selectedSession
	 */
	private void deleteSessionRequest() {
		RequestParams params = new RequestParams();
		params.add(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		params.add(SessionUserDAO.SESSION_ID, String.valueOf(EVoterShareMemory.getCurrentSession().getId()));
		client.post(RequestConfig.getURL(URIRequest.DELETE_SESSION), params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				if (response.contains("SUCCESS")) {
					EVoterMobileUtils.showeVoterToast(SessionActivity.this,
							"Deleted session: " + EVoterShareMemory.getCurrentSession().getTitle());
					adapter.deleteItem(EVoterShareMemory.getCurrentSession().getId());
					adapter.notifyDataSetChanged();
					//					loadListItemData();
				}
				else {
					EVoterMobileUtils.showeVoterToast(SessionActivity.this,
							"Cannot delete session: " + response);
				}
			}
			
			@Override
			public void onFailure(Throwable error, String content)
			{
				EVoterMobileUtils.showeVoterToast(SessionActivity.this,
						"FAILURE: " + error.toString());
				Log.e("FAILURE", "onFailure error : " + error.toString() + "content : " + content);
			}
		});
	}
	
}