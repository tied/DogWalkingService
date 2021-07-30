package com.dvoryanchikov.dogWalkingService.myPlugin.models;

import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IDog;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.enums.DogStatus;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.enums.Gender;
import org.codehaus.jackson.annotate.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;

public class Dog extends UniqID{

    @JsonProperty("dogName")
    private String dogName;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("dogBirthDate")
    private Timestamp dogBirthDate;

    @JsonProperty("breed")
    private String breed;

    @JsonProperty("color")
    private String color;

    @JsonProperty("dogCharacter")
    private String dogCharacter;

    @JsonProperty("dogStatus")
    private DogStatus dogStatus;

    @JsonProperty("ownerId")
    private String ownerId;

    public Dog() {
        dogStatus = DogStatus.AT_HOME;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender/*String description*/) {
//        this.gender.setDescription(description);
        this.gender = gender;
    }

    public Timestamp getDogBirthDate() {
        return dogBirthDate;
    }

    public void setDogBirthDate(Timestamp dogBirthDate) {
        this.dogBirthDate = dogBirthDate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDogCharacter() {
        return dogCharacter;
    }

    public void setDogCharacter(String dogCharacter) {
        this.dogCharacter = dogCharacter;
    }

    public DogStatus getDogStatus() {
        return dogStatus;
    }

    public void setDogStatus(DogStatus dogStatus) {
        this.dogStatus = dogStatus;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void toEntity(IDog entity) {
        entity.setDogName(this.getDogName());
        entity.setGender(this.getGender());
        entity.setDogBirthDate(this.getDogBirthDate());
        entity.setBreed(this.getBreed());
        entity.setColor(this.getColor());
        entity.setDogCharacter(this.getDogCharacter());
        entity.setDogStatus(this.getDogStatus());
        entity.setUniqueId(this.getUniqueId());
        entity.setOwnerId(this.getOwnerId());
        entity.setIssueId(this.getIssueId());
    }

    public static Dog fromEntity(IDog entity) {
        Dog dog = new Dog();
        dog.setDogName(entity.getDogName());
        dog.setGender(entity.getGender());
        dog.setDogBirthDate(entity.getDogBirthDate());
        dog.setBreed(entity.getBreed());
        dog.setColor(entity.getColor());
        dog.setDogCharacter(entity.getDogCharacter());
        dog.setDogStatus(entity.getDogStatus());
        dog.setUniqueId(entity.getUniqueId());
        dog.setOwnerId(entity.getOwnerId());
        dog.setIssueId(entity.getIssueId());
        return dog;
    }
}
