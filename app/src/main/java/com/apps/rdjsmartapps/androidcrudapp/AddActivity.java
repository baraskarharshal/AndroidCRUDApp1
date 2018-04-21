package com.apps.rdjsmartapps.androidcrudapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.apps.rdjsmartapps.androidcrudapp.ImageUpload.getBitmapAsByteArray;
import static java.sql.Types.VARCHAR;

public class AddActivity extends AppCompatActivity {

    DBConnect db;
    ImageUpload imgUpload;
    EditText movieName, rating;
    ImageView movieImage;
    Button addMovie, uploadImage;
    private static final int SELECT_PHOTO = 100;
    Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Set back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //get database object
        db = new DBConnect(this);


        // Initializze objects

        movieName = (EditText) findViewById(R.id.movieName);
        rating = (EditText) findViewById(R.id.rating);
        addMovie = (Button) findViewById(R.id.addMovie);
        uploadImage = (Button) findViewById(R.id.uploadImage);
        movieImage = (ImageView) findViewById(R.id.movieImage);
        imgUpload = new ImageUpload();




        // Add button on click listener
        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Validate input values

                String movie_name = movieName.getText().toString();
                String movie_rating_str = rating.getText().toString();

                if(TextUtils.isEmpty(movie_name)){
                    imgUpload.msg(getApplicationContext(), "Movie name is empty!");
                }
                else if(TextUtils.isEmpty(movie_rating_str)){
                    imgUpload.msg(getApplicationContext(), "Invalid Rating!");
                }
                else{
                    int movie_rating = Integer.valueOf(movie_rating_str);

                    insertMovie(movie_name, movie_rating, selectedImageBitmap);
                    imgUpload.msg(getApplicationContext(), "Movie item added successfully!");

                    //Intent mainIntent = new Intent(AddActivity.this, MainActivity.class);
                    //startActivity(mainIntent);
                }

            }
        });




        // upload Image on click listener

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });



    }

    // Image upload onActivity result method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        Uri selectedImage = imageReturnedIntent.getData();
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        selectedImageBitmap = BitmapFactory.decodeStream(imageStream);

                        // display selected image
                        movieImage.setImageBitmap(selectedImageBitmap);

                    }
                    catch (FileNotFoundException e){
                        // do stuff here..
                        imgUpload.msg(this, "File not found!");
                    }
                    catch(Exception e){
                        // do stuff here..
                        imgUpload.msg(this, "File not found!");
                    }
                }
        }
    }

    // Insert method

    public void insertMovie(String movie_name, int  movie_rating, Bitmap img ) {

        byte[] data = getBitmapAsByteArray(img); // this is a function

        ContentValues initialValues = new ContentValues();

        initialValues.put("MovieName", movie_name);
        initialValues.put("Image",data);
        initialValues.put("MovieRating",movie_rating);
        Long result =  db.insert(DBHelper.TABLE_NAME, initialValues);

        Bitmap movie_image = imgUpload.getImage(db, movie_name);

        movieImage.setImageBitmap(movie_image);
        imgUpload.msg(getApplicationContext(), "Movie image stored successfully!");

    }



}// End of class
