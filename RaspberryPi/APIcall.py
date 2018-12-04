import requests, json

response = requests.get("http://192.168.0.149/LitUp_API/api/weather")

data = response.json()
list = data["list"]
print(type(data))
print(list)
