
//
// Copyright 2015 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

// FirebaseDemo_ESP8266 is a sample that demo the different functions
// of the FirebaseArduino API.

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <ArduinoJson.h>
// Set these to run example.
#define FIREBASE_HOST "test1-e9b0a-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "cHqyetnoj6tsa3DVwjLO9TPhWoxzDYrb6fDj35q3"
#define WIFI_SSID "TP-Link_06C0"
#define WIFI_PASSWORD "42855507"

StaticJsonBuffer<256> jb;
JsonObject& DHTSensor = jb.createObject();
JsonObject& DHTSensorLog = jb.createObject();


void setup() {
  Serial.begin(9600);

  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.waitForConnectResult() != WL_CONNECTED) {
    delay(500);
  } // wait for connect to wifi
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  
  while(!Serial){
    
  }// wait for connect to Serial
  
}



void loop() {
  
  if(Serial.available()){
  String receiving = "";
  receiving = Serial.readString();
  
  int receivingTranform = receiving.toInt();
  Serial.println(receivingTranform);
  int Tem = receivingTranform / 100;
  int Hum = receivingTranform % 100;
  DHTSensor["Tem"] = int(Tem);
  DHTSensor["Hum"] = int(Hum);
  Firebase.set("/DHTSensor",DHTSensor);

  DHTSensorLog["Tem"] = int(Tem);
  DHTSensorLog["Hum"] = int(Hum);
  DHTSensorLog["Time"] = millis();
  Firebase.push("/DHTSensorLog", DHTSensorLog);
  
  
  }
}
