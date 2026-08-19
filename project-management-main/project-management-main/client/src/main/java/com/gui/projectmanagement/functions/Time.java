package com.gui.projectmanagement.functions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    public static String getCurrentTime () {
        LocalDateTime current_time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatted_time = current_time.format(formatter);
        return formatted_time ;
    }

    public static String getSession () {
        String session = "" ;
        LocalDateTime timeNow = LocalDateTime.now();
        int hour = timeNow.getHour() ;
        if (hour >= 0 && hour < 12 ) {
            session = "Morning" ;
        }else if (hour >= 12 && hour < 18 ) {
            session = "Afternoon" ;
        }else if (hour >= 18 && hour < 24 ) {
            session = "Evening" ;
        }
        return session ;
    }

    public static String getCurrentDate () {
        LocalDateTime current_date = LocalDateTime.now() ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return current_date.format(formatter);
    }

    public static String getCurrentDateIntl () {
        LocalDateTime current_date = LocalDateTime.now() ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return current_date.format(formatter);
    }

    public static String timeConversion(LocalDate time_value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (time_value == null) {
            return null ;
        }
        return time_value.format(formatter) ;
    }

    public static LocalDate timeConversion(String time_str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (time_str.isEmpty()){
            return null ;
        }
        return LocalDate.parse(time_str, formatter) ;
    }

}
