package com.example.aiju_chunghin_comp304_lab5;

public class Cinemas {
    private String cinemaName;
    private double lat;
    private double lnt;
    private String phone;
    private String hours;
    private String website;

    public Cinemas(String cinemaName, double lat, double lnt, String phone, String hours, String website) {
        this.cinemaName = cinemaName;
        this.lat = lat;
        this.lnt = lnt;
        this.phone = phone;
        this.hours = hours;
        this.website = website;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLnt() {
        return lnt;
    }

    public void setLnt(double lnt) {
        this.lnt = lnt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
