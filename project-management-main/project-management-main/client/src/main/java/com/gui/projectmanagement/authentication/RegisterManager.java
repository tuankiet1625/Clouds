package com.gui.projectmanagement.authentication;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RegisterManager {
    public static boolean usernameValidator (String username) {
        if (username == null) {
            return false ;
        }
        return username.matches("^[a-zA-Z0-9]{5,}$") ;
    }

    public static boolean passwordValidator (String password) {
        if (password == null) {
            return false ;
        }
        return password.matches("^[a-zA-Z0-9!@#$%^&*]{8,}$") ;
    }

    public static boolean confirmPassword (String password, String again_password) {
        if (password == null || again_password == null) {
            return false ;
        }
        return password.equals(again_password) ;
    }

    public static boolean emailValidator (String email) {
        if (email == null) {
            return false ;
        }
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$") ;
    }

    public static boolean emailExists (String email) {
        try {
            String url = "https://api.hunter.io/v2/email-verifier?email="+email+"&api_key=3934a1a928dce497e714f59cd953fa5ae06b777d" ;
            HttpGet httpGet = new HttpGet(url);
            // Mở kết nối HTTP
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpGet);

            // Đọc phản hồi từ Hunter API
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuilder response = new StringBuilder();
            String line ;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            String status = jsonObject.getAsJsonObject("data").get("status").getAsString();
            return status.equals("valid") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false ;
    }

    public static boolean dayOfBirthValidator (LocalDate day_of_birth) {
        if (day_of_birth == null) {
            return false ;
        }
        LocalDate date_now = LocalDate.now();
        return !day_of_birth.isAfter(date_now) ;
    }

    public static boolean phonenumberValidator (String phonenumber) {
        if (phonenumber == null) {
            return false ;
        }
        return phonenumber.matches("\\d{10}") ;
    }

}
