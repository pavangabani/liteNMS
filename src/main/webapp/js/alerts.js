var alertsmain = {

    onload: function ()
    {
        let request = {

            url: "AlertLoad",

            data: "",

            callback: alertscallback.onload,
        };
        helperMain.ajaxpost(request);
    },

    add: function ()
    {
        let param = $('#monitor').serializeArray().reduce(function (finalParam, currentValue)
        {
            finalParam[currentValue.name] = currentValue.value;
            return finalParam;
        }, {})

        let request = {

            url: "AlertAdd",

            data: param,

            callback: alertscallback.add,
        };
        helperMain.ajaxpost(request);

        $("#myModal").hide();

    },

    deletealert: function (that)
    {
        let id=$(that).data("id");

        let sdata = {
            id: id,
        };

        let request = {

            url: "AlertDelete",

            data: sdata,

            callback: alertscallback.deletealert,
        };
        helperMain.ajaxpost(request);
    }
};

var alertshelper = {

    adddata: function (data)
    {
        var tabaldata = "";

        $.each(data.alertsList, function ()
        {
            tabaldata += "<tr><td>" + this.name + "</td><td>" + this.status + "</td><td><button onclick='alertsmain.deletealert(this)' data-id='" + this.id + "' class='btn' style='margin-left: 5px'>Delete</button></td></tr>";
        });

        $("#tablebodyalert").html(tabaldata);
    },

    show: function ()
    {
        if ($("#type").val() == "ssh")
        {
            $("#sshshow").show();
            $("#pingshow").hide();
        } else
        {
            $("#pingshow").show();
            $("#sshshow").hide();
        }

    },

    floatbtn: function ()
    {
        $("#myModal").show();
    },

    closeadd: function ()
    {
        $("#myModal").hide();
    },

};
var alertscallback = {

    onload: function (data)
    {
        alertshelper.adddata(data);
    },

    add: function (data)
    {
        profilemain.alerts();

        toastr.success(data.status);
    },

    deletealert: function (data)
    {
        profilemain.alerts();

        toastr.success("Deleted");
    },
}

