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
				String i_email = etEmail.getText().toString();
				if (i_email.equals("")) {
					EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
							"Input your email!");
				} else if (!UserValidation.isValidEmail(i_email)) {
					EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
							"Input email is invalid. Try again!");
				} else {

					// TODO: Send request to sever: email to change the password
					EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
							"You will receive an email confirm to reset your password!");
					Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			}

		});
	}
}