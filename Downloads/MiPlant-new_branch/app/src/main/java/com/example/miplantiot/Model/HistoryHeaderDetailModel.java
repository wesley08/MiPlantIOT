package com.example.miplantiot.Model;

public class HistoryHeaderDetailModel {

    String Date, Status,Totals,NoTransaction,ID;

    public HistoryHeaderDetailModel(String date, String status, String totals, String noTransaction, String ID) {
        Date = date;
        Status = status;
        Totals = totals;
        NoTransaction = noTransaction;
        this.ID = ID;
    }

    public HistoryHeaderDetailModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotals() {
        return Totals;
    }

    public void setTotals(String totals) {
        Totals = totals;
    }

    public String getNoTransaction() {
        return NoTransaction;
    }

    public void setNoTransaction(String noTransaction) {
        NoTransaction = noTransaction;
    }
}
