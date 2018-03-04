package com.example.omar.businessboomers;

import android.content.Intent;
import android.media.AudioFocusRequest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Omar on 3/2/2018.
 */

public class ProductDetails extends AppCompatActivity {

    ImageView imageView_details;
    TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        imageView_details = (ImageView)findViewById(R.id.imageView_details);
        tv_name = (TextView)findViewById(R.id.tv_name);

        getData();

    }
    public void getData(){

        String name = "";
        String image = "";

        Intent intent = getIntent();
        if (null != intent) {
            name = intent.getStringExtra("name");
            image = intent.getStringExtra("imageUrl");

            tv_name.setText(name);
            Picasso.with(getApplicationContext()).load(image).into(imageView_details);

        }


    }
}