// sendSuccessAddFromBdRequestHistory
// sendSuccessLoadInfFromOwners
// sendSuccessRequestHistory
// sendSuccessLoadOwners
// sendSuccessLoadPets
// sendSuccessLoadWalkers

var additionalRequestHistoryFunctions = function () {

    var that = {};

    that.sendSuccessAddFromBdRequestHistory = function (data) {

        that.sendSuccessRequestHistory(data);
        // let parent = document.getElementById("historyTable");
        // parent.innerHTML = "";
        //
        // let table = document.createElement("table");
        // let row = document.createElement("tr");
        // table.append(row);
        //
        // createTitleTableHistory(table, row);
        //
        // addDataTableHistory(data, table);
        // parent.append(table);

    }

    that.sendSuccessLoadInfFromOwners = function (data) {
        let parent = document.getElementById("selectHistoryDogs");
        parent.innerHTML = "";

        if (data !== null) {

            let owner = document.createElement("option");
            owner.innerHTML = "select pet";
            owner.value = "default";
            parent.append(owner);

            for (let i = 0; i < data.length; i++) {
                createOptionHistoryDogs(parent, data[i].dogName + " " + data[i].breed,
                    data[i].uniqueId);
            }
        } else {
            createOptionHistoryDogs(parent, "no pets", "");
        }
    }

    that.sendSuccessRequestHistory = function (data) {
        let parent = document.getElementById("historyTable");
        parent.innerHTML = "";

        let table = document.createElement("table");
        table.classList.add("tableMain");
        let row = document.createElement("tr");
        row.classList.add("rowTh");

        table.append(row);

        if (data != null) {
            createTitleTableHistory(row);

            let tbody = document.createElement("tbody");

            addDataTableHistory(data, tbody);

            table.append(tbody);

            parent.append(table);
        }

    }

    that.sendSuccessLoadOwners = function (data) {
        if (data !== "") {
            let parent = document.getElementById("selectHistoryOwners");
            parent.innerHTML = "";

            let owner = document.createElement("option");
            owner.innerHTML = "select owner";
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

    that.sendSuccessLoadPets = function (data) {
        if (data !== "") {
            let parent = document.getElementById("selectHistoryDogs");
            parent.innerHTML = "";

            let owner = document.createElement("option");
            owner.innerHTML = "select pet";
            owner.value = "default";
            parent.append(owner);

            for (let i = 0; i < data.length; i++) {
                let ownerReq = document.createElement("option");
                ownerReq.innerHTML = data[i].dogName + " " + data[i].breed;
                ownerReq.value = data[i].uniqueId;
                parent.append(ownerReq);
            }
        }
    }

    that.sendSuccessLoadWalkers = function (data) {
        if (data !== "") {
            let parent = document.getElementById("selectHistoryWalkers");
            parent.innerHTML = "";

            let owner = document.createElement("option");
            owner.innerHTML = "select dog walker";
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


    var createOptionHistoryDogs = function (parent, strHtml, value) {
        let pet = document.createElement("option");
        pet.innerHTML = strHtml;
        pet.value = value;
        parent.append(pet);
    }


    var createTitleTableHistory = function (row) {
        let col1 = document.createElement("th");
        col1.innerHTML = "Owner";
        row.append(col1);

        let col2 = document.createElement("th");
        col2.innerHTML = "Pet";
        row.append(col2);

        let col3 = document.createElement("th");
        col3.innerHTML = "Request place";
        row.append(col3);

        let col4 = document.createElement("th");
        col4.innerHTML = "Time walk";
        row.append(col4);

        let col5 = document.createElement("th");
        col5.innerHTML = "Walk duration,min";
        row.append(col5);

        let col6 = document.createElement("th");
        col6.innerHTML = "Request status";
        row.append(col6);

        let col7 = document.createElement("th");
        col7.innerHTML = "ID request";
        row.append(col7);

        let col8 = document.createElement("th");
        col8.innerHTML = "Dog walker";
        row.append(col8);
    }

    var addDataTableHistory = function (array, table) {
        for (let i = 0; i < array.length; i++) {
            let statusReq = "";
            let walkerId = "";
            let dogId = "";
            let key = "";


            let addRow = document.createElement('tr');
            addRow.id = "row" + i;
            addRow.classList.add("row");
            table.append(addRow);

            for (let j = 0; j < 8; j++) {

                // if (array[i].requestWalkStatus === "COMPLETED") {
                let addCol = document.createElement('td');
                switch (j) {
                    case 0:
                        key = findClient(array[i].clientId);
                        break;
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
                // }

                // if (j < 8) {
                //
                //
                //
                // } else {
                //     if(statusReq === "SEARCHING_WALKER"){
                //         addCol.append(createPickReq("" + i, dogId));
                //     }
                //     if(statusReq === "WALKING"){
                //         addCol.append(createFinishReq("" + i, walkerId, dogId));
                //     }
                //     if(statusReq === "COMPLETED"){
                //         addCol.innerHTML = "COMPLETED";
                //     }
                //     addRow.append(addCol);
                // }
            }
        }
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

    return that;

}