package com.sorokin.dogWalkingService.myPlugin.controller.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.models.DogWalker;
import com.sorokin.dogWalkingService.myPlugin.models.common.StatusResponse;
import com.sorokin.dogWalkingService.myPlugin.services.DogWalkerService;

public class DogWalkerImpl {
    private final ActiveObjects ao;
    private DogWalkerService dogWalkerService;

    private DogWalkerImpl(ActiveObjects ao){
        this.ao = ao;
        this.dogWalkerService = dogWalkerService.create(ao);
    }

    public static DogWalkerImpl create(ActiveObjects ao){
        return new DogWalkerImpl(ao);
    }

    public DogWalker getDogWalkerByUniqueId(String uniqueId){
        return dogWalkerService.getDogWalkerByUniqueId(uniqueId);
    }

    public DogWalker[] getAllDogWalkers(){
        return dogWalkerService.getAllDogWalkers();
    }

    public StatusResponse createDogWalker(DogWalker model){
        try{
            dogWalkerService.createDogWalker(model);
            return StatusResponse.createSuccess("DogWalker: " + model.getName() + " " + model.getLastName());

        }catch (Exception ex){
            return StatusResponse.createFail(ex.getMessage());
        }

    }

    public StatusResponse deleteDogWalker(String uniqueId){
        try{
            dogWalkerService.deleteDogWalkerByUniqueId(uniqueId);
            return StatusResponse.deleteSuccess("DogWalker" + uniqueId);

        }catch (Exception ex){
            return StatusResponse.deleteFail(ex.getMessage());
        }
    }

    public StatusResponse updateDogWalker(DogWalker model){
        try{
            dogWalkerService.updateDogWalker(model);
            return StatusResponse.updateSuccess("Dog: " + model.getUniqueId());

        }catch (Exception ex){
            return StatusResponse.updateFail(ex.getMessage());
        }
    }

    public StatusResponse allUpdateDogWalker(DogWalker model){
        try{
            dogWalkerService.allUpdateDogWalker(model);
            return StatusResponse.updateSuccess("Dog: " + model.getUniqueId());

        }catch (Exception ex){
            return StatusResponse.updateFail(ex.getMessage());
        }
    }
}
