package evoter.mobile.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.Configuration;
import evoter.mobile.objects.OfflineEVoterManager;
import evoter.mobile.objects.RuntimeEVoterManager;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.*;
import evoter.share.utils.*;

/**Update by @author luongnv89 on Thu 30-Jan-2014:
 * <br>
 * <li> remove constructor for {@link OfflineEVoterManager} to {@link EVoterActivity}
 * Updated by @author luongnv89 on 19-Jan-2014:<br>
 * <li> Deleted start new LoginActivity in case "username and password is incorrect!"
 * <br>Just notify for user and user can try again
 * Update by @author luongnv89 on 18-Jan-2014<br>
 * <li>add comments for class, variable, method, <li>Edited onBackPressed() by
 * using {@link EVoterActivity#exit()} method; <li>Add relogin in case the input username and password is not correct. <br>
 * Created by luongnv89 on 05/12/13 </br> Updated by @author btdiem on
 * 08-Jan-2014:</br></li> parse response and store user key sent by server to
 * verify next time
 */
public class LoginActivity extends EVoterActivity {
	
	EditText etUsrName;
	EditText etPassword;
	
	Button btLogin;
	
	TextView tvRegister;
	TextView tvResetPassword;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		this.tvTitleBarContent.setText("Login");
		
		this.ivTitleBarIcon.setEnabled(false);
		
		
		etUsrName = (EditText) findViewById(R.id.usrname);
		if (RuntimeEVoterManager.getCurrentUserName() != null) {
			etUsrName.setText(RuntimeEVoterManager.getCurrentUserName());
		}
		etPassword = (EditText) findViewById(R.id.password);
		
		btLogin = (Button) findViewById(R.id.btLogin);
		
		//Login button click
		btLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// GetData getData = new GetData();
				AsyncHttpClient client = new AsyncHttpClient(1000);
				final String i_Usrname = etUsrName.getText().toString();
				RuntimeEVoterManager
						.setCurrentUserName(i_Usrname);
				final String i_Password = etPassword.getText().toString();
				
				//Pre-check validation of input username and password
				if (i_Usrname.equals("")) {
					EVoterMobileUtils.showeVoterToast(LoginActivity.this,
							"Please input your username");
				} else if (i_Password.equals("")) {
					EVoterMobileUtils.showeVoterToast(LoginActivity.this,
							"Please input your password");
				} else if (!UserValidation.isValidUserName(i_Usrname)) {
					EVoterMobileUtils.showeVoterToast(LoginActivity.this,
							"Input username is not valid");
				} else if (!UserValidation.isValidPassword(i_Password)) {
					EVoterMobileUtils.showeVoterToast(LoginActivity.this,
							"Input password is not valid");
				} else
				
				{
					// Send login request to server
					RequestParams params = new RequestParams();
					params.add(UserDAO.USER_NAME, i_Usrname);
					params.add(UserDAO.PASSWORD, i_Password);
					client.post(Configuration.get_urlLogin(), params,
							new AsyncHttpResponseHandler() {
								// Request successfully - client receive a response
								@Override
								public void onSuccess(String response) {
									Log.i("Response", response);
									String userKey = null;
									try {
										
										JSONObject object = new JSONObject(
												response);
										userKey = object
												.getString(UserDAO.USER_KEY);
										
									} catch (JSONException e) {
										e.printStackTrace();
										EVoterMobileUtils.showeVoterToast(LoginActivity.this, "Error! Cannot get user information");
									}
									
									//Got the userkey
									if (userKey != null && userKey != "null") {
										Log.i("USER_KEY", userKey);
										offlineEVoterManager
												.rememberCurrentUser(i_Usrname,
														userKey);
										RuntimeEVoterManager
												.setUSER_KEY(userKey);
										EVoterMobileUtils.showeVoterToast(
												LoginActivity.this,
												"Welcome "
														+ RuntimeEVoterManager
																.getCurrentUserName()
														+ " to eVoter!");
										
										Intent subjectIntent = new Intent(
												LoginActivity.this,
												SubjectActivity.class);
										subjectIntent
												.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										subjectIntent
												.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(subjectIntent);
										
									}
									else {
										EVoterMobileUtils.showeVoterToast(LoginActivity.this, "Error! Username and password is not correct. Please try again!");
									}
								}
								
								//Login fail
								@Override
								public void onFailure(Throwable error,
										String content) {
									EVoterMobileUtils.showeVoterToast(
											LoginActivity.this,
											"Cannot request to server!");
									Log.e("LoginTest", "onFailure error : "
											+ error.toString() + "content : "
											+ content);
								}
							});
				}
				
			}
		});
		

		tvRegister = (TextView) findViewById(R.id.tvSignUp);
		tvRegister.setPaintFlags(tvRegister.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		//Register textview click
		tvRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent registerIntent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(registerIntent);
			}
		});
		
		tvResetPassword = (TextView) findViewById(R.id.tvForgotPassword);
		tvResetPassword.setPaintFlags(tvResetPassword.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		
		//Reset password text view click
		tvResetPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent registerIntent = new Intent(LoginActivity.this,
						ResetPasswordActivity.class);
				startActivity(registerIntent);
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
}