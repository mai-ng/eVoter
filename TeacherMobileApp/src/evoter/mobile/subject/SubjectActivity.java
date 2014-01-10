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

						EVoterSessionManager
								.setCurrentSubjectID(((Subject) parent
										.getItemAtPosition(position)).getId());
						EVoterSessionManager
								.setCurrentSubject(((Subject) parent
										.getItemAtPosition(position))
										.getTitle());
						startActivity(new Intent(
								"android.intent.action.SESSION"));
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
		params.put(UserDAO.USER_KEY, EVoterSessionManager.getUserKey());
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

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater mnInfalter = getMenuInflater();
		mnInfalter.inflate(R.menu.student_subject_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnListSubjectReload:
			loadListSubjects();
			return true;
		case R.id.mnQuestion:
			startActivity(new Intent("android.intent.action.QUESTIONMANAGEMENT"));
			return true;
		case R.id.mnLogout:
			EVoterSessionManager eVoterSessionManager = new EVoterSessionManager(
					this);
			if (eVoterSessionManager.isLoggedIn()) {
				eVoterSessionManager.logoutUser();
			}
			eVoterSessionManager.checkLogin();
			return true;
		}
		return false;
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

	// private class LoadListSubject extends AsyncTask<Void, Void, Void> {
	// ProgressDialog dialog;
	//
	// /**
	// * Runs on the UI thread before {@link #doInBackground}.
	// *
	// * @see #onPostExecute
	// * @see #doInBackground
	// */
	// @Override
	// protected void onPreExecute() {
	// dialog = new ProgressDialog(context);
	// dialog.setTitle("Loading list subjects");
	// dialog.show();
	// super.onPreExecute();
	// }
	//
	// /**
	// * <p>Runs on the UI thread after {@link #doInBackground}. The
	// * specified result is the value returned by {@link #doInBackground}.</p>
	// * <p/>
	// * <p>This method won't be invoked if the task was cancelled.</p>
	// *
	// * @param aVoid The result of the operation computed by {@link
	// #doInBackground}.
	// * @see #onPreExecute
	// * @see #doInBackground
	// * @see #onCancelled(Object)
	// */
	// @Override
	// protected void onPostExecute(Void aVoid) {
	// dialog.dismiss();
	// super.onPostExecute(aVoid);
	// }
	//
	// /**
	// * Override this method to perform a computation on a background thread.
	// The
	// * specified parameters are the parameters passed to {@link #execute}
	// * by the caller of this task.
	// * <p/>
	// * This method can call {@link #publishProgress} to publish updates
	// * on the UI thread.
	// *
	// * @param params The parameters of the task.
	// * @return A result, defined by the subclass of this task.
	// * @see #onPreExecute()
	// * @see #onPostExecute
	// * @see #publishProgress
	// */
	// @Override
	// protected Void doInBackground(Void... params) {
	// String content = InternetChecking.getData(subjectsURL);
	// listSubjects.clear();
	// try {
	// JSONObject jsonObject = new JSONObject(content);
	// JSONObject jsonObject1 = jsonObject.getJSONObject("data");
	// JSONArray array = jsonObject1.getJSONArray("items");
	// for (int i = 0; i < array.length(); i++) {
	// JSONObject items = array.getJSONObject(i);
	// Subject subject = new Subject(items.getString("id"),
	// items.getString("title"), items.getString("updated"));
	// subject.setNbSessions(Integer.parseInt(items.getString("size")));
	// listSubjects.add(subject);
	//
	// }
	//
	// subjectBaseAdapter.notifyDataSetChanged();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	//
	// }

}