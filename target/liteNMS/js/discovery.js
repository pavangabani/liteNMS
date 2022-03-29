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

            callback: callback.onload,
        };
        helperMain.ajaxpost(request);
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

                callback: callback.add,
            };
            helperMain.ajaxpost(request);

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

            callback: callback.addforpolling,
        };
        helperMain.ajaxpost(request);
    },

    edit: function (that)
    {
        $("#myModalUpdate").show();

        //--------------------------------------------------------------------------------------------

        let tagElement = $(that).parent().prev();

        let typeElement = tagElement.prev();

        let ipElement = typeElement.prev();

        let nameElement = ipElement.prev();

        //-------------------------------------------------------------

        let id = $(that).data("id");

        let name = nameElement.text();

        let ip = ipElement.text();

        let type = typeElement.text();

        let tag = tagElement.text();

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

                callback: callback.update,
            };
            helperMain.ajaxpost(request);

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

                callback: callback.deletemonitor,
            };
            helperMain.ajaxpost(request);

            location.reload();
        }
    }
};

var helper = {

    adddata: function (data)
    {
        $.each(data.monitorList, function ()
        {
            table.row.add([this.name, this.ip, this.type, this.tag, "<button onclick='main.addforpolling(this)' data-id='" + this.id + "' class='btn' style='margin-left: 5px'>Provision</button><button onclick='main.edit(this)' data-id='" + this.id + "' data-type='" + this.type + "' class='btn' style='margin-left: 5px'>Edit</button><button onclick='main.deletemonitor(this)' data-id='" + this.id + "' class='btn' style='margin-left: 5px'>Delete</button>"]).draw();
        });
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
var callback =
    {
        onload: function (data)
        {
            table = $('#monitors').DataTable({lengthMenu: [5, 10, 20, 50, 100, 200, 500]});

            helper.adddata(data, table);
        },

        add: function (data)
        {
            alert(data.status);
        },

        addforpolling: function (data)
        {
            alert(data.status);
        },

        update: function (data)
        {
            helper.adddata(data);
        },

        deletemonitor: function (data)
        {
            alert(data.status);
        },
    }

