package com.gui.projectmanagement.entity;

import javafx.scene.image.Image;

public class WeatherObject {
    private String city ;
    private double temperature ;
    private String weather ;
    private String icon ;
    private Image icon_img ;

    public WeatherObject(String city, double temperature, String weather, String icon, Image icon_img) {
        this.city = city;
        this.temperature = temperature;
        this.weather = weather;
        this.icon = icon;
        this.icon_img = icon_img;
    }

    public WeatherObject() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Image getIcon_img() {
        return icon_img;
    }

    public void setIcon_img(Image icon_img) {
        this.icon_img = icon_img;
    }
}
