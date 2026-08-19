package com.gui.projectmanagement.functions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gui.projectmanagement.entity.WeatherObject;
import javafx.scene.image.Image;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {

    private static final String API_KEY = "4246ab7040273fc9c4665b44b894853d";
    private static final String LOCATION = "Danang";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&mode=xml&appid=" + API_KEY;

    public static WeatherObject getWeatherObject() {
        WeatherObject wo = new WeatherObject() ;
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();
            String xmlContent = content.toString();

            try (FileWriter fileWriter = new FileWriter("weather.xml")) {
                fileWriter.write(xmlContent);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
            doc.getDocumentElement().normalize();

            Element weatherElement = (Element) doc.getElementsByTagName("weather").item(0);
            String iconCode = weatherElement.getAttribute("icon");

            String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + ".png";

            javafx.scene.image.Image img = new Image(iconUrl) ;
            wo.setIcon(iconCode);
            wo.setIcon_img(img);

            String city = doc.getElementsByTagName("city").item(0).getAttributes().getNamedItem("name").getNodeValue();
            String temperature = doc.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getNodeValue();
            String weather = doc.getElementsByTagName("weather").item(0).getAttributes().getNamedItem("value").getNodeValue();

            if (city.equals("Turan")) {
                wo.setCity("Da Nang");
            } else {
                wo.setCity(city);
            }
            wo.setTemperature(Double.parseDouble(temperature) - 273.15);
            wo.setWeather(weather);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wo ;
    }
}
