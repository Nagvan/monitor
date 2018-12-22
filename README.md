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

Unable to write test cases due to time constraint

Run MonitorApplication to start springboot application
Then you can see the logs comming
You can change the url by changing server.name in application.properties.

I have used temp file to put the log status, which is OS independent.
But for 1 monitor 1 file is generated.

If more time given I could have written an API which will send json with all the status history for the given website.
Also instead of monitoring 1 site the application could have been extended to monitor list of sites.

And request for list of sites to API will give status for all of the website status.

A lot could have been done. But I invested most of my time to aggregate the files in single file.
But I had to make the code OS independent, so hardcode of file path was not good idea.
I could also place the file name in application property, but then people will have to change as per system.
