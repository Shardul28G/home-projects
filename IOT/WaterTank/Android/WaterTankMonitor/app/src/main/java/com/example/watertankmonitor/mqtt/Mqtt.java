package com.example.watertankmonitor.mqtt;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

public class Mqtt {


    MqttAndroidClient client;

    private String mqtt_switch_topic = "switch";
    private String mqtt_waterlvlsensor_topic = "waterlvlsensor";
    private String password = "mqttuser";
    private String username = "mqttuser";
    private String mqtt_host = "tcp://192.168.0.115:1883";

    Context c = null;

    public Mqtt(Context cnt) {
        super();
        c = cnt;

        System.out.println("mqtt constructor");
        MemoryPersistence mPer = new MemoryPersistence();
        String clientId = UUID.randomUUID().toString();
        String brokerUrl = mqtt_host;
        client = new MqttAndroidClient(c, brokerUrl, clientId, mPer);
        client.setCallback(new MqttCallbackHandler());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        try {
            client.connect(options, null, new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken mqttToken) {
                    System.out.println("Client connected");
                    System.out.println("Topics=" + mqttToken.getTopics());

                    MqttMessage message = new MqttMessage("Android app connected".getBytes());
                    message.setQos(2);
                    message.setRetained(false);

                    try {
                        client.publish(mqtt_waterlvlsensor_topic, message);
                        System.out.println("Message published");

                        client.subscribe(mqtt_waterlvlsensor_topic , 2, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                System.out.println( "Successfully subscribed to topic.");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                System.out.println( "Failed to subscribed to topic.");
                            }



                        });

                    } catch (MqttPersistenceException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("mqtt error 2 " + e.getMessage());

                    } catch (MqttException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("mqtt error 3 " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    System.out.println("Failed");
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("mqtt error 3 " + e.getMessage());
        }
    }

    public void publishMessage(String topic,String mess) throws MqttException {
        MqttMessage message = new MqttMessage(mess.getBytes());
        message.setQos(2);
        message.setRetained(false);
        client.publish(topic,message);
    }

}
