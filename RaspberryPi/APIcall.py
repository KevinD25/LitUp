import requests, json, time, os, multiprocessing

response = requests.get("http://84.197.169.91/LitUp_API/api/weather")

def showGifs(gif):
	os.system('./led-image-viewer -f /home/pi/apvalley-1819-litup/Rgb-Matrix/rpi-rgb-led-matrix/utils/' + gif + '--led-gpio-mapping="adafruit-hat-pwm" --led-pixel-mapper="Rotate:270" --led-brightness=60')

data = response.json()
list = data["list"]
file = open("weatherData.txt", "w")
for f in list:
	print(f["weather"])
	print(f["weatherDetail"])
	print(round((f["temp"] - 273), 1))
	print(f["time"])
	if f["weather"] == rain:
		p = multiprocessing.Process(target=showGifs, name="showGifs", args=("cloud-rain.gif",))
		p.start()
		time.sleep(4)
		p.terminate()
		p.join
file.close()