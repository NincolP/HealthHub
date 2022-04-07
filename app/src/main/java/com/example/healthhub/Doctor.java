package com.example.healthhub;

import java.util.ArrayList;

public class Doctor {

    private String name;
    private String specialty;
    private ArrayList<String> AvailableTimes;
    private String address;
    private String suite;
    private String city;
    private String state;
    private String zipcode;

    public Doctor() {

    }

    public int getAvailableSize () {
        return AvailableTimes.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }


    public void setAvailableTimes(ArrayList<String> times) {
        AvailableTimes.addAll(times);
    }

    public String getAvailableTimes(int index) {

        return AvailableTimes.get(index);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

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
        return zipcode;
    }

    public void setZipCode(String zipCode) {
        this.zipcode = zipCode;
    }
}
