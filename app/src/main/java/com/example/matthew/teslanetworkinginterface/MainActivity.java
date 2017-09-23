package com.example.matthew.teslanetworkinginterface;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.widget.TextView;

import com.loopj.android.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here
                // Create URL
                URL githubEndpoint = null;
                try {
                    githubEndpoint = new URL("https://api.github.com/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // Create connection
                HttpsURLConnection myConnection = null;
                try {
                    myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
                myConnection.setRequestProperty("Accept", "application/vnd.github.v3+json");
                myConnection.setRequestProperty("Contact-Me", "hathibelagal@example.com");

                try {
                    if (myConnection.getResponseCode() == 200) {
                        // Success
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key
                            if (key.equals("organization_url")) { // Check if desired key
                                // Fetch the value as a String
                                String value = jsonReader.nextString();

                                TextView newtext = (TextView) findViewById(R.id.my_output);
                                newtext.setText(value);
                                // Do something with the value
                                // ...

                                break; // Break out of the loop
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                        jsonReader.close();
                        myConnection.disconnect();

                        // Further processing here
                    } else {
                        // Error handling code goes here
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


//
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get("https://www.google.com", new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                // called when response HTTP status is "200 OK"
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//            }
//        });
    }
}
