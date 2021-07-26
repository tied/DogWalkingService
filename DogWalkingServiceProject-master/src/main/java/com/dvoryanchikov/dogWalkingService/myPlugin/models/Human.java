package com.dvoryanchikov.dogWalkingService.myPlugin.models;

import org.codehaus.jackson.annotate.JsonProperty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Human extends UniqID{

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("middleName")
    private String middleName;

    @JsonProperty("birthDate")
    private Timestamp birthDate;

    @JsonProperty("phoneNumber")
    private Double phoneNumber;

    @JsonProperty("email")
    private String email;

    public Human() {}

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    public Double getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Double phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public String toStringPhoneNumber() {
//        long phoneNumber = this.phoneNumber;
//        StringBuilder stringBuilder = new StringBuilder(11);
//        for (int i = 0; i < 11; i++) {
//            stringBuilder.append(phoneNumber%10);
//            phoneNumber = phoneNumber/10;
//        }
//        stringBuilder.reverse();
//        return stringBuilder.toString();
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
