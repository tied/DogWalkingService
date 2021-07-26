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



    return that;

}