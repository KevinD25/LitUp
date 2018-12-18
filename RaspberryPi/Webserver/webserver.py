from flask import Flask, render_template, request
import os
app = Flask(__name__)

ssid = ""
passwd = ""
timezone = ""
city = ""
temp = ""
sleep = ""
wake = ""
brightness = ""
screensaver = ""

@app.route("/setup", methods=['GET','POST'])
def setup():
	if request.method == 'GET':
		global ssid,passwd,continent,city,temp,timezone

		ssid = request.args.get('ssid', ssid)
		passwd = request.args.get('passwd', passwd)
		timezone = request.args.get('timezone', timezone)
		city = request.args.get('city', city)
		temp = request.args.get('temp', temp)
		print(ssid + " " + passwd + " " + timezone + " " + city + " " + temp)

		s = open("settings.txt", "w")
		s.write("sleep time: 22:00\r\n")
                s.write("wake time: 08:00\r\n")
                s.write("city: " + city + "\r\n")
                s.write("brightness: 50")
                s.close()

#		f = open("/etc/wpa_supplicant/wpa_supplicant.conf", "a")
#		if not passwd:
#			f.write("network={ \r\n")
#			f.write('\tssid=\"' + ssid + '\"\r\n')
#			f.write('\tkey_mgmt=NONE\r\n')
#			f.write("}\r\n")
#			f.close()
#		else:
#			f.write("network={ \r\n")
#			f.write('\tssid=\"' + ssid + '\"\r\n')
#			f.write('\tpsk=\"' + passwd + '\"\r\n')
#			f.write('\tkey_mgmt=WPA-PSK\r\n')
#			f.write("}\r\n")
#			f.close()
#		os.system('(cp /usr/share/zoneinfo/Europe/' + timezone + ' /etc/localtime) &')
#		os.system('(sleep 5; reboot) &')
	return "data received, rebooting now"

@app.route("/changesettings", methods=['GET','POST'])
def changeSettings():
        if request.method == 'GET':
                global sleep,wake,city,brightness,screensaver

                sleep = request.args.get('sleep', sleep)
                wake = request.args.get('wake', wake)
                city = request.args.get('city', city)
                brightness = request.args.get('brightness', brightness)
                print(sleep + " " + wake + " " + city + " " + brightness)
				s = open("screensaver", "w")
                f = open("settings.txt", "w")
                f.write("sleep time: " + sleep + "\r\n")
                f.write("wake time: " + wake + "\r\n")
                f.write("city: " + city + "\r\n")
                f.write("brightness: " + brightness + "\r\n")
				s.write(screensaver)
				s.close()
                f.close()
        return "data received, changing settings"


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80, debug=True)
