import requests, json, time, os,serial

response = requests.get("http://84.197.169.91/LitUp_API/api/weather")
playtime = 4
line = serial.Serial( port='/dev/ttyUSB0',baudrate=9600)

def showGifs(gif):
	f = open("settings.txt", "rt")
	brightness = f.readline()[12:]
	pstring = '../Rgb-Matrix/rpi-rgb-led-matrix/utils/led-image-viewer -t ' + str(playtime) + ' ../Rgb-Matrix/rpi-rgb-led-matrix/utils/' + gif + ' --led-gpio-mapping="adafruit-hat-pwm" --led-pixel-mapper="Rotate:270" --led-brightness=' + str(brightness)
	os.system(pstring)
data = response.json()
list = data["list"]
for f in list:
	print(f["weather"])

	print(f["weatherDetail"])

	temp = round((f["temp"] - 273), 1)
	print(round((f["temp"] - 273), 1))
	line.write(temp.encode("UTF-8"))
	time.sleep(playtime)
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
