package evoter.mobile.session;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.item.ItemDataActivity;
import evoter.mobile.main.Configuration;
import evoter.mobile.main.RuntimeEVoterManager;
import evoter.mobile.model.ItemData;
import evoter.mobile.model.Session;
import evoter.mobile.utils.Utils;
import evoter.server.dao.SessionDAO;
import evoter.server.dao.UserDAO;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SessionActivity extends ItemDataActivity {

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// Set title bar content is the subject of session
		this.tvTitleBarContent.setText(RuntimeEVoterManager
				.getCurrentSubjectName());

		adapter = new SessionAdapter(listInitial, SessionActivity.this);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Session selectedSession = (Session) parent
						.getItemAtPosition(position);
				RuntimeEVoterManager.setCurrentSessionID(selectedSession
						.getId());
				RuntimeEVoterManager.setCurrentSessionStatus(selectedSession
						.isActive());
				RuntimeEVoterManager.setCurrentSessionName(selectedSession
						.getTitle());
				startActivity(new Intent("android.intent.action.SESSIONVIEW"));
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Session selectedSession = (Session) parent
						.getItemAtPosition(position);
				Toast.makeText(
						SessionActivity.this,
						"Process item long clicked for item: "
								+ selectedSession.getTitle(),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});

	}

	protected void loadListItemData() {
		AsyncHttpClient client = new AsyncHttpClient(1000);
		RequestParams params = new RequestParams();
		params.add(SessionDAO.SUBJECT_ID,
				String.valueOf(RuntimeEVoterManager.getCurrentSubjectID()));
		params.put(UserDAO.USER_KEY, RuntimeEVoterManager.getUSER_KEY());
		Log.i("SUBJECT_ID",
				String.valueOf(RuntimeEVoterManager.getCurrentSubjectID()));
		client.post(Configuration.get_urlGetAllSession(), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						try {
							ArrayList<ItemData> listSession = new ArrayList<ItemData>();
							JSONArray array = Utils.getJSONArray(response);
							for (int i = 0; i < array.length(); i++) {
								String sString = array.get(i).toString();
								JSONObject s = new JSONObject(sString);
								Session session = new Session(Long.parseLong(s
										.getString("ID")), Long.parseLong(s
										.getString("SUBJECT_ID")), s
										.getString("NAME"), Utils
										.convertToDate(s
												.getString("CREATION_DATE")),
										Boolean.parseBoolean(s
												.getString("is_active")));
								listSession.add(session);
							}
							adapter.updateList(listSession);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.i("Get All Session Test", "response : " + response);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Session Test", "onFailure error : "
								+ error.toString() + "content : " + content);
					}
				});

	}
}