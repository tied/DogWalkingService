package com.sorokin.dogWalkingService.myPlugin.controller.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.models.RequestWalk;
import com.sorokin.dogWalkingService.myPlugin.models.common.StatusResponse;
import com.sorokin.dogWalkingService.myPlugin.services.RequestWalkService;

public class RequestWalkImpl {
    private final ActiveObjects ao;
    private RequestWalkService requestWalkService;

    private RequestWalkImpl(ActiveObjects ao){
        this.ao = ao;
        this.requestWalkService = requestWalkService.create(ao);
    }

    public static RequestWalkImpl create(ActiveObjects ao){
        return new RequestWalkImpl(ao);
    }

    public RequestWalk[] getAllRequestWalks(){
        return requestWalkService.getAllRequestWalks();
    }

    public RequestWalk getRequestWalkByUniqueId(String uniqueId){
        return requestWalkService.getRequestWalkByUniqueId(uniqueId);
    }

    public StatusResponse createRequestWalk(RequestWalk model){
        try{
            requestWalkService.createRequestWalk(model);
            return StatusResponse.createSuccess("RequestWalk: " + model.getRequestPlace() + " " + model.getPetId());

        }catch (Exception ex){
            return StatusResponse.createFail(ex.getMessage() + "ошибка отседова!");
        }
    }

    public StatusResponse deleteRequestWalk(String uniqueId){
        try{
            requestWalkService.deleteRequestWalkByUniqueId(uniqueId);
            return StatusResponse.deleteSuccess("RequestWalk: " + uniqueId);

        }catch (Exception ex){
            return StatusResponse.deleteFail(ex.getMessage());
        }
    }

    public StatusResponse updateRequestWalk(RequestWalk model){
        try{
            requestWalkService.updateRequestWalk(model);
            return StatusResponse.updateSuccess("RequestWalk: " + model.getUniqueId());

        }catch (Exception ex){
            return StatusResponse.updateFail(ex.getMessage());
        }
    }
}
