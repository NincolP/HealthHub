package com.example.healthhub;

import com.google.firebase.firestore.DocumentReference;

//import java.util.ArrayList;


public class Report {

    private String Date;
    private String providerName;
    private String providerAddress;
    private String phoneNumber;
    private String type;
    private String Report;
    private String reportId;
    private String city;
    private String state;
    private String zipCode;
    private String unitNumber;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }


    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }


    public Report() {
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

    public String getProviderAddress() {

        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    public String getPhoneNumber() {
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

    public void setReport(String Report) {

        this.Report = Report;
    }

    public String getReport () {
        return Report;
    }

}