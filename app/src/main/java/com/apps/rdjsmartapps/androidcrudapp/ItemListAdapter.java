package com.apps.rdjsmartapps.androidcrudapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Harshal on 4/1/2018.
 */

public class ItemListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Item> mItemList;

    // Constructor


    public ItemListAdapter(Context mContext, List<Item> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.list_item, null);
        ImageView movieImage = (ImageView) v.findViewById(R.id.movieImage);
        TextView movieName = (TextView) v.findViewById(R.id.movieName);
        TextView rating = (TextView) v.findViewById(R.id.rating);

        // set values

        movieImage.setImageBitmap(mItemList.get(position).getImage());
        movieName.setText(mItemList.get(position).getMovieName());
        rating.setText(mItemList.get(position).getRating() + "");

        // save item id to tag

        v.setTag(mItemList.get(position).getId());

        return v;
    }
}
