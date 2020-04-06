# ICalendar

# Purpose

- command-line tool to create a new ICalendar / Google calendar file based on an existing calendar and modify the events to start from a specified date, time and timezone <br />

- run on all platforms/operating system that has a Java Virtual Machine (JVM) installed


![source](https://raw.githubusercontent.com/marknismo/ICalendar/master/images/source.jpg)
![output](https://raw.githubusercontent.com/marknismo/ICalendar/master/images/output.jpg)

# Pre-requisite
Check if Java/JVM is installed on your computer <br />
java -version <br />
(this program has been tested on openjdk version "11.0.6")


Instructions to install JVM on Windows <br />
http://java.boot.by/scbcd5-guide/apas02.html


Instructions to install JVM on MacOS <br />
https://mkyong.com/java/how-to-install-java-on-mac-osx/


Instructions to install JVM on Ubuntu <br />
https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-18-04



# Usage

```
1. Download the folder "icalcopy" to your computer

2. Export the source ICS file / ICalendar / Google calendar from your application and place it in the "icalcopy" folder
 
3. Open config.toml with a text editor and modify the settings as explained in the comments
 
4. Run the command:
java -jar icalcopy.jar
 
5. Import the output file to your Google/calendar app.
```

# Known limitation

Why are attendees or guests not imported into Google calendar? <br />

According to Google Help Center, when you import an event, guests and conference data for that event are not imported. <br />
https://support.google.com/calendar/answer/37118?co=GENIE.Platform%3DDesktop&hl=en



# Development notes

This program is coded in Kotlin and uses Java libraries and built using Gradle. <br />
Although this program is functional and is safe to use on your own computer, work is needed in sanitizing user input before exposing the functionality as an API for the public.




# References

https://github.com/ical4j/ical4j/wiki/Examples <br />
http://ical4j.sourceforge.net/introduction.html <br />
http://ical4j.github.io/docs/ical4j/api/3.0.4/net/fortuna/ical4j/model/Component.html


