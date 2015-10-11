#Algorithm thing
#
#

import json,httplib,urllib
import time
import datetime
from array import array
import sys 	

numpulled = int(sys.argv[1])
#a = array("d", [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
#aveDay = array("d", [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

a = [[0. for x in xrange(0,24)] for x in xrange(0,7)] 

IRWEIGHT = 1
AUDIOWEIGHT = 1
ENERGYWEIGHT = 1
WATERWEIGHT = 1

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

	print curTimeStamp

	#seconds = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%S'))
	curMinutes = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%M'))
	curHour = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%H'))
	curDay = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%d'))	
	curMonth = int(datetime.datetime.fromtimestamp(curTimeStamp).strftime('%m'))
	
	print datetime.datetime.fromtimestamp(curTimeStamp).strftime('%Y-%m-%d %H:%M:%S') 

	#CURRENTLY THE ALGORITHM WILL ONLY USE THE HEAT AND AUDIO SENSORS + ADD ENERGY
       

	a[curDay-1][curHour] = (a[curDay-1][curHour] + ENERGYWEIGHT * int(dataJSON['Energy']['value']) + WATERWEIGHT * int(dataJSON['Water']['value']) + int(dataJSON['IRSensor']['value']) * IRWEIGHT + int(dataJSON['audioSensor']['value']) * AUDIOWEIGHT) /60

print a

aa = array("d",[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0])
i = 0;

for x in xrange(0,23):
	for y in xrange(0,7):
		
		aa[x] = aa[x] + a[y][x]	

	aa[x] = aa[x]/(7)	

aa[9] = .1
aa[10] = .1
aa[11] = .2
aa[12] = .1
aa[13] = .2
aa[14] = .2
aa[15] = .1		
aa[16] = .2

#aa[3] = aa[3]-.4
print aa.tolist()

connection = httplib.HTTPSConnection('api.parse.com', 443)
connection.connect()
connection.request('POST', '/1/classes/AverageDump', json.dumps({
       "array":aa.tolist()  

     }), {
       "X-Parse-Application-Id": "RlOF7ViCpvt0d7H8nE7TYHz8HbQtSCfma3GopAsg",
       "X-Parse-REST-API-Key": "kCxkFLu5TwgxCYrtwC2vB6Ru5NZOhR1NROJZCPxq",
       "Content-Type": "application/json"
     })

results = json.loads(connection.getresponse().read())

print results







