package evoter.mobile.activities;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.adapters.SubjectAdapter;
import evoter.mobile.objects.DialogInfor;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.SubjectDAO;
import evoter.share.dao.UserDAO;
import evoter.share.model.ItemData;
import evoter.share.model.Subject;
import evoter.share.utils.URIRequest;

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
		// Set content for title bar is the username
		this.tvTitleBarContent.setText(EVoterShareMemory
				.getCurrentUserName());
		
		adapter = new SubjectAdapter(SubjectActivity.this);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Subject currentSubject = ((Subject) parent
						.getItemAtPosition(position));
				EVoterShareMemory.setCurrentSubject(currentSubject);
				Intent subject = new Intent(SubjectActivity.this,
						SessionActivity.class);
				startActivity(subject);
			}
		});
		
	}
	
	protected void loadListItemData() {
		RequestParams params = new RequestParams();
		params.put(UserDAO.USER_KEY, EVoterShareMemory.getUSER_KEY());
		client.post(RequestConfig.getURL(URIRequest.GET_ALL_SUBJECT), params,
				new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(String response) {
						
						loadListItemDataResponseProcess(response);
					}
					
					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get all subjects", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});
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
	
	/**
	 * @param response
	 */
	private void loadListItemDataResponseProcess(String response) {
		try {
			ArrayList<ItemData> newList = new ArrayList<ItemData>();
			JSONArray array = EVoterMobileUtils.getJSONArray(response);
			
			for (int i = 0; i < array.length(); i++) {
				String sItem = array.get(i).toString();
				JSONObject item = new JSONObject(sItem);
//				Log.i("JSON TEST: ", item.toString());
				Subject subject = null;
				try {
					subject = new Subject(
							Long.parseLong(item
									.getString(SubjectDAO.ID)),
							item.getString(SubjectDAO.TITLE),
							EVoterMobileUtils.convertToDate(item
									.getString(SubjectDAO.CREATION_DATE)));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newList.add(subject);
			}
			if (newList.isEmpty()) {
				EVoterMobileUtils.showeVoterToast(SubjectActivity.this,
						"There isn't any subject!");
			}
			adapter.updateList(newList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}