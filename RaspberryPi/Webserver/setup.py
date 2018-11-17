from flask import Flask, render_template, request
import os
app = Flask(__name__)

@app.route("/", methods=['GET','POST'])
def index():
	if request.method == 'POST':
		ssid = request.form['ssid']
		passwd = request.form['passwd']
		f = open("/etc/wpa_supplicant/wpa_supplicant.conf", "a")
		if not passwd:
			f.write("network={ \r\n")
			f.write('\tssid=\"' + ssid + '\"\r\n')
			f.write('\tkey_mgmt=NONE\r\n')
			f.write("}")
			f.close()
		else:
			f.write("network={ \r\n")
			f.write('\tssid=\"' + ssid + '\"\r\n')
			f.write('\tpsk=\"' + passwd + '\"\r\n')
			f.write('\tkey_mgmt=WPA-PSK\r\n')
			f.write("}")
			f.close()
		os.system('(sleep 5; reboot) &')
	return render_template('index.html')

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80, debug=True)
