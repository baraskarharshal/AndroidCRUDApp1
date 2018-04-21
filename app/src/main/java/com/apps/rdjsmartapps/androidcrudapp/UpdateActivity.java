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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateActivity extends AppCompatActivity {

    DBConnect db;
    ImageUpload imgUpload;
    EditText movieName, rating;
    Button updateMovie, uploadImage;
    ImageView movieImage;
    private static final int SELECT_PHOTO = 100;
    Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Set back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // Fetch current item values

        Bundle inBundle = getIntent().getExtras();
        final int id = Integer.valueOf(inBundle.get("id").toString());
        String mov_name = inBundle.get("mov_name").toString();
        int mov_rating = Integer.valueOf(inBundle.get("mov_rating").toString());



        // Initialize objects
        movieName = (EditText) findViewById(R.id.movieName);
        rating = (EditText) findViewById(R.id.rating);
        updateMovie = (Button) findViewById(R.id.updateMovie);
        uploadImage = (Button) findViewById(R.id.uploadImage);
        movieImage = (ImageView) findViewById(R.id.movieImage);
        imgUpload = new ImageUpload();


        //get database object
        db = new DBConnect(this);


        // Fetch current item values
        String queryString = "SELECT * FROM Movie WHERE MovieId ='"+id+"'";
        Cursor c = db.rawQuery(queryString);

        if(c.getCount()==0)
        {
            imgUpload.msg(this, "No records found");
        }

        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            byte[] imageData = c.getBlob(3);
            Bitmap image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            selectedImageBitmap = image;
            movieImage.setImageBitmap(image);
        }



        // Set fetched values in views

        movieName.setText(mov_name);
        rating.setText(mov_rating+"");




        // upload Image on click listener

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });




        // Update button onclick listener

        updateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Validate values

                String movie_name = movieName.getText().toString().trim();
                String movie_rating_str = rating.getText().toString().trim();


                if(TextUtils.isEmpty(movie_name)){
                    imgUpload.msg(getApplicationContext(), "Movie name is empty!");
                }
                else if(TextUtils.isEmpty(movie_rating_str)){
                    imgUpload.msg(getApplicationContext(), "Invalid Rating!");
                }
                else{
                    int movie_rating = Integer.valueOf(movie_rating_str);
                    byte[] imageData = imgUpload.getBitmapAsByteArray(selectedImageBitmap);

                    //db.execSQL("UPDATE Movie SET MovieName = '"+movie_name+"', MovieRating = '"+movie_rating+"', Image = '"+imageData+"' WHERE MovieId ='"+id+"'");
                    ContentValues initialValues = new ContentValues();

                    initialValues.put("MovieName", movie_name);
                    initialValues.put("Image",imageData);
                    initialValues.put("MovieRating",movie_rating);
                    int result =  db.update("Movie", initialValues, "MovieId=" + id);
                    imgUpload.msg(getApplicationContext(), "Movie item updated successfully!");

                    //Intent mainIntent = new Intent(UpdateActivity.this, MainActivity.class);
                    //startActivity(mainIntent);
                }

            }
        });


    }// end of class




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


}
