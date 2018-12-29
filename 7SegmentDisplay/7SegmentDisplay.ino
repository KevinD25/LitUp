#include "SegmentDecoder.h"

SegmentDecoder display;

char temperature[3] = {' ', ' ', ' '};

void setup() {
  Serial.begin(9600);
  Serial.println("Setup complete");
}

void loop() {
  if(Serial.available()){
    String in = Serial.readString();
    Serial.println(in);
    in.toCharArray(temperature, 4);
    for(int i = 0; i < 3; i++) Serial.print(temperature[i]);
  }
  display.show(temperature);
}
