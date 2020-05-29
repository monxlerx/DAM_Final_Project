package com.example.dam_project.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dam_project.MainActivity;
import com.example.dam_project.R;
import com.example.dam_project.sessionmanagment.UserSessionManager;

import java.util.HashMap;

public class ConfirmLoginActivity extends AppCompatActivity {

    UserSessionManager session;
    TextView userName;
    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_login);

        session = new UserSessionManager(getApplicationContext());
        userName = (TextView) findViewById(R.id.tx_confirm);

        HashMap<String, String> user = session.getUserDetails();

        userName.setText(user.get(UserSessionManager.KEY_NAME));

        btnLogOut = (Button) findViewById(R.id.buttonLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });

    }

    //Allow to hide the navigation bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    //Back to the previous activity
    public void moveBack(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}