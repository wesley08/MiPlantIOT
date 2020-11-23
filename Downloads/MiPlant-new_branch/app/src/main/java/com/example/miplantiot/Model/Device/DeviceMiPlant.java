package com.example.miplantiot.Model.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceMiPlant {
    String Id, Password,PlantName,PlantAge,idDeviceMiPlant;
    ArrayList<Data> dataDevice;

    public DeviceMiPlant(String id, String password, String plantName, String plantAge, String idDeviceMiPlant, ArrayList<Data> dataDevice) {
        Id = id;
        Password = password;
        PlantName = plantName;
        PlantAge = plantAge;
        this.idDeviceMiPlant = idDeviceMiPlant;
        this.dataDevice = dataDevice;
    }

    public String getIdDeviceMiPlant() {
        return idDeviceMiPlant;
    }

    public ArrayList<Data> getDataDevice() {
        return dataDevice;
    }

    public void setDataDevice(ArrayList<Data> dataDevice) {
        this.dataDevice = dataDevice;
    }

    public void setIdDeviceMiPlant(String idDeviceMiPlant) {
        this.idDeviceMiPlant = idDeviceMiPlant;
    }

    public DeviceMiPlant() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPlantName() {
        return PlantName;
    }

    public void setPlantName(String plantName) {
        PlantName = plantName;
    }

    public String getPlantAge() {
        return PlantAge;
    }

    public void setPlantAge(String plantAge) {
        PlantAge = plantAge;
    }
}
