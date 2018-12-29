/*
   OPMERKING: arrays(leds, cath en getallen) moeten in elke methode waar ze gebruikt worden
   geinstantieerd worden anders geeft de display geen licht om een of andere onbekende reden.

   OPMERKING2: segment pins: A=11, B=7, C=4, D=2, E=1, F=10, G=5
               cathode pins: D1=12, D2=9, D3=8, D4=6
*/
#include <Arduino.h>
#include "SegmentDecoder.h"

SegmentDecoder::SegmentDecoder() {
  int _leds[7] = {2 /*A*/, 3 /*B*/, 4 /*C*/, 5 /*D*/, 6 /*E*/, 7 /*F*/, 8 /*G*/};
  int _cath[4] = {9 /*1*/, 10 /*2*/, 11 /*3*/, 12 /*4*/};
  for (int i = 0; i < 7; i++) {
    pinMode(_leds[i], OUTPUT);
  }
  for (int i = 0; i < 4; i++) {
    pinMode(_cath[i], OUTPUT);
  }
  for (int i = 0; i < 4; i++) {
    digitalWrite(_cath[i], HIGH);
  }

}

void SegmentDecoder::showDigit(int getal, int cathode) {
  int _leds[7] = {2 /*A*/, 3 /*B*/, 4 /*C*/, 5 /*D*/, 6 /*E*/, 7 /*F*/, 8 /*G*/};
  int _cath[4] = {9 /*1*/, 10 /*2*/, 11 /*3*/, 12 /*4*/};
  byte getallen[13][7] = {
    {1, 1, 1, 1, 1, 1, 0},
    {0, 1, 1, 0, 0, 0, 0},
    {1, 1, 0, 1, 1, 0, 1},
    {1, 1, 1, 1, 0, 0, 1},
    {0, 1, 1, 0, 0, 1, 1},
    {1, 0, 1, 1, 0, 1, 1},
    {1, 0, 1, 1, 1, 1, 1},
    {1, 1, 1, 0, 0, 0, 0},
    {1, 1, 1, 1, 1, 1, 1},
    {1, 1, 1, 1, 0, 1, 1},
    {1, 1, 0, 0, 0, 1, 1}, //°
    {0, 0, 0, 0, 0, 0, 1}  //-
  };
  int _cathode = cathode;

  for(int i = 0; i < 4; i++) digitalWrite(_cath[i], HIGH);

  if (_cathode <= 4 && _cathode > 0) {
    digitalWrite(_cath[_cathode - 1], LOW);

    for (int i = 0; i < 7; i++) {
      if (getallen[getal][i] == 1) {
        digitalWrite(_leds[i], HIGH);
      }
      else {
        digitalWrite(_leds[i], LOW);
      }
    }
  }
  delay(5);
}

void SegmentDecoder::show(char str[]) {
  if (str[0] == '-') showDigit(11, 1);
  if (str[1] == '-') showDigit(11, 2);
  else if (str[1] >= 48 && str[1] <= 57) showDigit(str[1] - 48, 2);
  if (str[2] >= 48 && str[2] <= 57) showDigit(str[2] - 48, 3);
  showDigit(10, 4);

}
