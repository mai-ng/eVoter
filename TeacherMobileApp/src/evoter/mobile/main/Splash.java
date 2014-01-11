package evoter.mobile.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import evoter.mobile.utils.Utils;

public class Splash extends EVoterActivity {

	ProgressBar progressBar;
	OfflineEVoterManager eVoterSessionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		this.tvTitleBarContent.setContentDescription("eVoter Mobile");
		
		if (getIntent().getBooleanExtra("Exit application", false)) {
			finish();
			return;
		}

		eVoterSessionManager = new OfflineEVoterManager(this);

		progressBar = (ProgressBar) findViewById(R.id.prBar);

		progressBar.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (Utils.hasInternetConnection(Splash.this)) {
					eVoterSessionManager.checkLogin();
				} else {
					EVoterDialogInfor dialog = new EVoterDialogInfor(
							Splash.this, "Error connection!");
					dialog.setMessageContent("Cannot connect to internet. Check your mobile internet connection an try again!");
					dialog.show();
					dialog.getBtOK().setText("Retry");
					dialog.getBtOK().setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent exitIntent = new Intent(Splash.this,
									Splash.class);
							exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							exitIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(exitIntent);
						}
					});
					dialog.getBtKO().setText("Exit");
					dialog.getBtKO().setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							finish();
						}
					});
				}
			}
		}, 1000);
	}
}
