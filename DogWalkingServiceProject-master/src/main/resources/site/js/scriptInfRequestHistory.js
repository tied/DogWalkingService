var scriptInfRequestHistory = function () {

    var that = {
        api: apiErpRoles("http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService", 120000),
        additional: additionalRequestHistoryFunctions(),
        scripts: scripts(),
    };


    that.createForm = function () {

        that.scripts.createForm();

        var col1 = document.getElementById("col1");
        var col2 = document.getElementById("col2");
        var col3 = document.getElementById("col3");

        col1.innerHTML = "Enter any";
        col2.innerHTML = "<input class=\"inputText\" placeholder=\"Enter any\" type=text id=\"uniqueIdReq\" oninput=\"scriptInfRequestHistory().tableSearch()\">";
        col3.innerHTML =
            "          <input class=\"button\" type=\"button\" onclick=\"scriptInfRequestHistory().addFromBdReqHistory()\" value=\"get all requests for History\">\n" +
            "          <input class=\"button\" type=\"button\" onclick=\"scriptInfRequestHistory().clearAllHistory()\" value=\"hide all requests\">\n" +
            "\n" +
            "          <select class=\"inputHistory\" id=\"selectHistoryOwners\" onchange=\"scriptInfRequestHistory().loadInfFromOwners()\">\n" +
            "            <option value=\"default\"> select owner </option>\n" +
            "          </select>\n" +
            "\n" +
            "          <select class=\"inputHistory\" id=\"selectHistoryDogs\" >\n" +
            "            <option value=\"default\"> select pet </option>\n" +
            "          </select>\n" +
            "\n" +
            "          <select class=\"inputHistory\" id=\"selectHistoryWalkers\" >\n" +
            "            <option value=\"default\"> select dog walker </option>\n" +
            "          </select>\n" +
            "\n" +
            "          <input class=\"button\" type=\"button\" value=\"find\" id=\"sendButtonHistory\" onclick=\"scriptInfRequestHistory().requestHistory()\">\n";

        that.loadOwners();
        that.loadPets();
        that.loadWalkers();

    }

    that.createMenu = function () {

        that.scripts.createMenu();

    }

    that.tableSearch = function () {
        var phrase = document.getElementById('uniqueIdReq');
        var table = document.getElementById('tableMain');
        var regPhrase = new RegExp(phrase.value, 'i');
        var flag = false;
        // var len = table.length;
        for (var i = 1; i < table.rows.length; i++) {
            flag = false;
            for (var j = table.rows[i].cells.length - 1; j >= 0; j--) {
                flag = regPhrase.test(table.rows[i].cells[j].innerHTML);
                if (flag) break;
            }
            if (flag) {
                table.rows[i].style.display = "";
            } else {
                table.rows[i].style.display = "none";
            }

        }
    }

    that.formFindRequest = function () {

    }

    that.addFromBdReqHistory = function () {

        that.api.allRequestWalks(that.additional.sendSuccessAddFromBdRequestHistory);

    }

    that.clearAllHistory = function () {
        let parent = document.getElementById("historyTable");
        parent.innerText = "";
    }

    that.loadInfFromOwners = function () {
        let str = document.getElementById("selectHistoryOwners").value;

        if (str !== "default") {

            that.api.ownerDogs(str, that.additional.sendSuccessLoadInfFromOwners);

        }
    }

    that.requestHistory = function () {
        let owner = document.getElementById("selectHistoryOwners").value;
        let dog = document.getElementById("selectHistoryDogs").value;
        let walker = document.getElementById("selectHistoryWalkers").value;

        that.api.getHistoryRequest(owner, dog, walker, that.additional.sendSuccessRequestHistory);

    }

    that.loadOwners = function () {

        that.api.allClients(that.additional.sendSuccessLoadOwners);

    }

    that.loadPets = function () {

        that.api.allDogs(that.additional.sendSuccessLoadPets);

    }

    that.loadWalkers = function () {

        that.api.allDogWalkers(that.additional.sendSuccessLoadWalkers);

    }

    return that;

}






