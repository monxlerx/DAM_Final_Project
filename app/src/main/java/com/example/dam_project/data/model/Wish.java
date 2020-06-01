package com.example.dam_project.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

public class Wish {

    private String wish_id;
    private String wish_name;
    private String wish_description;
    private String wish_category;
    private String wish_prize;
    private String wish_avatarUri;
    private String wish_email;

    public Wish(String wish_name, String wish_description, String wish_category, String wish_prize, String wish_avatarUri, String wish_email) {
        this.wish_id = UUID.randomUUID().toString();
        this.wish_name = wish_name;
        this.wish_description = wish_description;
        this.wish_category = wish_category;
        this.wish_prize = wish_prize;
        this.wish_avatarUri = wish_avatarUri;
        this.wish_email = wish_email;
    }

    public Wish(Cursor cursor) {
        wish_id = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_ID));
        wish_name = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_NAME));
        wish_description = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_DESCRIPTION));
        wish_category = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_CATEGORY));
        wish_prize = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_PRIZE));
        wish_avatarUri = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_AVATAR_URI));
        wish_email = cursor.getString(cursor.getColumnIndex(WishContract.WishEntry.WISH_EMAIL));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(WishContract.WishEntry.WISH_ID, wish_id);
        values.put(WishContract.WishEntry.WISH_NAME, wish_name);
        values.put(WishContract.WishEntry.WISH_DESCRIPTION, wish_description);
        values.put(WishContract.WishEntry.WISH_CATEGORY, wish_category);
        values.put(WishContract.WishEntry.WISH_PRIZE, wish_prize);
        values.put(WishContract.WishEntry.WISH_AVATAR_URI, wish_avatarUri);
        values.put(WishContract.WishEntry.WISH_EMAIL, wish_email);
        return values;
    }


    public String getWishId(){return wish_id;}

    public String getWishName(){return wish_name;}

    public String getWishDescription(){return wish_description;}

    public String getWishCategory(){return wish_category;}

    public String getWishPrize(){return wish_prize;}

    public String getWishAvatarUri(){return wish_avatarUri;}

    public String getWishEmail(){return wish_email;}

}
