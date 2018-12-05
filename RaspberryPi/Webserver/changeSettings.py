from flask import Flask, render_template, request
import os
app = Flask(__name__)

sleep = ""
wake = ""
city = ""
brightness = ""


@app.route("/", methods=['GET','POST'])
def index():
	if request.method == 'GET':
		global sleep,wake,city,brightness

		sleep = request.args.get('sleep', sleep)
		wake = request.args.get('wake', wake)
		city = request.args.get('city', city)
		brightness = request.args.get('brightness', brightness)
		print(sleep + " " + wake + " " + city + " " + brightness)
		f = open("settings.txt", "w")
		f.write("sleep time: " + sleep + "\r\n")
		f.write("wake time: " + wake + "\r\n")
		f.write("city: " + city + "\r\n")
		f.write("brightness: " + brightness)
		f.close()
	return "data received, rebooting now"

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80, debug=True)
