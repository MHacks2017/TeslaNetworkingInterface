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
                URL endpoint = null;
                try {
                    endpoint = new URL("https://carhack2017.azurewebsites.net/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // Create connection
                HttpsURLConnection myConnection = null;
                try {
                    myConnection = (HttpsURLConnection) endpoint.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myConnection.setRequestProperty("User-Agent", "car-hack");

                try {
                    if (myConnection.getResponseCode() == 200) {
                        // Success
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) { // Loop through all keys
                            String key = jsonReader.nextName(); // Fetch the next key
                            if (key.equals("value")) { // Check if desired key
                                // Fetch the value as a String

                                final String value = jsonReader.nextString();

                                runOnUiThread(new Runnable() { // any UI changes must be inside here
                                    @Override
                                    public void run() {
                                        TextView newText = (TextView) findViewById(R.id.my_output);
                                        newText.setText(value);
                                    }
                                });
                                break; // Break out of the loop once desired value is found
                            } else {
                                jsonReader.skipValue(); // Skip values of other keys
                            }
                        }
                        // Further processing here

                        // close up
                        jsonReader.close();
                        myConnection.disconnect();
                    } else {
                        // Error handling code goes here
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
