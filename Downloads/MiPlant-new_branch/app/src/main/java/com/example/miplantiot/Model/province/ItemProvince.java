package com.example.miplantiot.Model.province;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemProvince {
    @SerializedName("rajaongkir")
    @Expose
    private RajaOngkir rajaongkir;

    public ItemProvince(RajaOngkir rajaongkir) {
        this.rajaongkir = rajaongkir;
    }

    public RajaOngkir getRajaongkir() {
        return rajaongkir;
    }

    public void setRajaongkir(RajaOngkir rajaongkir) {
        this.rajaongkir = rajaongkir;
    }
}
