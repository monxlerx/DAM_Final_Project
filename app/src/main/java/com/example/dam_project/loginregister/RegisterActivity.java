package com.example.dam_project.loginregister;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam_project.R;
import com.example.dam_project.data.model.SqliteHelper;
import com.example.dam_project.data.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration TextInputLayout
    TextInputLayout textInputLayoutUserName;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    //Declaration Button
    Button buttonRegister;

    //Declaration com.example.dam_project.ui.help.SettingsActivity.SqliteHelper
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteHelper = new SqliteHelper(this);
        initTextViewLogin();
        initViews();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!sqliteHelper.isEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        sqliteHelper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            Toast.makeText(this, "Please enter a valid user", Toast.LENGTH_SHORT).show();
        } else {
            if (UserName.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(this, "Username is too short", Toast.LENGTH_SHORT).show();
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
        } else {
            if (Password.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(this, "Password is to short", Toast.LENGTH_SHORT).show();
            }
        }


        return valid;
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
        onBackPressed();
    }

}