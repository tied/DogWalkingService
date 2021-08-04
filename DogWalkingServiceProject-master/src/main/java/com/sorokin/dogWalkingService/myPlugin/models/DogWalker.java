package com.sorokin.dogWalkingService.myPlugin.models;

import com.sorokin.dogWalkingService.myPlugin.entities.IDogWalker;
import com.sorokin.dogWalkingService.myPlugin.models.enums.DogWalkerStatus;
import org.codehaus.jackson.annotate.JsonProperty;

public class DogWalker extends Human{

    @JsonProperty("dogWalkerStatus")
    private DogWalkerStatus dogWalkerStatus;

    public DogWalker(){
        dogWalkerStatus = DogWalkerStatus.FREE;
    }

    public DogWalkerStatus getDogWalkerStatus() {
        return dogWalkerStatus;
    }

    public void setDogWalkerStatus(DogWalkerStatus dogWalkerStatus) {
        this.dogWalkerStatus = dogWalkerStatus;
    }

    public void toEntity(IDogWalker entity) {
        entity.setLastName(this.getLastName());
        entity.setName(this.getName());
        entity.setMiddleName(this.getMiddleName());
        entity.setBirthDate(this.getBirthDate());
        entity.setPhoneNumber(this.getPhoneNumber());
        entity.setEmail(this.getEmail());
        entity.setDogWalkerStatus(this.getDogWalkerStatus());
        entity.setUniqueId(this.getUniqueId());
        entity.setIssueId(this.getIssueId());
    }

    public static DogWalker fromEntity(IDogWalker entity) {
        DogWalker dogWalker = new DogWalker();
        dogWalker.setLastName(entity.getLastName());
        dogWalker.setName(entity.getName());
        dogWalker.setMiddleName(entity.getMiddleName());
        dogWalker.setBirthDate(entity.getBirthDate());
        dogWalker.setPhoneNumber(entity.getPhoneNumber());
        dogWalker.setEmail(entity.getEmail());
        dogWalker.setDogWalkerStatus(entity.getDogWalkerStatus());
        dogWalker.setUniqueId(entity.getUniqueId());
        dogWalker.setIssueId(entity.getIssueId());
        return dogWalker;
    }
}
