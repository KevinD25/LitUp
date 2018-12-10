import requests, json, time, os, multiprocessing

response = requests.get("http://84.197.169.91/LitUp_API/api/weather")

def showGifs(gif):
	pstring = '../Rgb-Matrix/rpi-rgb-led-matrix/utils/led-image-viewer ../Rgb-Matrix/rpi-rgb-led-matrix/utils/' + gif + ' --led-gpio-mapping="adafruit-hat-pwm" --led-pixel-mapper="Rotate:270" --led-brightness=60'
	os.system(pstring)
	time.sleep(3)
	for line in os.popen("ps ax | grep " + pstring + " | grep -v grep"):
		fields = line.split()
		pid = fields[0]
		os.kill(int(pid), signal.SIGKILL)
data = response.json()
list = data["list"]
for f in list:
	print(f["weather"])
	print(f["weatherDetail"])
	print(round((f["temp"] - 273), 1))
	print(f["time"])
	if f["weather"] == "Rain":
		showGifs("cloud-rain.gif")