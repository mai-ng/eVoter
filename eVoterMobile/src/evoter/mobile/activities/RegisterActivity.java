package evoter.mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.main.ActivityManager;
import evoter.mobile.main.EVoterRequestManager;
import evoter.mobile.main.EVoterShareMemory;
import evoter.mobile.main.R;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.utils.UserValidation;

/**
 * Update by @author luongnv89 on 19-Jan-2014 <br>
 * <li>completed registering by request to server. Only student can register new
 * account Created by luongnv89 on 05/12/13.
 */
public class RegisterActivity extends EVoterActivity {
	
	EditText etUsrname, etPassword, etPasswordConfirm, etEmail;
	Button btSubmit;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		this.tvTitleBarContent.setText("Register new account");
		
		etEmail = (EditText) findViewById(R.id.etRegisterEmail);
		etUsrname = (EditText) findViewById(R.id.etRegisterUsername);
		etPassword = (EditText) findViewById(R.id.etRegisterPwd);
		etPasswordConfirm = (EditText) findViewById(R.id.etRegisterPwdConfirm);
		btSubmit = (Button) findViewById(R.id.btRegisterSubmit);
		btSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String i_email = etEmail.getText().toString();
				final String i_usrname = etUsrname.getText().toString();
				String i_password = etPassword.getText().toString();
				String i_passowrdConfirm = etPasswordConfirm.getText()
						.toString();
				
				if (i_email.equals("")) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Please input your email");
				} else if (!UserValidation.isValidEmail(i_email)) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Input email is not valid");
				} else if (i_usrname.equals("")) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Please input your username");
				} else if (!UserValidation.isValidUserName(i_usrname)) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Input username is not valid");
				} else if (i_password.equals("")) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Please input your password");
				} else if (!UserValidation.isValidPassword(i_password)) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Input password is not valid");
				} else if (i_passowrdConfirm.equals("")) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Please input your password confirm");
				} else if (!UserValidation
						.isValidPassword(i_passowrdConfirm)) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Input confirm password is not valid");
				} else if (!i_passowrdConfirm.equals(i_password)) {
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"Password and confirm password are not the same!");
				} else {
					
					EVoterRequestManager.createNewUser(i_email, i_usrname, i_password, RegisterActivity.this);
				}
				
			}
		});
		
	}
	
	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		// TODO Auto-generated method stub
		super.setupTitleBar();
		ivTitleBarIcon.setEnabled(false);
		ivTitleBarRefresh.setEnabled(false);
	}



	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.CREATE_USER_EVOTER_REQUEST)) {
			if (response.contains("USER EXISTS ALREADY")) {
				EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
						"Username already used by other user. Please choose another username");
			}
			else if (response.contains("EMAIL EXISTS ALREADY")) {
				EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
						"Email already registered in system. Please register by another email or use reset password!");
			}
			else {
				EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
						"You will receive an email to confirm your register!");
				EVoterShareMemory.setCurrentUserName(etUsrname.getText().toString());
				ActivityManager.startLoginActivity(RegisterActivity.this);
			}
		}else{
			super.updateRequestCallBack(response, callBackMessage);
		}
	}

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
}