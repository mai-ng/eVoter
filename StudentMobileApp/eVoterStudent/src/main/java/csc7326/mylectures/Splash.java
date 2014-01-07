package csc7326.mylectures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class Splash extends Activity {

    ProgressBar progressBar;
    EVoterSessionManager eVoterSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.splash);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.window_title);
        if (getIntent().getBooleanExtra("Exit application", false)) {
            finish();
            return;
        }

        eVoterSessionManager = new EVoterSessionManager(this);

        progressBar = (ProgressBar) findViewById(R.id.prBar);

        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                eVoterSessionManager.checkLogin();
            }
        }, 3000);
    }

}
