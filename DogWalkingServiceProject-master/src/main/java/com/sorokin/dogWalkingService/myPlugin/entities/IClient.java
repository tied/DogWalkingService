package com.sorokin.dogWalkingService.myPlugin.entities;

import net.java.ao.Entity;

import java.sql.Timestamp;
import java.util.Date;


public interface IClient extends Entity {

    String getLastName();

    void setLastName(String lastName);

    String getName();

    void setName(String name);

    String getMiddleName();

    void setMiddleName(String middleName);

    Timestamp getBirthDate();

    void setBirthDate(Date birthDate);

    Double getPhoneNumber();

    void setPhoneNumber(Double phoneNumber);

    String getEmail();

    void setEmail(String email);

    String getAddress();

    void setAddress(String address);

    String getUniqueId();

    void setUniqueId(String uniqueId);

    String getIssueId();

    void setIssueId(String issueId);
}
