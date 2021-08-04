var scriptInfClient = function() {

    var that = {
        api: apiErpRoles("http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService", 120000),
        additional: additionalClientFunctions(),
        scripts: scripts(),
    };

    that.createForm = function () {

        that.scripts.createForm();

        var col1 = document.getElementById("col1");
        var col2 = document.getElementById("col2");
        var col3 = document.getElementById("col3");

        var form = document.getElementById("myFormReq");
        form.onsubmit = function (e) {that.formFindClient(e)};

        col1.innerHTML = "Enter unique id client";
        col2.innerHTML = "<input class=\"inputText\" placeholder=\"Enter unique id client\" type=text id=\"uniqueId\">";
        col3.innerHTML =
            // "<input class=\"button\" type=submit name=\"submit\" value=\"find\" id=\"new\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfClient().formFindClientFromBtn()\" name=\"submit\" value=\"find\" id=\"new\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfClient().addFromBd()\" value=\"get all clients\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfClient().clearClient()\" value=\"hide found client\">\n" +
            "<input class=\"button\" type=\"button\" onclick=\"scriptInfClient().clearAllClient()\" value=\"hide all clients\">"

    }

    that.createMenu = function () {

        that.scripts.createMenu();

    }

    that.formFindClient = function(e) {

        e.preventDefault();

        let str = document.getElementById("uniqueId").value;

        that.api.clientByUniqueId(str, that.additional.sendSuccessFindClient);

    }

    that.formFindClientFromBtn = function () {

        var ev = new Event("submit");
        document.getElementById("myFormReq").dispatchEvent(ev);

    }

    that.addFromBd = function(idEditRow) {

        that.api.allClients(function (data) {that.additional.sendSuccessAllClient(data, idEditRow)});

    }

    that.clearClient = function() {
        let parent = document.getElementById("findTable");
        parent.innerText = "";
    }

    that.clearAllClient = function() {
        let parent = document.getElementById("clientTable");
        parent.innerText = "";
    }


    that.inputValueLastName = function() {
        document.getElementById("inputLastName").innerHTML = "";
    }

    that.inputValueName = function() {
        document.getElementById("inputName").innerHTML = "";
    }

    that.inputValueMiddleName = function() {
        document.getElementById("inputMiddleName").innerHTML = "";
    }

    that.inputValueBirthday = function() {
        document.getElementById("inputBirthday").innerHTML = "";
    }

    that.inputValuePhone = function() {
        document.getElementById("inputPhone").innerHTML = "";
    }

    that.inputValueEmail = function() {
        document.getElementById("inputEmail").innerHTML = "";
    }

    that.inputValueAddress = function() {
        document.getElementById("inputAddress").innerHTML = "";
    }


    that.crt = function() {
        if (that.additional.checkValid() === true) {
            let out = document.getElementById("out");
            out.innerHTML = "";

            let newClient = {
                lastName: document.getElementById("lastName").value,
                name: document.getElementById("name").value,
                middleName: document.getElementById("middleName").value,
                birthDate: document.getElementById("birthDate").value,
                phoneNumber: document.getElementById("phoneNumber").value,
                email: document.getElementById("email").value +
                    document.getElementById("selectDomains").value,
                address: document.getElementById("address").value,
            };

            that.api.createClient(newClient, that.additional.sendSuccessCreateClient);

        }
    }

    that.deleteClient = function (clientId, clientFullName) {

        that.api.deleteClient(clientId, function () {that.additional.sendSuccessDeleteClient(clientFullName)});

    }

    that.saveChanges = function(row) {
        let updateClient = {
            uniqueId: row.children[0].textContent,
            lastName: row.children[1].children[0].value,
            name: row.children[2].children[0].value,
            middleName: row.children[3].children[0].value,
            birthDate: row.children[4].children[0].value,
            phoneNumber: row.children[5].children[0].value,
            email: row.children[6].children[0].value,
            address: row.children[7].children[0].value,
        };

        that.api.updateClient(updateClient, that.additional.sendSuccessUpdateClient);

    }

    return that;
}
