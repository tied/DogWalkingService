var scriptInfDogWalker = function () {

    var that = {
        api: apiErpRoles("http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService", 120000),
        additional: additionalDogWalkerFunctions(),
        scripts: scripts(),
    }

    that.createForm = function () {

        that.scripts.createForm();

        var col1 = document.getElementById("col1");
        var col2 = document.getElementById("col2");
        var col3 = document.getElementById("col3");

        var form = document.getElementById("myFormReq");
        form.onsubmit = that.formFindDogWalker;

        col1.innerHTML = "Enter unique id dog walker";
        col2.innerHTML = "<input class=\"inputText\" placeholder=\"Enter unique id dog walker\" type=text id=\"uniqueIdDW\">";
        col3.innerHTML =
            "<input class=\"button\" type=submit name=\"submit\" value=\"find\" id=\"newDW\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfDogWalker().addFromBdDW()\" value=\"get all dog walkers\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfDogWalker().clearDogWalker()\" value=\"hide found dog walker\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfDogWalker().clearAllDogWalkers()\" value=\"hide all dog walkers\">"

    }

    that.formFindDogWalker = function () {
        let str = document.getElementById("uniqueIdDW").value;

        that.api.dogWalkerByUniqueId(str, that.additional.sendSuccessFindDogWalker);

    }

    that.addFromBdDW = function (idEditRow) {

        that.api.allDogWalkers(function (data) {that.additional.sendSuccessAddFromBdDW(data,idEditRow)});

    }

    that.clearDogWalker = function () {

        let parent = document.getElementById("findTableDW");
        parent.innerText = "";
    }

    that.clearAllDogWalkers = function () {

        let parent = document.getElementById("dogWalkerTable");
        parent.innerText = "";
    }


    that.inputValueLastNameDW = function () {
        document.getElementById("inputLastNameDW").innerHTML = "";
    }

    that.inputValueNameDW = function () {
        document.getElementById("inputNameDW").innerHTML = "";
    }

    that.inputValueMiddleNameDW = function () {
        document.getElementById("inputMiddleNameDW").innerHTML = "";
    }

    that.inputValueBirthdayDW = function () {
        document.getElementById("inputBirthdayDW").innerHTML = "";
    }

    that.inputValuePhoneDW = function () {
        document.getElementById("inputPhoneDW").innerHTML = "";
    }

    that.inputValueEmailDW = function () {
        document.getElementById("inputEmailDW").innerHTML = "";
    }


    that.crtDW = function () {
        if (that.additional.checkValid() === true) {
            let out = document.getElementById("outDW");
            out.innerHTML = "";

            let newDogWalker = {
                lastName: document.getElementById("lastNameDW").value,
                name: document.getElementById("nameDW").value,
                middleName: document.getElementById("middleNameDW").value,
                birthDate: document.getElementById("birthDateDW").value,
                phoneNumber: document.getElementById("phoneNumberDW").value,
                email: document.getElementById("email").value
                    +document.getElementById("selectDomainsDW").value,
            }

            that.api.createDogWalker(newDogWalker, that.additional.sendSuccessCreateDogWalker)

        }
    }

    that.saveChanges = function (row) {
        let editObject = {
            uniqueId: row.children[0].textContent,
            lastName: row.children[1].children[0].value,
            name: row.children[2].children[0].value,
            middleName: row.children[3].children[0].value,
            birthDate: row.children[4].children[0].value,
            phoneNumber: row.children[5].children[0].value,
            email: row.children[6].children[0].value,
            dogWalkerStatus: row.children[7].textContent
        };

        that.api.allUpdateDogWalker(editObject, that.additional.sendSuccessSaveChanges);

    }

    that.deleteDogWalker = function (dogWalkerId, dogWalkerFullName) {
        that.api.deleteDogWalker(dogWalkerId, function () {that.additional.sendSuccessDeleteDogWalker(dogWalkerFullName)})
    }

    return that;
}



