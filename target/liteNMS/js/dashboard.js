var dashboardmain = {

    onload: function ()
    {
        let request = {

            url: "LoadDashboard",

            data: "",

            callback: dashboardcallback.onload,
        };
        helperMain.ajaxpost(request);
    },
};

var dashboardhelper = {

    adddata: function (data)
    {
        $("#one").append("<h1>" + data.availability[0] + "</h1>");

        $("#two").append("<h1>" + data.availability[1] + "</h1>");

        $("#three").append("<h1>" + data.availability[2] + "</h1>");

        $("#four").append("<h1>" + data.availability[3] + "</h1>");
    },
};

var dashboardcallback = {

    onload: function (data)
    {
        dashboardhelper.adddata(data);
    },
}