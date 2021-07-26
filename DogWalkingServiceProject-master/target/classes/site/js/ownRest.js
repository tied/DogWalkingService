function apiErpRoles (uri, timeout) {

    var that = {
        ajax: apiAjax(uri, timeout),
    };

    /////////////////////////////for client

    that.clientByUniqueId = function (str, onSuccess, onError) {
        return that.ajax.get("/client", "?uniqueId=" + str, onSuccess, onError);
    }

    that.createClient = function (newClient, onSuccess, onError) {
        return that.ajax.post("/createClient", newClient, onSuccess, onError);
    }

    that.updateClient = function (updateClient, onSuccess, onError) {
        return that.ajax.put("/updateClient", updateClient, onSuccess, onError);
    }

    that.deleteClient = function (str, onSuccess, onError) {
        return that.ajax.delete("/deleteClient", "?uniqueId=" + str, onSuccess,onError);
    }

    that.allClients = function (onSuccess, onError) {
        return that.ajax.get("/allClients", null, onSuccess, onError);
    }

    ///////////////////////////////////////////////for dog

    that.dogByUniqueId = function (str, onSuccess, onError) {
        return that.ajax.get("/dog", "?uniqueId=" + str, onSuccess, onError);
    }

    that.allDogs = function (onSuccess, onError) {
        return that.ajax.get("/allDogs", null, onSuccess, onError);
    }

    that.createDog = function (newDog, onSuccess, onError) {
        return that.ajax.post("/createDog", newDog, onSuccess, onError);
    }

    that.fullUpdateDog = function (newDog, onSuccess, onError) {
        return that.ajax.put("/fullUpdateDog", newDog, onSuccess, onError);
    }

    that.deleteDog = function (str, onSuccess, onError) {
        return that.ajax.delete("/deleteDog", "?uniqueId=" + str, onSuccess, onError);
    }

    that.ownerDogs = function (str, onSuccess, onError) {
        return that.ajax.get("/ownerDogs", "?ownerId=" + str, onSuccess, onError);
    }

    that.updateDog = function (newDog, onSuccess, onError) {
        return that.ajax.put("/updateDog", newDog, onSuccess, onError);
    }

    //////////////////////////////////////////////////for dogwalker

    that.dogWalkerByUniqueId = function (str, onSuccess, onError) {
        return that.ajax.get("/dogWalker", "?uniqueId=" + str, onSuccess, onError);
    }

    that.allDogWalkers = function (onSuccess, onError) {
        return that.ajax.get("/allDogWalkers", null, onSuccess, onError);
    }

    that.createDogWalker = function (newDogWalker, onSuccess, onError) {
        return that.ajax.post("/createDogWalker", newDogWalker, onSuccess, onError);
    }

    that.allUpdateDogWalker = function (newDogWalker, onSuccess, onError) {
        return that.ajax.put("/allUpdateDogWalker", newDogWalker, onSuccess, onError);
    }

    that.deleteDogWalker = function (str, onSuccess, onError) {
        return that.ajax.delete("/deleteDogWalker", "?uniqueId=" + str, onSuccess, onError);
    }

    that.updateDogWalker = function (newDogWalker, onSuccess, onError) {
        return that.ajax.put("/updateDogWalker", newDogWalker, onSuccess, onError);
    }

    ///////////////////////////////////////////////////for request

    that.requestWalkByUniqueId = function (str, onSuccess, onError) {
        return that.ajax.get("/requestWalk", "?uniqueId=" + str, onSuccess, onError);
    }

    that.allRequestWalks = function (onSuccess, onError) {
        return that.ajax.get("/allRequestWalks", null, onSuccess, onError);
    }

    that.createRequestWalk = function (newRequest, onSuccess, onError) {
        return that.ajax.post("/createRequestWalk", newRequest, onSuccess, onError);
    }

    that.updateRequestWalk = function (updateRequest, onSuccess, onError) {
        return that.ajax.put("/updateRequestWalk", updateRequest, onSuccess, onError);
    }

    // delete

    ///////////////////////////////////for history

    that.getHistoryRequest = function (owner, dog, walker, onSuccess, onError) {
        return that.ajax.get("/getHistoryRequest",
            "?owner=" + owner + "&dog=" + dog + "&walker=" + walker,
            onSuccess, onError);
    }

    return that;

}