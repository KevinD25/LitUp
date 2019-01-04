import serial

line = serial.Serial( port='/dev/ttyUSB0',baudrate=9600)

import requests, json, time, os

response = requests.get("http://192.168.0.218/LitUp_API/api/weather")
playtime = 4

def showGifs(gif):
	f = open("Webserver/settings.txt", "rt")
	brightness = f.readline()[12:]
	pstring = 'sudo ../Rgb-Matrix/rpi-rgb-led-matrix/utils/led-image-viewer -t ' + str(playtime) + ' ../Rgb-Matrix/rpi-rgb-led-matrix/utils/' + gif + ' --led-gpio-mapping="adafruit-hat-pwm" --led-pixel-mapper="Rotate:270" --led-brightness=100'
	os.system(pstring)
	print(pstring)
	print("show Gif")
data = response.json()
list = data["list"]
for f in list:
	print(f["weather"])

	print(f["weatherDetail"])

	temp = round((f["temp"] - 273), 1)
	temp = str(temp)[:str(temp).find(".")]
	if len(temp) < 3:
		if len(temp) == 1:
			temp = "  " + temp
		elif len(temp) == 2:
			temp = " " + temp
	print(temp)
	line.write(str(temp).encode("UTF-8"))
#	time.sleep(playtime)
	print(f["time"])
	weather = f["weather"]
	if weather == "Rain":
		showGifs("cloud-rain.gif")
		#time.sleep(playtime)
	elif weather == "Thunderstorm":
		showGifs("thunderstorm.gif")
	elif weather == "Drizzle":
		showGifs("cloud-rain.gif")
	elif weather == "Snow":
		showGifs("snow.gif")
	elif weather == "Atmosphere":
		showGifs("fog.gif")
	elif weather == "Clear":
		showGifs("sun.gif")
	elif weather == "Clouds":
		showGifs("clouds.gif")
