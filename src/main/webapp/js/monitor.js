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
    chart();
})

function showstatistic(that){
    var type=$(that).parent().prev().prev().prev().val();
    if(type=="ssh"){
        showsshdata();
    }else {
        showpingdata();
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
            "<button onclick='showstatistic(this)' className='btn' style='margin-left: 5px'>Show</button>" +
            "<button onclick='deletemonitor("+this.id+")' className='btn' style='margin-left: 5px'>Delete</button>" +
            "</td>" +
            "</tr>";
    });
    $("#tablebody").html(tabledata);
}

function chart(){
    var xValues = ["Up", "Down"];
    var yValues = [4, 2];
    var barColors = [
        "blue", "orange"
    ];

    new Chart("pie", {
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

    var xValues2 = ["10:35", "10:40", "10:45", "10:50", "10:55","10:35", "10:40", "10:45", "10:50", "10:55"];
    var yValues2 = [4,3,2,4,4,4,3,2,4,4];
    var barColors2 = ["red", "green", "blue", "orange", "brown","red", "green", "blue", "orange", "brown"];

    new Chart("bar", {
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
                text: "Last 5 polling packet loss"
            }
        }
    });
}

function showsshdata(){
    $("#myModalStatistic").show();
}

function showpingdata(){
    $("#myModalStatistic").show();
}