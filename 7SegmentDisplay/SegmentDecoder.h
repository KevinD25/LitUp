/* 
  segmentDecoder.h - library for decoding decimals to 7 segment output.
  created by Jorren Schepers, 16/11/2017
*/
#ifndef SegmentDecoder_h
#define SegmentDecoder_h

#include <Arduino.h>

class SegmentDecoder{
	public:
		SegmentDecoder();//int a, int b, int c, int d, int e, int f, int g, int c1, int c2, int c3, int c4);
    void showDigit(int getal, int cathode);
    void show(char str[]);
  private:
		//int getallen[10][7];	
    int _leds[7];
    int _cath[4];
};

#endif
