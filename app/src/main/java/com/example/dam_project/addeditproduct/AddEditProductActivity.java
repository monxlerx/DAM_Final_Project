package com.example.dam_project.addeditproduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.dam_project.R;
import com.example.dam_project.products.ProductsActivity;

public class AddEditProductActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_PRODUCT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String productId = getIntent().getStringExtra(ProductsActivity.EXTRA_PRODUCT_ID);

        setTitle(productId == null ? "Añadir producto" : "Editar producto");

        AddEditProductFragment addEditProductFragment = (AddEditProductFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_product_container);
        if (addEditProductFragment == null) {
            addEditProductFragment = AddEditProductFragment.newInstance(productId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_product_container, addEditProductFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}