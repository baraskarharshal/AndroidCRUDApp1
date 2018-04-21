package com.apps.rdjsmartapps.androidcrudapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MovieInfoActivity extends AppCompatActivity {

    private TextView movieName;
    private RatingBar ratingBar;
    private ImageView movieImage;
    private Button update, delete;
    String id, movie_name;
    int movie_rating, movie_id;
    DBConnect db;
    ImageUpload imgUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        // Set back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //get database object
        db = new DBConnect(this);



        // Initialize view objects
        movieName = (TextView) findViewById(R.id.movieName);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        movieImage = (ImageView)  findViewById(R.id.movieImage);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        imgUpload = new ImageUpload();



        // Fetch current item values

        Bundle inBundle = getIntent().getExtras();
        id = inBundle.get("id").toString();

        String queryString = "SELECT * FROM Movie WHERE MovieId ='"+id+"'";
        Cursor c = db.rawQuery( queryString);

        if(c.getCount()==0)
        {
            imgUpload.msg(this, "No records found");
        }

        StringBuffer buffer=new StringBuffer();
        while(c.moveToNext())
        {
            movie_id = Integer.valueOf(c.getString(0));
            movie_name = c.getString(1);
            movie_rating = Integer.valueOf(c.getString(2));
            byte[] imageData = c.getBlob(3);
            Bitmap image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            movieImage.setImageBitmap(image);
        }




        // Set values in text view

        movieName.setText(movie_name);
        ratingBar.setRating(Float.valueOf(movie_rating));





        //  alert dialogue for delete button

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete confirmation!");
        builder.setMessage("You are about to delete the record. Do you really want to proceed ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Delete movie item from database
                db.execSQL("DELETE FROM Movie WHERE MovieId ='"+id+"'");
                Toast.makeText(getApplicationContext(), "Movie deleted successfully!", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(MovieInfoActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've changed your mind to delete record", Toast.LENGTH_SHORT).show();
            }
        });




        // handle delete button click

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.show(); // call alert dialogue
            }
        });



        // handle update button click

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent(getApplicationContext(), UpdateActivity.class);
                updateIntent.putExtra("id", movie_id);
                updateIntent.putExtra("mov_name", movie_name);
                updateIntent.putExtra("mov_rating", movie_rating);
                startActivity(updateIntent);
            }
        });


    }


}
