package com.dvoryanchikov.dogWalkingService.myPlugin.models;

import com.dvoryanchikov.dogWalkingService.myPlugin.entities.IRequestWalk;
import com.dvoryanchikov.dogWalkingService.myPlugin.models.enums.RequestWalkStatus;
import org.codehaus.jackson.annotate.JsonProperty;


public class RequestWalk extends UniqID{
    @JsonProperty("requestPlace")
    private String requestPlace;
    @JsonProperty("timeWalk")
    private String timeWalk;
    @JsonProperty("petId")
    private String petId;
    @JsonProperty("walkDuration")
    private Double walkDuration;
    @JsonProperty("requestWalkStatus")
    private RequestWalkStatus requestWalkStatus;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("dogWalkerId")
    private String dogWalkerId;

    //Constructor
    public RequestWalk() {
        requestWalkStatus = RequestWalkStatus.SEARCHING_WALKER;
        dogWalkerId = "No dog walker";
    }

    public String getDogWalkerId() {
        return dogWalkerId;
    }

    public void setDogWalkerId(String dogWalkerId) {
        this.dogWalkerId = dogWalkerId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRequestPlace() {
        return requestPlace;
    }

    public void setRequestPlace(String requestPlace) {
        this.requestPlace = requestPlace;
    }

    public String getTimeWalk() {
        return timeWalk;
    }

    public void setTimeWalk(String timeWalk) {
        this.timeWalk = timeWalk;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public Double getWalkDuration() {
        return walkDuration;
    }

    public void setWalkDuration(Double walkDuration) {
        this.walkDuration = walkDuration;
    }

    public RequestWalkStatus getRequestWalkStatus() {
        return requestWalkStatus;
    }

    public void setRequestWalkStatus(RequestWalkStatus requestWalkStatus) {
        this.requestWalkStatus = requestWalkStatus;
    }

    public void toEntity(IRequestWalk entity){
        entity.setRequestPlace(this.getRequestPlace());
        entity.setTimeWalk(this.getTimeWalk());
        entity.setPetId(this.getPetId());
        entity.setWalkDuration(this.getWalkDuration());
        entity.setRequestWalkStatus(this.getRequestWalkStatus());
        entity.setUniqueId(this.getUniqueId());
        entity.setClientId(this.getClientId());
        entity.setDogWalkerId(this.getDogWalkerId());
        entity.setIssueId(this.getIssueId());
    }

    public static RequestWalk fromEntity(IRequestWalk entity){
        RequestWalk requestWalk = new RequestWalk();
        requestWalk.setRequestPlace(entity.getRequestPlace());
        requestWalk.setTimeWalk(entity.getTimeWalk());
        requestWalk.setPetId(entity.getPetId());
        requestWalk.setWalkDuration(entity.getWalkDuration());
        requestWalk.setRequestWalkStatus(entity.getRequestWalkStatus());
        requestWalk.setUniqueId(entity.getUniqueId());
        requestWalk.setClientId(entity.getClientId());
        requestWalk.setDogWalkerId(entity.getDogWalkerId());
        requestWalk.setIssueId(entity.getIssueId());
        return requestWalk;
    }
}
