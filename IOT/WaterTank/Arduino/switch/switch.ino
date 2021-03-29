#include <ESP8266WiFi.h> //ESP8266 Library
#include <ESP8266HTTPClient.h> //ESP8266 Library
#include <string.h>
#include <ArduinoJson.h>

//const char* ssid = "TP-Link_EC18_EXT";
//const char* password = "27221020";

const char* ssid = "Galaxyf41";
const char* password = "hode8471";

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
}

void loop() {
  // put your main code here, to run repeatedly:
   if (WiFi.status() == WL_CONNECTED) { //If Wi-Fi connected successfully 
    HTTPClient http;  //start a HTTPClinet as http 
    //####DO NOT USE THE SAME API as below
    http.begin("http://192.168.0.108:8081/switch");  //Enter your API 
    
    int httpCode = http.GET(); //pass a get request                                                                  

    Serial.println(httpCode);
    if (httpCode > 0) { //Check the returning code
 
     payload = http.getString();   // Store the value on varibale Payload for debugging
     Serial.println(payload);   //Print the payload for debugging otherwise comment both lines
   
    delay(500);
     StaticJsonDocument<32> doc;

      DeserializationError error = deserializeJson(doc, http.getString());
      
      if (error) {
        Serial.print(F("deserializeJson() failed: "));
        Serial.println(error.f_str());
        return;
      }
      
      const char* switch_status = doc["status"];
      Serial.print("Status read from Json = ");
      Serial.println(switch_status);

      delay(50);
    
      if(strcmp(switch_status,"ON") == 0)
      {
         Serial.print("LED ON ");
        digitalWrite(LED,LOW);
        digitalWrite(relay,LOW);
      }
      if(strcmp(switch_status,"OFF") == 0)
      {
         Serial.print(":ED OFF");
        digitalWrite(LED,HIGH);
        digitalWrite(relay,HIGH);
      }
        delay(5000);
    }
   }
}
