package evoter.mobile.subject;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
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
import android.widget.Button;
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
import evoter.mobile.session.SessionActivity;
import evoter.mobile.utils.Utils;
import evoter.server.dao.SubjectDAO;
import evoter.server.dao.UserDAO;
import evoter.server.model.Subject;

/**
 * Created by @author nvluong on 05-Dec-2013</br> Updated by @author btdiem on
 * 08-Jan-2014 : </br> </li>update loadListSubjects() method: </li>remove
 * parameters </li>add userKey to parameter map when sending request to server
 */
public class SubjectActivity extends EVoterActivity {

	ListView listSubView;
	ArrayList<Subject> listSubjects;
	SubjectBaseAdapter subjectBaseAdapter;
	Context context;
	EditText etSearch;
	EVoterSessionManager eVoterSessionManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_list_subjects);
		eVoterSessionManager = new EVoterSessionManager(this);
		// Set content for title bar is the username
		this.tvTitleBarContent.setText(EVoterSessionManager
				.getCurrentUserName());

		// Set listener for refresh button
		this.ivTitleBarRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadListSubjects();
			}
		});

		this.ivTitleBarIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO: Show
				eVoterSessionManager.logoutUser();
			}
		});

		context = this;
		listSubjects = new ArrayList<Subject>();
		listSubView = (ListView) findViewById(R.id.lvSubjects);
		loadListSubjects();
		subjectBaseAdapter = new SubjectBaseAdapter(listSubjects,
				SubjectActivity.this);
		listSubView.setAdapter(subjectBaseAdapter);

		listSubView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Subject currentSubject = ((Subject) parent
								.getItemAtPosition(position));

						EVoterSessionManager.setCurrentSubjectID(currentSubject
								.getId());
						EVoterSessionManager
								.setCurrentSubjectName(currentSubject
										.getTitle());
						Intent sessionIntent = new Intent(SubjectActivity.this,
								SessionActivity.class);
						startActivity(sessionIntent);
					}
				});

		listSubView
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						final Dialog dialog = new Dialog(SubjectActivity.this);
						final Subject subject = (Subject) parent
								.getItemAtPosition(position);
						dialog.setContentView(R.layout.subject_dialog);
						dialog.setTitle("Subject Action");

						Button btSbjDelete = (Button) dialog
								.findViewById(R.id.btSubjectDeleting);

						btSbjDelete
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										Toast.makeText(
												SubjectActivity.this,
												"Deleted subject: "
														+ subject.getTitle(),
												Toast.LENGTH_LONG).show();
										subjectBaseAdapter.deleteItem(subject
												.getId());
										subjectBaseAdapter
												.notifyDataSetChanged();
										dialog.dismiss();
									}
								});

						Button btSbjDetail = (Button) dialog
								.findViewById(R.id.btSubjectDetail);
						btSbjDetail
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										Toast.makeText(
												SubjectActivity.this,
												"Not implemented yet!!!"
														+ subject.getTitle(),
												Toast.LENGTH_LONG).show();
										dialog.dismiss();
									}
								});
						dialog.show();

						return true;
					}
				});

		etSearch = (EditText) findViewById(R.id.etQuestionSearchSessionRunning);
		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				SubjectActivity.this.subjectBaseAdapter.getFilter().filter(s);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void loadListSubjects() {

		AsyncHttpClient client = new AsyncHttpClient(1000);
		RequestParams params = new RequestParams();
		// params.add(userID2, key2);
		params.put(UserDAO.USER_KEY, EVoterSessionManager.getUSER_KEY());
		client.post(Configuration.get_urlGetAllSubject(), params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						try {
							JSONArray array = Utils.getJSONArray(response);
							for (int i = 0; i < array.length(); i++) {
								String sItem = array.get(i).toString();
								JSONObject item = new JSONObject(sItem);
								Log.i("JSON TEST: ", item.toString());
								Subject subject = null;
								try {
									subject = new Subject(
											Long.parseLong(item
													.getString(SubjectDAO.ID)),
											item.getString(SubjectDAO.TITLE),
											Utils.convertToDate(item
													.getString(SubjectDAO.CREATION_DATE)));
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								listSubjects.add(subject);

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

						Log.i("Get All Subject Test", "response : " + response
								+ "\nListSubject: " + listSubjects.size());
					}

					@Override
					public void onFailure(Throwable error, String content) {
						Log.e("Get All Subject Test", "onFailure error : "
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
		Intent exitIntent = new Intent(this, Splash.class);
		exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		exitIntent.putExtra("Exit application", true);
		startActivity(exitIntent);
		finish();
	}

}