package com.example.omar.businessboomers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Omar on 3/1/2018.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Model> productDetails;
    ImageView imageView;
    TextView textView;
    View grid;

    public CustomAdapter(Context context, ArrayList<Model> productDetails){
        this.context = context;
        this.productDetails = productDetails;
    }

    @Override
    public int getCount() {
        return productDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return productDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = inflater.inflate(R.layout.custom_gridview, null);

        imageView = (ImageView)grid.findViewById(R.id.imageView);
        textView = (TextView)grid.findViewById(R.id.textView);

        Picasso.with(context)
                .load(productDetails.get(position).getImageUrl())
                .resize(300, 300)
                .into(imageView);

        textView.setText(productDetails.get(position).getProductName());

        return grid;
    }
}
