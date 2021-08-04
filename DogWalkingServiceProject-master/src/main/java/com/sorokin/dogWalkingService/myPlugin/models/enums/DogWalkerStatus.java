package com.sorokin.dogWalkingService.myPlugin.models.enums;

public enum DogWalkerStatus {
        FREE("10104"),
        BUSY("10105");

        private final String description;

        DogWalkerStatus(String description){
            this.description = description;
        }

        public String getDescription() {
                return description;
        }
}
