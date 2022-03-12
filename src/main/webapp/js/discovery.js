$(document).ready(function () {

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
            adddata(data);
        },
    };
    ajaxpost(request);
})

function add() {
    var name = $("#name").val();
    var ip = $("#ip").val();
    var type = $("#type").val();
    var tag = $("#tag").val();
    var password = $("#password").val();
    var username = $("#username").val();
    if (validate(name, ip, type, tag, username, password)) {
        var request = {
            url: "Add.action",
            data: "username=" + username + "&password=" + password + "&name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
            runfunction: function (data) {
                adddata(data);
            },
        };
        ajaxpost(request);
        $('#monitor').trigger("reset");
        $("#myModal").hide();
    }
}

function addforpolling(that, id) {
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
    ajaxpost(request);
}

function edit(that, id) {
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
}

function update() {
    var id = $("#rawid").val()
    var name = $("#updatename").val();
    var ip = $("#updateip").val();
    var type = $("#updatetype").val();
    var tag = $("#updatetag").val();
    var password = $("#updatepassword").val();
    var username = $("#updateusername").val();
    if (validate(name, ip, type, tag, username, password)) {
        var request = {
            url: "Edit.action",
            data: "id=" + id + "&username=" + username + "&password=" + password + "&name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
            runfunction: function (data) {
                adddata(data);
            },
        };
        ajaxpost(request);
    }
    $("#myModalUpdate").hide();
    location.reload();
}

function deletemonitor(id) {
    if (confirm("Do you want to delete?")) {
        var request = {
            url: "Delete.action",
            data: "id=" + id,
            runfunction: function (data) {
                alert(data.status);
            },
        };
        ajaxpost(request);
        location.reload();
    }
}

//helper functions

function ajaxpost(request) {
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

function adddata(data) {
    var tabledata = "";
    $.each(data.monitorList, function () {
        tabledata += "<tr>" +
            "<td>" + this.name + "</td>" +
            "<td>" + this.ip + "</td>" +
            "<td>" + this.type + "</td>" +
            "<td>" + this.tag + "</td>" +
            "<td>" +
            "<button onclick='addforpolling(this," + this.id + ")' className='btn' style='margin-left: 5px'>Add</button>" +
            "<button onclick='edit(this," + this.id + ")' className='btn' style='margin-left: 5px'>Edit</button>" +
            "<button onclick='deletemonitor(" + this.id + ")' className='btn' style='margin-left: 5px'>Delete</button>" +
            "</td>" +
            "</tr>";
    });
    $("#tablebody").html(tabledata);
}

function validate(name, ip, type, tag, username, password) {
    var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    if (name == "") {
        customalert(".failure", "Enter Valid Name");
    } else if (ip == "") {
        customalert(".failure", "Enter IP");
    } else if (!ipformat.test(ip)) {
        customalert(".failure", "Enter Valid IP");
    } else if (tag == "") {
        customalert(".failure", "Enter Tag");
    } else if (type!="ping" && (username == "" || password == "")) {
        customalert(".failure", "Enter Username & Password");
    } else if (true) {
        return true;
    }
}

function showssh() {
    if ($("#updatetype").val() == "ssh") {
        $("#updatesshdivision").show();
    } else if ($("#type").val() == "ssh") {
        $("#sshdivision").show();
    } else {
        $("#sshdivision").hide();
        $("#updatesshdivision").hide();
    }
}

function customalert(id, message) {
    $(id).text(message);
    $(id).show();
    setTimeout(function () {
        $(id).hide();
    }, 2000);
}

