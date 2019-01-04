import requests, json, time, os

response = requests.get("http://172.16.161.184/LitUp_API/api/weather")
playtime = 4

def showGifs(gif):
	f = open("Webserver/settings.txt", "rt")
	brightness = f.readline()[12:]
	pstring = 'sudo ../Rgb-Matrix/rpi-rgb-led-matrix/utils/led-image-viewer -t ' + str(playtime) + ' ../Rgb-Matrix/rpi-rgb-led-matrix/utils/' + gif + ' --led-gpio-mapping="adafruit-hat-pwm" --led-pixel-mapper="Rotate:270" --led-brightness=100'
	os.system(pstring)
	print(pstring)
data = response.json()
list = data["list"]
for f in list:
	print(f["weather"])
	print(f["weatherDetail"])
	print(round((f["temp"] - 273), 1))
	print(f["time"])
	if f["weather"] == "Clouds":
		showGifs("cloud-rain.gif")
		#time.sleep(playtime)
