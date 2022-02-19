$(document).ready(function () {
    var modal = document.getElementById("myModal");
    var btn = document.getElementById("myBtn");
    var span = document.getElementsByClassName("close")[0];
    btn.onclick = function () {
        modal.style.display = "block";
    }
    span.onclick = function () {
        modal.style.display = "none";
    }
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
    $.ajax({
        type: "GET",
        url: "MonitorLoad.action",
        success: function (data) {
            adddata(data);
        },
        error: function (data) {
            alert("Some error occured.");
        }
    });
})

function showssh() {
    var type = document.monitor.type.value;
    if (type == "ssh") {
        document.getElementById("sshdivision").style.display = "block";
    } else {
        document.getElementById("sshdivision").style.display = "none";
    }
}

function add() {

    var modal = document.getElementById("myModal");
    var name = $("#name").val();
    var ip = $("#ip").val();
    var type = $("#type").val();
    var tag = $("#tag").val();
    var password = $("#password").val();
    var username = $("#username").val();

    if(validate(name,ip,type,tag)){

        if (type == "ssh") {
            $.ajax({
                type: "GET",
                url: "AddMonitor.action",
                data: "username=" + username + "&password=" + password + "&name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
                success: function (data) {
                    adddata(data);
                },
                error: function (data) {
                    alert("Some error occured.");
                }
            });
        } else {
            $.ajax({
                type: "GET",
                url: "AddMonitor.action",
                data: "name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
                success: function (data) {
                    adddata(data);
                },
                error: function (data) {
                    alert("Some error occured.");
                }
            });
        }
    }
    modal.style.display = "none";
}

function adddata(data){
    var tabledata = "";
    $.each(data.monitorList, function () {
        tabledata += "<tr>" +
            "<td>" + this.name + "</td>" +
            "<td>" + this.ip + "</td>" +
            "<td>" + this.type + "</td>" +
            "<td>" + this.tag + "</td>" +
            "<td>" +
            "<button onclick='run(this)' className='btn' style='margin-left: 5px'>Run</button>" +
            "<button onclick='addforpolling(this)' className='btn' style='margin-left: 5px'>Add</button>" +
            "<button onclick='edit(this)' className='btn' style='margin-left: 5px'>Edit</button>" +
            "<button onclick='deletemonitor(this)' className='btn' style='margin-left: 5px'>Delete</button>" +
            "</td>" +
            "</tr>";
    });
    $("#tablebody").html(tabledata);
}

function addforpolling(that){
    var name=$(that).parent().prev().prev().prev().prev().text();
    var ip=$(that).parent().prev().prev().prev().text();
    var type=$(that).parent().prev().prev().text();
    var tag=$(that).parent().prev().text();
    $.ajax({
        type:'POST',
        url: 'AddPolling.action',
        data: "name="+name+"&ip="+ip+"&type="+type+"&tag="+tag,
        success: function (data){
            alert(data.status);
        },
        error: function (data) {
            alert("Some error occured.");
        }
    });

}
function run(that){
    var ip=$(that).parent().prev().prev().prev().text();
    var type=$(that).parent().prev().prev().text();
    $.ajax({
        type:'POST',
        url: 'Discovery.action',
        data: "ip="+ip+"&type="+type,
        success: function (data){
            alert(data.status);
        },
        error: function (data) {
            alert("Some error occured.");
        }
    });
}

function validate(name,ip,type,tag){

    var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

    if (name == "" || name == null) {
        alert("Enter Name Of Monitor");
    }
    else if (ip == "" || ip == null) {
        alert("Enter IP");
    }
    else if (!ipformat.test(ip)) {
        alert("Enter Valid IP");
    }
    else if (tag == "" || tag == null) {
        alert("Enter Tag");
    }
    else {
        return true;
    }
}