# monitor
an application to monitor the website

This is a Spring boot application.

This uses scheduler to monitor the website at regular interval of 5 sec.

use application property to give any website to monitor

All the logs are placed in temp file with time stamp and status
Ok = 200
Not Ok = some other issue
Error = Unable to hit url or server error or connection error.

LogFactory will tell where the logs are present.
