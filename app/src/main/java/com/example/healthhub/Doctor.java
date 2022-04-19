package com.example.healthhub;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.List;

public class Doctor {

    private String name;
    private String specialty;
    private List<String> AvailableTimes;
    public String[] times;
    private String address;
    private String suite;
    private String city;
    private String state;
    private String zipcode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;

    public Doctor() {

    }

    public int getAvailableSize () {
        return
                times.length;
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


    public void setAvailableTimes(List<String> AvailableTimes) {
        this. AvailableTimes = AvailableTimes;
        for(int i = 0; i < AvailableTimes.size(); i++) {

            times = AvailableTimes.get(i).split(",");
        }
    }


    public String getAvailableTimes(int index) {

        return times[index].toString();
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
