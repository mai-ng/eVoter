package evoter.mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import evoter.mobile.main.R;
import evoter.mobile.objects.DialogInfor;
import evoter.mobile.objects.OfflineEVoterManager;
import evoter.mobile.utils.Utils;

public class StartActivity extends EVoterActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		this.tvTitleBarContent.setContentDescription("eVoter Mobile");
		
		if (getIntent().getBooleanExtra("Exit application", false)) {
			finish();
			return;
		}

		offlineEVoterManager = new OfflineEVoterManager(this);

		progressBar = (ProgressBar) findViewById(R.id.prBar);

		progressBar.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (Utils.hasInternetConnection(StartActivity.this)) {
					offlineEVoterManager.checkLogin();
				} else {
					DialogInfor dialog = new DialogInfor(
							StartActivity.this, "Error connection!");
					dialog.setMessageContent("Cannot connect to internet. Check your mobile internet connection an try again!");
					dialog.show();
					dialog.getBtOK().setText("Retry");
					dialog.getBtOK().setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent exitIntent = new Intent(StartActivity.this,
									StartActivity.class);
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
