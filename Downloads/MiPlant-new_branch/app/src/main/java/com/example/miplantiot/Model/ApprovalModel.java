package com.example.miplantiot.Model;

public class ApprovalModel {

    String Status,Totals,NoTransaction,ID,IDTransaction,KeyPush;

    public ApprovalModel(String status, String totals, String noTransaction, String ID, String IDTransaction, String keyPush) {
        Status = status;
        Totals = totals;
        NoTransaction = noTransaction;
        this.ID = ID;
        this.IDTransaction = IDTransaction;
        KeyPush = keyPush;
    }

    public ApprovalModel() {
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDTransaction() {
        return IDTransaction;
    }

    public void setIDTransaction(String IDTransaction) {
        this.IDTransaction = IDTransaction;
    }

    public String getKeyPush() {
        return KeyPush;
    }

    public void setKeyPush(String keyPush) {
        KeyPush = keyPush;
    }
}
