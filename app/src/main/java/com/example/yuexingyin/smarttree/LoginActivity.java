package com.example.yuexingyin.smarttree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }


    public void gotoSignup(View view){

        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_btn){

            //get all the parameters that user enter
            EditText emailAddress = (EditText) findViewById(R.id.email_login);
            String email = emailAddress.getText().toString();
            EditText pass = (EditText) findViewById(R.id.password_login);
            String password = pass.getText().toString();

            DBConnector dbConnector = new DBConnector(this);

            String password1 = dbConnector.login(email);

            if(password.equals(password1)){

                Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                String username = dbConnector.getUsername(password);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }

        }
    }
}
