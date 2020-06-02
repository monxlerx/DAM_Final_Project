package com.example.dam_project.productdetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.dam_project.R;
import com.example.dam_project.addeditproduct.AddEditProductActivity;
import com.example.dam_project.addeditproduct.AddEditProductFragment;
import com.example.dam_project.data.model.Product;
import com.example.dam_project.data.model.SqliteHelper;
import com.example.dam_project.data.model.Wish;
import com.example.dam_project.data.model.WishContract;
import com.example.dam_project.products.ProductsActivity;
import com.example.dam_project.products.ProductsFragment;
import com.example.dam_project.sessionmanagment.UserSessionManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.HashMap;

import static com.example.dam_project.data.model.SqliteHelper.NAME;
import static com.example.dam_project.data.model.SqliteHelper.TABLE_PRODUCTS;

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
    UserSessionManager session;
    private Button mButtonFav;
    String email;
    String name;


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

        //Disable actions of these options that will only available in the future as a Administrator. Not showing to customers
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
        mButtonFav = (Button) root.findViewById(R.id.favBtn);

        mProductsDbHelper = new SqliteHelper(getActivity());

        //Get user
        session = new UserSessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(UserSessionManager.KEY_EMAIL);

        loadProduct();

        Cursor cursor = mProductsDbHelper.getWishByName(mName.getText().toString());

        mButtonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mName.getText().toString();
                String description = mDescription.getText().toString();
                String category = mCategory.getText().toString();
                String prize = mPrize.getText().toString();
                String avatar = "chef.jpg";
                email = email;

                if(session.isUserLoggedIn()) {
                    //Check if the query has return some cursor. If the result is > 0, it is because there is a coincidence in the database.
                    Cursor cursorCoincidence = mProductsDbHelper.getWishCoincidence(name, email);
                    if(cursorCoincidence.getCount() > 0) {
                        Toast.makeText(getActivity(), "Ya estaba añadido a favoritos", Toast.LENGTH_SHORT).show();
                    } else {
                        Wish wish = new Wish(name, description,category, prize, avatar, email);
                        mProductsDbHelper.saveWish(wish);
                        mButtonFav.setBackgroundResource(R.drawable.ic_favorite_red_24);
                        Toast.makeText(getContext(), "Se ha añadido a favoritos " , Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Debes iniciar sesión para guardar tus favoritos" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void loadProduct() {
        new GetProductByIdTask().execute();
    }

    //Add functionality to Edit and Delete in the Action Bar
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

    //Set the visibility to false for edit and delete icons.
    //To restore edit and delete actions, just comment or delete the method onPrepareOptionsMenu
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_delete).setVisible(false);
        super.onPrepareOptionsMenu(menu);
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
