#!/bin/bash
#source: https://www.raspberrypi.org/forums/viewtopic.php?t=201709
#New cron job m/h dom mon dow

if [ ! $# -eq 3 ]
	then
		echo "You must provide atleast 3 arguments"
		echo "Usage: $0 minutes hour dayofmonth month dayofweek"
		exit 1
fi
CRON_ADD="*/$3 $1-$2 * * * /home/pi/Documents/School/apvalley-1819-litup/RaspberryPi/Webserver/testcron.sh"

#Get crontab and remove lines containing "something" with grep
CRON=$(crontab -l -u pi)
CRON=$(printf "$CRON" | grep -v "testcron.sh")
#Make sure crontab ends with newline
if [ $(printf "$CRON" | tail -c 1 | wc -l) -eq 0 ]; then
  CRON="$CRON\n"
fi

printf "$CRON$CRON_ADD\n" | crontab -u pi -
