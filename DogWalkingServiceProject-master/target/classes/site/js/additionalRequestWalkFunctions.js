// sendSuccessFindRequestWalk
// sendSuccessAllRequestWalks
// sendSuccessOwnerDogs
// createOptionPet
// checkValid
// sendSuccessCreateRequest
// sendSuccessUpdateRequestWalk
// sendSuccessUpdateDogWalker
// sendSuccessUpdateDog
// sendSuccessUpdateFinishRequestWalk
// sendSuccessLoadAddress
// sendSuccessLoadOwners

var additionalRequestWalkFunctions = function () {

    var that = {};

    that.sendSuccessFindRequestWalk = function (data) {
        let parent = document.getElementById("findTableReq");
        parent.innerHTML = "";

        let table = document.createElement("table");
        let row = document.createElement("tr");
        table.append(row);

        createTitleTableReq(table, row);

        addDataFindReq(data, table);

        parent.append(table);
    }

    that.sendSuccessAllRequestWalks = function (data) {
        let parent = document.getElementById("requestTable");
        parent.innerHTML = "";

        let table = document.createElement("table");
        let row = document.createElement("tr");
        table.append(row);

        createTitleTableReq(table, row);

        let col = document.createElement("td");
        col.innerHTML = "Pick request";
        row.append(col);

        addDataTableReq(data, table);
        parent.append(table);

        createChartDogWalkers(data, getChartWalkers());
        createChartDogs(data, getChartDogs());
    }

    that.sendSuccessOwnerDogs = function (data, parent) {
        parent.innerHTML = "";

        if (data !== null) {
            for (let i = 0; i < data.length; i++) {
                that.createOptionPet(parent, data[i].dogName + " " + data[i].breed,
                    data[i].uniqueId);
            }
        } else {
            that.createOptionPet(parent, "no pets", "");
        }
    }

    that.createOptionPet = function (parent, strHtml, value) {
        let pet = document.createElement("option");
        pet.innerHTML = strHtml;
        pet.value = value;
        parent.append(pet);
    }

    that.checkValid = function () {
        let valid = document.getElementsByClassName("inputReq");

        let check = true;

        for (let i = 0; i < valid.length; i++) {
            if (valid[i].value === "") {
                switch (i) {
                    case 1:
                        document.getElementById("inputPet").innerHTML = "Go to pets page and create" +
                            " it for this owner!";
                        break;
                    case 2:
                        document.getElementById("inputRequestPlace").innerHTML = "Fill Request place!";
                        break;
                    case 3:
                        document.getElementById("inputTimeWalk").innerHTML = "Fill Time walk!";
                        break;
                    case 4:
                        document.getElementById("inputWalkDuration").innerHTML = "Fill Walk duration!";
                        break;
                }
                check = false;
            }
            if ((valid[0].value || valid[1].value) === "default") {
                document.getElementById("inputOwner").innerHTML = "Select Owner!";
                check = false;
            }
            // if(!(valid[4].value.match(/\d/g))){
            //     document.getElementById("inputWalkDuration").innerHTML = "Enter number!";
            //     check = false; // я поставил
            // }
        }
        return check;
    }

    that.sendSuccessCreateRequest = function () {
        document.getElementById("requestPlace").value = "";
        document.getElementById("timeWalk").value = "";
        document.getElementById("walkDuration").value = "";

        document.getElementById("newReq").style.display = "block";

        setTimeout(function () {
            document.getElementById("newReq").style.display = "none";
        }, 3000);

        scriptInfRequestWalk().addFromBdReq();
    }

    that.sendSuccessUpdateRequestWalk = function () {
        scriptInfRequestWalk().addFromBdReq();
    }

    that.sendSuccessUpdateDogWalker = function () {}

    that.sendSuccessUpdateDog = function () {}

    that.sendSuccessUpdateFinishRequestWalk = function () {
        scriptInfRequestWalk().addFromBdReq();
    }

    that.sendSuccessLoadAddress = function (data) {
        if (data !== null) {
            let address = document.getElementById("requestPlace");
            address.value = "";
            address.value = data.address;
        }
    }

    that.sendSuccessLoadOwners = function (data) {
        if (data !== "") {
            let parent = document.getElementById("selectReqOwner");
            parent.innerHTML = "";

            let owner = document.createElement("option");
            owner.innerHTML = "default";
            owner.value = "default";
            parent.append(owner);

            for (let i = 0; i < data.length; i++) {
                let ownerReq = document.createElement("option");
                ownerReq.innerHTML = data[i].name + " " + data[i].lastName;
                ownerReq.value = data[i].uniqueId;
                parent.append(ownerReq);
            }
        }
    }


    var createTitleTableReq = function (table, row) {
        let col1 = document.createElement("td");
        col1.innerHTML = "Owner";
        row.append(col1);

        let col2 = document.createElement("td");
        col2.innerHTML = "Pet";
        row.append(col2);

        let col3 = document.createElement("td");
        col3.innerHTML = "Request place";
        row.append(col3);

        let col4 = document.createElement("td");
        col4.innerHTML = "Time walk";
        row.append(col4);

        let col5 = document.createElement("td");
        col5.innerHTML = "Walk duration,min";
        row.append(col5);

        let col6 = document.createElement("td");
        col6.innerHTML = "Request status";
        row.append(col6);

        let col7 = document.createElement("td");
        col7.innerHTML = "ID request";
        row.append(col7);

        let col8 = document.createElement("td");
        col8.innerHTML = "Dog walker";
        row.append(col8);
    }

    var toDate = function (value) {
        let date = new Date(value);
        return date.toLocaleString();
    }

    var findClient = function (id) {
        let owner = "";

        let xhttp = new XMLHttpRequest();

        xhttp.open("GET",
            "http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService/client?uniqueId="
            + id, false);
        xhttp.send();

        if (xhttp.status === 200) {
            myFunction(xhttp.responseText);
        } else {
            // alert(xhttp.status + ': ' + xhttp.statusText);
            owner = "No owner";
        }

        function myFunction(data) {
            let array = JSON.parse(data);
            owner = array.name + " " + array.lastName;
        }

        return owner;
    }

    var findDog = function (id) {
        let dog = "";
        let xhttp = new XMLHttpRequest();

        xhttp.open("GET",
            "http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService/dog?uniqueId="
            + id, false);
        xhttp.send();

        if (xhttp.status === 200) {
            myFunction(xhttp.responseText);
        } else {
            dog = "No dog";
        }

        function myFunction(data) {
            if (data === "") {
                dog = "No dog";
            } else {
                let array = JSON.parse(data);
                dog = array.dogName + " " + array.breed;
            }

        }
        return dog;
    }

    var findDogWalker = function (id) {
        let dogWalker = "";

        let xhttp = new XMLHttpRequest();

        xhttp.open("GET",
            "http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService/dogWalker?uniqueId="
            + id, false);
        xhttp.send();

        if (xhttp.status === 200) {
            myFunction(xhttp.responseText);
        } else {
            dogWalker = "No dog walker";
        }

        function myFunction(data) {
            if (data === "") {
                dogWalker = "No dog walker";
            } else {
                let array = JSON.parse(data);
                dogWalker = array.lastName + " " + array.name;
            }
        }
        return dogWalker;
    }

    var addDataFindReq = function (array, table) {
        let addRow = document.createElement('tr');
        table.append(addRow);

        for (let j = 0; j < 8; j++) {
            let addCol = document.createElement('td');

            let key = findClient(array.clientId);

            switch (j) {
                case 1:
                    key = findDog(array.petId);
                    break;
                case 2:
                    key = array.requestPlace;
                    break;
                case 3:
                    key = toDate(array.timeWalk);
                    break;
                case 4:
                    key = array.walkDuration;
                    break;
                case 5:
                    key = array.requestWalkStatus;
                    break;
                case 6:
                    key = array.uniqueId;
                    break;
                case 7:
                    key = findDogWalker(array.dogWalkerId);
                    break;
            }
            addCol.innerHTML = key;
            addRow.append(addCol);
        }
    }

    var addDataTableReq = function (array, table) {
        for (let i = 0; i < array.length; i++) {
            let statusReq = "";
            let walkerId = "";
            let dogId = "";

            let addRow = document.createElement('tr');
            addRow.id = "row" + i;
            table.append(addRow);

            for (let j = 0; j < 9; j++) {
                let addCol = document.createElement('td');

                if (j < 8) {
                    let key = findClient(array[i].clientId);

                    switch (j) {
                        case 1:
                            key = findDog(array[i].petId);
                            dogId = array[i].petId;
                            break;
                        case 2:
                            key = array[i].requestPlace;
                            break;
                        case 3:
                            key = toDate(array[i].timeWalk);
                            break;
                        case 4:
                            key = array[i].walkDuration;
                            break;
                        case 5:
                            key = array[i].requestWalkStatus;
                            statusReq = key;
                            break;
                        case 6:
                            key = array[i].uniqueId;
                            break;
                        case 7:
                            key = findDogWalker(array[i].dogWalkerId);
                            walkerId = array[i].dogWalkerId;
                            break;
                    }
                    addCol.innerHTML = key;
                    addRow.append(addCol);

                } else {
                    if(statusReq === "SEARCHING_WALKER"){
                        addCol.append(createPickReq("" + i, dogId));
                    }
                    if(statusReq === "WALKING"){
                        addCol.append(createFinishReq("" + i, walkerId, dogId));
                    }
                    if(statusReq === "COMPLETED"){
                        addCol.innerHTML = "COMPLETED";
                    }
                    addRow.append(addCol);
                }
            }
        }
    }

    var getAllWalkers = function () {
        let parent = document.createElement("select");
        parent.id = "selectWalkerReq";

        let xhttp = new XMLHttpRequest();

        xhttp.open("GET",
            "http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService/allDogWalkers", false);
        xhttp.send();

        if (xhttp.status === 200) {
            myFunction(xhttp.responseText);
        }

        function myFunction(data){
            if (data !== "") {
                let array = JSON.parse(data);

                for (let i = 0; i < array.length; i++) {
                    if (array[i].dogWalkerStatus === "FREE") {
                        let walker = document.createElement("option");
                        walker.innerHTML = array[i].lastName + " " + array[i].name;
                        walker.value = array[i].uniqueId;
                        parent.append(walker);
                    }

                }
            }
            else {
                let noWalkers = document.createElement("option");
                noWalkers.innerHTML = "No Walkers";
                noWalkers.value = "No Walkers";
                parent.append(noWalkers);
            }
        }
        return parent;
    }

    var pickRequest = function (dogId) {
        let butn = document.createElement("input");
        butn.type = "button";
        butn.value = "Pick request";

        butn.addEventListener("click", function () {
            let idDivButton = butn.parentElement.id;
            let idRow = document.getElementById("row" + idDivButton);
            let idRequest = idRow.children[6].textContent;
            let idWalker = document.getElementById(idDivButton).children[0].value;

            if (!(idWalker === "")) {
                alert("Request has been picked!");
                scriptInfRequestWalk().updateReqWalk(idRequest, idWalker);
                scriptInfRequestWalk().updateDogWalker(idWalker, "BUSY");
                scriptInfRequestWalk().updateDog(dogId, "WALKING");
            } else {
                alert("The request was not selected!");
            }
        });
        return butn;
    }

    var createPickReq = function (id, dogId) {
        let parent = document.createElement("div");
        parent.id = id;
        parent.append(getAllWalkers()); // добавили селект в последнюю ячейку
        parent.append(pickRequest(dogId)); // добавили кнопку
        return parent;
    }

    var createFinishReq = function (id, walkerId, dogId){
        let parent = document.createElement("div");
        parent.id = id;

        let finishButn = document.createElement("input");
        finishButn.type = "button";
        finishButn.value = "Finish request";

        finishButn.addEventListener("click", function () {
            let idFinishButton = finishButn.parentElement.id;
            let idRow = document.getElementById("row" + idFinishButton);
            let idRequest = idRow.children[6].textContent;

            alert("Request has been completed!");
            scriptInfRequestWalk().updateFinishReqWalk(idRequest);
            scriptInfRequestWalk().updateDogWalker(walkerId, "FREE");
            scriptInfRequestWalk().updateDog(dogId, "AT_HOME");
        });
        parent.append(finishButn);

        return parent;
    }

    var getChartWalkers = function () {
        let walkers = "";
        let xhttp = new XMLHttpRequest();

        xhttp.open("GET",
            "http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService/allDogWalkers", false);
        xhttp.send();

        if (xhttp.status === 200) {
            myFunction(xhttp.responseText);
        } else {
            alert("No data!");
        }

        function myFunction(data) {
            walkers = JSON.parse(data);
        }
        return walkers;
    }

    var createChartDogWalkers = function (arrayReq, arrayWalkers) {
        let reqWalkers = [];
        let resultWalkers = [];
        let counter = 0;

        for (let i = 0; i < arrayReq.length ; i++) {
            if(arrayReq[i].requestWalkStatus === "COMPLETED") {
                reqWalkers[i] = arrayReq[i].dogWalkerId;
            }
        }

        for (let i = 0; i < arrayWalkers.length; i++) {
            resultWalkers[i] = [];
            counter = 0;

            for (let j = 0; j < 2; j++) {
                if(j === 0) {
                    resultWalkers[i][j] = findDogWalker(arrayWalkers[i].uniqueId);
                }else {
                    for (let k = 0; k < arrayReq.length; k++) {
                        if(arrayWalkers[i].uniqueId === reqWalkers[k]) {
                            counter++;
                        }
                    }
                    resultWalkers[i][j] = counter;
                }
            }
        }
        let elem = document.getElementById("chart1");
        elem.innerHTML = "";
        elem.style.width = "400px";
        elem.style.height = "400px";

        let chart = anychart.pie(resultWalkers);
        chart.title("Dog walkers completed requests");
        chart.container("chart1").draw();
    }

    var getChartDogs = function () {
        let dogs = "";
        let xhttp = new XMLHttpRequest();

        xhttp.open("GET",
            "http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService/allDogs", false);
        xhttp.send();

        if (xhttp.status === 200) {
            myFunction(xhttp.responseText);
        } else {
            alert("No data!");
        }

        function myFunction(data) {
            dogs = JSON.parse(data);
        }
        return dogs;
    }

    var createChartDogs = function (arrayReq, arrayDogs) {
        let reqDogs = [];
        let resultDogs = [];
        let counter = 0;

        for (let i = 0; i < arrayReq.length ; i++) {
            if(arrayReq[i].requestWalkStatus === "COMPLETED") {
                reqDogs[i] = arrayReq[i].petId;
            }
        }

        for (let i = 0; i < arrayDogs.length; i++) {
            resultDogs[i] = [];
            counter = 0;

            for (let j = 0; j < 2; j++) {
                if(j === 0) {
                    resultDogs[i][j] = findDog(arrayDogs[i].uniqueId);
                }else {
                    for (let k = 0; k < arrayReq.length; k++) {
                        if(arrayDogs[i].uniqueId === reqDogs[k]) {
                            counter++;
                        }
                    }
                    resultDogs[i][j] = counter;
                }
            }
        }
        let elem = document.getElementById("chart2");
        elem.innerHTML = "";
        elem.style.width = "400px";
        elem.style.height = "400px";

        let chart = anychart.column(resultDogs);
        chart.title("How many dogs walked with walkers");
        chart.container("chart2").draw();
    }

    return that;

}