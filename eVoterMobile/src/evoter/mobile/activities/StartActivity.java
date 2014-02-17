package evoter.mobile.activities;

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
}
