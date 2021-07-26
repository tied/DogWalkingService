// sendSuccessFindClient
// sendSuccessAllClient
// checkValid
// sendSuccessCreateClient

var additionalClientFunctions = function () {

    var that = {};

    that.sendSuccessFindClient = function(data) {
        let parent = document.getElementById("findTable");
        parent.innerHTML = "";

        let table = document.createElement("table");

        let row = document.createElement("tr");
        table.append(row);

        createTitleTable(row);

        addDataFind(data, table);

        parent.append(table);
    }

    that.sendSuccessAllClient = function (data, idEditRow) {
        let parent = document.getElementById("clientTable");
        parent.innerHTML = "";

        let table = document.createElement("table");
        table.id = "tableMain";
        let row = document.createElement("tr");
        row.classList.add("rowTh");

        createTitleTable(row);

        let col = document.createElement("th");
        col.innerHTML = "Edit";
        row.append(col);
        table.append(row);

        let tbody = document.createElement("tbody");

        addDataTable(data, tbody, idEditRow);

        table.append(tbody);

        parent.append(table);
    }

    that.checkValid = function() {
        let valid = document.getElementsByClassName("inputClient");

        let check = true;

        for (let i = 0; i < valid.length; i++) {
            if (valid[i].value === "") {
                switch (i) {
                    case 0:
                        document.getElementById("inputLastName").innerHTML = "Fill Last name!";
                        break;
                    case 1:
                        document.getElementById("inputName").innerHTML = "Fill Name!";
                        break;
                    case 2:
                        document.getElementById("inputMiddleName").innerHTML = "Fill Middle name!";
                        break;
                    case 3:
                        document.getElementById("inputBirthday").innerHTML = "Fill Birthday!";
                        break;
                    case 4:
                        document.getElementById("inputPhone").innerHTML = "Fill Phone!";
                        break;
                    case 5:
                        document.getElementById("inputEmail").innerHTML = "Fill Email!";
                        break;
                    case 6:
                        document.getElementById("inputAddress").innerHTML = "Fill Address!";
                        break;
                }
                check = false;
            }
        }
        return check;
    }

    that.sendSuccessCreateClient = function() {
        document.getElementById("lastName").value = "";
        document.getElementById("name").value = "";
        document.getElementById("middleName").value = "";
        document.getElementById("birthDate").value = "";
        document.getElementById("phoneNumber").value = "";
        document.getElementById("email").value = "";
        document.getElementById("address").value = "";

        let inf = document.getElementById("newClt");
        inf.innerText = "Client has been created!";
        inf.style.display = "block";

        setTimeout(function () {
            inf.style.display = "none";
        }, 5000);

        scriptInfClient().addFromBd();
    }

    that.sendSuccessDeleteClient = function (clientFullName) {
        scriptInfClient().addFromBd();

        let inf = document.getElementById("newClt");
        inf.innerText = "";
        inf.innerText = "Client: " + clientFullName + " deleted!";
        inf.style.display = "block";

        setTimeout(function () {
            inf.style.display = "none";
        }, 5000);
    }

    that.sendSuccessUpdateClient = function () {
        let inf = document.getElementById("newClt");
        inf.innerText = "";
        inf.innerText = "Changes saved!";
        inf.style.display = "block";

        setTimeout(function () {
            inf.style.display = "none";
        }, 5000);

        scriptInfClient().addFromBd();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

    var createTitleTable = function(row) {
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
        col8.innerHTML = "Address";
        row.append(col8);
    }

    var addDataFind = function(array, table) {
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
                    key = array.address;
                    break;
            }
            addCol.innerHTML = key;
            addRow.append(addCol);
        }
    }

    var toDate = function(value) {
        let date = new Date(value);
        return date.toLocaleDateString();
    }

    var createModeEditRow = function (array, addRow) {
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
                        key = array.address;
                        break;
                }
                if (i === 0) {
                    addCol.innerHTML = key;

                }else if(i === 4){
                    let calendar = document.createElement("input");
                    calendar.type = "date";
                    calendar.className = "form-control";
                    calendar.value = key;
                    addCol.append(calendar);
                }
                else {
                    let field = document.createElement("input");
                    field.style.width = "80%";
                    field.value = key;
                    addCol.append(field);
                }
                addRow.append(addCol);

            } else {
                addCol.append(createButtonsEditMode(addRow));
                addRow.append(addCol);
            }
        }
    }

    var createButtonsEditMode = function(row) {
        let parent = document.createElement("div");
        parent.append(cancel());
        parent.append(saveButton(row));
        return parent;
    }

    var saveButton = function(row) {
        let saveButton = document.createElement("input");
        saveButton.type = "button";
        saveButton.value = "Save";

        saveButton.addEventListener("click", function () {
            let question = confirm("Are you sure?");
            if(question) scriptInfClient().saveChanges(row);
        });
        return saveButton;
    }

    var cancel = function() {
        let cancelButton = document.createElement("input");
        cancelButton.type = "button";
        cancelButton.value = "Cancel";

        cancelButton.addEventListener("click", function () {
            let inf = document.getElementById("newClt");
            inf.innerText = "";
            inf.innerText = "Editing canceled!";
            inf.style.display = "block";

            setTimeout(function () {
                inf.style.display = "none";
            }, 5000);

            scriptInfClient().addFromBd();
        });
        return cancelButton;
    }

    var addDataTable = function (array, table, idEditRow) {
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
                                key = array[i].address;
                                break;
                        }
                        addCol.innerHTML = key;
                        addRow.append(addCol);

                    } else {
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

    var createButnEdit = function (id) {
        let buttonEdit = document.createElement("input");
        buttonEdit.classList.add("button");
        buttonEdit.type = "button";
        buttonEdit.value = "Edit";

        buttonEdit.addEventListener("click", function () {
            let idChangeButtonDiv = buttonEdit.parentElement.id;
            let row = document.getElementById("row" + idChangeButtonDiv);
            scriptInfClient().addFromBd(row.id);////////////////////////////////////////////////////////////////////////////

            let inf = document.getElementById("newClt");
            inf.innerText = "";
            inf.innerText = "Edit mode activated";
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
                let clientId = row.children[0].textContent;
                let clientFullName = row.children[1].textContent + " " + row.children[2].textContent;
                scriptInfClient().deleteClient(clientId, clientFullName);
            }
        });
        return buttonDelete;
    }

    return that;

}