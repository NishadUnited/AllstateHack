import pycurl, json
import time
import datetime
from random import randint

github_url = 'http://a6.cfapps.io/groups/245bb05a-48dc-4607-af08-8a22649528c1/sensors/47a7e2b9-df5d-4f4e-9925-a74b6b80888a/data'

time = 1420070400

#door = 100
#doorc = 0

#garage = 100
#garagec = 0

heat = 0
#heatc = 0
heatRangeLow = 0
heatRangeHigh = 100

audio = 0
#audioc = 0
audioRangeLow = 0
audioRangeHigh = 100

for x in xrange (1, 60): #Usually use 10080 for 1 week
	time = time + 60 

	#seconds = datetime.datetime.fromtimestamp(time).strftime('%S')
	minutes = datetime.datetime.fromtimestamp(time).strftime('%M')
	hour = datetime.datetime.fromtimestamp(time).strftime('%H')

	#LET THE RANDOMNESS BEGIN 
	if (0 < hour <= 8):
		#doorc = 15
		#garagec = 80
		heatRangeLow = 0
		heatRangeHigh = 20
		audioRangeLow = 0
		audioRangeHigh = 10
	
	elif (8 < hour <= 11):
		#doorc = 30
		#garagec = 90
		heatRangeLow = 40
		heatRangeHigh = 95
		audioRangeLow = 10
		audioRangeHigh = 50

	elif (11 < hour <= 16):
		#doorc = 90
		#garagec = 99
		heatRangeLow = 0
		heatRangeHigh = 10
		audioRangeLow = 0
		audioRangeHigh = 15

	elif (16 < hour <= 20):
		#doorc = 60
		#garagec = 90
		heatRangeLow = 30
		heatRangeHigh = 50
		audioRangeLow = 20
		audioRangeHigh = 45

	elif (16 < hour <= 20):
		#doorc = 40
		#garagec = 99
		heatRangeLow = 40
		heatRangeHigh = 80
		audioRangeLow = 30
		audioRangeHigh = 50

	elif (20 < hour <= 24):
		#doorc = 50
		#garagec = 99
		heatRangeLow = 20
		heatRangeHigh = 30
		audioRangeLow = 10
		audioRangeHigh = 25					


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

	data = json.dumps({"homeWaterSensorAlarmOff" :{"IRSensor":{"value":str(heat)},"audioSensor":{"value":str(audio)},"timestamp":str(time)}})

	c = pycurl.Curl()
	c.setopt(pycurl.URL, github_url)
	c.setopt(pycurl.HTTPHEADER, ['Content-Type: application/json','Accept: application/json'])
	c.setopt(pycurl.POST, 1)
	c.setopt(pycurl.POSTFIELDS, data)
	c.perform()



