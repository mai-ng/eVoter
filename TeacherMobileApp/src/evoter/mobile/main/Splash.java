package evoter.mobile.main;

import evoter.mobile.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import evoter.mobile.utils.Utils;

public class Splash extends Activity {

	ProgressBar progressBar;
	EVoterSessionManager eVoterSessionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.splash);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.window_title);
		if (getIntent().getBooleanExtra("Exit application", false)) {
			finish();
			return;
		}

		eVoterSessionManager = new EVoterSessionManager(this);

		progressBar = (ProgressBar) findViewById(R.id.prBar);

		progressBar.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (Utils.isConnected(Splash.this)) {
					eVoterSessionManager.checkLogin();
				} else {
					Dialog dialog = new Dialog(Splash.this);
					dialog.setTitle("Cannot connect internet!");
					dialog.show();
					Intent exitIntent = new Intent(Splash.this, Splash.class);
					exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					exitIntent.putExtra("Exit application", true);
					startActivity(exitIntent);
					finish();
				}
			}
		}, 3000);
	}

}
