package com.apps.rdjsmartapps.androidcrudapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemListAdapter adapter;
    public List<Item> mItemList;
    ListView listView1;
    DBConnect db;
    ImageUpload imgUpload;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView1 = (ListView) findViewById(R.id.listView1);
        mItemList = new ArrayList<>();
        imgUpload = new ImageUpload();



        //get database object
        db = new DBConnect(this);



        // Fetch movie list from database

        String queryString = "SELECT * FROM Movie";
        Cursor c = db.rawQuery(queryString);
        if(c.getCount()==0)
        {
            imgUpload.msg(this, "No records found");
        }

        StringBuffer buffer=new StringBuffer();

        while(c.moveToNext())
        {
            int movie_id = Integer.valueOf(c.getString(0));
            String movie_name = c.getString(1);
            int movie_rating = Integer.valueOf(c.getString(2));
            byte[] imageData = c.getBlob(3);
            Bitmap image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

            mItemList.add(new Item(movie_id, movie_name,movie_rating,image ));
        }



        // Create adapter class object
       adapter = new ItemListAdapter(this, mItemList);
       listView1.setAdapter(adapter);




       // set on item click listener

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Clicked on Item: " + view.getTag(),Toast.LENGTH_LONG ).show();

                String id = view.getTag().toString();
                int itemId = Integer.valueOf(id);

                Intent movieInfo = new Intent(MainActivity.this, MovieInfoActivity.class);
                movieInfo.putExtra("id", itemId);
                startActivity(movieInfo);
            }
        });




        // ADD fab action button on click listener
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(addIntent);
            }
        });



    }



}
