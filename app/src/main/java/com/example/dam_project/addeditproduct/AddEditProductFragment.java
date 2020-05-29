package com.example.dam_project.addeditproduct;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dam_project.R;
import com.example.dam_project.data.model.Product;
import com.example.dam_project.data.model.SqliteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * View para create/edit a product*/

public class AddEditProductFragment extends Fragment {
    private static final String ARG_PRODUCT_ID = "arg_product_id";

    private String mProductId;

    private SqliteHelper mProductsDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mDescriptionField;
    private TextInputEditText mCategoryField;
    private TextInputEditText mPrizeField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mDescriptionLabel;
    private TextInputLayout mCategoryLabel;
    private TextInputLayout mPrizeLabel;


    public AddEditProductFragment() {
        // Required empty public constructor
    }

    public static AddEditProductFragment newInstance(String productId) {
        AddEditProductFragment fragment = new AddEditProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductId = getArguments().getString(ARG_PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_product, container, false);

        // References UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mDescriptionField = (TextInputEditText) root.findViewById(R.id.et_description);
        mCategoryField = (TextInputEditText) root.findViewById(R.id.et_category);
        mPrizeField = (TextInputEditText) root.findViewById(R.id.et_prize);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mDescriptionLabel = (TextInputLayout) root.findViewById(R.id.til_description);
        mCategoryLabel = (TextInputLayout) root.findViewById(R.id.til_category);
        mPrizeLabel = (TextInputLayout) root.findViewById(R.id.til_prize);

        // Events
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditProduct();
            }
        });

        mProductsDbHelper = new SqliteHelper(getActivity());

        // Loading data
        if (mProductId != null) {
            loadProduct();
        }

        return root;
    }

    private void loadProduct() {
        new GetProductByIdTask().execute();
    }

    private void addEditProduct() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String description = mDescriptionField.getText().toString();
        String category  = mCategoryField.getText().toString();
        String prize = mPrizeField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(description)) {
            mDescriptionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(category)) {
            mCategoryLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(prize)) {
            mPrizeLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Product product = new Product(name, description, category, prize, "");

        new AddEditProductTask().execute(product);

    }

    private void showProductsScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showProduct(Product product) {
        mNameField.setText(product.getName());
        mDescriptionField.setText(product.getDescription());
        mCategoryField.setText(product.getCategory());
        mPrizeField.setText(product.getPrize());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar producto", Toast.LENGTH_SHORT).show();
    }

    private class GetProductByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mProductsDbHelper.getProductById(mProductId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showProduct(new Product(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditProductTask extends AsyncTask<Product, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Product... products) {
            if (mProductId != null) {
                return mProductsDbHelper.updateProduct(products[0], mProductId) > 0;

            } else {
                return mProductsDbHelper.saveProduct(products[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showProductsScreen(result);
        }

    }

}