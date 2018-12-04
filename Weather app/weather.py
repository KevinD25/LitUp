import requests

city = input('Enter your city : ')

url = 'https://samples.openweathermap.org/data/2.5/weather?q={}&appid=2c165f1b7fbd650cf068827adc8eb952'.format(city)

url2 = 'http://api.openweathermap.org/data/2.5/weather?q=Antwerpen&lang=nl&APPID=c29dbdf3ccc2d57a361ceaeac49d9e53'

res = requests.get(url2)

data = res.json()

print(data)
