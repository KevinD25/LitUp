#!/usr/bin/python
import serial
line = serial.Serial( port='/dev/ttyUSB0',baudrate=9600)
a = input()
line.write(a.encode("UTF-8"))
