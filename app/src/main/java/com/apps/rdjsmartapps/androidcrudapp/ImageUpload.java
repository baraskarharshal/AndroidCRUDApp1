package com.apps.rdjsmartapps.androidcrudapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by Harshal on 4/4/2018.
 */

public class ImageUpload {

    Bitmap selectedImageBitmap;
    private static final int SELECT_PHOTO = 100;
    DBConnect db;

    public ImageUpload() {
        // do something
    }



    // bitmap to byte array converter

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    // get bitmap image
    // This method is not necessory here. It is written to demonstrate how to fetch image from database
    // and display

    public Bitmap getImage(DBConnect db, String i){

        String queryString = "SELECT Image FROM Movie WHERE MovieId= (SELECT max(MovieId) FROM Movie)";
        Cursor cur = db.rawQuery(queryString);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(0);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null ;
    }


    // Display message function

    public void msg(Context context, String str)
    {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


}// end of class
