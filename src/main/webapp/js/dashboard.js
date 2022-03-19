$(document).ready(function () {
    main.onload()
});

var main = {

    onload: function () {
        let request = {
            url: "LoadDashboard",
            data: "",
            runfunction: function (data) {
                helper.adddata(data);
            },
        };
        helper.ajaxpost(request);
    },
};

var helper = {

    adddata: function (data) {
        $("#one").append("<h1>" + data.availability[0] + "</h1>");
        $("#two").append("<h1>" + data.availability[1] + "</h1>");
        $("#three").append("<h1>" + data.availability[2] + "</h1>");
        $("#four").append("<h1>" + data.availability[3] + "</h1>");
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
