package evoter.mobile.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import evoter.mobile.main.R;
import evoter.mobile.objects.EVoterShareMemory;
import evoter.mobile.objects.OfflineEVoterManager;
import evoter.mobile.utils.EVoterMobileUtils;
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
		
		this.tvTitleBarContent.setText("Login");
		
		this.ivTitleBarIcon.setEnabled(false);
		
		etUsrName = (EditText) findViewById(R.id.usrname);
		if (EVoterShareMemory.getCurrentUserName() != null) {
			etUsrName.setText(EVoterShareMemory.getCurrentUserName());
		}
		etPassword = (EditText) findViewById(R.id.password);
		
		btLogin = (Button) findViewById(R.id.btLogin);
		
		//Login button click
		btLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loginButtonAction();
				
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
	
	/**
	 * 
	 */
	private void loginButtonAction() {
		// GetData getData = new GetData();
		final String i_Usrname = etUsrName.getText().toString();
		EVoterShareMemory
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
			EVoterRequestManager.doLogin(i_Usrname, i_Password,LoginActivity.this);
		}
	}

}