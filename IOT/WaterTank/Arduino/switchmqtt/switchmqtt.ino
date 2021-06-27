#include <ESP8266WiFi.h> //ESP8266 Library
#include <PubSubClient.h> //ESP8266 Library
#include <string.h>


const char* ssid = "TP-Link_EC18";
const char* password = "27221020";

//const char* ssid = "Galaxyf41";
//const char* password = "hode8471";


const char *mqtt_broker = "192.168.0.115";
const char *topic = "test";
const char *mqtt_username = "mqttuser";
const char *mqtt_password = "mqttuser";
const int mqtt_port = 1883;

WiFiClient espClient;
PubSubClient client(espClient);


String payload;

int LED = 2;
int relay = 4;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  WiFi.begin(ssid,password);
  pinMode(LED,OUTPUT);
  pinMode(relay,OUTPUT);
  digitalWrite(LED,HIGH);
        digitalWrite(relay,HIGH);
 

  while( WiFi.status() != WL_CONNECTED){
    delay(1000);
    Serial.println("Connecting..");
    Serial.println(WiFi.status());
  }

  client.setServer(mqtt_broker,mqtt_port);
  client.setCallback(callback);

  while (!client.connected()) {
   String client_id = "esp8266-client-";
   client_id += String(WiFi.macAddress());
   Serial.printf("The client %s connects to the public mqtt broker\n", client_id.c_str());
   if (client.connect(client_id.c_str(), mqtt_username, mqtt_password)) {
   } else {
       Serial.print("failed with state ");
       Serial.print(client.state());
       delay(2000);
   }
  }
  client.publish(topic, "1:esp:50:N");
  client.subscribe(topic);
}

void callback(char *topic, byte *payload, unsigned int length) {
 Serial.print("Message arrived in topic: ");
 Serial.println(topic);
 Serial.print("Message:");
 for (int i = 0; i < length; i++) {
     Serial.print((char) payload[i]);
 }
 Serial.println();
 Serial.println("-----------------------");
}

void loop() {
  // put your main code here, to run repeatedly:
   if (WiFi.status() == WL_CONNECTED) { //If Wi-Fi connected successfully 

        client.loop();
        delay(50);
    
   }
}
