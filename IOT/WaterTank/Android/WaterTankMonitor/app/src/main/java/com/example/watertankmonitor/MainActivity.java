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
import com.example.watertankmonitor.http.apiRequest;
import com.example.watertankmonitor.mqtt.Mqtt;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String mqtt_switch_topic = "switch";
        String mqtt_waterlvlsensor_topic = "waterlvlsensor";

        Button refresh = findViewById(R.id.refresh);
        Button stateON = findViewById(R.id.stateON);
        Button stateOFF = findViewById(R.id.stateOFF);

        // ...
        apiRequest ar = new apiRequest(this);

        Mqtt mqtt = new Mqtt(this);

        refresh.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            mqtt.publishMessage(mqtt_waterlvlsensor_topic, "Refresh");
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );

        stateON.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            mqtt.publishMessage(mqtt_switch_topic, "ON");
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );

        stateOFF.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            mqtt.publishMessage(mqtt_switch_topic, "OFF");
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );


    }



}