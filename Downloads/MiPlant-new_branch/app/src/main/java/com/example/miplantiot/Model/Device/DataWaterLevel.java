package com.example.miplantiot.Model.Device;

public class DataWaterLevel {

    String Date, Time;
    int WaterLevel;

    public DataWaterLevel(String date, String time, int waterLevel) {
        Date = date;
        Time = time;
        WaterLevel = waterLevel;
    }

    public DataWaterLevel() {
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

    public int getWaterLevel() {
        return WaterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        WaterLevel = waterLevel;
    }
}
