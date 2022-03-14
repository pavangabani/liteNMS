$(document).ready(function () {
    main.onload()
});

var main = {

    onload: function () {
        $("#loginsubmit").click(function (event) {
            var username = $("#username").val();
            var password = $("#password").val();
            if (helper.validate(username, password)) {
                main.login(username, password);
            } else {
                helper.customalert("#failure");
            }
            event.preventDefault();
        });
    },

    login: function (username, password) {
        var request = {
            data: "username=" + username + "&password=" + password,
            url: "Login.action",
            runfunction: function (data) {
                if (data.status == "Success") {
                    window.location.replace('Dashboard.action');
                } else {
                    helper.customalert("#failure")
                }
            }
        };
        helper.ajaxpost(request);
    }
};

var helper = {

    customalert: function (id) {
        $(id).show();
        setTimeout(function () {
            $(id).hide();
        }, 2000);
    },

    validate: function (username, password) {
        if (username == "" || password == "") {
            return false;
        } else {
            return true;
        }
    },

    ajaxpost: function (request) {
        $.ajax({
            type: 'POST',
            url: request.url,
            data: request.data,
            success: function (data) {
                request.runfunction(data);
            },
            error: function (data) {
                alert("Some error occured.");
            }
        });
    }
};
