package evoter.mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import evoter.mobile.main.R;
import evoter.mobile.utils.EVoterMobileUtils;

public class StartActivity extends EVoterActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		this.tvTitleBarContent.setContentDescription("eVoter Mobile");
		
		this.ivTitleBarIcon.setEnabled(false);
		
		if (getIntent().getBooleanExtra("Exit application", false)) {
			finish();
			return;
		}
		if (EVoterMobileUtils.hasInternetConnection(StartActivity.this)) {
			offlineEVoterManager.checkLogin();
		} else {
			errorConnection();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#refreshActivity()
	 */
	@Override
	public void refreshActivity() {
		// TODO Auto-generated method stub
		Intent exitIntent = new Intent(StartActivity.this,
				StartActivity.class);
		exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		exitIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(exitIntent);
	}
	
}
