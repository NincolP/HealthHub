package com.example.healthhub;

public class Patient {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String unitNumber = null;
    private String city;
    private String state;
    private String zipCode;
    private String email;
    private String password;


    public Patient() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
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
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //Valid password registration method
    //Password must contain at least 8 characters. These characters must contain at least one of each:
    //One uppercase, lower case, special character and number.
    public boolean validPassword () {
        int capitalCounter = 0, specialCounter = 0, numCounter = 0,lowerCaseCounter = 0;

        for(int i = 0; i < password.length(); i++) {
            if(Character.isDigit(password.charAt(i))) numCounter++;
            else if(Character.isUpperCase(password.charAt(i))) capitalCounter++;
            else if(Character.isLowerCase(password.charAt(i))) lowerCaseCounter++;
            else if(!Character.isLetter(password.charAt(i)) && !Character.isDigit(password.charAt(i)))specialCounter++;
        }
        if(capitalCounter > 0 && lowerCaseCounter > 0 && numCounter > 0 && specialCounter > 0 && password.length() > 7){
            return true;
        }
        else
            return false;
    }



}
