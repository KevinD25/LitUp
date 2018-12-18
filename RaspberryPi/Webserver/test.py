f = open("settings.txt", "rt")
sleep = f.readline()[12:-2]
wake = f.readline()[11:-2]
city = f.readline()[6:-2]
brightness = f.readline()[12:]
print(sleep)
print(wake)
print(city)
print(brightness)
