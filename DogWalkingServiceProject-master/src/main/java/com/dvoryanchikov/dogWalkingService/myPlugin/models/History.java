package com.dvoryanchikov.dogWalkingService.myPlugin.models;

import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IHistory;
import org.codehaus.jackson.annotate.JsonProperty;

public class History {
    @JsonProperty("ownerUniqueId")
    private String ownerUniqueId;
    @JsonProperty("dogUniqueId")
    private String dogUniqueId;
    @JsonProperty("walkerUniqueId")
    private String walkerUniqueId;

    public History() {};

    public String getOwnerUniqueId() {
        return ownerUniqueId;
    }

    public void setOwnerUniqueId(String ownerUniqueId) {
        this.ownerUniqueId = ownerUniqueId;
    }

    public String getDogUniqueId() {
        return dogUniqueId;
    }

    public void setDogUniqueId(String dogUniqueId) {
        this.dogUniqueId = dogUniqueId;
    }

    public String getWalkerUniqueId() {
        return walkerUniqueId;
    }

    public void setWalkerUniqueId(String walkerUniqueId) {
        this.walkerUniqueId = walkerUniqueId;
    }

    public void toEntity (IHistory entity) {
        entity.setOwnerUniqueId(this.ownerUniqueId);
        entity.setDogUniqueId(this.dogUniqueId);
        entity.setWalkerUniqueId(this.walkerUniqueId);
    }

    public static History fromEntity (IHistory entity) {
        History history = new History();
        history.setOwnerUniqueId(entity.getOwnerUniqueId());
        history.setDogUniqueId(entity.getDogUniqueId());
        history.setWalkerUniqueId(entity.getWalkerUniqueId());
        return history;
    }
}
