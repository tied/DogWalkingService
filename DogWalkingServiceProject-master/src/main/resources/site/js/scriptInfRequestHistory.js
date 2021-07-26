// formFindReq()
// addFromBdReqHistory()
// clearAllHistory()
// loadInfFromOwners()
// requestHistory()

var scriptInfRequestHistory = function () {

    var that = {
        api: apiErpRoles("http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService", 120000),
        additional: additionalRequestHistoryFunctions(),
        scripts: scripts(),
    };


    that.createForm = function () {
        // var parent = document.getElementById("topForm");
        // // parent.innerHTML = "";
        //
        // var form = document.createElement("form");
        // form.id = "myFormReq";
        // form.action = "#";
        // var table = document.createElement("table");
        // table.id = "findReq";
        //
        // var row1 = document.createElement("tr");
        // var col1 = document.createElement("td");
        // col1.innerHTML = "enter any";
        //
        // var row2 = document.createElement("tr");
        // var col2 = document.createElement("td");
        // var search = document.createElement("input")
        // search.type = "text";
        // search.name = "term";
        // search.id = "uniqueIdReq";
        // search.oninput = that.tableSearch;
        //
        // var row3 = document.createElement("tr");
        // var col3 = document.createElement("td");
        // var input1 = document.createElement("input");
        // input1.type = "button";
        // input1.onclick = that.addFromBdReqHistory;
        // input1.value = "get all requests for History";
        // var input2 = document.createElement("input");
        // input2.type = "button";
        // input2.onclick = that.clearAllHistory;
        // input2.value = "hide all requests";
        //
        // var select1 = document.createElement("select");
        // select1.id = "selectHistoryOwners";
        // select1.onchange = that.loadInfFromOwners;
        // var option1 = document.createElement("option");
        // option1.value = "default";
        // option1.innerHTML = "default";
        //
        // var select2 = document.createElement("select");
        // select2.id = "selectHistoryDogs";
        // var option2 = document.createElement("option");
        // option2.value = "default";
        // option2.innerHTML = "default";
        //
        // var select3 = document.createElement("select");
        // select3.id = "selectHistoryWalkers";
        // var option3 = document.createElement("option");
        // option3.value = "default";
        // option3.innerHTML = "default";
        //
        // that.loadOwners();
        // that.loadPets();
        // that.loadWalkers();
        //
        //
        // var input3 = document.createElement("input");
        // input3.type = "button";
        // input3.id = "sendButtonHistory";
        // input3.onclick = that.requestHistory;
        //
        // parent.append(form);
        // form.append(table);
        //
        // table.append(row1);
        // table.append(row2);
        // table.append(row3);
        //
        // row1.append(col1);
        // row2.append(col2);
        // col2.append(search);
        // row3.append(col3);
        // col3.append(input1);
        // col3.append(input2);
        // col3.append(select1);
        // col3.append(select2);
        // col3.append(select3);
        // col3.append(input3)

    //     var parent = document.getElementById("topForm");
    //     parent.innerHTML = "  <form id=\"myFormReq\" action=\"#\" onsubmit=\"scriptInfRequestHistory().formFindReq()\">\n" +
    //         "    <table id=\"findReq\">\n" +
    //         "      <tr>\n" +
    //         "        <td>Enter unique id request</td>\n" +
    //         "      </tr>\n" +
    //         "\n" +
    //         "      <tr>\n" +
    //         "        <td><input type=text name=\"term\" id=\"uniqueIdReq\" oninput=\"scriptInfRequestHistory().tableSearch()\"></td>\n" +
    //         "      </tr>\n" +
    //         "\n" +
    //         "      <tr>\n" +
    //         "        <td>\n" +
    //         "          <input type=\"button\" onclick=\"scriptInfRequestHistory().addFromBdReqHistory()\" value=\"get all requests for History\">\n" +
    //         "          <input type=\"button\" onclick=\"scriptInfRequestHistory().clearAllHistory()\" value=\"hide all requests\">\n" +
    //         "\n" +
    //         "          <select id=\"selectHistoryOwners\" onchange=\"scriptInfRequestHistory().loadInfFromOwners()\">\n" +
    //         "            <option value=\"default\"> default </option>>\n" +
    //         "          </select>\n" +
    //         "\n" +
    //         "          <select id=\"selectHistoryDogs\" >\n" +
    //         "            <option value=\"default\"> default </option>>\n" +
    //         "          </select>\n" +
    //         "\n" +
    //         "          <select id=\"selectHistoryWalkers\" >\n" +
    //         "            <option value=\"default\"> default </option>>\n" +
    //         "          </select>\n" +
    //         "\n" +
    //         "          <script>\n" +
    //         "            scriptInfRequestHistory().loadOwners();\n" +
    //         "            scriptInfRequestHistory().loadPets();\n" +
    //         "            scriptInfRequestHistory().loadWalkers();\n" +
    //         "          </script>\n" +
    //         "\n" +
    //         "          <input type=\"button\" id=\"sendButtonHistory\" onclick=\"scriptInfRequestHistory().requestHistory()\">\n" +
    //         "        </td>\n" +
    //         "      </tr>\n" +
    //         "    </table>\n" +
    //         "  </form>"
    //
    //
    //
    //

        that.scripts.createForm();

        var col1 = document.getElementById("col1");
        var col2 = document.getElementById("col2");
        var col3 = document.getElementById("col3");
        col1.innerHTML = "Enter unique id request";


        col2.innerHTML = "<input type=text id=\"uniqueIdReq\" oninput=\"scriptInfRequestHistory().tableSearch()\">";
        col3.innerHTML =
            "          <input type=\"button\" onclick=\"scriptInfRequestHistory().addFromBdReqHistory()\" value=\"get all requests for History\">\n" +
            "          <input type=\"button\" onclick=\"scriptInfRequestHistory().clearAllHistory()\" value=\"hide all requests\">\n" +
            "\n" +
            "          <select id=\"selectHistoryOwners\" onchange=\"scriptInfRequestHistory().loadInfFromOwners()\">\n" +
            "            <option value=\"default\"> default </option>\n" +
            "          </select>\n" +
            "\n" +
            "          <select id=\"selectHistoryDogs\" >\n" +
            "            <option value=\"default\"> default </option>\n" +
            "          </select>\n" +
            "\n" +
            "          <select id=\"selectHistoryWalkers\" >\n" +
            "            <option value=\"default\"> default </option>\n" +
            "          </select>\n" +
            "\n" +
            "          <input type=\"button\" id=\"sendButtonHistory\" onclick=\"scriptInfRequestHistory().requestHistory()\">\n";

        that.loadOwners();
        that.loadPets();
        that.loadWalkers();

    }


    that.tableSearch = function () {
        var phrase = document.getElementById('uniqueIdReq');
        var table = document.getElementById('tableHistoryRequest');
        var regPhrase = new RegExp(phrase.value, 'i');
        var flag = false;
        var len = table.length;
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






