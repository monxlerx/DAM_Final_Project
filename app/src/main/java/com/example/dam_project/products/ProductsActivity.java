package com.example.dam_project.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.dam_project.R;

public class ProductsActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_ID = "extra_product_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create arrow back to the toolbar
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Retrieve Category key from the previous fragment
        Bundle bundle = getIntent().getExtras();
        String category = getIntent().getStringExtra("Category");

        //Create a new Bundle to pass this category to the next fragment in order to filtrate results with getElementByTag()
        Bundle args = new Bundle();
        args.putString("CategoryMenu", category);

        ProductsFragment fragment = (ProductsFragment)
                getSupportFragmentManager().findFragmentById(R.id.products_container);

        if (fragment == null) {
            fragment = ProductsFragment.newInstance();
            fragment.setArguments(args);//Add bundle to the fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.products_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle arrow click
        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
