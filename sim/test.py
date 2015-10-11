import pycurl, json
import time
import datetime
from random import randint

github_url = 'http://a6.cfapps.io/groups/245bb05a-48dc-4607-af08-8a22649528c1/sensors/47a7e2b9-df5d-4f4e-9925-a74b6b80888a/data'

time = 1420099200 # Starting 1/1/2015, 0:0:0

water = 0
waterRangeLow = 0
waterRangeHigh = 100

energy = 0
energyRangeLow = 0
energyRangeHigh = 100

heat = 0
#heatc = 0
heatRangeLow = 0
heatRangeHigh = 100

audio = 0
#audioc = 0
audioRangeLow = 0
audioRangeHigh = 100

for x in xrange (1000, 1000): #Usually use 10080 for 1 week
	

	#seconds = datetime.datetime.fromtimestamp(time).strftime('%S')
	minutes = datetime.datetime.fromtimestamp(time).strftime('%M')
	hour = datetime.datetime.fromtimestamp(time).strftime('%H')

	#LET THE RANDOMNESS BEGIN 
	if (0 < hour <= 8):
		#doorc = 15
		#garagec = 80
		heatRangeLow = 0
		heatRangeHigh = 10
		audioRangeLow = 0
		audioRangeHigh = 10
		energyRangeLow = 0
		energyRangeHigh = 3
		waterRangeLow = 0
		waterRangeHigh = 15
	
	elif (8 < hour <= 11):
		#doorc = 30
		#garagec = 90
		heatRangeLow = 40
		heatRangeHigh = 95
		audioRangeLow = 10
		audioRangeHigh = 50
		energyRangeLow = 0
		energyRangeHigh = 10
		waterRangeLow = 0
		waterRangeHigh = 40
	

	elif (11 < hour <= 16):
		#doorc = 90
		#garagec = 99
		heatRangeLow = 0
		heatRangeHigh = 5
		audioRangeLow = 0
		audioRangeHigh = 5
		nergyRangeLow = 0
		energyRangeHigh = 3
		waterRangeLow = 0
		waterRangeHigh = 10
	

	elif (16 < hour <= 20):
		#doorc = 60
		#garagec = 90
		heatRangeLow = 30
		heatRangeHigh = 70
		audioRangeLow = 20
		audioRangeHigh = 45
		energyRangeLow = 0
		energyRangeHigh = 10
		waterRangeLow = 10
		waterRangeHigh = 30
	

	elif (16 < hour <= 20):
		#doorc = 40
		#garagec = 99
		heatRangeLow = 40
		heatRangeHigh = 80
		audioRangeLow = 30
		audioRangeHigh = 50
		energyRangeLow = 10
		energyRangeHigh = 20
		waterRangeLow = 30
		waterRangeHigh = 50
	

	elif (20 < hour <= 24):
		#doorc = 50
		#garagec = 99
		heatRangeLow = 10
		heatRangeHigh = 20
		audioRangeLow = 10
		audioRangeHigh = 20
		energyRangeLow = 0
		energyRangeHigh = 3
		waterRangeLow = 0
		waterRangeHigh = 10
						


	#if not (randint(0,100) > doorc):
	#	 door = 100
	#else:
	#	 door = 0	

	#if not 	(randint(0,100) > garagec):
	#	 garage = 100
	#else:
	#	 garage = 0	

	heat = randint(heatRangeLow,heatRangeHigh)
	audio = randint(audioRangeLow,audioRangeHigh)
	water = randint(waterRangeLow,waterRangeHigh)
	energy = randint(energyRangeLow,energyRangeHigh)	 	 

	data = json.dumps({"homeWaterSensorAlarmOff" :{"Water":{"value":str(water)},"Energy":{"value":str(energy)},"IRSensor":{"value":str(heat)},"audioSensor":{"value":str(audio)},"timestamp":str(time)}})

	c = pycurl.Curl()
	c.setopt(pycurl.URL, github_url)
	c.setopt(pycurl.HTTPHEADER, ['Content-Type: application/json','Accept: application/json'])
	c.setopt(pycurl.POST, 1)
	c.setopt(pycurl.POSTFIELDS, data)
	c.perform()



	time = time + 300
