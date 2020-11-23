package com.example.miplantiot.Model.Device;

public class DataSoilMoisture {
    String Date, Time;
    int SoilMoisture;

    public DataSoilMoisture(String date, String time, int soilMoisture) {
        Date = date;
        Time = time;
        SoilMoisture = soilMoisture;
    }

    public DataSoilMoisture() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getSoilMoisture() {
        return SoilMoisture;
    }

    public void setSoilMoisture(int soilMoisture) {
        SoilMoisture = soilMoisture;
    }
}
