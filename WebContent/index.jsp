<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weather Data App</title>
<Style>
html, body {
        height: 100%;
        margin: 10px;
        padding: 10px;
        background-color: #353535;
        color:  #f0f0f0;
      }</Style>
</head>
<body>
<h3>Welcome to the Weather App!</h3><br>
       <form action="getData" method="post">
            Enter Zipcode: <input type="text" name="zip">
            <input type="submit" style="color: black">
       </form><br>
       
</body>
</html>