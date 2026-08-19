package com.gui.projectmanagement.test;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetcher {
    private static final String API_KEY = "4246ab7040273fc9c4665b44b894853d"; // Thay bằng API key của bạn
    private static final String LOCATION = "Danang";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&mode=xml&appid=" + API_KEY;

    public static void main(String[] args) {
        try {
            // Gửi yêu cầu tới API và nhận phản hồi
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

            // Chuyển đổi nội dung StringBuilder thành chuỗi
            String xmlContent = content.toString();

            // In chuỗi XML ra màn hình console
            System.out.println("Received XML:");
            System.out.println(xmlContent);

            // Ghi chuỗi XML vào tệp
            try (FileWriter fileWriter = new FileWriter("weather.xml")) {
                fileWriter.write(xmlContent);
            }

            // Phân tích nội dung XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
            doc.getDocumentElement().normalize();

            Element weatherElement = (Element) doc.getElementsByTagName("weather").item(0);
            String iconCode = weatherElement.getAttribute("icon");

            String iconUrl = "http://openweathermap.org/img/wn/" + iconCode + ".png";

            Image img = new Image(iconUrl) ;

            // Trích xuất dữ liệu từ tài liệu XML
            String city = doc.getElementsByTagName("city").item(0).getAttributes().getNamedItem("name").getNodeValue();
            String temperature = doc.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getNodeValue();
            String weather = doc.getElementsByTagName("weather").item(0).getAttributes().getNamedItem("value").getNodeValue();

            System.out.println("City: " + city);
            System.out.println("Temperature: " + temperature + " Kelvin");
            System.out.println("Weather: " + weather);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
