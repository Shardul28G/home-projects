#include <ArduinoJson.h>
#include <ESP8266WiFi.h> //ESP8266 Library
#include <ESP8266HTTPClient.h>

const int pingPin = 14;
const int echoPin_large = 12;
const  int echoPin_small = 13;

const char* ssid = "TP-Link_EC18";
const char* password = "27221020";

String payload;

void setup() {
  // put your setup code here, to run once:
  pinMode(pingPin, OUTPUT);
  pinMode(echoPin_large, INPUT);
  pinMode(echoPin_small, INPUT);
  Serial.begin(115200);
   WiFi.begin(ssid,password);
   while( WiFi.status() != WL_CONNECTED){
    delay(1000);
    Serial.println("Connecting..");
    Serial.println(WiFi.status());
  }

}

void loop() {
  // put your main code here, to run repeatedly:
  long duration_large, duration_small,  cm_large, cm_small;
  if (WiFi.status() == WL_CONNECTED) { //If Wi-Fi connected successfully 
     
      
      
    
      digitalWrite(pingPin, LOW);
      delayMicroseconds(2);
      digitalWrite(pingPin, HIGH);
      delayMicroseconds(10);
      duration_large = pulseIn(echoPin_large, HIGH);
      duration_small = pulseIn(echoPin_small, HIGH);
      cm_large = duration_large/29/2;
      cm_small = duration_small/29/2;
      Serial.print("cm large : ");
      Serial.print(cm_large);
      Serial.println();
      Serial.print("cm small : ");
      Serial.print(cm_small);
      Serial.println();

      delay(50);
      
      StaticJsonDocument<200> doc;
      doc["large_tank_level"] = cm_large;
      doc["small_tank_level"] = cm_small;
      String json;
      serializeJson(doc, json);


      HTTPClient http;
      http.begin("http://192.168.0.108:8081/setWaterLevel");
      http.addHeader("Content-Type", "application/json");
      int httpResponseCode = http.POST(json);

      if (httpResponseCode > 0) { //Check the returning code
 
        payload = http.getString();   // Store the value on varibale Payload for debugging
        Serial.println(payload); 
      }
    
      
  }

  
  delay(5000);
}
