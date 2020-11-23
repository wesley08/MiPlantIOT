package com.example.miplantiot.Model.Device;

public class Data {
    int SensorSoilMoisture,SensorWaterLevel,SensorSoilContent;

    public Data() {
    }

    public Data(int sensorSoilMoisture, int sensorWaterLevel, int sensorSoilContent) {
        SensorSoilMoisture = sensorSoilMoisture;
        SensorWaterLevel = sensorWaterLevel;
        SensorSoilContent = sensorSoilContent;
    }

    public int getSensorSoilMoisture() {
        return SensorSoilMoisture;
    }

    public void setSensorSoilMoisture(int sensorSoilMoisture) {
        SensorSoilMoisture = sensorSoilMoisture;
    }

    public int getSensorWaterLevel() {
        return SensorWaterLevel;
    }

    public void setSensorWaterLevel(int sensorWaterLevel) {
        SensorWaterLevel = sensorWaterLevel;
    }

    public int getSensorSoilContent() {
        return SensorSoilContent;
    }

    public void setSensorSoilContent(int sensorSoilContent) {
        SensorSoilContent = sensorSoilContent;
    }
}
