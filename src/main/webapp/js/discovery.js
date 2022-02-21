function ajaxpost(request){
    $.ajax({
        type:'POST',
        url: request.url,
        data: request.data,
        success: function (data){
            request.runfunction(data);
        },
        error: function (data) {
            alert("Some error occured.");
        }
    });
}

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
    var request={
      url:"MonitorLoad.action",
      data:"",
      runfunction:function (data){
          adddata(data);
      },
    };
    ajaxpost(request);
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
            var request={
                url:"AddMonitor.action",
                data:"username=" + username + "&password=" + password + "&name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
                runfunction:function (data){
                    adddata(data);
                },
            };
            ajaxpost(request);
        } else {
            var request={
                url:"AddMonitor.action",
                data: "name=" + name + "&ip=" + ip + "&type=" + type + "&tag=" + tag,
                runfunction:function (data) {
                    adddata(data);
                },
            };
            ajaxpost(request);
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
            "<button onclick='addforpolling(this,"+this.id+")' className='btn' style='margin-left: 5px'>Add</button>" +
            "<button onclick='edit("+this+")' className='btn' style='margin-left: 5px'>Edit</button>" +
            "<button onclick='deletemonitor("+this.id+")' className='btn' style='margin-left: 5px'>Delete</button>" +
            "</td>" +
            "</tr>";
    });
    $("#tablebody").html(tabledata);
}
function deletemonitor(id){
    var request={
        url:"DeleteMonitor.action",
        data: "id="+id,
        runfunction:function (data){
            alert(data.status);
        },
    };
    ajaxpost(request);
}

function addforpolling(that,id){
    var name=$(that).parent().prev().prev().prev().prev().text();
    var ip=$(that).parent().prev().prev().prev().text();
    var type=$(that).parent().prev().prev().text();
    var tag=$(that).parent().prev().text();
    var request={
        url:"AddPolling.action",
        data: "id="+id+"&name="+name+"&ip="+ip+"&type="+type+"&tag="+tag,
        runfunction:function (data){
            alert(data.status);
        },
    };
    ajaxpost(request);
}
function run(that){
    var ip=$(that).parent().prev().prev().prev().text();
    var type=$(that).parent().prev().prev().text();
    var request={
        url:"Discovery.action",
        data: "ip="+ip+"&type="+type,
        runfunction:function (data){
            alert(data.status);
        },
    };
    ajaxpost(request);
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