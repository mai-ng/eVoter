package evoter.mobile.session;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.Configuration;
import evoter.mobile.main.EVoterActivity;
import evoter.mobile.main.EVoterSessionManager;
import evoter.mobile.main.R;
import evoter.mobile.main.Splash;
import evoter.mobile.utils.Utils;
import evoter.server.dao.SessionDAO;
import evoter.server.dao.UserDAO;
import evoter.server.model.Session;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SessionActivity extends EVoterActivity {

	ArrayList<Session> listSessions = new ArrayList<Session>();
	ListView listView;
	SessionBaseAdapter sessionBaseAdapter;
	Context context;
	EditText etSearch;

	// String subjectID = "1";

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_list_sessions);
		// Set title bar content is the subject of session
		this.tvTitleBarContent.setText(EVoterSessionManager
				.getCurrentSubjectName());
		this.ivTitleBarRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadListSession();

			}
		});

		context = this;
		listView = (ListView) findViewById(R.id.lvQuestionSesionRunning);
		loadListSession();
		sessionBaseAdapter = new SessionBaseAdapter(listSessions,
				SessionActivity.this);
		listView.setAdapter(sessionBaseAdapter);

		etSearch = (EditText) findViewById(R.id.etSessionSearch);
		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				SessionActivity.this.sessionBaseAdapter.getFilter().filter(s);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Session selectedSession = (Session) parent
						.getItemAtPosition(position);
				EVoterSessionManager.setCurrentSessionID(selectedSession
						.getId());
				EVoterSessionManager.setCurrentSessionStatus(selectedSession
						.isActive());
				EVoterSessionManager.setCurrentSessionName(selectedSession
						.getName());
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
								+ selectedSession.getName(), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});

	}

	private void loadListSession() {
		AsyncHttpClient client = new AsyncHttpClient(1000);
		RequestParams params = new RequestParams();
		params.add(SessionDAO.SUBJECT_ID,
				String.valueOf(EVoterSessionManager.getCurrentSessionID()));
		params.put(UserDAO.USER_KEY, EVoterSessionManager.getUserKey());
		client.post(Configuration.get_urlGetAllSession(), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						try {
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
								listSessions.add(session);
							}
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