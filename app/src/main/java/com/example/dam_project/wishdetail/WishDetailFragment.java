package com.example.dam_project.wishdetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.dam_project.data.model.Product;
import com.example.dam_project.data.model.SqliteHelper;
import com.example.dam_project.data.model.Wish;
import com.example.dam_project.productdetail.ProductDetailFragment;
import com.example.dam_project.products.ProductsActivity;
import com.example.dam_project.products.ProductsFragment;
import com.example.dam_project.sessionmanagment.UserSessionManager;
import com.example.dam_project.wishs.WishsActivity;
import com.example.dam_project.wishs.WishsFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

/**
 * View to show the wish detail
 */

public class WishDetailFragment extends Fragment {
    private static final String ARG_WISH_ID = "wishId";

    private String mWishId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mName;
    private TextView mDescription;
    private TextView mCategory;
    private TextView mPrize;
    private FloatingActionButton fab_main;
    String email;
    Button btnFav;

    UserSessionManager session;


    private SqliteHelper mWishsDbHelper;

    public WishDetailFragment() {
        // Required empty public constructor
    }

    public static WishDetailFragment newInstance(String wishId) {
        WishDetailFragment fragment = new WishDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WISH_ID, wishId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mWishId = getArguments().getString(ARG_WISH_ID);
        }

        //Disable actions of these options that will only available in the future as a Administrator. Not showing to customers
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wish_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mName = (TextView) root.findViewById(R.id.tv_name);
        mDescription = (TextView) root.findViewById(R.id.tv_description);
        mCategory = (TextView) root.findViewById(R.id.tv_category);
        mPrize = (TextView) root.findViewById(R.id.tv_prize);

        mWishsDbHelper = new SqliteHelper(getActivity());
        fab_main = (FloatingActionButton) root.findViewById(R.id.fab);

        //Get Email details
        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(UserSessionManager.KEY_EMAIL);

        //Add button functionality and remove items from the list view
        btnFav = (Button) root.findViewById(R.id.favWishBtn);
        btnFav.setBackgroundResource(R.drawable.ic_favorite_red_24);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WishDetailFragment.DeleteWishTask().execute();
                Toast.makeText(getActivity(), "Se ha eliminado de tus favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        loadWishs();

        return root;
    }

    private void loadWishs() {
        new GetWishByIdTask().execute();
    }

    //Add functionality to Edit and Delete in the Action Bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                //showEditScreen();
                break;
            case R.id.action_delete:
                //new WishDetailFragment.DeleteWishTask().execute();
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
        if (requestCode == WishsFragment.REQUEST_UPDATE_DELETE_WISH) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showWish(Wish wish) {
        mCollapsingView.setTitle(wish.getWishName());
        Glide.with(getContext())
                .load(Uri.parse("file:///android_asset/" + wish.getWishAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mName.setText(wish.getWishName());
        mDescription.setText(wish.getWishDescription());
        mCategory.setText(wish.getWishCategory());
        mPrize.setText(wish.getWishPrize());
    }


     private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditProductActivity.class);
        intent.putExtra(WishsActivity.EXTRA_WISH_ID, mWishId);
        startActivityForResult(intent, WishsFragment.REQUEST_UPDATE_DELETE_WISH);
    }

    private void showWishScreen(boolean requery) {
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
                "Error al eliminar favorito", Toast.LENGTH_SHORT).show();
    }

    private class GetWishByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mWishsDbHelper.getWishById(mWishId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showWish(new Wish(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteWishTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mWishsDbHelper.deleteWish(mWishId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showWishScreen(integer > 0);
        }

    }
}