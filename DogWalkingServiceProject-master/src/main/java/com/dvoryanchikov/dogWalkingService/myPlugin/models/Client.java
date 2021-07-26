package com.dvoryanchikov.dogWalkingService.myPlugin.models;

import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IClient;
import org.codehaus.jackson.annotate.JsonProperty;

public class Client extends Human {
    @JsonProperty("address")
    private String address;

    public Client() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void toEntity(IClient entity) {
        entity.setLastName(this.getLastName());
        entity.setName(this.getName());
        entity.setMiddleName(this.getMiddleName());
        entity.setBirthDate(this.getBirthDate());
        entity.setPhoneNumber(this.getPhoneNumber());
        entity.setEmail(this.getEmail());
        entity.setAddress(this.getAddress());
        entity.setUniqueId(this.getUniqueId());
        entity.setIssueId(this.getIssueId());
    }

    public static Client fromEntity(IClient entity) {
        Client client = new Client();
        client.setLastName(entity.getLastName());
        client.setName(entity.getName());
        client.setMiddleName(entity.getMiddleName());
        client.setBirthDate(entity.getBirthDate());
        client.setPhoneNumber(entity.getPhoneNumber());
        client.setEmail(entity.getEmail());
        client.setAddress(entity.getAddress());
        client.setUniqueId(entity.getUniqueId());
        client.setIssueId(entity.getIssueId());
        return client;
    }
}
