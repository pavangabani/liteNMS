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
        helperMain.ajaxpost(request);
    },
};

var helper = {

    adddata: function (data) {

        $("#one").append("<h1>" + data.availability[0] + "</h1>");

        $("#two").append("<h1>" + data.availability[1] + "</h1>");

        $("#three").append("<h1>" + data.availability[2] + "</h1>");

        $("#four").append("<h1>" + data.availability[3] + "</h1>");
    },
};
