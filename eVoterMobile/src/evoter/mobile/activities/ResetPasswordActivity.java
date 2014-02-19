package evoter.mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import evoter.mobile.abstracts.EVoterActivity;
import evoter.mobile.main.ActivityManager;
import evoter.mobile.main.EVoterRequestManager;
import evoter.mobile.main.R;
import evoter.mobile.utils.CallBackMessage;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

/**
 * Update on 19-Jan-2014 by @author luongnv89: <br>
 * <li>Completed request to server Created by luongnv89 on 05/12/13.
 */
public class ResetPasswordActivity extends EVoterActivity {
	
	EditText etEmail;
	Button btReset;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_reset);
		
		this.tvTitleBarContent.setText("Reset password");
		
		etEmail = (EditText) findViewById(R.id.etEmail);
		btReset = (Button) findViewById(R.id.btResetPassword);
		
		btReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String i_email = etEmail.getText().toString();
				if (i_email.equals("")) {
					EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
							"Input your email!");
				} else if (!UserValidation.isValidEmail(i_email)) {
					EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
							"Input email is invalid. Try again!");
				} else {
					EVoterRequestManager.resetPassword(i_email, ResetPasswordActivity.this);
				}
			}
			
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * evoter.mobile.activities.EVoterActivity#updateRequestCallBack(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public void updateRequestCallBack(String response, String callBackMessage) {
		if (callBackMessage.equals(CallBackMessage.RESET_PASSWORD_EVOTER_REQUEST)) {
			if (response.contains(URIRequest.FAILURE_MESSAGE)) {
				EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
						"Email not exists. Please try again or register new account!");
			}
			else if (response.contains(URIRequest.EMAIL_EXIST_MESSAGE)) {
				// TODO: Send request to sever: email to change the password
				EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
						"You will receive an email confirm to reset your password! Email send to address: " + etEmail.getText().toString());
				ActivityManager.startLoginActivity(ResetPasswordActivity.this);
			}else{
				EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
						"Cannot reset password: " + response);
			}
		}else{
			super.updateRequestCallBack(response, callBackMessage);
		}
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

	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
}