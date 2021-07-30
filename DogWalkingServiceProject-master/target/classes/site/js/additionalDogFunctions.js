// sendSuccessFindDog
// sendSuccessAllDogs
// checkValid
// sendSuccessCreateDog

var additionalDogFunctions = function () {

    var that = {};

    that.sendSuccessFindDog = function (data) {
        let parent = document.getElementById("findTableD");
        parent.innerHTML = "";

        let table = document.createElement("table");
        let row = document.createElement("tr");
        table.append(row);

        createTitleTableD(row);

        addDataFindD(data, table);

        parent.append(table);
    }

    that.sendSuccessAllDogs = function (data, idEditRow) {
        let parent = document.getElementById("dogTable");
        parent.innerHTML = "";

        let table = document.createElement("table");
        table.id = "tableMain"
        let row = document.createElement("tr");
        row.classList.add("rowTh")

        createTitleTableD(row);

        let col = document.createElement("th");
        col.innerHTML = "Edit";
        row.append(col);
        table.append(row);

        let tbody = document.createElement("tbody");

        addDataTableD(data, tbody, idEditRow);

        table.append(tbody);

        parent.append(table);
    }

    that.checkValid = function () {
        let valid = document.getElementsByClassName("inputString");

        let check = true;

        for (let i = 0; i < valid.length; i++) {
            if (valid[i].value === "") {
                switch (i) {
                    case 0:
                        document.getElementById("inputDogName").innerHTML = "Fill Dog name!";
                        break;
                    case 1:
                        document.getElementById("inputGender").innerHTML = "Fill Gender!";
                        break;
                    case 2:
                        document.getElementById("inputDogBirthday").innerHTML = "Fill Birthday!";
                        break;
                    case 3:
                        document.getElementById("inputBreed").innerHTML = "Fill Breed!";
                        break;
                    case 4:
                        document.getElementById("inputColor").innerHTML = "Fill Color!";
                        break;
                    case 5:
                        document.getElementById("inputCharacter").innerHTML = "Fill Character!";
                        break;
                }
                check = false;
            }
        }
        return check;
    }

    that.sendSuccessCreateDog = function () {
        document.getElementById("dogName").value = "";
        document.getElementById("gender").value = "";
        document.getElementById("dogBirthDate").value = "";
        document.getElementById("breed").value = "";
        document.getElementById("color").value = "";
        document.getElementById("dogCharacter").value = "";

        let infDog = document.getElementById("newDg");
        infDog.innerText = "";
        infDog.innerText = "Dog has been created!";
        infDog.style.display = "block";

        setTimeout(function () {
            document.getElementById("newDg").style.display = "none";
        }, 5000);

        scriptInfDog().addFromBdD();
    }

    var findClient = function (id) {
        let str = id;
        let owner = "";

        let xhttp = new XMLHttpRequest();

        xhttp.open("GET",
            "http://localhost:8080/rest/dogWalkingRest/1.0/dogWalkingService/client?uniqueId=" + str, false);
        xhttp.send();

        if (xhttp.status === 200) {
            myFunction(xhttp.responseText);
        } else {
            alert(xhttp.status + ': ' + xhttp.statusText);
        }

        function myFunction(data) {

            let array = JSON.parse(data);
            owner = array.name + " " + array.lastName;
        }
        return owner;
    }

    var createTitleTableD = function (row) {
        let col1 = document.createElement("th");
        col1.innerHTML = "Unique ID";
        row.append(col1);

        let col2 = document.createElement("th");
        col2.innerHTML = "Dog name";
        row.append(col2);

        let col3 = document.createElement("th");
        col3.innerHTML = "Dog Gender";
        row.append(col3);

        let col4 = document.createElement("th");
        col4.innerHTML = "Dog Birthday";
        row.append(col4);

        let col5 = document.createElement("th");
        col5.innerHTML = "Breed";
        row.append(col5);

        let col6 = document.createElement("th");
        col6.innerHTML = "Color";
        row.append(col6);

        let col7 = document.createElement("th");
        col7.innerHTML = "Dog character";
        row.append(col7);

        let col8 = document.createElement("th");
        col8.innerHTML = "Dog status";
        row.append(col8);

        let col9 = document.createElement("th");
        col9.innerHTML = "Owner";
        row.append(col9);
    }

    var toDate = function (value) {
        let date = new Date(value);
        return date.toLocaleDateString();
    }

    var addDataFindD = function (array, table) {
        let addRow = document.createElement('tr');
        table.append(addRow);

        for (let j = 0; j < 9; j++) {
            let addCol = document.createElement('td');

            let key = array.uniqueId;

            switch (j) {
                case 1:
                    key = array.dogName;
                    break;
                case 2:
                    key = array.gender;
                    break;
                case 3:
                    key = toDate(array.dogBirthDate);
                    break;
                case 4:
                    key = array.breed;
                    break;
                case 5:
                    key = array.color;
                    break;
                case 6:
                    key = array.dogCharacter;
                    break;
                case 7:
                    key = array.dogStatus;
                    break;
                case 8:
                    key = findClient(array.ownerId);
                    break;
            }
            addCol.innerHTML = key;
            addRow.append(addCol);
        }
    }

    var createModeEditRow = function (array, addRow) {
        for (let i = 0; i < 10; i++) {
            let addCol = document.createElement('td');

            if (i < 9) {
                let key = array.uniqueId;

                switch (i) {
                    case 1:
                        key = array.dogName;
                        break;
                    case 2:
                        key = array.gender;
                        break;
                    case 3:
                        key = toDate(array.dogBirthDate);
                        break;
                    case 4:
                        key = array.breed;
                        break;
                    case 5:
                        key = array.color;
                        break;
                    case 6:
                        key = array.dogCharacter;
                        break;
                    case 7:
                        key = array.dogStatus;
                        break;
                    case 8:
                        key = findClient(array.ownerId);
                }
                if (i === 0 || i === 7) {
                    addCol.innerHTML = key;

                }else if(i === 3){
                    createCalendar(addCol);
                }
                else if(i === 8){
                    createSelect(addCol);
                }
                else {
                    createInput(addCol, key);
                }
                addRow.append(addCol);

            } else {
                addCol.append(createButtonsEditMode(addRow));
                addRow.append(addCol);
            }
        }
    }

    var createInput = function (col, key) {
        let field = document.createElement("input");
        field.style.width = "80%";
        field.value = key;
        col.append(field);
    }

    var createSelect = function (col) {
        let select = document.createElement("select");
        select.id = "selectEdit";
        scriptInfDog().loadOwners("selectEdit");
        select.style.width = "80%";
        col.append(select);
    }

    var createCalendar = function (col) {
        let calendar = document.createElement("input");
        calendar.type = "date";
        // calendar.className = "form-control";
        col.append(calendar);
    }

    var createButtonsEditMode = function (row) {
        let parent = document.createElement("div");
        parent.append(cancel());
        parent.append(saveButton(row));
        return parent;
    }

    var saveButton = function (row) {
        let saveButton = document.createElement("input");
        saveButton.classList.add("button");
        saveButton.type = "button";
        saveButton.value = "Save";

        saveButton.addEventListener("click", function () {
            let question = confirm("Are you sure?");
            if(question) scriptInfDog().saveChanges(row);
        });
        return saveButton;
    }

    that.sendSuccessSaveChanges = function () {
        let inf = document.getElementById("newDg");
        inf.innerText = "";
        inf.innerText = "Changes saved!";
        inf.style.display = "block";

        setTimeout(function () {
            inf.style.display = "none";
        }, 5000);

        scriptInfDog().addFromBdD();
    }

    var cancel = function () {
        let cancelButton = document.createElement("input");
        cancelButton.type = "button";
        cancelButton.value = "Cancel";

        cancelButton.addEventListener("click", function () {
            let inf = document.getElementById("newDg");
            inf.innerText = "";
            inf.innerText = "Editing canceled!";
            inf.style.display = "block";

            setTimeout(function () {
                inf.style.display = "none";
            }, 5000);

            scriptInfDog().addFromBdD();
        });
        return cancelButton;
    }

    var addDataTableD = function (array, table, idEditRow) {
        for (let i = 0; i < array.length; i++) {
            let addRow = document.createElement('tr');
            addRow.id = "row" + i;
            addRow.classList.add("row");
            table.append(addRow);

            if (addRow.id === idEditRow) {
                createModeEditRow(array[i], addRow);

            } else {
                for (let j = 0; j < 10; j++) {
                    let addCol = document.createElement('td');

                    if (j < 9) {
                        let key = array[i].uniqueId;

                        switch (j) {
                            case 1:
                                key = array[i].dogName;
                                break;
                            case 2:
                                key = array[i].gender;
                                break;
                            case 3:
                                key = toDate(array[i].dogBirthDate);
                                break;
                            case 4:
                                key = array[i].breed;
                                break;
                            case 5:
                                key = array[i].color;
                                break;
                            case 6:
                                key = array[i].dogCharacter;
                                break;
                            case 7:
                                key = array[i].dogStatus;
                                break;
                            case 8:
                                key = findClient(array[i].ownerId);
                                break;
                        }
                        addCol.innerHTML = key;
                        addRow.append(addCol);

                    }else {
                        addCol.append(createEdit("" + i));
                        addRow.append(addCol);
                    }
                }
            }
        }
    }

    var createEdit = function (id) {
        let parent = document.createElement("div");
        parent.id = id;
        parent.append(createButnEdit(id));
        parent.append(createButnDelete(id));
        return parent;
    }

    var createButnEdit = function () {
        let buttonEdit = document.createElement("input");
        buttonEdit.classList.add("button");
        buttonEdit.type = "button";
        buttonEdit.value = "Edit";

        buttonEdit.addEventListener("click", function () {
            let idChangeButtonDiv = buttonEdit.parentElement.id;
            let row = document.getElementById("row" + idChangeButtonDiv);
            scriptInfDog().addFromBdD(row.id);

            let inf = document.getElementById("newDg");
            inf.innerText = "";
            inf.innerText = "Edit mode activated!";
            inf.style.display = "block";

            setTimeout(function () {
                inf.style.display = "none";
            }, 5000);
        });
        return buttonEdit;
    }

    var createButnDelete = function () {
        let buttonDelete = document.createElement("input");
        buttonDelete.classList.add("button");
        buttonDelete.type = "button";
        buttonDelete.value = "Delete";

        buttonDelete.addEventListener("click", function () {
            let question = confirm("Are you sure?");

            if(question){
                let idDetButtonDiv = buttonDelete.parentElement.id;
                let row = document.getElementById("row" + idDetButtonDiv);
                let dogId = row.children[0].textContent;
                let dogFullName = row.children[1].textContent + " " + row.children[4].textContent;
                scriptInfDog().deleteDog(dogId, dogFullName);
            }
        });
        return buttonDelete;
    }

    that.sendSuccessDeleteDog = function (dogFullName) {
        scriptInfDog().addFromBdD();

        let inf = document.getElementById("newDg");
        inf.innerText = "";
        inf.innerText = "Pet: " + dogFullName + " deleted!";
        inf.style.display = "block";

        setTimeout(function () {
            inf.style.display = "none";
        }, 5000);
    }

    that.sendSuccessLoadOwners = function (data ,id) {
        if(data !== ""){
            let parent = document.getElementById(id);
            parent.innerHTML = "";

            for (let i = 0; i < data.length; i++) {
                let owner = document.createElement("option");
                owner.innerHTML = data[i].name + " " + data[i].lastName;
                owner.value = data[i].uniqueId;
                parent.append(owner);
            }
        }
    }

    return that;

}