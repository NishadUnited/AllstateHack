import json,httplib,urllib
import time
import datetime
from array import array

numpulled = 60
#a = array("d", [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
#aveDay = array("d", [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

a = [[0. for x in xrange(24)] for x in xrange(31)] 

IRWEIGHT = 5
AUDIOWEIGHT = 4

connection = httplib.HTTPSConnection('api.parse.com', 443)
params = urllib.urlencode({"limit":numpulled,"order":"-createdAt"})
connection.connect()
connection.request('GET', '/1/classes/sensorData?%s' % params, '', {

       "X-Parse-Application-Id": "RlOF7ViCpvt0d7H8nE7TYHz8HbQtSCfma3GopAsg",

       "X-Parse-REST-API-Key": "kCxkFLu5TwgxCYrtwC2vB6Ru5NZOhR1NROJZCPxq"

     })
result = json.loads(connection.getresponse().read())

#DEBUG LINE
#print json.dumps(result, sort_keys=True, indent=4, separators=(',', ': '))


for x in xrange(0,numpulled):

	dataJSON = result['results'][x]['data']['homeWaterSensorAlarmOff']
	#print dataJSON
	curTimeStamp = dataJSON['timestamp']
	curTimeStamp = float(curTimeStamp)
	#print curTimeStamp #DEBUG LINE CURRENT

	#seconds = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%S'))
	curMinutes = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%M'))
	curHour = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%H'))
	curDay = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%d'))	
	curMonth = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%m'))
	
	print datetime.datetime.fromtimestamp(curTimeStamp).strftime('%Y-%m-%d %H:%M:%S') 

	#CURRENTLY THE ALGORITHM WILL ONLY USE THE HEAT AND AUDIO SENSORS + ADD ENERGY

	a[curDay-1][curHour] = (a[curDay-1][curHour] + int(dataJSON['IRSensor']['value']) * IRWEIGHT + int(dataJSON['audioSensor']['value']) * AUDIOWEIGHT) / 60




print a







