package proiect;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WeatherInfo {
    public void getWeather(DataOutputStream out, DataInputStream in)  {
        try {
            // variabila pentru locatie
            String loc = "Bucharest";
            // Introducere locatie
//            out.writeUTF("Enter location: ");
//            loc = in.readUTF();

            // HTTP Request
            URL url = new URL("https://www.metaweather.com/api/location/search/?query=" + URLEncoder.encode(loc, String.valueOf(StandardCharsets.UTF_8)));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //raspunsul cererii http
            int status = con.getResponseCode();

            // reader pentru citit raspunsul con
            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine = rd.readLine();

            // terminare conexiune
            con.disconnect();

            // Creare objectmapper
            ObjectMapper map = new ObjectMapper();
            // JsonNode pentru preluare date specifice din Json
            JsonNode node = map.readTree(inputLine);
            // preluare val woeid
            JsonNode woeidRequestResult = node.get(0).get("woeid");

            // HTTP request pentru preluare date meteo(PT 2 DE COD)
            URL url1 = new URL("https://www.metaweather.com/api/location/" + URLEncoder.encode(String.valueOf(woeidRequestResult), String.valueOf(StandardCharsets.UTF_8)) + "/");
            HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();
            con1.setRequestMethod("GET");

            int status1 = con1.getResponseCode();

            // reader pentru citit raspunsul con
            BufferedReader rd1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));
            String inputLine1 = rd1.readLine();

            con1.disconnect();

            // Creare objectmapper
            ObjectMapper map1 = new ObjectMapper();
            // JsonNode pentru preluare date specifice din Json
            JsonNode node1 = map1.readTree(inputLine1);
            // preluare val relevante
            JsonNode location = node1.get("title");
            JsonNode weatherState = node1.get("consolidated_weather").get(0).get("weather_state_name");
            JsonNode currentTemp = node1.get("consolidated_weather").get(0).get("the_temp");
            JsonNode minTemp = node1.get("consolidated_weather").get(0).get("min_temp");
            JsonNode maxTemp = node1.get("consolidated_weather").get(0).get("max_temp");

            // afisare informatii
            out.writeUTF( "location name: " + location  + "\nweather state: " + weatherState.asText() + " \ncurrent temperature: " + currentTemp.asText() + " \nlowest temp: " + minTemp.asText() + " \nhighest temp: " + maxTemp.asText());
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL");
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
