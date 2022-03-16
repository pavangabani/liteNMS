$(document).ready(function () {
    main.onload()
});
var bar;
var pie;
var main = {

    onload: function () {
        $("#close").click(function () {
            $("#myModalStatistic").hide();

        });

        $.ajax({
            type: "GET",
            url: "LoadPolling.action",
            success: function (data) {
                helper.adddata(data);
            },
            error: function (data) {
                alert("Some error occured.");
            }
        });

    },

    showstatistic: function (that, id) {
        var type = $(that).parent().prev().prev().prev().text();
        if (type == "ssh") {
            helper.showsshdata(id, type);
        } else {
            helper.showpingdata(id, type);
        }
    },

    deletemonitor: function (id) {
        if(confirm("Do you want to delete?")){
            var request = {
                url: "DeletePolling.action",
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
        $.each(data.pollingMonitorBeanList, function () {
            tabledata += "<tr>" +
                "<td>" + this.name + "</td>" +
                "<td>" + this.ip + "</td>" +
                "<td>" + this.type + "</td>" +
                "<td>" + this.tag + "</td>" +
                "<td>" + this.availability + "</td>" +
                "<td>" +
                "<button onclick='main.showstatistic(this," + this.id + ")' className='btn' style='margin-left: 5px'>Show</button>" +
                "<button onclick='main.deletemonitor(" + this.id + ")' className='btn' style='margin-left: 5px'>Delete</button>" +
                "</td>" +
                "</tr>";
        });
        $("#tablebody").html(tabledata);
    },

    chartping: function (data) {
        var xValues = ["Up", "Down"];
        var yValues = data.pingStatistic.pie;
        var barColors = [
            "blue", "orange"
        ];

        if(pie){
            pie.destroy();
        }
        var pie = new Chart("pie", {
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

        var xValues2 = data.pingStatistic.barx;
        var yValues2 = data.pingStatistic.bary;
        var barColors2 = ["orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange"];

        if(bar){
            bar.destroy();
        }
        bar=new Chart("bar", {
            type: "bar",
            data: {
                labels: xValues2,
                datasets: [{
                    backgroundColor: barColors2,
                    data: yValues2
                }]
            },
            options: {
                legend: {display: false},
                title: {
                    display: true,
                    text: "Last 10 polling receive packet"
                },
                scales: {
                    xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Time'
                        },
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Receive Packets'
                        },
                        ticks: {
                            min: 0,
                            max: 5,
                            stepSize: 1 // <----- This prop sets the stepSize
                        }
                    }]
                }
            }
        });
    },

    chartssh: function (data) {
        var xValues = ["Up", "Down"];
        var yValues = data.sshStatistic.pie;
        var barColors = [
            "blue", "orange"
        ];

        if(pie){
            pie.destroy();
        }
        pie = new Chart("pie", {
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

        var xValues2 = data.sshStatistic.barx;
        var yValues2 = data.sshStatistic.bary;
        var barColors2 = ["orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange"];

        if(bar){
            bar.destroy();
        }
        bar = new Chart("bar", {
            type: "bar",
            data: {
                labels: xValues2,
                datasets: [{
                    backgroundColor: barColors2,
                    data: yValues2
                }]
            },
            options: {
                legend: {display: false},
                title: {
                    display: true,
                    text: "Last 10 polling cpu usage"
                },
                scales: {
                    xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Time'
                        },
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'CPU Usage'
                        },
                        ticks: {
                            min: 0,
                            max: 100,
                            stepSize: 10 // <----- This prop sets the stepSize
                        }
                    }]
                }

            }
        });
    },

    showsshdata: function (id, type) {
        var request = {
            url: "PollingStatistic.action",
            data: "id=" + id + "&type=" + type,
            runfunction: function (data) {
                helper.chartssh(data);
                $("#matrix1").html("<h3>CPU Usage: " + data.sshStatistic.matrix[0] + "%</h3>");
                $("#matrix2").html("<h3>Memory Usage: " + data.sshStatistic.matrix[1] + "%<br><br>Memory:"+data.sshStatistic.matrix[5]+"MB</h3>");
                $("#matrix3").html("<h3>Disk Usage: " + data.sshStatistic.matrix[2] + "%<br><br>Disk:"+data.sshStatistic.matrix[4]+"</h3>");
                $("#matrix4").html("<h3>Uptime : " + data.sshStatistic.matrix[3]+"</h3>");
            }
        }
        helper.ajaxpost(request);
        $("#myModalStatistic").show();
    },

    showpingdata: function (id, type) {
        var request = {
            url: "PollingStatistic.action",
            data: "id=" + id + "&type=" + type,
            runfunction: function (data) {
                helper.chartping(data);
                $("#matrix1").html("<h3>Sent Packet: " + data.pingStatistic.matrix[0]+"</h3>");
                $("#matrix2").html("<h3>Receive Packet: " + data.pingStatistic.matrix[1]+"</h3>");
                $("#matrix3").html("<h3>Packet Loss: " + data.pingStatistic.matrix[2]+"</h3>");
                $("#matrix4").html("<h3>RTT(ms): " + data.pingStatistic.matrix[3]+"</h3>");
            }
        }
        helper.ajaxpost(request);
        $("#myModalStatistic").show();
    },
};

