package evoter.mobile.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.subject.SubjectActivity;
import evoter.server.dao.UserDAO;

/**
 * Created by luongnv89 on 05/12/13 </br>
 * Updated by @author btdiem on 08-Jan-2014:</br>
 * 		</li> parse response and store user key sent by server to verify next time
 * 
 */
public class Login extends Activity {

	EditText etUsrName;
	EditText etPassword;
	TextView tvStatusLogin;

	Button btLogin;
	Button btFgPassword;
	Button btRegister;

	CheckBox cbRemember;

	EVoterSessionManager eVoterSessionManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		eVoterSessionManager = new EVoterSessionManager(this);

		etUsrName = (EditText) findViewById(R.id.usrname);
		etPassword = (EditText) findViewById(R.id.password);
		tvStatusLogin = (TextView) findViewById(R.id.tvStatusLogin);

		btLogin = (Button) findViewById(R.id.btLogin);
		btFgPassword = (Button) findViewById(R.id.btfgPassword);
		btRegister = (Button) findViewById(R.id.btRegister);

		cbRemember = (CheckBox) findViewById(R.id.cb_remember);

		btLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// GetData getData = new GetData();
				AsyncHttpClient client = new AsyncHttpClient(1000);
				final String i_Usrname = etUsrName.getText().toString();
				final String i_Password = etPassword.getText().toString();
				RequestParams params = new RequestParams();
				params.add(UserDAO.USER_NAME, i_Usrname);
				params.add(UserDAO.PASSWORD, i_Password);

				// String sttMsg = getData.getContent("http://www.vogella.com");
				//Pre-checking on client	
				if (i_Usrname.equals("") || i_Password.equals("")) {
					tvStatusLogin.setText("User name or password is empty!");
					btFgPassword.setVisibility(View.VISIBLE);
					btRegister.setVisibility(View.VISIBLE);
				} else

				{
					//Send request to login
					client.post(Configuration.get_urlLogin(), params,
							new AsyncHttpResponseHandler() {
								//Request successfully
								@Override
								public void onSuccess(String response) {
									Log.i("LoginTest", "response : " + response);
									String userKey = null;
									
									try {
										
										JSONObject object = new JSONObject(response);
										userKey = object.getString(UserDAO.USER_KEY);
										
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									if (userKey != null || userKey != "null"){
										
										if (cbRemember.isChecked()) {
											eVoterSessionManager
													.createLoginSession(
															i_Usrname,
															i_Password);
										}
										Intent subjectIntent = new Intent(
												Login.this,
												SubjectActivity.class);
										subjectIntent
												.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										subjectIntent
												.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(subjectIntent);
										Log.i("LoginTest", "SUCCESS");
										EVoterSessionManager.setUserKey(userKey);
										
									}
									
//									if (response.equalsIgnoreCase("TRUE")) {
//										//Correct user and password
//										if (cbRemember.isChecked()) {
//											eVoterSessionManager
//													.createLoginSession(
//															i_Usrname,
//															i_Password);
//										}
//										Intent subjectIntent = new Intent(
//												Login.this,
//												SubjectActivity.class);
//										subjectIntent
//												.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//										subjectIntent
//												.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//										startActivity(subjectIntent);
//										Log.i("LoginTest", "SUCCESS");
//									} else {
//										Log.i("LoginTest", "response : " + response);
////										//Incorrect user and password	
////										tvStatusLogin
////												.setText("Username and password incorrect!");
////										// tvStatusLogin.setText(sttMsg);
////										btFgPassword
////												.setVisibility(View.VISIBLE);
////										btRegister.setVisibility(View.VISIBLE);
////										Log.i("LoginTest",
////												"Username and password incorrect!");
//										//Correct user and password
//										if (cbRemember.isChecked()) {
//											eVoterSessionManager
//													.createLoginSession(
//															i_Usrname,
//															i_Password);
//										}
//										Intent subjectIntent = new Intent(
//												Login.this,
//												SubjectActivity.class);
//										subjectIntent
//												.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//										subjectIntent
//												.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//										startActivity(subjectIntent);
//										Log.i("LoginTest", "SUCCESS");
//									}
									// tvStatusLogin.setText(response);

								}

								@Override
								public void onFailure(Throwable error,
										String content) {
									tvStatusLogin.setText("Failure!");
									// tvStatusLogin.setText(sttMsg);
									btFgPassword.setVisibility(View.VISIBLE);
									btRegister.setVisibility(View.VISIBLE);
									Log.i("LoginTest", "FAILURE");
									Log.e("LoginTest", "onFailure error : "
											+ error.toString() + "content : "
											+ content);
								}
							});
				}

			}
		});

		btFgPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(
						"android.intent.action.RECOVERPASSWORD"));
			}
		});

		btRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent("android.intent.action.REGISTER"));
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