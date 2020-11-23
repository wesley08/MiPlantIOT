package com.example.miplantiot.Model.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemCity {
    @SerializedName("rajaongkir")
    @Expose
    private RajaOngkir rajaongkir;

    public RajaOngkir getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(RajaOngkir rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
