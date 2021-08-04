package com.sorokin.dogWalkingService.myPlugin.entities;

import com.sorokin.dogWalkingService.myPlugin.models.enums.RequestWalkStatus;
import net.java.ao.Entity;

public interface IRequestWalk extends Entity {
    String getRequestPlace();

    void setRequestPlace(String requestPlace);

    String getTimeWalk();

    void setTimeWalk(String timeWalk);

    String getPetId();

    void setPetId(String petId);

    Double getWalkDuration();

    void setWalkDuration(Double walkDuration);

    RequestWalkStatus getRequestWalkStatus();

    void setRequestWalkStatus(RequestWalkStatus requestWalkStatus);

    String getUniqueId();

    void setUniqueId(String uniqueId);

    String getClientId();

    void setClientId(String clientId);

    String getDogWalkerId();

    void setDogWalkerId(String dogWalkerId);

    String getIssueId();

    void setIssueId(String issueId);
}
