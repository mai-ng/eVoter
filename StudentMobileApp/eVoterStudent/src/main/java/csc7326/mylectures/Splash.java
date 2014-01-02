package csc7326.mylectures;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;

public class Splash extends ActionBarActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        progressBar = (ProgressBar) findViewById(R.id.prBar);

        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
//                for(int i=0;i<3;i++){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                Intent login = new Intent(Splash.this, Login.class);
                startActivity(login);
                finish();
            }
        }, 3000);
    }

}
