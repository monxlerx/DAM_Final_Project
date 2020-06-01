package com.example.dam_project.wishs;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dam_project.R;
import com.example.dam_project.addeditproduct.AddEditProductActivity;
import com.example.dam_project.data.model.ProductContract;
import com.example.dam_project.data.model.SqliteHelper;
import com.example.dam_project.data.model.WishContract;
import com.example.dam_project.productdetail.ProductDetailActivity;
import com.example.dam_project.products.ProductsActivity;
import com.example.dam_project.products.ProductsCursorAdapter;
import com.example.dam_project.products.ProductsFragment;
import com.example.dam_project.sessionmanagment.UserSessionManager;
import com.example.dam_project.wishdetail.WishDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

/**
 * View for wish list
 */

public class WishsFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_WISH = 2;

    private SqliteHelper mWishDbHelper;

    private ListView mWishsList;
    private WishsCursorAdapter mWishsAdapter;

    UserSessionManager session;
    String email;

    public WishsFragment() {
        // Required empty public constructor
    }

    public static WishsFragment newInstance() {
        return new WishsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wishs, container, false);

        // References UI
        mWishsList = (ListView) root.findViewById(R.id.wishs_list);
        mWishsAdapter = new WishsCursorAdapter(getActivity(), null);

        //Get the email to obtain wish list of a user or send it to LogActivity
        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(UserSessionManager.KEY_EMAIL);

        // Setup
        mWishsList.setAdapter(mWishsAdapter);
        mWishsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mWishsAdapter.getItem(i);
                String currentWishId = currentItem.getString(
                        currentItem.getColumnIndex(WishContract.WishEntry.WISH_ID));

                showDetailScreen(currentWishId);
            }
        });

        mWishDbHelper = new SqliteHelper(getActivity());
        loadWishs();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                //case AddEditProductActivity.REQUEST_ADD_PRODUCT:
                    //showSuccessfullSavedMessage();
                    //loadProducts();
                  // break;
                case REQUEST_UPDATE_DELETE_WISH:
                    loadWishs();
                    break;
            }
        }
    }

    private void loadWishs() {
        new WishsFragment.WishsLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
        startActivityForResult(intent, AddEditProductActivity.REQUEST_ADD_PRODUCT);
    }

    private void showDetailScreen(String wishId) {
        Intent intent = new Intent(getActivity(), WishDetailActivity.class);
        intent.putExtra(WishsActivity.EXTRA_WISH_ID, wishId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_WISH);
    }

    private class WishsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mWishDbHelper.getWishByEmail(email); //Method to pick up all coincidences by tag.
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mWishsAdapter.swapCursor(cursor);
            } else {
                // Show empty state
            }
        }
    }

}
