package evoter.mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import evoter.mobile.main.R;
import evoter.mobile.objects.RequestConfig;
import evoter.mobile.utils.EVoterMobileUtils;
import evoter.share.dao.UserDAO;
import evoter.share.utils.URIRequest;
import evoter.share.utils.UserValidation;

/**
 * Update on 19-Jan-2014 by @author luongnv89: <br>
 * <li> Completed request to server
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
				final String i_email = etEmail.getText().toString();
				if (i_email.equals("")) {
					EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
							"Input your email!");
				} else if (!UserValidation.isValidEmail(i_email)) {
					EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
							"Input email is invalid. Try again!");
				} else {
					RequestParams params = new RequestParams();
					params.put(UserDAO.EMAIL, i_email);
					client.post(RequestConfig.getURL(URIRequest.RESET_PASSWORD), params, new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(String response) {
							Log.i("REGISTER", "Successful: " + response);
							if (response.contains("FAILURE")) {
								EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
										"Email not exists. Please try again or register new account!");
							}
							else if (response.contains("SUCCESS")) {
								// TODO: Send request to sever: email to change the password
								EVoterMobileUtils.showeVoterToast(ResetPasswordActivity.this,
										"You will receive an email confirm to reset your password! Email send to address: " + i_email);
								Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
							}
						}
						
						@Override
						public void onFailure(Throwable error, String content) {
							Log.e("REGISTER", "onFailure error : "
									+ error.toString() + "content : " + content);
						}
					});
				}
			}
			
		});
	}
}