Hello and welcome to my Weather Data project!

Contributions are welcome but you must sign up for your own API Keys. APIs used: Google Maps, OpenWeatherMap, weatherAPI.com, ClimaCell. They are free, just sign up. Mine are stored as local environment variables and are not intended for heavy use. Too many requests will cause errors anyway (which is another thing on the 'to do' list.)

I started this project when I was learning Java Servlets and JSP and decided to create this dynamic web application to pull data from different weather APIs to display data and maps.

Starting with index.html, the user enters a zipcode and submits. Current weather data is pulled from openweathermap.org and weatherapi.com and is displayed in a simple table in data.jsp.

The 7-day forecast data is pulled from NOAA at weather.gov and displayed with it's supplied icons and detailed descriptions of upcoming weather.

I used Google Maps API to display a map of the area and used either ClimaCell or openWeatherMap weather layers to overlay the map and show precipitation, cloud cover, and temperature. The radio buttons will update the map with the new layer when clicked.

Screen shots to give you an idea of current status:
![Screenshot1](https://raw.githubusercontent.com/friedunit/weatherData/master/ScreenShots/Screen%20Shot%202020-06-11%20at%208.09.17%20PM.png)
![Screenshot2](https://raw.githubusercontent.com/friedunit/weatherData/master/ScreenShots/Screen%20Shot%202020-06-11%20at%208.09.39%20PM.png)
![Screenshot3](https://raw.githubusercontent.com/friedunit/weatherData/master/ScreenShots/Screen%20Shot%202020-06-11%20at%208.09.55%20PM.png)

**This was built in Eclipse running on Apache Tomcat so you will need a web server running. I also needed to install json-simple-1.1.jar to handle the JSON files in Java:
http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm

**TO DO List:
This web application works at the moment but is still pretty raw. When I was first thinking of this project, I figured it had to be innovative to be worth hosting for users. Right now, it doesn't do anything too exciting... but it could. 
2 things I thought of:

1. Weather is so important, and like most people, I have several weather apps on my phone that give slight variations in data or data that others don't supply. Having one source that gives everything would be beneficial. Or it could display data from several sources to compare the data.
2. With the map, you don't often see driving directions with accurate weather layers anymore. Google Maps used to have this option but not anymore. Having a radar overlay with weather data for origin and destination would be great.

- I want to move away from Google Maps, maybe to open street map or MapBox. 
- Several front-end CSS styling would make everything look much better. Right now it has a simple "dark mode" feel.
- Better management of API keys needs to be done with exception handling for too many requests
- More weather data including moon phase and tides should be added.
- The ability to enter a city instead of just a zipcode should be added as well.
- Right now its set to work in the US, worldwide capability should be added
- Ability to adjust metric/imperial
