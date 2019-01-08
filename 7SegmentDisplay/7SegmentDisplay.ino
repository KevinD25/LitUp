#include "SegmentDecoder.h"

SegmentDecoder display;

char temperature[3] = {'l', ' ', ' '};
long prevTime = 0;
int pos = 3;

void setup() {
  Serial.begin(9600);
  Serial.println("Setup complete");
}

void loop() {
  if (Serial.available()) {
    String in = Serial.readString();
    Serial.println(in);
    in.toCharArray(temperature, 4);
    for (int i = 0; i < 3; i++) Serial.print(temperature[i]);
  }
  if ((temperature[0] == ' ' && temperature[1] == ' ' && temperature[2] == ' '))
    for ( int i = 1; i < 4; i++)
      display.showDigit(16, i);
  else if (temperature[0] == 'l') {
    if (millis() - prevTime >= 400) {
      if (pos > 0) pos--;
      else pos = 3;
      Serial.println(pos);
      display.showDigit(12 + pos, 4);
      prevTime = millis();
    }
  }
  else display.show(temperature);
}
