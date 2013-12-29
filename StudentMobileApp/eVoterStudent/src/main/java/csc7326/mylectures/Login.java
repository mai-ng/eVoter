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
public class Login extends Activity {
    String usrname = "a";
    String password = "a";

    EditText etUsrName;
    EditText etPassword;
    TextView tvStatusLogin;

    Button btLogin;
    Button btFgPassword;
    Button btRegister;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        etUsrName = (EditText) findViewById(R.id.usrname);
        etPassword = (EditText) findViewById(R.id.password);
        tvStatusLogin = (TextView) findViewById(R.id.tvStatusLogin);

        btLogin = (Button) findViewById(R.id.btLogin);
        btFgPassword = (Button) findViewById(R.id.btfgPassword);
        btRegister = (Button) findViewById(R.id.btRegister);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GetData getData = new GetData();

                String i_Usrname = etUsrName.getText().toString();
                String i_Password = etPassword.getText().toString();
                String sttMsg = "";
//                String sttMsg = getData.getContent("http://www.vogella.com");

                if (i_Usrname.equals("") || i_Password.equals("")) {
                    sttMsg += "User name or password is empty!";
                    tvStatusLogin.setText(sttMsg);
                    btFgPassword.setVisibility(View.VISIBLE);
                    btRegister.setVisibility(View.VISIBLE);
                } else if (!i_Usrname.equals(usrname) || !i_Password.equals(password)) {
                    sttMsg += "Username and password incorrect!";
                    tvStatusLogin.setText(sttMsg);
                    btFgPassword.setVisibility(View.VISIBLE);
                    btRegister.setVisibility(View.VISIBLE);
                } else if (i_Usrname.equals(usrname) && i_Password.equals(password)) {
                    startActivity(new Intent("android.intent.action.SUBJECT"));
                }

            }
        });

        btFgPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.RECOVERPASSWORD"));
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.REGISTER"));
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
                finish();
                return true;
        }
        return false;
    }
}