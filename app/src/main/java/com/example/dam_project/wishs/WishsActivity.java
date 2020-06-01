package com.example.dam_project.wishs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.dam_project.MainActivity;
import com.example.dam_project.R;
import com.example.dam_project.products.ProductsFragment;

public class WishsActivity extends AppCompatActivity {

    public static final String EXTRA_WISH_ID = "extra_wish_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create arrow back to the toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        WishsFragment fragment = (WishsFragment)
                getSupportFragmentManager().findFragmentById(R.id.wishs_container);

        if (fragment == null) {
            fragment = WishsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.wishs_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle arrow click
        if(item.getItemId() == android.R.id.home) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }

        return super.onOptionsItemSelected(item);
    }


}
