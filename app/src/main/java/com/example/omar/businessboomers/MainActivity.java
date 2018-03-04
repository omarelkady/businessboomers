package com.example.omar.businessboomers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public ArrayList<Model> allProducts = new ArrayList<>();
    GridView gridView;
    String access_token;
    String name, imageUrl;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.MainGridView);
        gridViewClicked();

        new getToken().execute();
    }

    public void gridViewClicked(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Model item = (Model) adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, ProductDetails.class);
                intent.putExtra("name", item.getProductName());
                intent.putExtra("imageUrl", item.getImageUrl());
                startActivity(intent);
            }
        });
    }
   private class getToken extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.getWindow().setBackgroundDrawable(new
                    ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.my_progress);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"client_id\": \"46v3htox13uookw4o8c8gs44oggocgos88804oggggkwss8o04\" , \"client_secret\": \"4jm5k8h9vxmokkssw4wkcsgs0cws0kow0w48s8gc80cwc404g0\" , \"grant_type\": \"password\" , \"username\": \"api@example.com\" , \"password\": \"api\"}");
            final Request request = new Request.Builder()
                    .url("http://office.businessboomers.net:666/dresscode/web/app_dev.php/api/oauth/v2/token")
                    .addHeader("content-type", "application/x-www-form-urlencode")
                    .post(body)
                    .build();
            Response response = null;

            try {
                response = client.newCall(request).execute();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        } else {
                            String access = response.body().string();
                            Log.i("result", "result: " + access);
                            try {
                                JSONObject obj = new JSONObject(access);
                                access_token = obj.getString("access_token");
                                Log.i("access_token", "token: " + access_token);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        OkHttpClient client = new OkHttpClient();

                        MediaType mediaType = MediaType.parse("application/json");
                        Request request = new Request.Builder()
                                .url("http://office.businessboomers.net:666/dresscode/web/app_dev.php/api/v1/products/")
                                .addHeader("Authorization" , "Bearer " + access_token)
                                .build();
                        try {
                            Response response2 = client.newCall(request).execute();
                            String products = response2.body().string();

                            JSONObject jsonObject = new JSONObject(products).getJSONObject("_embedded");
                            JSONArray jsonArray = jsonObject.getJSONArray("items");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject finalJsonObject = jsonArray.getJSONObject(i);
                                name = finalJsonObject.getString("name");

                                JSONArray finalJsonArray = finalJsonObject.getJSONArray("images");
                                JSONObject jsonObject1 = finalJsonArray.getJSONObject(0);
                                imageUrl = jsonObject1.getString("path");

                                allProducts.add(new Model(0, name, "http://office.businessboomers.net:666/dresscode/web/media/image/" + imageUrl, 0));
                            }

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pDialog.dismiss();
                                    adapter = new CustomAdapter(getApplicationContext(), allProducts);
                                    gridView.setAdapter(adapter);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

       @Override
       protected void onPostExecute(Void result) {
           super.onPostExecute(result);
       }
   }
}