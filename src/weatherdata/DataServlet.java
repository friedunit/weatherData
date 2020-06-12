 /*
  * Filename: DataServlet.java
  * Author: John Kaiser
  * Date: May, 2020
  */

package weatherdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.*;
import org.json.simple.*;


/**
 *
 * @author johnkaiser
 */
@SuppressWarnings("serial")
//@WebServlet("/getData")
public class DataServlet extends HttpServlet {
	
    
    private String zip, line, cityName, destination = "data.jsp";
    private String urlString, noaaURL, forecastURL;
    private JSONArray weatherArray, periods;
    private double latitude, longitude;
    private Object object;
    private JSONObject jObject;
    private Date sunrise, sunset;
    SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");
    //Maps for parsing JSON files
    private Map<String, Object> coordinates = new HashMap<>();
    private Map<String, Object> main = new HashMap<>();
    private Map<String, Object> jsonMap = new HashMap<>();
    private Map<String, Object> wind = new HashMap<>();
    private Map<String, Object> weather = new HashMap<>();
    private Map<String, Object> noaaProperties = new HashMap<>();
    private Map<String, Object> forecast = new HashMap<>();
    private Map<String, Object> sysMap = new HashMap<>();
    private Map<String, Object> weatherAPIcurrent = new HashMap<>();
    private Map<String, Object> weatherAPIlocation = new HashMap<>();
    
