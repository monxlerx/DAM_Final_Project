package com.example.dam_project.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

public class Product {

    private String id;
    private String name;
    private String description;
    private String category;
    private String prize;
    private String avatarUri;

    public Product(String name, String description, String category, String prize, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.category = category;
        this.prize = prize;
        this.avatarUri = avatarUri;
    }

    public Product(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.NAME));
        description = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.DESCRIPTION));
        category = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.CATEGORY));
        prize = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.PRIZE));
        avatarUri = cursor.getString(cursor.getColumnIndex(ProductContract.ProductEntry.AVATAR_URI));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.ID, id);
        values.put(ProductContract.ProductEntry.NAME, name);
        values.put(ProductContract.ProductEntry.DESCRIPTION, description);
        values.put(ProductContract.ProductEntry.CATEGORY, category);
        values.put(ProductContract.ProductEntry.PRIZE, prize);
        values.put(ProductContract.ProductEntry.AVATAR_URI, avatarUri);
        return values;
    }


    public String getId(){return id;}

    public String getName(){return name;}

    public String getDescription(){return description;}

    public String getCategory(){return category;}

    public String getPrize(){return prize;}

    public String getAvatarUri(){return avatarUri;}

}
