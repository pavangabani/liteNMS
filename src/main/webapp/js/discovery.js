$(document).ready(function () {
    main.onload()
});


var main = {

    onload: function () {
        $("#myBtn").on("click", function () {
            $("#myModal").show();
        });
        $("#close").click(function () {
            $("#myModal").hide();
        });
        $("#close2").click(function () {
            $("#myModalUpdate").hide();
        });

        var request = {
            url: "Load.action",
            data: "",
            runfunction: function (data) {
                helper.adddata(data);
            },
        };
        helper.ajaxpost(request);
    },

    add: function () {
        var name = $("#name").val();
        var ip = $("#ip").val();
        var type = $("#type").val();
        var tag = $("#tag").val();
        var password = $("#password").val();
        var username = $("#username").val();
        if (helper.validate(name, ip, type, tag, username, password)) {
            var request = {
                url: "Add.action",
                data: "username=" + username + "&password=" + password + "&name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
                runfunction: function (data) {
                    helper.adddata(data);
                },
            };
            helper.ajaxpost(request);
            $('#monitor').trigger("reset");
            $("#myModal").hide();
            location.reload();
        }
    },

    addforpolling: function (that, id) {
        var name = $(that).parent().prev().prev().prev().prev().text();
        var ip = $(that).parent().prev().prev().prev().text();
        var type = $(that).parent().prev().prev().text();
        var tag = $(that).parent().prev().text();
        var request = {
            url: "AddPolling.action",
            data: "id=" + id + "&name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
            runfunction: function (data) {
                alert(data.status);
            },
        };
        helper.ajaxpost(request);
    },

    edit: function (that, id) {
        $("#myModalUpdate").show();
        var name = $(that).parent().prev().prev().prev().prev().text();
        var ip = $(that).parent().prev().prev().prev().text();
        var type = $(that).parent().prev().prev().text();
        var tag = $(that).parent().prev().text();
        $("#rawid").val(id);
        $("#updateip").val(ip);
        $("#updatename").val(name);
        $("#updatetype").val(type);
        $("#updatetag").val(tag);
        if (type == "ssh") {
            $("#updatesshdivision").show();
        } else {
            $("#updatesshdivision").hide();
        }
    },

    update: function () {
        var id = $("#rawid").val()
        var name = $("#updatename").val();
        var ip = $("#updateip").val();
        var type = $("#updatetype").val();
        var tag = $("#updatetag").val();
        var password = $("#updatepassword").val();
        var username = $("#updateusername").val();
        if (helper.validate(name, ip, type, tag, username, password)) {
            var request = {
                url: "Edit.action",
                data: "id=" + id + "&username=" + username + "&password=" + password + "&name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
                runfunction: function (data) {
                    helper.adddata(data);
                },
            };
            helper.ajaxpost(request);
            $("#myModalUpdate").hide();
            location.reload();
        }

    },

    deletemonitor: function (id) {
        if (confirm("Do you want to delete?")) {
            var request = {
                url: "Delete.action",
                data: "id=" + id,
                runfunction: function (data) {
                    alert(data.status);
                },
            };
            helper.ajaxpost(request);
            location.reload();
        }
    }
};

var helper = {

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
    },

    adddata: function (data) {
        var tabledata = "";
        $.each(data.monitorList, function () {
            tabledata += "<tr>" +
                "<td>" + this.name + "</td>" +
                "<td>" + this.ip + "</td>" +
                "<td>" + this.type + "</td>" +
                "<td>" + this.tag + "</td>" +
                "<td>" +
                "<button onclick='main.addforpolling(this," + this.id + ")' className='btn' style='margin-left: 5px'>Add</button>" +
                "<button onclick='main.edit(this," + this.id + ")' className='btn' style='margin-left: 5px'>Edit</button>" +
                "<button onclick='main.deletemonitor(" + this.id + ")' className='btn' style='margin-left: 5px'>Delete</button>" +
                "</td>" +
                "</tr>";
        });
        $("#tablebody").html(tabledata);
    },

    validate: function (name, ip, type, tag, username, password) {
        var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        if (name == "") {
            helper.customalert(".failure", "Enter Valid Name");
            return false;
        }
        if (ip == "") {
            helper.customalert(".failure", "Enter IP");
            return false;
        }
        if (!ipformat.test(ip)) {
            helper.customalert(".failure", "Enter Valid IP");
            return false;
        }
        if (tag == "") {
            helper.customalert(".failure", "Enter Tag");
            return false;
        }
        if (type != "ping" && (username == "" || password == "")) {
            helper.customalert(".failure", "Enter Username & Password");
            return false;
        }
        return true;
    },

    showssh: function () {
        if ($("#updatetype").val() == "ssh") {
            $("#updatesshdivision").show();
        } else if ($("#type").val() == "ssh") {
            $("#sshdivision").show();
        } else {
            $("#sshdivision").hide();
            $("#updatesshdivision").hide();
        }
    },

    customalert: function (id, message) {
        $(id).text(message);
        $(id).show();
        setTimeout(function () {
            $(id).hide();
        }, 2000);
    },

};

