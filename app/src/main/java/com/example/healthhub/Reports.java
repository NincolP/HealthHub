package com.example.healthhub;

import java.util.List;
//import java.util.ArrayList;
import java.util.Objects;

public class Reports {

    private String Date;
    private String providerName;
    private String providerAddress;
    private String phoneNumber;
    private String type;
    private String Report;


    public Reports() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String providerAddress() {
        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String Report() {
        return Report;
    }

    public void setReport(String Report) {
        this.Report = Report;
    }
    
    
}