package com.sorokin.dogWalkingService.myPlugin.controller.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.sorokin.dogWalkingService.myPlugin.models.Dog;
import com.sorokin.dogWalkingService.myPlugin.models.common.StatusResponse;
import com.sorokin.dogWalkingService.myPlugin.services.DogService;

public class DogImpl {
    private final ActiveObjects ao;
    private DogService dogService;

    private DogImpl(ActiveObjects ao){
        this.ao = ao;
        this.dogService = dogService.create(ao);
    }

    public static DogImpl create(ActiveObjects ao){
        return new DogImpl(ao);
    }

    public Dog getDogByUniqueId(String uniqueId){
        return dogService.getDogByUniqueId(uniqueId);
    }

    public Dog[] getDogByOwnerId(String ownerId){
        return dogService.getDogByOwnerId(ownerId);
    }

    public Dog[] getAllDogs(){
        return dogService.getAllDogs();
    }

    public StatusResponse createDog(Dog model){
        try{
            dogService.createDog(model);
            return StatusResponse.createSuccess("Dog: " + model.getDogName() + " " + model.getBreed());

        }catch (Exception ex){
            return StatusResponse.createFail(ex.getMessage());
        }
    }

    public StatusResponse deleteDog(String uniqueId){
        try{
            dogService.deleteDogByUniqueId(uniqueId);
            return StatusResponse.deleteSuccess("Dog: " + uniqueId);

        }catch (Exception ex){
            return StatusResponse.deleteFail(ex.getMessage());
        }
    }

    public StatusResponse updateDog(Dog model){
        try{
            dogService.updateDog(model);
            return StatusResponse.updateSuccess("Dog: " + model.getUniqueId());

        }catch (Exception ex){
            return StatusResponse.updateFail(ex.getMessage());
        }
    }

    public StatusResponse fullUpdateDog(Dog model){
        try{
            dogService.fullUpdateDog(model);
            return StatusResponse.updateSuccess("Dog: " + model.getUniqueId());

        }catch (Exception ex){
            return StatusResponse.updateFail(ex.getMessage());
        }
    }
}
