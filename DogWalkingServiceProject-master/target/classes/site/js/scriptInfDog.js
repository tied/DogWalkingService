var scriptInfDog = function () {

    var that = {

        api: apiErpRoles("http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService", 120000),
        additional: additionalDogFunctions(),
        scripts: scripts(),
    };

    that.createForm = function () {

        that.scripts.createForm();

        var col1 = document.getElementById("col1");
        var col2 = document.getElementById("col2");
        var col3 = document.getElementById("col3");

        var form = document.getElementById("myFormReq");
        form.onsubmit = function (e) {that.formFindDog(e)};

        col1.innerHTML = "Enter unique id dog";
        col2.innerHTML = "<input class=\"inputText\" placeholder=\"Enter unique id dog\" type=text id=\"uniqueIdD\">";
        col3.innerHTML =
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfDog().formFindDogFromBtn()\" name=\"submit\" value=\"find\" id=\"newD\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfDog().addFromBdD()\" value=\"get all dogs\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfDog().clearDog()\" value=\"hide found dog\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfDog().clearAllDog()\" value=\"hide all dogs\">"

    }

    that.createMenu = function () {

        that.scripts.createMenu();

    }

    that.formFindDog = function (e) {

        e.preventDefault();

        let str = document.getElementById("uniqueIdD").value;
        that.api.dogByUniqueId(str, that.additional.sendSuccessFindDog)
    }

    that.formFindDogFromBtn = function () {

        var ev = new Event("submit");
        document.getElementById("myFormReq").dispatchEvent(ev);

    }

    that.addFromBdD = function (idEditRow) {
        that.api.allDogs(function (data) {that.additional.sendSuccessAllDogs(data, idEditRow)})
    }

    that.clearDog = function () {
        let parent = document.getElementById("findTableD");
        parent.innerText = "";
    }

    that.clearAllDog = function () {
        let parent = document.getElementById("dogTable");
        parent.innerText = "";
    }

    that.inputValueDogName = function () {
        document.getElementById("inputDogName").innerHTML = "";
    }

    that.inputValueGender = function () {
        document.getElementById("inputGender").innerHTML = "";
    }

    that.inputValueDogBirthday = function () {
        document.getElementById("inputDogBirthday").innerHTML = "";
    }

    that.inputValueBreed = function () {
        document.getElementById("inputBreed").innerHTML = "";
    }

    that.inputValueColor = function () {
        document.getElementById("inputColor").innerHTML = "";
    }

    that.inputValueCharacter = function () {
        document.getElementById("inputCharacter").innerHTML = "";
    }

    that.crtD = function () {
        if (that.additional.checkValid() === true) {
            let out = document.getElementById("outD");
            out.innerHTML = "";

            let newDog = {
                dogName: document.getElementById("dogName").value,
                gender: document.getElementById("gender").value,
                dogBirthDate: document.getElementById("dogBirthDate").value,
                breed: document.getElementById("breed").value,
                color: document.getElementById("color").value,
                dogCharacter: document.getElementById("dogCharacter").value,
                ownerId: document.getElementById("selectOwner").value,
            }

            that.api.createDog(newDog, that.additional.sendSuccessCreateDog);

        }
    }

    that.saveChanges = function (row) {
        let editObject = {
            uniqueId: row.children[0].textContent,
            dogName: row.children[1].children[0].value,
            gender: row.children[2].children[0].value,
            dogBirthDate: row.children[3].children[0].value,
            breed: row.children[4].children[0].value,
            color: row.children[5].children[0].value,
            dogCharacter: row.children[6].children[0].value,
            dogStatus: row.children[7].textContent,
            ownerId: row.children[8].children[0].value
        };

        that.api.fullUpdateDog(editObject, that.additional.sendSuccessSaveChanges);

    }

    that.deleteDog = function (dogId, dogFullName) {
        that.api.deleteDog(dogId, function () {that.additional.sendSuccessDeleteDog(dogFullName)});
    }

    that.loadOwners = function (id) {
        that.api.allClients(function (data) {that.additional.sendSuccessLoadOwners(data, id)});
    }

    return that;

}
