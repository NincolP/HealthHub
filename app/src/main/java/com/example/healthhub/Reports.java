package com.example.healthhub;

import com.google.firebase.firestore.DocumentReference;

//import java.util.ArrayList;


public class Reports {

    private String Date;
    private String providerName;
    private String providerAddress;
    private String phoneNumber;
    private String type;
    private DocumentReference Report;
    private String reportId;

    private String path;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }




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


    public void setReport(DocumentReference Report) {

        path = Report.getPath();


        this.Report = Report;
    }

    public DocumentReference getReport () {
        return Report;
    }

    public String getPath() {
        return path;
    }
    
    
}