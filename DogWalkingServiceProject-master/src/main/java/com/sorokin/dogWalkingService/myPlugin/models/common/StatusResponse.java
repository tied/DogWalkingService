package com.sorokin.dogWalkingService.myPlugin.models.common;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusResponse {

    @JsonProperty("success")
    protected Boolean success = false;

    @JsonProperty("message")
    protected String message = "";

    public StatusResponse() { }

    private StatusResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static StatusResponse createSuccess(String message) {
        return new StatusResponse(true, message);
    }

    public static StatusResponse createFail(String message) {
        return new StatusResponse(false, message);
    }

    public static StatusResponse deleteSuccess(String message){
        return new StatusResponse(true,message);
    }

    public static StatusResponse deleteFail(String message){
        return new StatusResponse(false,message);
    }

    public static StatusResponse updateSuccess(String message){
        return new StatusResponse(true,message);
    }

    public static StatusResponse updateFail(String message){
        return new StatusResponse(false,message);
    }
}
