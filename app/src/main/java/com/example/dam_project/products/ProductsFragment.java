package com.example.dam_project.products;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam_project.R;
import com.example.dam_project.addeditproduct.AddEditProductActivity;
import com.example.dam_project.data.model.ProductContract;
import com.example.dam_project.data.model.SqliteHelper;
import com.example.dam_project.productdetail.ProductDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * View for products list
 */
public class ProductsFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_PRODUCT = 2;

    private SqliteHelper mProductDbHelper;

    private ListView mProductsList;
    private ProductsCursorAdapter mProductsAdapter;

    //private FloatingActionButton mAddButton;
    String tag;

    //Variable for Button Option
    String buttonOption;

    //Add floating bottoms
    FloatingActionButton fab_main, fab_salad, fab_starter, fab_meat, fab_fish, fab_dessert, fab_drink;
    Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textView_salad, textView_starter, textView_meat, textView_fish, textView_dessert, textView_drink;
    Boolean isOpen = false;


    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        // References UI
        mProductsList = (ListView) root.findViewById(R.id.products_list);
        mProductsAdapter = new ProductsCursorAdapter(getActivity(), null);

        //Button with all functionality to add new products to the menu. It is a future update
        //mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mProductsList.setAdapter(mProductsAdapter);

        //Test to check if the bundle has been passed correctly from the activity to the fragment
        tag = getArguments().getString("CategoryMenu");

        mProductsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mProductsAdapter.getItem(i);
                String currentProductId = currentItem.getString(
                        currentItem.getColumnIndex(ProductContract.ProductEntry.ID));

                showDetailScreen(currentProductId);
            }
        });

        /*
        *
        * Show create function fot future updates
        *
        * mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });
        *
        * */

        //Use it only if you would like to clear data before to start the app.
        //getActivity().deleteDatabase(SqliteHelper.DATABASE_NAME);

        mProductDbHelper = new SqliteHelper(getActivity());

        //Setting all stuff to floating action button
        fab_main = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab_salad = (FloatingActionButton) getActivity().findViewById(R.id.fab1);
        fab_starter = (FloatingActionButton) getActivity().findViewById(R.id.fab2);
        fab_meat = (FloatingActionButton) getActivity().findViewById(R.id.fab3);
        fab_fish = (FloatingActionButton) getActivity().findViewById(R.id.fab4);
        fab_dessert = (FloatingActionButton) getActivity().findViewById(R.id.fab5);
        fab_drink = (FloatingActionButton) getActivity().findViewById(R.id.fab6);

        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_fab_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_fab_anticlock);

        textView_salad = (TextView) getActivity().findViewById(R.id.textview_salads);
        textView_starter = (TextView) getActivity().findViewById(R.id.textview_starters);
        textView_meat = (TextView) getActivity().findViewById(R.id.textview_meat);
        textView_fish = (TextView) getActivity().findViewById(R.id.textview_fish);
        textView_dessert = (TextView) getActivity().findViewById(R.id.textview_desserts);
        textView_drink = (TextView) getActivity().findViewById(R.id.textview_drinks);

        fab_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    textView_salad.setVisibility(View.INVISIBLE);
                    textView_starter.setVisibility(View.INVISIBLE);
                    textView_meat.setVisibility(View.INVISIBLE);
                    textView_fish.setVisibility(View.INVISIBLE);
                    textView_dessert.setVisibility(View.INVISIBLE);
                    textView_drink.setVisibility(View.INVISIBLE);

                    fab_salad.startAnimation((fab_close));
                    fab_starter.startAnimation((fab_close));
                    fab_meat.startAnimation((fab_close));
                    fab_fish.startAnimation((fab_close));
                    fab_dessert.startAnimation((fab_close));
                    fab_drink.startAnimation((fab_close));

                    fab_main.startAnimation(fab_anticlock);
                    fab_salad.setClickable(false);
                    fab_starter.setClickable(false);
                    fab_meat.setClickable(false);
                    fab_fish.setClickable(false);
                    fab_dessert.setClickable(false);
                    fab_drink.setClickable(false);

                    isOpen = false;

                } else {
                    textView_salad.setVisibility(View.VISIBLE);
                    textView_starter.setVisibility(View.VISIBLE);
                    textView_meat.setVisibility(View.VISIBLE);
                    textView_fish.setVisibility(View.VISIBLE);
                    textView_dessert.setVisibility(View.VISIBLE);
                    textView_drink.setVisibility(View.VISIBLE);

                    fab_salad.startAnimation((fab_open));
                    fab_starter.startAnimation((fab_open));
                    fab_meat.startAnimation((fab_open));
                    fab_fish.startAnimation((fab_open));
                    fab_dessert.startAnimation((fab_open));
                    fab_drink.startAnimation((fab_open));

                    fab_main.startAnimation(fab_clock);

                    fab_salad.setClickable(true);
                    fab_starter.setClickable(true);
                    fab_meat.setClickable(true);
                    fab_fish.setClickable(true);
                    fab_dessert.setClickable(true);
                    fab_drink.setClickable(true);

                    isOpen = true;
                }
            }
        });

        fab_salad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonOption = "Ensaladas";
                loadProductsButtons();

            }
        });

        fab_starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonOption = "Entrantes";
                loadProductsButtons();

            }
        });

        fab_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonOption = "Carnes";
                loadProductsButtons();
            }
        });

        fab_fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOption = "Pescado";
                loadProductsButtons();
            }
        });

        fab_dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonOption = "Postres";
                loadProductsButtons();
            }
        });

        fab_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonOption = "Bebidas";
                loadProductsButtons();
            }
        });

        // Loading data. It is mandatory, get all data that have been filtered by category in fragment_menu_bar
        loadProducts();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditProductActivity.REQUEST_ADD_PRODUCT:
                    showSuccessfullSavedMessage();
                    loadProducts();
                    break;
                case REQUEST_UPDATE_DELETE_PRODUCT:
                    loadProducts();
                    break;
            }
        }
    }

    private void loadProducts() {
        new ProductsLoadTask().execute();
    }

    //Loading data from the button
    private void loadProductsButtons() {
        new ProductsLoadTaskButton().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                getActivity().getString(R.string.add_product_message), Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
        startActivityForResult(intent, AddEditProductActivity.REQUEST_ADD_PRODUCT);
    }

    private void showDetailScreen(String productId) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(ProductsActivity.EXTRA_PRODUCT_ID, productId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_PRODUCT);
    }

    private class ProductsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mProductDbHelper.getProductByCategory(tag); //Method to pick up all coincidences by tag.
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mProductsAdapter.swapCursor(cursor);
            } else {
                // Show empty state
            }
        }
    }

    //Add button functionality
    private class ProductsLoadTaskButton extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mProductDbHelper.getProductByCategory(buttonOption); //Method to pick up all coincidences by tag in button
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mProductsAdapter.swapCursor(cursor);
            } else {
                // Show empty state
            }
        }
    }

}
