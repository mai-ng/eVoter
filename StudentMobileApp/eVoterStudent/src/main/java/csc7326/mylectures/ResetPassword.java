package csc7326.mylectures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by luongnv89 on 05/12/13.
 */
public class ResetPassword extends Activity {

    String validEmail = "luongnv89@gmail.com";
    EditText etEmail;
    Button btReset;
    TextView tvFgPasswordStt;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btReset = (Button) findViewById(R.id.btResetPassword);
        tvFgPasswordStt = (TextView) findViewById(R.id.tvFgPassStatus);

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i_email = etEmail.getText().toString();
                if (i_email.equals(""))
                    tvFgPasswordStt.setText("Input your email!");
                else if (!i_email.equals(validEmail)) {
                    tvFgPasswordStt.setText("Incorrect email!");
                } else if (i_email.equals(validEmail)) {
                    //Do something to send email to reset password
//                    startActivity(new Intent("android.intent.action.CONFIRMRESETPASSWORD"));
                    setContentView(R.layout.password_reset_confirm);
                }
            }

        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mnInfalter = getMenuInflater();
        mnInfalter.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnExit:
                Intent exitIntent = new Intent(this, Splash.class);
                exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                exitIntent.putExtra("Exit application", true);
                startActivity(exitIntent);
                finish();
                return true;
        }
        return false;
    }
}