package com.dvoryanchikov.dogWalkingService.myPlugin.models;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.UUID;

public class UniqID {

    @JsonProperty("uniqueId")
    private String uniqueId;
    private String issueId;

    public UniqID(){
        this.uniqueId = UUID.randomUUID().toString();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getIssueId(){
        return issueId;
    }

    public void setIssueId(String issueId){
        this.issueId = issueId;
    }
}
