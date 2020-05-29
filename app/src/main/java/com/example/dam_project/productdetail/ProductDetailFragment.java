package com.example.dam_project.productdetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.dam_project.R;
import com.example.dam_project.addeditproduct.AddEditProductActivity;
import com.example.dam_project.data.model.Product;
import com.example.dam_project.data.model.SqliteHelper;
import com.example.dam_project.products.ProductsActivity;
import com.example.dam_project.products.ProductsFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;


/**
 * View to show the product detail
 */
public class ProductDetailFragment extends Fragment {
    private static final String ARG_PRODUCT_ID = "productId";

    private String mProductId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mName;
    private TextView mDescription;
    private TextView mCategory;
    private TextView mPrize;


    private SqliteHelper mProductsDbHelper;


    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(String productId) {
        ProductDetailFragment fragment = new ProductDetailFragment();
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mName = (TextView) root.findViewById(R.id.tv_name);
        mDescription = (TextView) root.findViewById(R.id.tv_description);
        mCategory = (TextView) root.findViewById(R.id.tv_category);
        mPrize = (TextView) root.findViewById(R.id.tv_prize);

        mProductsDbHelper = new SqliteHelper(getActivity());

        loadProduct();

        return root;
    }

    private void loadProduct() {
        new GetProductByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteProductTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ProductsFragment.REQUEST_UPDATE_DELETE_PRODUCT) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showProduct(Product product) {
        mCollapsingView.setTitle(product.getName());
        Glide.with(getContext())
                .load(Uri.parse("file:///android_asset/" + product.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mName.setText(product.getName());
        mDescription.setText(product.getDescription());
        mCategory.setText(product.getCategory());
        mPrize.setText(product.getPrize());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
        intent.putExtra(ProductsActivity.EXTRA_PRODUCT_ID, mProductId);
        startActivityForResult(intent, ProductsFragment.REQUEST_UPDATE_DELETE_PRODUCT);
    }

    private void showProductScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar producto", Toast.LENGTH_SHORT).show();
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
            }
        }

    }

    private class DeleteProductTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mProductsDbHelper.deleteProduct(mProductId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showProductScreen(integer > 0);
        }

    }

}
