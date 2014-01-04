package csc7326.mylectures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;

public class Splash extends Activity {

    ProgressBar progressBar;
    EVoterSessionManager eVoterSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

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