    private URL url;
    private URLConnection connect;
    private StringBuilder newString;
    private BufferedReader reader;
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	//Map for getting API keys stored as environment variables
    	Map<String, String> env = System.getenv();
    	final String API_KEY = env.get("OWM_KEY"); //OpenWeatherMap API Key
        final String weatherAPI = env.get("WEATHERAPI_KEY"); //weatherAPI.com API Key
        final String googleAPI = env.get("GOOGLE_KEY"); //Google Maps API Key
        final String climaCellAPI = env.get("CLIMACELL_KEY"); //ClimaCell API
        final String mapBoxToken = env.get("MAPBOX_KEY");
        System.out.println("API: " + API_KEY);
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                              envName,
                              env.get(envName));
        }
    	//Get zipcode from user input index.jsp
        zip = request.getParameter("zip");
        //Create first URL to openWeatherMap with zip, api key and set units to imperial
        urlString = "http://api.openweathermap.org/data/2.5/weather?zip=" + zip + ",us&appid=" + API_KEY + "&units=imperial";
        /*
         * getData passes in URL string, makes the connection, and pulls JSON data and appends to StringBuilder newString
         * 
         * */
        getData(urlString);
        try {   
        	//Assign new JSONParser to object to parse the data
			object = new JSONParser().parse(newString.toString());
			jObject = (JSONObject) object;
			/*
			 * parseJSON passes in a JSONObject and creates a new HashMap of data (jsonMap) for easy retrieval
			 * */
			parseJSON(jObject);	
		} catch (ParseException e) {
			e.printStackTrace();
		}
        //resource https://openweathermap.org/current for JSON response
		cityName = jsonMap.get("name").toString();
		coordinates = (Map<String, Object>) jsonMap.get("coord");
		sysMap = (Map<String, Object>) jsonMap.get("sys");
		//New Data objects for sunrise and sunset to get times, they come in Epoch time
		sunrise = new Date((long)(sysMap.get("sunrise")) * 1000);
		sunset = new Date((long)(sysMap.get("sunset")) * 1000);
		latitude = (double)coordinates.get("lat");
		longitude = (double) coordinates.get("lon");
		main = (Map<String, Object>) jsonMap.get("main");
		wind = (Map<String, Object>) jsonMap.get("wind");
		weatherArray = (JSONArray) jsonMap.get("weather");
		JSONObject w = (JSONObject) weatherArray.get(0);
		weather = toMap(w);
		//long temp = (int)main.get("temp");
		//int current_temp = (int)temp;
		
		//Set attributes on data.jsp page to fill in data table
		request.setAttribute("name", cityName);
		request.setAttribute("weather", weather.get("main"));
		request.setAttribute("current_temp", main.get("temp"));
		request.setAttribute("latitude", latitude);
		request.setAttribute("longitude", longitude);
		request.setAttribute("sunrise", df.format(sunrise));
		request.setAttribute("sunset", df.format(sunset));
		request.setAttribute("humidity", main.get("humidity"));
		
		//temp = (int)main.get("feels_like");
		//int feels_like = (int)temp;
		request.setAttribute("feels_like", main.get("feels_like"));
		request.setAttribute("high", main.get("temp_max"));
		request.setAttribute("low", main.get("temp_min"));
		request.setAttribute("wind_speed", wind.get("speed"));
		//request.setAttribute("degrees", wind.get("deg"));
		
		//New API request to weatherAPI.com for other data points not given through openWeatherMap
		String weatherAPI_current = "http://api.weatherapi.com/v1/current.json?key=" + weatherAPI + "&q=" + zip;
		try {
			getData(weatherAPI_current);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			object = new JSONParser().parse(newString.toString());
			jObject = (JSONObject) object;
			parseJSON(jObject);	
		} catch (ParseException e) {
			e.printStackTrace();
		}
		weatherAPIlocation = (Map<String, Object>) jsonMap.get("location");
		weatherAPIcurrent = (Map<String, Object>) jsonMap.get("current");
		request.setAttribute("region", weatherAPIlocation.get("region"));
		request.setAttribute("pressure", weatherAPIcurrent.get("pressure_in"));
		request.setAttribute("uv", weatherAPIcurrent.get("uv"));
		request.setAttribute("visibility", weatherAPIcurrent.get("vis_miles"));
		request.setAttribute("degrees", weatherAPIcurrent.get("wind_dir"));
		request.setAttribute("CCapiKey", climaCellAPI);
		request.setAttribute("googleAPI", googleAPI);
		request.setAttribute("apiKey", API_KEY);
		request.setAttribute("mapBoxToken", mapBoxToken);
		
		//New API request to NOAA for 7-day forecast
		noaaURL = "https://api.weather.gov/points/" + latitude + "," + longitude;
		try {
			getData(noaaURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			object = new JSONParser().parse(newString.toString());
			jObject = (JSONObject) object;
			parseJSON(jObject);	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		noaaProperties = (Map<String, Object>) jsonMap.get("properties");
		forecastURL = (String) noaaProperties.get("forecast");
		try {
			getData(forecastURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			object = new JSONParser().parse(newString.toString());
			jObject = (JSONObject) object;
			parseJSON(jObject);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        noaaProperties = (Map<String, Object>) jsonMap.get("properties");
        periods = (JSONArray) noaaProperties.get("periods");
        for(int i = 0; i < periods.size(); i++) {
        	JSONObject day = (JSONObject) periods.get(i);
        	forecast = toMap(day);
        	request.setAttribute("d" + Integer.toString(i), forecast.get("name"));
        	request.setAttribute("f" + Integer.toString(i), forecast.get("detailedForecast"));
        	request.setAttribute("i" + Integer.toString(i), forecast.get("icon").toString());
        }
        //Forward requests to data.jsp
        RequestDispatcher rd = request.getRequestDispatcher(destination);
        rd.forward(request, response);
    }
    
    public void getData(String urlToConnect) throws IOException {
    	url = new URL(urlToConnect);
        connect = url.openConnection();
        reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        newString = new StringBuilder();
        while((line = reader.readLine()) != null) {
        	newString.append(line);
        }
    }
    
    public void parseJSON(JSONObject jObject) throws ParseException {
			jsonMap = toMap(jObject);		
    }
    
    public Map<String, Object> toMap(JSONObject jObject) {
		Map<String, Object> map = new HashMap<>();
		Iterator<String> iterator = jObject.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			Object value = jObject.get(key);
			
			if(value instanceof JSONArray) {
				value = (JSONArray) value;
			} else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
		}
    	
    	return map;
    	
    }
    

}
