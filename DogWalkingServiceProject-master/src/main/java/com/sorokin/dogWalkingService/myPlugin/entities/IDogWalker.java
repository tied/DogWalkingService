package com.sorokin.dogWalkingService.myPlugin.entities;

import com.sorokin.dogWalkingService.myPlugin.models.enums.DogWalkerStatus;
import net.java.ao.Entity;

import java.sql.Timestamp;
import java.util.Date;

public interface IDogWalker extends Entity {

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

    DogWalkerStatus getDogWalkerStatus();

    void setDogWalkerStatus(DogWalkerStatus dogWalkerStatus);

    String getUniqueId();

    void setUniqueId(String uniqueId);

    String getIssueId();

    void setIssueId(String issueId);

}
