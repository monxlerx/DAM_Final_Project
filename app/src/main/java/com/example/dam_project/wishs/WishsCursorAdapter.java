package com.example.dam_project.wishs;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.dam_project.R;
import com.example.dam_project.data.model.ProductContract;
import com.example.dam_project.data.model.WishContract;

/**
 * Wishs Adapter
 */

public class WishsCursorAdapter extends CursorAdapter {

    public WishsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_wish, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // References UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);
        TextView descriptionText = (TextView) view.findViewById(R.id.tv_description);
        TextView categoryText = (TextView) view.findViewById(R.id.tv_category);
        TextView prizeText = (TextView) view.findViewById(R.id.tv_prize);

        // Get values
        String name = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_NAME));
        String avatarUri = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_AVATAR_URI));
        String description = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_DESCRIPTION));
        String category = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_CATEGORY));
        String prize = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_PRIZE));

        //Set Values
        //nameText.setText(name);
        //descriptionText.setText(description);
        //categoryText.setText(category);
        //prizeText.setText(prize);


        // Setup.
        nameText.setText(name);
        Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .asBitmap()
                .error(R.drawable.ic_account_circle)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });
    }

}
