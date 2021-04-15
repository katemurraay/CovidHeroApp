package com.kmm.a117349221ca2_parta.covid;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Covid {
    @SerializedName("ID")
    private String covidID;

    @SerializedName("CountryCode")
    private String countryCode;
    @SerializedName("CityCode")
    private String cityCode;

    @SerializedName("Lon")
    private String longitude;

    @SerializedName("Lag")
    private String  latitude;

    private int Deaths, Confirmed, Recovered, Active;

    private Date Date;
    private String Country, Province, City;

    public Covid(){
        this.Active =0;
        this.Country = "";
        this.countryCode ="";
        this.Deaths = 0;
        this.Confirmed =0;
        this.Recovered =0;
        this.covidID ="";
        this.longitude ="";
        this.Date = null;
        this.City ="";
        this.Province ="";
        this.latitude ="";
        this.cityCode ="";
    }

    public Covid(String covidID, String countryCode, String cityCode, String longitude, String latitude, int deaths, int confirmed, int recovered, int active, Date date, String country, String province, String city) {
        this.covidID = covidID;
        this.countryCode = countryCode;
        this.cityCode = cityCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.Deaths = deaths;
        this.Confirmed = confirmed;
        this.Recovered = recovered;
        this.Active = active;
        this.Date = date;
        this.Country = country;
        this.Province = province;
        this.City = city;
    }


    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public String getCovidID() {
        return covidID;
    }

    public void setCovidID(String covidID) {
        this.covidID = covidID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getDeaths() {
        return Deaths;
    }

    public void setDeaths(int deaths) {
        Deaths = deaths;
    }

    public int getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(int confirmed) {
        Confirmed = confirmed;
    }

    public int getRecovered() {
        return Recovered;
    }

    public void setRecovered(int recovered) {
        Recovered = recovered;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
    //</editor-fold>
}
