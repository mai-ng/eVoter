package evoter.mobile.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.main.ActivityManager;
import evoter.mobile.main.EVoterRequestManager;
import evoter.mobile.main.EVoterShareMemory;
import evoter.mobile.main.OfflineEVoterManager;
import evoter.mobile.main.R;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.UserDAO;
import evoter.share.utils.UserValidation;

/**
 * <br>
 * Update by @author luongnv89 on 12-Feb-2014: <br>
 * <li>using {@link EVoterActivity#client} instead of local variable <br>
 * Update by @author luongnv89 on Thu 30-Jan-2014: <br> <li>remove constructor
 * for {@link OfflineEVoterManager} to {@link EVoterActivity} <br>
 * Updated by @author luongnv89 on 19-Jan-2014:<br> <li>Deleted start new
 * LoginActivity in case "username and password is incorrect!" <br>
 * Just notify for user and user can try again <br>
 * Update by @author luongnv89 on 18-Jan-2014<br> <li>add comments for class,
 * variable, method, <li>Edited onBackPressed() by using
 * {@link EVoterActivity#exit()} method; <li>Add relogin in case the input
 * username and password is not correct. <br>
 * <br>
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
		initComponent();
	}
	
	/**
	 * Init components of {@link LoginActivity}
	 */
	private void initComponent() {
		etUsrName = (EditText) findViewById(R.id.usrname);
		etPassword = (EditText) findViewById(R.id.password);
		btLogin = (Button) findViewById(R.id.btLogin);
		tvRegister = (TextView) findViewById(R.id.tvSignUp);
		tvResetPassword = (TextView) findViewById(R.id.tvForgotPassword);
		
		if (EVoterShareMemory.getCurrentUserName() != null) {
			etUsrName.setText(EVoterShareMemory.getCurrentUserName());
		}
		
		btLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String i_Usrname = etUsrName.getText().toString();
				String i_Password = etPassword.getText().toString();
				if(validInput()){
					EVoterRequestManager.doLogin(i_Usrname, i_Password, LoginActivity.this);
				}
			}
		});
		
		tvRegister.setPaintFlags(tvRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		tvRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityManager.startRegisterActivity(LoginActivity.this);
			}
		});
		
		tvResetPassword.setPaintFlags(tvResetPassword.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
		
		tvResetPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityManager.startResetPasswordActivity(LoginActivity.this);
			}
		});
	}

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		super.setupTitleBar();
		ivTitleBarIcon.setEnabled(false);
	}

	/**
	 * Check valid input before send login request.
	 * 
	 * @return false in cases:
	 * <br> {@link LoginActivity#etUsrName} is empty
	 * <br> {@link LoginActivity#etUsrName} is invalid user name
	 * <br> {@link LoginActivity#etPassword} is empty
	 * <br> {@link LoginActivity#etPassword} is invalid password
	 * 
	 * <br>
	 * <br>otherwise return true;
	 */
	protected boolean validInput() {
		String i_Usrname = etUsrName.getText().toString();
		String i_Password = etPassword.getText().toString();
		if (i_Usrname.equals("")) {
			EVoterMobileUtils.showeVoterToast(LoginActivity.this,
					"Please input your username");
			return false;
		} else if (i_Password.equals("")) {
			EVoterMobileUtils.showeVoterToast(LoginActivity.this,
					"Please input your password");
			return false;
		} else if (!UserValidation.isValidUserName(i_Usrname)) {
			EVoterMobileUtils.showeVoterToast(LoginActivity.this,
					"Input username is not valid");
			return false;
		} else if (!UserValidation.isValidPassword(i_Password)) {
			EVoterMobileUtils.showeVoterToast(LoginActivity.this,
					"Input password is not valid");
			return false;
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.LOGIN_EVOTER_REQUEST)) {
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
				EVoterShareMemory.getOfflineEVoterManager()
						.rememberCurrentUser(etUsrName.getText().toString(),
								etPassword.getText().toString());
				EVoterShareMemory
						.setUSER_KEY(userKey);
				EVoterShareMemory
						.setCurrentUserName(etUsrName.getText().toString());
				EVoterMobileUtils.showeVoterToast(
						LoginActivity.this,
						"Welcome "
								+ EVoterShareMemory
										.getCurrentUserName()
								+ " to eVoter!");
				
				ActivityManager.startSubjectActivity(LoginActivity.this);
				
			}
			else {
				EVoterMobileUtils.showeVoterToast(LoginActivity.this, "Error! Username and password is not correct. Please try again!");
			}
		}else{
			super.updateRequestCallBack(response, callBackMessage);
		}
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

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
}