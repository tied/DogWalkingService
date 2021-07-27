var additionalDogWalkerFunctions = function () {

    var that = {};

    that.sendSuccessFindDogWalker = function (data) {
        let parent = document.getElementById("findTableDW");
        parent.innerHTML = "";

        let table = document.createElement("table");
        let row = document.createElement("tr");

        createTitleTableDW(row);
        table.append(row);

        addDataFindDW(data, table);

        parent.append(table);
    }

    that.sendSuccessAddFromBdDW = function (data, idEditRow) {
        let parent = document.getElementById("dogWalkerTable");
        parent.innerHTML = "";

        let table = document.createElement("table");
        table.id = "tableMain";
        let row = document.createElement("tr");
        row.classList.add("rowTh");

        createTitleTableDW(row);

        let col = document.createElement("th");
        col.innerHTML = "Edit";
        row.append(col);
        table.append(row);

        let tbody = document.createElement("tbody");

        addDataTableDW(data, tbody, idEditRow);

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
                        document.getElementById("inputLastNameDW").innerHTML = "Fill Last name!";
                        break;
                    case 1:
                        document.getElementById("inputNameDW").innerHTML = "Fill Name!";
                        break;
                    case 2:
                        document.getElementById("inputMiddleNameDW").innerHTML = "Fill Middle name!";
                        break;
                    case 3:
                        document.getElementById("inputBirthdayDW").innerHTML = "Fill Birthday!";
                        break;
                    case 4:
                        document.getElementById("inputPhoneDW").innerHTML = "Fill Phone!";
                        break;
                    case 5:
                        document.getElementById("inputEmailDW").innerHTML = "Fill Email!";
                        break;
                }
                check = false;
            }
        }
        return check;
    }

    that.sendSuccessCreateDogWalker = function () {
        document.getElementById("lastNameDW").value = "";
        document.getElementById("nameDW").value = "";
        document.getElementById("middleNameDW").value = "";
        document.getElementById("birthDateDW").value = "";
        document.getElementById("phoneNumberDW").value = "";
        document.getElementById("email").value = "";

        let inf = document.getElementById("newDgWlkr");
        inf.innerText = "";
        inf.innerText = "Dog walker has been created!";
        inf.style.display = "block";

        setTimeout(function () {
            document.getElementById("newDgWlkr").style.display = "none";
        }, 3000);

        scriptInfDogWalker().addFromBdDW();
    }

    that.sendSuccessSaveChanges = function () {
        let inf = document.getElementById("newDgWlkr");
        inf.innerText = "";
        inf.innerText = "Changes saved!";
        inf.style.display = "block";

        setTimeout(function () {
            inf.style.display = "none";
        }, 5000);

        scriptInfDogWalker().addFromBdDW();
    }

    that.sendSuccessDeleteDogWalker = function (dogWalkerFullName) {
        scriptInfDogWalker().addFromBdDW();

        let inf = document.getElementById("newDgWlkr");
        inf.innerText = "";
        inf.innerText = "Dog walker: " + dogWalkerFullName + " deleted!";
        inf.style.display = "block";

        setTimeout(function () {
            inf.style.display = "none";
        }, 5000);
    }



    function createTitleTableDW(row) {
        let col1 = document.createElement("th");
        col1.innerHTML = "Unique ID";
        row.append(col1);

        let col2 = document.createElement("th");
        col2.innerHTML = "Last name";
        row.append(col2);

        let col3 = document.createElement("th");
        col3.innerHTML = "Name";
        row.append(col3);

        let col4 = document.createElement("th");
        col4.innerHTML = "Middle name";
        row.append(col4);

        let col5 = document.createElement("th");
        col5.innerHTML = "Birth date";
        row.append(col5);

        let col6 = document.createElement("th");
        col6.innerHTML = "Phone number";
        row.append(col6);

        let col7 = document.createElement("th");
        col7.innerHTML = "Email";
        row.append(col7);

        let col8 = document.createElement("th");
        col8.innerHTML = "Status";
        row.append(col8);
    }

    function addDataFindDW(array, table) {
        let addRow = document.createElement('tr');
        table.append(addRow);

        for (let j = 0; j < 8; j++) {
            let addCol = document.createElement('td');

            let key = array.uniqueId;

            switch (j) {
                case 1:
                    key = array.lastName;
                    break;
                case 2:
                    key = array.name;
                    break;
                case 3:
                    key = array.middleName;
                    break;
                case 4:
                    key = toDate(array.birthDate);
                    break;
                case 5:
                    key = array.phoneNumber;
                    break;
                case 6:
                    key = array.email;
                    break;
                case 7:
                    key = array.dogWalkerStatus;
                    break;
            }
            addCol.innerHTML = key;
            addRow.append(addCol);
        }
    }

    function toDate(value) {
        let date = new Date(value);
        return date.toLocaleDateString();
    }

    function createModeEditRow(array, addRow) {
        for (let i = 0; i < 9; i++) {
            let addCol = document.createElement('td');

            if (i < 8) {
                let key = array.uniqueId;

                switch (i) {
                    case 1:
                        key = array.lastName;
                        break;
                    case 2:
                        key = array.name;
                        break;
                    case 3:
                        key = array.middleName;
                        break;
                    case 4:
                        key = toDate(array.birthDate);
                        break;
                    case 5:
                        key = array.phoneNumber;
                        break;
                    case 6:
                        key = array.email;
                        break;
                    case 7:
                        key = array.dogWalkerStatus;
                        break;
                }
                if (i === 0 || i === 7) {
                    addCol.innerHTML = key;

                }else if(i === 4){
                    createCalendar(addCol);
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

    var createCalendar = function (col) {
        let calendar = document.createElement("input");
        calendar.type = "date";
        calendar.className = "form-control";
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
        buttonEdit.classList.add("button");
        saveButton.type = "button";
        saveButton.value = "Save";

        saveButton.addEventListener("click", function () {
            let question = confirm("Are you sure?");
            if(question) scriptInfDogWalker().saveChanges(row);
        });
        return saveButton;
    }

    var cancel = function () {
        let cancelButton = document.createElement("input");
        cancelButton.type = "button";
        cancelButton.value = "Cancel";

        cancelButton.addEventListener("click", function () {
            let inf = document.getElementById("newDgWlkr");
            inf.innerText = "";
            inf.innerText = "Editing canceled!";
            inf.style.display = "block";

            setTimeout(function () {
                inf.style.display = "none";
            }, 5000);

            scriptInfDogWalker().addFromBdDW();
        });
        return cancelButton;
    }

    var addDataTableDW = function (array, table, idEditRow) {
        for (let i = 0; i < array.length; i++) {
            let addRow = document.createElement('tr');
            addRow.id = "row" + i;
            addRow.classList.add("row");
            table.append(addRow);

            if (addRow.id === idEditRow) {
                createModeEditRow(array[i], addRow);

            } else {
                for (let j = 0; j < 9; j++) {
                    let addCol = document.createElement('td');

                    if (j < 8) {
                        let key = array[i].uniqueId;

                        switch (j) {
                            case 1:
                                key = array[i].lastName;
                                break;
                            case 2:
                                key = array[i].name;
                                break;
                            case 3:
                                key = array[i].middleName;
                                break;
                            case 4:
                                key = toDate(array[i].birthDate);
                                break;
                            case 5:
                                key = array[i].phoneNumber;
                                break;
                            case 6:
                                key = array[i].email;
                                break;
                            case 7:
                                key = array[i].dogWalkerStatus;
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
        parent.append(createButnEdit());
        parent.append(createButnDelete());
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
            scriptInfDogWalker().addFromBdDW(row.id);

            let inf = document.getElementById("newDgWlkr");
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
                let dogWalkerId = row.children[0].textContent;
                let dogWalkerFullName = row.children[1].textContent + " " + row.children[2].textContent;
                scriptInfDogWalker().deleteDogWalker(dogWalkerId, dogWalkerFullName);
            }
        });
        return buttonDelete;
    }

    return that;

}