package com.example.youtube;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> titleList = new ArrayList<>();
        final ArrayList<String> videoIdList = new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NetworkUtil util = new NetworkUtil();

        String url = "https://www.googleapis.com/youtube/v3/search";
        String param = "?part=snippet&key=AIzaSyA05CJLU0WyOVh6ISGTKIEx9DflQTCij-U&maxResults=10&q=";
        url = url + param + "ppap";



        String result = util.get(url);
        JSONObject obj = null;
        try {
            obj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray items = null;
        try {
            items = obj.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        for(int i = 0; i<items.length();i++) {
            JSONObject item = null;
            try {
                item = items.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject id = null;
            try {
                id = item.getJSONObject("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String videoId = null;
            try {
                videoId = id.getString("videoId");
                videoIdList.add(videoId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(videoId);

            String title = null;
            try {
                title = item.getJSONObject("snippet").getString("title");
                titleList.add(title);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListView listView = findViewById(R.id.list_view);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(
                            this, android.R.layout.simple_list_item_1,
                            titleList
                    );
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String videoId = videoIdList.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoId));
                    startActivity(intent);
                }
            });


            System.out.println(title);
        }



    }
}
