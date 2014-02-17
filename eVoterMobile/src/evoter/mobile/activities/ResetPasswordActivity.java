package evoter.mobile.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import evoter.mobile.main.R;
import evoter.mobile.utils.EVoterMobileUtils;
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
	
}