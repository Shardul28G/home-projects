package com.example.watertankmonitor.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCallbackHandler implements MqttCallbackExtended {
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        Log.w("mqtt", serverURI);
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.w("Message ", message.toString());
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
