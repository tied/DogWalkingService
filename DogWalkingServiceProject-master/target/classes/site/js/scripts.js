var scripts = function () {

    var that = {};

    that.createForm = function () {

        var parent = document.getElementById("topForm");
        parent.innerHTML =
            "  <form id=\"myFormReq\" action=\"#\">\n" +
            "    <table id=\"findReq\">\n" +
            "      <tr>\n" +
            "        <td id=\"col1\" name=\"col1\">\n" +
            "		 </td>\n" +
            "      </tr>\n" +
            "\n" +
            "      <tr>\n" +
            "        <td id=\"col2\">\n" +
            "		 </td>\n" +
            "      </tr>\n" +
            "\n" +
            "      <tr>\n" +
            "        <td id=\"col3\">\n" +
            "        </td>\n" +
            "      </tr>\n" +
            "    </table>\n" +
            "  </form>"

    }

    that.createMenu = function () {

        var parent = document.getElementById("menu");
        parent.innerHTML = "<button class=\"dropbtn\">Menu</button>\n" +
            "    <div class=\"dropdown-content\">\n" +
            "        <a href=\"/plugins/servlet/servlet-inf/site/mainPage.html\">main page</a>\n" +
            "        <a href=\"/plugins/servlet/servlet-inf/site/infClient.html\">clients</a>\n" +
            "        <a href=\"/plugins/servlet/servlet-inf/site/infDog.html\">pets</a>\n" +
            "        <a href=\"/plugins/servlet/servlet-inf/site/infDogWalker.html\">dogwalkers</a>\n" +
            "        <a href=\"/plugins/servlet/servlet-inf/site/infRequestWalk.html\">requests</a>\n" +
            "        <a href=\"/plugins/servlet/servlet-inf/site/infRequestHistory.html\">history</a>\n" +
            "    </div>"

        parent.classList.add("dropdown");

    }


    return that;

}