var discoverymain = {

    onload: function ()
    {
        let request = {

            url: "Load",

            data: "",

            callback: discoverycallback.onload,
        };
        helperMain.ajaxpost(request);
    },

    autodiscover: function ()
    {
        var ipWithCider = $('#ipwithcider').val();

        let param = {
            ipWithCider: ipWithCider,
        };

        let request = {

            url: "autodiscover",

            data: param,

            callback: discoverycallback.autodiscover,
        };
        helperMain.ajaxpost(request);

        $("#myModalDiscover").hide();
    },
    add: function ()
    {
        let param = $('#monitor').serializeArray().reduce(function (finalParam, currentValue)
        {
            finalParam[currentValue.name] = currentValue.value;
            return finalParam;
        }, {})

        if (discoveryhelper.validate(param.name, param.ip, param.type, param.tag, param.username, param.password))
        {
            let request = {

                url: "Add",

                data: param,

                callback: discoverycallback.add,
            };
            helperMain.ajaxpost(request);

            $("#myModal").hide();
        }
    },

    adddiscovery: function (that)
    {
        $("#au")
        let id = $(that).data("id");

        let sdata = {
            id: id
        };

        let request = {

            url: "AddPolling",

            data: sdata,

            callback: discoverycallback.adddiscovery,
        };
        helperMain.ajaxpost(request);
    },

    edit: function (that)
    {
        let id = $(that).data("id");

        let sdata = {
            id: id
        };

        let request = {

            url: "EditData",

            data: sdata,

            callback: discoverycallback.editdata,
        };
        helperMain.ajaxpost(request);

        $("#myModalUpdate").show();
    },

    update: function ()
    {
        let id = $("#rawid").val();

        let param = $('form').serializeArray().reduce(function (finalParam, currentValue)
        {
            finalParam[currentValue.name] = currentValue.value;
            return finalParam;
        }, {});

        param.id = id;

        if (discoveryhelper.validate(param.name, param.ip, param.type, param.tag, param.username, param.password))
        {
            let request = {

                url: "Edit",

                data: param,

                callback: discoverycallback.update,
            };
            helperMain.ajaxpost(request);

            $("#myModalUpdate").hide();
        }
    },

    deletemonitor: function (id)
    {
        let sdata = {
            id: id,
        };

        let request = {

            url: "Delete",

            data: sdata,

            callback: discoverycallback.deletemonitor,
        };
        helperMain.ajaxpost(request);
    }
};

var discoveryhelper = {

    adddata: function (data)
    {
        $.each(data.monitorList, function ()
        {
            table.row.add([this.name, this.ip, this.type, this.tag, "<button onclick='discoverymain.adddiscovery(this)' data-id='" + this.id + "' class='btn' style='margin-left: 5px'>Provision</button><button onclick='discoverymain.edit(this)' data-id='" + this.id + "' data-type='" + this.type + "' class='btn' style='margin-left: 5px'>Edit</button><button onclick='discoveryhelper.deleteconfirm(this)' data-id='" + this.id + "' class='btn' style='margin-left: 5px'>Delete</button>"]).draw();
        });
    },

    validate: function (name, ip, type, tag, username, password)
    {
        let ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[1-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        if (name == "")
        {
            discoveryhelper.customalert(".failure", "Enter Valid Name");
            return false;
        }
        if (ip == "")
        {
            discoveryhelper.customalert(".failure", "Enter IP");
            return false;
        }
        if (!ipformat.test(ip))
        {
            discoveryhelper.customalert(".failure", "Enter Valid IP");
            return false;
        }
        if (tag == "")
        {
            discoveryhelper.customalert(".failure", "Enter Tag");
            return false;
        }
        if (type != "ping" && (username == "" || password == ""))
        {
            discoveryhelper.customalert(".failure", "Enter Username & Password");
            return false;
        }
        return true;
    },

    showssh: function ()
    {
        if ($("#updatetype").val() == "ssh")
        {
            $("#updatesshdivision").show();

        } else if ($("#type").val() == "ssh")
        {
            $("#sshdivision").show();

        } else
        {
            $("#sshdivision").hide();

            $("#updatesshdivision").hide();
        }
    },

    customalert: function (id, message)
    {
        $(id).text(message);

        $(id).show();

        setTimeout(function ()
        {
            $(id).hide();

        }, 2000);
    },

    floatbtn: function ()
    {
        $("#myModal").show();
    },

    autodiscover: function ()
    {
        $("#myModalDiscover").show();
    },

    closeadd: function ()
    {
        $("#myModal").hide();
    },

    closediscover: function ()
    {
        $("#myModalDiscover").hide();
    },
    closeupdate: function ()
    {
        $("#myModalUpdate").hide();
    },

    deleteconfirm: function (that)
    {
        let id = $(that).data("id");

        $("#deleteid").text(id);

        $("#deletebox").show();
    },

    yes: function ()
    {
        id = $("#deleteid").text();

        discoverymain.deletemonitor(id);

        $("#deletebox").hide();
    },

    no: function ()
    {
        $("#deletebox").hide();
    }

};
var discoverycallback = {

    onload: function (data)
    {
        table = $('#monitors').DataTable({lengthMenu: [5, 10, 20, 50, 100, 200, 500]});

        discoveryhelper.adddata(data, table);
    },

    add: function (data)
    {
        profilemain.discovery();

        toastr.success(data.status);
    },

    autodiscover: function ()
    {
        profilemain.discovery();

        toastr.success(data.status);
    },
    adddiscovery: function (data)
    {
        toastr.success(data.status);

    },

    editdata: function (data)
    {
        let id = data.id;

        let name = data.name;

        let ip = data.ip;

        let type = data.type;

        let tag = data.tag;

        $("#rawid").val(id);

        $("#updateip").val(ip);

        $("#updatename").val(name);

        $("#updatetype").val(type);

        $("#updatetag").val(tag);

        if (type == "ssh")
        {
            $("#updatesshdivision").show();

        } else
        {
            $("#updatesshdivision").hide();
        }
    },

    update: function ()
    {
        profilemain.discovery();
    },

    deletemonitor: function (data)
    {
        profilemain.discovery();

        toastr.success(data.status);
    },
}

