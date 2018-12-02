from flask import Flask, render_template, request
import os
app = Flask(__name__)

ssid = ""
passwd = ""
timezone = ""
city = ""
temp = ""


@app.route("/", methods=['GET','POST'])
def index():
	if request.method == 'GET':
		global ssid,passwd,continent,city,temp,timezone

		ssid = request.args.get('ssid', ssid)
		passwd = request.args.get('passwd', passwd)
		timezone = request.args.get('timezone', timezone)
		city = request.args.get('city', city)
		temp = request.args.get('temp', temp)
		print(ssid + " " + passwd + " " + timezone + " " + city + " " + temp)
		f = open("/etc/wpa_supplicant/wpa_supplicant.conf", "a")
		if not passwd:
			f.write("network={ \r\n")
			f.write('\tssid=\"' + ssid + '\"\r\n')
			f.write('\tkey_mgmt=NONE\r\n')
			f.write("}\r\n")
			f.close()
		else:
			f.write("network={ \r\n")
			f.write('\tssid=\"' + ssid + '\"\r\n')
			f.write('\tpsk=\"' + passwd + '\"\r\n')
			f.write('\tkey_mgmt=WPA-PSK\r\n')
			f.write("}\r\n")
			f.close()
		os.system('(cp /usr/share/zoneinfo/Europe/' + timezone + ' /etc/localtime) &')
#		os.system('(sleep 5; reboot) &')
	return "data received, rebooting now"

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80, debug=True)
