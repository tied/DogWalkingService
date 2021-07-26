// formFindReq()
// addFromBdReq()
// clearReq()
// clearAllReq()
//
// loadInf()
// inputValueSelectOwner()
// inputValueRequestPlace()
// inputValueTimeWalk()
// inputValueWalkDuration()
// crtReq()

var scriptInfRequestWalk = function () {

    var that = {
        api: apiErpRoles("http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService", 120000),
        additional: additionalRequestWalkFunctions(),
        scripts: scripts(),
    };

    that.createForm = function () {

        that.scripts.createForm();

        var col1 = document.getElementById("col1");
        var col2 = document.getElementById("col2");
        var col3 = document.getElementById("col3");
        col1.innerHTML = "Enter unique id request";
        col2.innerHTML = "<input type=text id=\"uniqueIdReq\">";
        col3.innerHTML =
            "<input type=submit name=\"submit\" value=\"find\" id=\"new\">\n" +
            "<input type=\"button\" onclick=\"scriptInfRequestWalk().addFromBdReq()\" value=\"get all requests\">\n" +
            "<input type=\"button\" onclick=\"scriptInfRequestWalk().clearReq()\" value=\"hide found request\">\n" +
            "<input type=\"button\" onclick=\"scriptInfRequestWalk().clearAllReq()\" value=\"hide all requests\">"
    }

    that.formFindReq = function () {
        let str = document.getElementById("uniqueIdReq").value;

        that.api.requestWalkByUniqueId(str, that.additional.sendSuccessFindRequestWalk);

    }

    that.addFromBdReq = function () {

        that.api.allRequestWalks(that.additional.sendSuccessAllRequestWalks);

    }

    that.clearReq = function () {
        let parent = document.getElementById("findTableReq");
        parent.innerText = "";
    }

    that.clearAllReq = function () {
        let parent = document.getElementById("requestTable");
        parent.innerText = "";

        let chartWalkers = document.getElementById("chart1");
        chartWalkers.innerHTML = "";
        chartWalkers.style.width = "0px";
        chartWalkers.style.height = "0px";

        let chartDogs = document.getElementById("chart2");
        chartDogs.innerHTML = "";
        chartDogs.style.width = "0px";
        chartDogs.style.height = "0px";
    }

    that.loadInf = function () {
        let str = document.getElementById("selectReqOwner").value;
        let parent = document.getElementById("selectPet");

        that.loadAddress(str);

        if (str !== "default") {

            that.api.ownerDogs(str, function (data) {that.additional.sendSuccessOwnerDogs(data, parent)});

        } else {
            parent.innerHTML = "";
            that.additional.createOptionPet(parent, "no pets", "default");
        }
    }


    that.inputValueSelectOwner = function () {
        document.getElementById("inputOwner").innerHTML = "";
        document.getElementById("inputPet").innerHTML = "";
    }

    that.inputValueRequestPlace = function () {
        document.getElementById("inputRequestPlace").innerHTML = "";
    }

    that.inputValueTimeWalk = function () {
        document.getElementById("inputTimeWalk").innerHTML = "";
    }

    that.inputValueWalkDuration = function () {
        document.getElementById("inputWalkDuration").innerHTML = "";
    }


    that.crtReq = function () {
        if (that.additional.checkValid() === true) {
            let out = document.getElementById("outReq");
            out.innerHTML = "";

            let newReq = {
                requestPlace: document.getElementById("requestPlace").value,
                timeWalk: document.getElementById("timeWalk").value,
                petId: document.getElementById("selectPet").value,
                walkDuration: document.getElementById("walkDuration").value,
                clientId: document.getElementById("selectReqOwner").value,
            }

            that.api.createRequestWalk(newReq, that.additional.sendSuccessCreateRequest)

        }
    }

    that.updateReqWalk = function (idRequest, idWalker) {
        let reqObject = {
            uniqueId: idRequest,
            dogWalkerId: idWalker,
            requestWalkStatus: "WALKING"
        }

        that.api.updateRequestWalk(reqObject, that.additional.sendSuccessUpdateRequestWalk)

    }

    that.updateDogWalker = function (id, status) {
        let dogWalkerObject = {
            uniqueId: id,
            dogWalkerStatus: status
        }

        that.api.updateDogWalker(dogWalkerObject, that.additional.sendSuccessUpdateDogWalker);

    }

    that.updateDog = function (id, status) {
        let dogObject = {
            uniqueId: id,
            dogStatus: status
        }

        that.api.updateDog(dogObject, that.additional.sendSuccessUpdateDog);

    }

    that.updateFinishReqWalk = function (idRequest){
        let reqObject = {
            uniqueId: idRequest,
            requestWalkStatus: "COMPLETED"
        }

        that.api.updateRequestWalk(reqObject, that.additional.sendSuccessUpdateFinishRequestWalk);

    }

    that.loadAddress = function (clientId) {
        if (clientId === "default") {
            let address = document.getElementById("requestPlace");
            address.value = "";
            return;
        }

        that.api.clientByUniqueId(clientId, that.additional.sendSuccessLoadAddress);

    }

    that.loadOwners = function () {
        that.api.allClients(that.additional.sendSuccessLoadOwners);
    }

    return that;

}
