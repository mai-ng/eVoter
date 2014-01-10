package evoter.mobile.main;

import evoter.mobile.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import evoter.mobile.utils.Utils;

/**
 * Created by luongnv89 on 05/12/13.
 */
public class Register extends Activity {

	EditText etUsrname, etPassword, etEmail;
	CheckBox cbAcceptPolicy;
	Button btSubmit;

	TextView tvStatus;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		etUsrname = (EditText) findViewById(R.id.etUserNameRegister);
		etPassword = (EditText) findViewById(R.id.etPwdRegister);
		etEmail = (EditText) findViewById(R.id.etEmailRegister);

		tvStatus = (TextView) findViewById(R.id.tvRegisterStt);

		cbAcceptPolicy = (CheckBox) findViewById(R.id.cbAcceptPolicy);
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

		btSubmit = (Button) findViewById(R.id.btSubmit);
		btSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String i_usrname = etUsrname.getText().toString();
				String i_password = etPassword.getText().toString();
				String i_email = etEmail.getText().toString();
				String status = "";
				if (!Utils.usrnameValid(i_usrname)) {
					status += "Usrname is invalid!";
				}
				if (!Utils.passwordValid(i_password)) {
					status += "Password is invalid!";
				}
				if (!Utils.emailValid(i_email)) {
					status += "Email is invalid!";
				}
				if (Utils.passwordValid(i_password)
						&& Utils.emailValid(i_email)
						&& Utils.usrnameValid(i_usrname)) {
					setContentView(R.layout.register_confirm);
				}
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater mnInfalter = getMenuInflater();
		mnInfalter.inflate(R.menu.menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mnExit:
			Intent exitIntent = new Intent(this, Splash.class);
			exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			exitIntent.putExtra("Exit application", true);
			startActivity(exitIntent);
			finish();
			return true;
		}
		return false;
	}
}