package com.sorokin.dogWalkingService.myPlugin.models.enums;

public enum  RequestWalkStatus {
    SEARCHING_WALKER("10113"),
    WALKING("10114"),
    COMPLETED("10115");

    private String description;

    RequestWalkStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
