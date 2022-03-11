$(document).ready(function () {
    $("#close").click(function () {
        $("#myModalStatistic").hide();
    });

    $.ajax({
        type: "GET",
        url: "LoadPolling.action",
        success: function (data) {
            adddata(data);
        },
        error: function (data) {
            alert("Some error occured.");
        }
    });

})

function showstatistic(that,id){
    var type=$(that).parent().prev().prev().prev().text();
    if(type=="ssh"){
        showsshdata(id,type);
    }else {
        showpingdata(id,type);
    }
}

function deletemonitor(id) {
    var request = {
        url: "DeletePolling.action",
        data: "id=" + id,
        runfunction: function (data) {
            alert(data.status);
        },
    };
    ajaxpost(request);
    location.reload();
}

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
    $.each(data.pollingMonitorBeanList, function () {
        tabledata += "<tr>" +
            "<td>" + this.name + "</td>" +
            "<td>" + this.ip + "</td>" +
            "<td>" + this.type + "</td>" +
            "<td>" + this.tag + "</td>" +
            "<td>" + this.availability + "</td>" +
            "<td>" +
            "<button onclick='showstatistic(this,"+ this.id +")' className='btn' style='margin-left: 5px'>Show</button>" +
            "<button onclick='deletemonitor("+this.id+")' className='btn' style='margin-left: 5px'>Delete</button>" +
            "</td>" +
            "</tr>";
    });
    $("#tablebody").html(tabledata);
}

function chart(data){
    var xValues = ["Up", "Down"];
    var yValues = data.pingStatistic.pie;
    var barColors = [
        "blue", "orange"
    ];

    var pie=new Chart("pie", {
        type: "doughnut",
        data: {
            labels: xValues,
            datasets: [{
                backgroundColor: barColors,
                data: yValues
            }]
        },
        options: {
            title: {
                display: true,
                text: "Monitor on for Last 24 Hours"
            }
        }
    });
    pie.update();

    var xValues2 = data.pingStatistic.barx;
    var yValues2 = data.pingStatistic.bary;
    var barColors2 = ["orange","orange","orange","orange","orange","orange","orange","orange","orange","orange"];

    var bar=new Chart("bar", {
        type: "bar",
        data: {
            labels: xValues2,
            datasets: [{
                backgroundColor: barColors2,
                data: yValues2
            }]
        },
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: "Last 10 polling receive packet"
            }
        }
    });
    bar.update();
}

function chart2(data){
    var xValues = ["Up", "Down"];
    var yValues = data.sshStatistic.pie;
    var barColors = [
        "blue", "orange"
    ];

    var pie=new Chart("pie", {
        type: "doughnut",
        data: {
            labels: xValues,
            datasets: [{
                backgroundColor: barColors,
                data: yValues
            }]
        },
        options: {
            title: {
                display: true,
                text: "Monitor on for Last 24 Hours"
            }
        }
    });
    pie.update();

    var xValues2 = data.sshStatistic.barx;
    var yValues2 = data.sshStatistic.bary;
    var barColors2 = ["orange","orange","orange","orange","orange","orange","orange","orange","orange","orange"];

    var bar=new Chart("bar", {
        type: "bar",
        data: {
            labels: xValues2,
            datasets: [{
                backgroundColor: barColors2,
                data: yValues2
            }]
        },
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: "Last 10 polling cpu usage"
            }
        }
    });
    bar.update();
}

function showsshdata(id,type){
    var request={
        url:"PollingStatistic.action",
        data:"id="+id+"&type="+type,
        runfunction:function (data){
            chart2(data);
            $("#matrix1").text("CPU: "+data.sshStatistic.matrix[0]+"%");
            $("#matrix2").text("Memory: "+data.sshStatistic.matrix[1]+"%");
            $("#matrix3").text("Disk: "+data.sshStatistic.matrix[2]+"%");
            $("#matrix4").text("Uptime: "+data.sshStatistic.matrix[3]);
        }
    }
    ajaxpost(request);
    $("#myModalStatistic").show();
}

function showpingdata(id,type){

    var request={
        url:"PollingStatistic.action",
        data:"id="+id+"&type="+type,
        runfunction:function (data){
            chart(data);
            $("#matrix1").text("Sent Packet: "+data.pingStatistic.matrix[0]);
            $("#matrix2").text("Receive Packet: "+data.pingStatistic.matrix[1]);
            $("#matrix3").text("Packet Loss: "+data.pingStatistic.matrix[2]);
            $("#matrix4").text("RTT(ms): "+data.pingStatistic.matrix[3]);
        }
    }
    ajaxpost(request);
    $("#myModalStatistic").show();
}