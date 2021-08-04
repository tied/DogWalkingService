package com.sorokin.dogWalkingService.myPlugin.models.enums;

public enum Gender {
    boy("10100"),
    girl("10101");

    String description;

    Gender (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
