$(document).ready(function ()
{
    main.onload()
});

var main = {

    onload: function ()
    {
        let request = {

            url: "Load",

            data: "",

            runfunction: function (data)
            {
                helper.adddata(data);
            },
        };
        helper.ajaxpost(request);
    },

    add: function ()
    {
        let name = $("#name").val();

        let ip = $("#ip").val();

        let type = $("#type").val();

        let tag = $("#tag").val();

        let password = $("#password").val();

        let username = $("#username").val();

        let sdata = {
            username: username,
            password: password,
            name: name,
            ip: ip,
            type: type,
            tag: tag
        };

        if (helper.validate(name, ip, type, tag, username, password))
        {
            let request = {

                url: "Add",

                data: sdata,

                runfunction: function (data)
                {
                    alert(data.status);
                },
            };
            helper.ajaxpost(request);

            $("#myModal").hide();

            location.reload();
        }
    },

    addforpolling: function (that)
    {
        let id = $(that).data("id");

        let sdata = {
            id: id
        };

        let request = {

            url: "AddPolling",

            data: sdata,

            runfunction: function (data)
            {
                alert(data.status);
            },
        };
        helper.ajaxpost(request);
    },

    edit: function (that)
    {

        $("#myModalUpdate").show();

        let id = $(that).data("id");

        let name = $(that).parent().prev().prev().prev().prev().text();

        let ip = $(that).parent().prev().prev().prev().text();

        let type = $(that).parent().prev().prev().text();

        let tag = $(that).parent().prev().text();

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
        let id = $("#rawid").val();

        let name = $("#updatename").val();

        let ip = $("#updateip").val();

        let type = $("#updatetype").val();

        let tag = $("#updatetag").val();

        let password = $("#updatepassword").val();

        let username = $("#updateusername").val();

        let sdata = {
            id: id,
            username: username,
            password: password,
            name: name,
            ip: ip,
            type: type,
            tag: tag
        };
        if (helper.validate(name, ip, type, tag, username, password))
        {
            let request = {

                url: "Edit",

                data: sdata,

                runfunction: function (data)
                {
                    helper.adddata(data);
                },
            };
            helper.ajaxpost(request);

            $("#myModalUpdate").hide();

            location.reload();
        }
    },

    deletemonitor: function (that)
    {
        let id = $(that).data("id");

        let sdata = {
            id: id,
        };

        if (confirm("Do you want to delete?"))
        {
            let request = {

                url: "Delete",

                data: sdata,

                runfunction: function (data)
                {
                    alert(data.status);
                },
            };
            helper.ajaxpost(request);

            location.reload();
        }
    }
};

var helper = {

    ajaxpost: function (request)
    {
        $.ajax({

            type: 'POST',

            url: request.url,

            data: request.data,

            success: function (data)
            {
                request.runfunction(data);
            },
            error: function ()
            {
                alert("Some error occured.");
            },
        });
    },

    adddata: function (data)
    {
        let tabledata = "";

        $.each(data.monitorList, function ()
        {
            tabledata += "<tr>" +
                "<td>" + this.name + "</td>" +
                "<td>" + this.ip + "</td>" +
                "<td>" + this.type + "</td>" +
                "<td>" + this.tag + "</td>" +
                "<td>" +
                "<button onclick='main.addforpolling(this)' data-id='" + this.id + "' class='btn' style='margin-left: 5px'>Add</button>" +
                "<button onclick='main.edit(this)' data-id='" + this.id + "' data-type='" + this.type + "' class='btn' style='margin-left: 5px'>Edit</button>" +
                "<button onclick='main.deletemonitor(this)' data-id='" + this.id + "' class='btn' style='margin-left: 5px'>Delete</button>" +
                "</td>" +
                "</tr>";
        });
        $("#tablebody").html(tabledata);
    },

    validate: function (name, ip, type, tag, username, password)
    {
        let ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[1-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        if (name == "")
        {
            helper.customalert(".failure", "Enter Valid Name");
            return false;
        }
        if (ip == "")
        {
            helper.customalert(".failure", "Enter IP");
            return false;
        }
        if (!ipformat.test(ip))
        {
            helper.customalert(".failure", "Enter Valid IP");
            return false;
        }
        if (tag == "")
        {
            helper.customalert(".failure", "Enter Tag");
            return false;
        }
        if (type != "ping" && (username == "" || password == ""))
        {
            helper.customalert(".failure", "Enter Username & Password");
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

    closeadd: function ()
    {
        $("#myModal").hide();
    },

    closeupdate: function ()
    {
        $("#myModalUpdate").hide();
    }
};

