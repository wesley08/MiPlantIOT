package com.example.miplantiot.Model;

public class CourierCostModel {
    String destination, time, cost, couriername;

    public CourierCostModel(String destination, String time, String cost, String couriername) {
        this.destination = destination;
        this.time = time;
        this.cost = cost;
        this.couriername = couriername;
    }

    public CourierCostModel() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCouriername() {
        return couriername;
    }

    public void setCouriername(String couriername) {
        this.couriername = couriername;
    }
}
