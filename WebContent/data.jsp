


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>Weather Data App</title>
 <style>

       .map {
       float: right;
       width: 60%;

       }
       
      .map #map-canvas {
        height: 400px;
         
      }
      
      .map #spectrum {
        width: 40%;
        float: right;
        padding-top: 5px;
      
      }
      
      .map #layers {
         float: left;
         padding-top: 2px;
      }
      

      html, body {
        height: 100%;
        margin: 10px;
        padding: 5px;
        background-color: #353535;
        color:  #f0f0f0;
      }
    </style>
 		
    	   <script src="https://maps.googleapis.com/maps/api/js?key=${googleAPI }&callback=initMap"
    async defer></script>
    

    
</head>
<body onload="initMap()">
<h3>Welcome to the Weather App!</h3><br>
       <form action="getData" method="post">
            Enter Zipcode: <input type="text" name="zip">
            <input type="submit" style="color: black">
       </form><br>
       <div class="map">
			
  			
			<div id='map-canvas'></div>
			<form id="layers">
    			<label class="radio-inline">
      			<input type="radio" name="layer" value = "precipitation" onclick="initMap();" checked>Precipitation
    			</label>
    			<label class="radio-inline">
      			<input type="radio" name="layer" value = "cloud-cover" onclick="initMap();">Cloud Cover
    			</label>
    			<label class="radio-inline">
      			<input type="radio" name="layer" value="temperature" onclick="initMap();">Temperature
    			</label>

  			</form>
			<img src="" id="spectrum" />
			</div>
			<script>
    	   var map;
    	   var latitude = ${latitude};
    	   var longitude = ${longitude};
    	   var layer;
		   var spectrum = "SpectrumPics/precipSpectrum.png";
    	   
    	   //Taken and modified from Google Maps Platform https://developers.google.com/maps/documentation/javascript/examples/maptype-image-overlay
    	      function initMap() {

    	    	layer = getLayer();
    	        map = new google.maps.Map(document.getElementById('map-canvas'), {
    	          center: {lat: latitude, lng: longitude}, //insert latitude and longitude
    	          zoom: 7
    	        });
    	        var imageMapType = new google.maps.ImageMapType({
    	    	    getTileUrl: function(coord, zoom) {
    	    	      if (zoom > 12) {
    	    	        return null;
    	    	      }

    	    	      return 'https:/api.climacell.co/v3/weather/layers/' + layer + '/now/'+ zoom + '/' + coord.x + '/' + coord.y + '.png?apikey=${CCapiKey}';
    	    	      //Testing between climaCell and openWeatherMap layers
    	    	      //return 'https://tile.openweathermap.org/map/'+ layer + '/' + zoom + '/' + coord.x + '/' + coord.y + '.png?appid=${apiKey}';
    	    	    },
    	    	    tileSize: new google.maps.Size(256, 256),
    	    	    maxZoom: 12,
    	    	    minZoom: 0,
    	    	  });

    	    	  map.overlayMapTypes.push(imageMapType);
    	    	  
    	    	  switch (layer) {
					case "precipitation":
						spectrum = "SpectrumPics/precipSpectrum.png";
						break;
					case "cloud-cover":
						spectrum = "SpectrumPics/cloudSpectrum.png";
						break;
					case "temperature":
						spectrum = "SpectrumPics/tempSpectrum.png";
						break;
					}
    	    	  document.getElementById("spectrum").src = spectrum;
				}
    	      
    	      
    	      function getLayer() {
      	    	  return document.querySelector('input[name="layer"]:checked').value;
      	        }
    	            

    	   </script>

    	   <h3>Weather data for ${name }, ${region }</h3><br>
    	   

           <table id="weather_data" style="width:30%">
            	
            	<tr>
            		<td>Latitude</td> 
            		<td><script>if(latitude > 0) { //Add degrees sign and direction to Latitude and Longitude
            			document.write(latitude + '\xB0' + 'N');
            		} else {
            			document.write(-latitude + '\xB0' + 'S');
            		}</script></td>
            	</tr>
            	<tr>
            		<td>Longitude</td>
            		<td><script>if(longitude > 0) {
            			document.write(longitude + '\xB0' + 'E');
            		} else {
            			document.write(-longitude + '\xB0' + 'W');
            		}</script></td>
            	</tr>
            	<tr>
            		<td>Sunrise</td>
            		<td>${sunrise}</td>
            	</tr>
            	<tr>
            		<td>Sunset</td>
            		<td>${sunset}</td>
            	</tr>
            	<tr>
            		<td>Current Weather</td>
            		<td>${weather}</td>
            	</tr>
            	<tr>
            		<td>Current temperature</td>
            		<td> ${current_temp}&deg</td>
            	</tr>
            	<tr>
            		<td>Feels like</td>
            		<td>${feels_like}&deg</td>
            	</tr>
            	<tr>
            		<td>High</td>
            		<td>${high}&deg</td>
            	</tr>
            	<tr>
            		<td>Low</td>
            		<td>${low}&deg</td>
            	</tr>
            	<tr>
            		<td>Humidity</td>
            		<td>${humidity}&#37</td>
            	</tr>
            	<tr>
            		<td>Pressure</td>
            		<td>${pressure} inHg</td>
            	</tr>
            	<tr>
            		<td>Visibility</td>
            		<td>${visibility} mi</td>
            	</tr>
            	<tr>
            		<td>UV Index</td>
            		<td>${uv}</td>
            	</tr>
            	<tr>
            		<td>Wind Speed</td>
            		<td>${degrees} at ${wind_speed} mph</td>
            	</tr>
 </table><br>
 
 <table id="forecast" style="width:90%">
 	<colgroup>
       <col span="1" style="width: 10%;">
       <col span="1" style="width: 10%;">
       <col span="1" style="width: 70%;">
    </colgroup>
    <tbody>
            	<tr>
            		<td colspan="2"><h2>7-Day Forecast</h2></td>
            	</tr>
            	<tr>
            		<td>${d0 }</td>
            		<td><img src ="${i0 }"></td>
            		<td>${f0 }</td>
            	</tr>
            	<tr>
            		<td>${d1 }</td>
            		<td><img src="${i1 }"></td>
            		<td>${f1 }</td>
            	</tr>
            	<tr>
            		<td>${d2 }</td>
            		<td><img src="${i2 }"></td>
            		<td>${f2 }</td>
            	</tr>
            	<tr>
            		<td>${d3 }</td>
            		<td><img src="${i3 }"></td>
            		<td>${f3 }</td>
            	</tr>
            	<tr>
            		<td>${d4 }</td>
            		<td><img src="${i4 }"></td>
            		<td>${f4 }</td>
            	</tr>
            	<tr>
            		<td>${d5 }</td>
            		<td><img src="${i5 }"></td>
            		<td>${f5 }</td>
            	</tr>
            	<tr>
            		<td>${d6 }</td>
            		<td><img src="${i6 }"></td>
            		<td>${f6 }</td>
            	</tr>
            	<tr>
            		<td>${d7 }</td>
            		<td><img src="${i7 }"></td>
            		<td>${f7 }</td>
            	</tr>
            	<tr>
            		<td>${d8 }</td>
            		<td><img src="${i8 }"></td>
            		<td>${f8 }</td>
            	</tr>
            	<tr>
            		<td>${d9 }</td>
            		<td><img src="${i9 }"></td>
            		<td>${f9 }</td>
            	</tr>
            	<tr>
            		<td>${d10 }</td>
            		<td><img src="${i10 }"></td>
            		<td>${f10 }</td>
            	</tr>
            	<tr>
            		<td>${d11 }</td>
            		<td><img src="${i11 }"></td>
            		<td>${f11 }</td>
            	</tr>
            	<tr>
            		<td>${d12 }</td>
            		<td><img src="${i12 }"></td>
            		<td>${f12 }</td>
            	</tr>
            	<tr>
            		<td>${d13 }</td>
            		<td><img src="${i13 }"></td>
            		<td>${f13 }</td>
            	</tr>
            	</tbody>
            	
            </table>

</body>
</html>