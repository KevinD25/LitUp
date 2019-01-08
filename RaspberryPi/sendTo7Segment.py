#!/usr/bin/python
import serial
import sys
line = serial.Serial( port='/dev/ttyUSB0',baudrate=9600)
#a = input()
print(str(sys.argv[1]))
a = str(sys.argv[1])
print(line.readline())
print(line.write(a.encode("UTF-8")))
#print(line.readline())
