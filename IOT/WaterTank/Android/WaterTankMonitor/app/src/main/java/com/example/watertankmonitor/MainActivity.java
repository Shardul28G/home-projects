package com.example.watertankmonitor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button refresh = findViewById(R.id.refresh);
        Button stateON = findViewById(R.id.stateON);
        Button stateOFF = findViewById(R.id.stateOFF);

        // ...
        apiRequest ar =  new apiRequest(this);


        refresh.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ar.getWaterLevel();

                    }
                }
        );

        stateON.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ar.switchState("ON");

                    }
                }
        );

        stateOFF.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ar.switchState("OFF");

                    }
                }
        );






        
    }


    public class apiRequest {

        Context c = null;

        public apiRequest(Context cnt) {
            super();
            c = cnt;

        }

        public void getWaterLevel()
        {
            // Instantiate the RequestQueue.

            TextView largeWaterTankLevelValue =  findViewById(R.id.largeWaterTankLevelValue);
            TextView smallWaterTankLevelValue =  findViewById(R.id.smallWaterTankLevelValue);

            RequestQueue queue = Volley.newRequestQueue( c);
            String url ="http://192.168.0.108:8081/getWaterLevel";

// Request a string response from the provided URL.
            StringRequest getstringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            smallWaterTankLevelValue.setText( response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.toString());
                    largeWaterTankLevelValue.setText("Didnt work : " + error.toString());
                }
            });


// Add the request to the RequestQueue.
            queue.add(getstringRequest);
        }

        public void switchState(String state){

            try {
                RequestQueue requestQueue = Volley.newRequestQueue(c);
                String URL = "http://192.168.0.108:8081/setSwitch";

                final String requestBody = state;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                requestQueue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}