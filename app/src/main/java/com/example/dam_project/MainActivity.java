package com.example.dam_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam_project.loginregister.ConfirmLoginActivity;
import com.example.dam_project.loginregister.LogActivity;
import com.example.dam_project.sessionmanagment.UserSessionManager;
import com.example.dam_project.wishs.WishsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    public UserSessionManager session;
    private AppBarConfiguration mAppBarConfiguration;
    private Button myButton;
    public TextView textName;
    public TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menuBar, R.id.nav_promotions, R.id.nav_help, R.id.nav_feedback)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Check if user has been logged and get data from SharedPreferences
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(UserSessionManager.KEY_NAME);
        String email = user.get(UserSessionManager.KEY_EMAIL);

        //Add TextView name and TextView email to Navigation Drawer
        textName = (TextView) findViewById(R.id.tvCustomerName);
        textEmail = (TextView) findViewById(R.id.tvCustomerEmail);
        View headerView = navigationView.getHeaderView(0);
        textName = headerView.findViewById(R.id.tvCustomerName);
        textEmail = headerView.findViewById(R.id.tvCustomerEmail);

        if (name == null) {
            textName.setText("Invitado");
            textEmail.setText("");
        } else {
            textName.setText(name);
            textEmail.setText(email);
        }


        //Remove navigation bar to allow full screen view when the activity is onCreate
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.action_login:
                    if(session.isUserLoggedIn()) {
                        i = new Intent(this, ConfirmLoginActivity.class);
                    } else {
                        i = new Intent(this, LogActivity.class);
                    }
                    startActivity(i);
                    finish();
                    return true;

            case R.id.action_favourites:
                    if(session.isUserLoggedIn()) {
                        i = new Intent(this, WishsActivity.class);
                    } else {
                        i = new Intent(this, MainActivity.class);
                        Toast.makeText(getApplicationContext(), "Debes iniciar sesión para ver tus favoritos", Toast.LENGTH_LONG).show();
                    }
                    startActivity(i);
                    finish();
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

    public void moveToLogin(View v) {
        Intent i = new Intent(this, LogActivity.class);
        startActivity(i);
    }

}
