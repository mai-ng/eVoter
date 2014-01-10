package evoter.mobile.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import evoter.mobile.utils.UserAccountValidation;
import evoter.mobile.utils.Utils;

/**
 * Created by luongnv89 on 05/12/13.
 */
public class Register extends EVoterActivity {

	EditText etUsrname, etPassword, etPasswordConfirm, etEmail;
	CheckBox cbAcceptPolicy;
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
					Utils.showeVoterToast(Register.this,
							"Please input your email");
				} else if (!UserAccountValidation.isValidEmail(i_email)) {
					Utils.showeVoterToast(Register.this,
							"Input email is not valid");
				} else if (i_usrname.equals("")) {
					Utils.showeVoterToast(Register.this,
							"Please input your username");
				} else if (!UserAccountValidation.isValidUserName(i_usrname)) {
					Utils.showeVoterToast(Register.this,
							"Input username is not valid");
				} else if (i_password.equals("")) {
					Utils.showeVoterToast(Register.this,
							"Please input your password");
				} else if (!UserAccountValidation.isValidPassword(i_password)) {
					Utils.showeVoterToast(Register.this,
							"Input password is not valid");
				} else if (i_passowrdConfirm.equals("")) {
					Utils.showeVoterToast(Register.this,
							"Please input your password confirm");
				} else if (!UserAccountValidation
						.isValidPassword(i_passowrdConfirm)) {
					Utils.showeVoterToast(Register.this,
							"Input confirm password is not valid");
				} else if (!i_passowrdConfirm.equals(i_password)) {
					Utils.showeVoterToast(Register.this,
							"Password and confirm password are not the same!");
				} else {
					// TODO: request to sever: email,username,password,user_type
					Utils.showeVoterToast(Register.this,
							"You will receive an email to confirm your register!");
					Intent intent = new Intent(Register.this, Login.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}

			}
		});

		cbAcceptPolicy = (CheckBox) findViewById(R.id.cbRegisterPolicy);
		cbAcceptPolicy
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							btSubmit.setEnabled(true);
						} else {
							btSubmit.setEnabled(false);
						}
					}
				});

	}
}