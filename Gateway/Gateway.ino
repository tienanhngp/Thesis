


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
#include "FirebaseESP8266.h"
#include <ESP8266WiFi.h>
#include <MQ135.h>

// Set these to run example.
#define FIREBASE_HOST "test1-e9b0a-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "cHqyetnoj6tsa3DVwjLO9TPhWoxzDYrb6fDj35q3"
#define WIFI_SSID "iphone"
#define WIFI_PASSWORD "tienanh123"

FirebaseData firebaseData;
FirebaseJson json;

MQ135 airSensor = MQ135(A0);
void setup() {
  Serial.begin(9600);

  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
  } // wait for connect to wifi
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
  
 
  
}



void loop() {

  
  float ppm = airSensor.getPPM();
  
  
  if(Serial.available()){
  
  String receiving = "";
  receiving = Serial.readString();
  
  int receivingTranform = receiving.toInt();
  Serial.println(receivingTranform);

  
  int Light = receivingTranform % 10;
  int SoilMos = receivingTranform / 1000;
  int Temp = (receivingTranform / 10) % 100;
  int AirQual = checkAir(ppm);
 
  Firebase.set(firebaseData, "/Light", Light);
  Firebase.set(firebaseData, "/SoilMos", SoilMos);
  Firebase.set(firebaseData, "/Temp", Temp);
  Firebase.set(firebaseData, "/airQuality", AirQual);
  
  }
}
int checkAir(float ppm){
  if(ppm < 100) 
    return 1;
  else if(ppm <= 150) 
    return 2;
  return 3;
}
  
