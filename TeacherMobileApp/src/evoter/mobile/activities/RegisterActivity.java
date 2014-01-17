package evoter.mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import evoter.mobile.main.R;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.utils.UserValidation;

/**
 * Created by luongnv89 on 05/12/13.
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
				String i_usrname = etUsrname.getText().toString();
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
					// TODO: request to sever: email,username,password,user_type
					EVoterMobileUtils.showeVoterToast(RegisterActivity.this,
							"You will receive an email to confirm your register!");
					RuntimeEVoterManager.setCurrentUserName(i_usrname);
					Intent intent = new Intent(RegisterActivity.this,
							LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}

			}
		});

	}
}