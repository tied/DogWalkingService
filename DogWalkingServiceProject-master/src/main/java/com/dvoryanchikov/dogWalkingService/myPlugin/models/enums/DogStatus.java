package com.dvoryanchikov.dogWalkingService.myPlugin.models.enums;

public enum DogStatus {
    WALKING("10102"),
    AT_HOME("10103");

    private final String description;

    DogStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
