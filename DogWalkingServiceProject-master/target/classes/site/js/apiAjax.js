var apiAjax = function (uri, timeout) {

    var that = {
        uri: uri, // uri to api servlet/rest
        timeout: timeout || 120000, // 120 seconds for default timeout
    };

    // private methods
    var tryParseJson = function (val, def) {
        try {
            if (val)
                return JSON.parse(val);
        } catch (e) {
            var a = e;
        }
        if (def)
            return def;
        return val;
    };

    var callSync = function (method, action, params) {
        var json = "";
        if (params)
            json = JSON.stringify(params);

        var res = $.ajax({
            type: method,
            url: that.uri + action,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: json,
            async: false,
        }).responseText;

        return tryParseJson(res);
    };

    var call = function (method, action, params, str, onSuccess, onError) {
        var json = "";
        if (params) {
            json = JSON.stringify(params);
        }
        // для Get запросов
        if (!str) {
            str = "";
        }
        $.ajax({
            type: method,
            url: that.uri + action + str,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: json,
            timeout: that.timeout,
            success: function (d) {
                if (onSuccess) {
                    onSuccess(d);
                }
            },
            error: function (d) {
                if (onError) {
                    onError(d);
                }
            },
        });
        return false;
    }

    // public methods
    that.post = function (action, params, onSuccess, onError) {
        return call("post", action, params, null, onSuccess, onError);
    };

    that.get = function (action, str, onSuccess, onError) {
        return call("get", action, null, str, onSuccess, onError);
    };

    that.put = function (action, params, onSuccess, onError) {
        return call("put", action, params, null, onSuccess, onError);
    }

    that.delete = function (action, str, onSuccess, onError) {
        return call("delete", action, null, str, onSuccess, onError);
    }

    that.postSync = function (action, params) {
        return callSync("post", action, params);
    };

    that.getSync = function (action, params) {
        return callSync("get", action, params);
    };

    that.postFile = function(action, formData, onSuccess, onError) {
        $.ajax({
            type: 'post',
            url: this.uri + action,
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            enctype: 'multipart/form-data',
            success: function (data) {
                if (onSuccess)
                    onSuccess(data);
            },
            error: function (data) {
                if (onError)
                    onError(data);
            }
        });
        return false;
    };

    return that;
}
