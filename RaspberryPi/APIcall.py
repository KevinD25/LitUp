import requests, json

response = requests.get("http://192.168.0.213/LitUp_API/api/weather")

data = response.json()
list = data["list"]
for f in list:
	print(f["weather"])
	print(f["weatherDetail"])
	print(round((f["temp"] - 273), 1))
	print(f["time"])
