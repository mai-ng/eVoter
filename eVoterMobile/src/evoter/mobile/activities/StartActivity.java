package evoter.mobile.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import evoter.mobile.main.R;
import evoter.mobile.utils.EVoterMobileUtils;

public class StartActivity extends EVoterActivity {
	
	/**
	 */
	public static final String EXIT_APPLICATION = "EXIT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		if (getIntent().getBooleanExtra(EXIT_APPLICATION, false)) {
			finish();
			return;
		}
		
		if (EVoterMobileUtils.hasInternetConnection(StartActivity.this)) {
			offlineEVoterManager.checkLogin();
		} else {
			errorConnection();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#setupTitleBar()
	 */
	@Override
	protected void setupTitleBar() {
		super.setupTitleBar();
		ivTitleBarIcon.setEnabled(false);
	}



	/**
	 * Show dialog when there is some error internet connection.
	 * <br> User can exit application or try to start again
	 */
	protected void errorConnection() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("Internet connection")
				.setMessage("Cannot connect to internet. Check your mobile internet connection an try again!")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(R.string.exit_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						exitApplication();
					}
				})
				.setNegativeButton(R.string.retry_button, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent exitIntent = new Intent(StartActivity.this,
								StartActivity.class);
						exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						exitIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(exitIntent);
					}
				}).show();
	}


	/* (non-Javadoc)
	 * @see evoter.mobile.activities.EVoterActivity#loadData()
	 */
	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
}
