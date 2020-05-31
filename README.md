Hello and welcome to my Weather Data project!

Contributions are welcome but PLEASE use your own API Keys! The ones in here are just for development and not intended for heavy use. Too many requests will cause errors anyway (which is another thing on the 'to do' list.)

I started this project when I was learning Java Servlets and JSP and decided to create this dynamic web application to pull data from different weather APIs to display data and maps.

Starting with index.html, the user enters a zipcode and submits. Current weather data is pulled from openweathermap.org and weatherapi.com and is displayed in a simple table in data.jsp.

The 7-day forecast data is pulled from NOAA at weather.gov and displayed with it's supplied icons and detailed descriptions of upcoming weather.

I used Google Maps API to display a map of the area and used either ClimaCell or openWeatherMap weather layers to overlay the map and show precipitation, cloud cover, and temperature.

Screen shots to give you an idea of current status:
![Screenshot] (

This was built in Eclipse running on Apache Tomcat. I also needed to install json-simple-1.1.jar to handle the JSON files in Java:
http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm
