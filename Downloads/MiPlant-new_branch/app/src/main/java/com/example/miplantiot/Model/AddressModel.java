package com.example.miplantiot.Model;

import com.example.miplantiot.Model.city.Result;

import java.util.ArrayList;
import java.util.List;

public class AddressModel {
    String City, FullAddress, IdAddress,NameAddress,NameReceiver,PhoneNumber,PostCode,Provience;
    private Result ListCity ;
    private com.example.miplantiot.Model.province.Result ListProvince ;

    public AddressModel(String city, String fullAddress, String idAddress, String nameAddress, String nameReceiver, String phoneNumber, String postCode, String provience, Result listCity, com.example.miplantiot.Model.province.Result listProvince) {
        City = city;
        FullAddress = fullAddress;
        IdAddress = idAddress;
        NameAddress = nameAddress;
        NameReceiver = nameReceiver;
        PhoneNumber = phoneNumber;
        PostCode = postCode;
        Provience = provience;
        ListCity = listCity;
        ListProvince = listProvince;
    }

    public String getProvience() {
        return Provience;
    }

    public void setProvience(String provience) {
        Provience = provience;
    }

    public Result getListCity() {
        return ListCity;
    }

    public void setListCity(Result listCity) {
        ListCity = listCity;
    }

    public com.example.miplantiot.Model.province.Result getListProvince() {
        return ListProvince;
    }

    public void setListProvince(com.example.miplantiot.Model.province.Result listProvince) {
        ListProvince = listProvince;
    }

    public AddressModel() {
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getFullAddress() {
        return FullAddress;
    }

    public void setFullAddress(String fullAddress) {
        FullAddress = fullAddress;
    }

    public String getIdAddress() {
        return IdAddress;
    }

    public void setIdAddress(String idAddress) {
        IdAddress = idAddress;
    }

    public String getNameAddress() {
        return NameAddress;
    }

    public void setNameAddress(String nameAddress) {
        NameAddress = nameAddress;
    }

    public String getNameReceiver() {
        return NameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        NameReceiver = nameReceiver;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }
}
