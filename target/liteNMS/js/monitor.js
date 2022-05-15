var bar;

var pie;

var monitormain = {

    onload: function ()
    {
        let request = {

            url: "LoadPolling",

            data: "",

            callback: monitorcallback.onload,
        }
        helperMain.ajaxpost(request);
    },

    showstatistic: function (that)
    {
        let id = $(that).data("id");

        let type = $(that).data("type");

        if (type == "ssh")
        {
            monitorhelper.showsshdata(id, type);

        } else
        {
            monitorhelper.showpingdata(id, type);
        }
    },

    deletemonitor: function (id)
    {
        let sdata = {
            id: id
        };

        let request = {

            url: "DeletePolling",

            data: sdata,

            callback: monitorcallback.deletemonitor,
        };
        helperMain.ajaxpost(request);
    },
    emailalerts:function () {

        var email = $('#email').val();

        let param = {
            email: email,
        };

        let request = {

            url: "emailalerts",

            data: param,

            callback: monitorcallback.emailalerts,
        };
        helperMain.ajaxpost(request);

        $("#myalertsmodal").hide();
    }
};

var monitorhelper = {

    adddata: function (data, table)
    {
        $.each(data.pollingMonitorBeanList, function ()
        {
            table.row.add([this.name, this.ip, this.type, this.tag, this.availability, "<button onclick='monitormain.showstatistic(this)' data-id='" + this.id + "' data-type='" + this.type + "' class='btn' style='margin-left: 5px'>Show</button><button onclick='monitorhelper.deleteconfirm(this)' data-id='" + this.id + "' class='btn' style='margin-left: 5px'>Delete</button>"]).draw();
        });
    },

    chartping: function (data)
    {
        monitorhelper.pie(data.pingStatistic.pie);

        baraData = {
            x: data.pingStatistic.barx,
            y: data.pingStatistic.bary,
            title: "Last 10 polling received packets",
            xlabel: "received packets",
            maxSize: 5,
            step: 4,
        };

        monitorhelper.bar(baraData);
    },

    chartssh: function (data)
    {
        monitorhelper.pie(data.sshStatistic.pie);

        baraData = {
            x: data.sshStatistic.barx,
            y: data.sshStatistic.bary,
            title: "Last 10 polling cpu usage",
            xlabel: "cpu",
            maxSize: 100,
            step: 10,
        };

        monitorhelper.bar(baraData);
    },

    showsshdata: function (id, type)
    {

        let sdata = {
            id: id, type: type
        };

        let request = {

            url: "PollingStatistic",

            data: sdata,

            callback: monitorcallback.showsshdata,
        }
        helperMain.ajaxpost(request);

        $("#myModalStatistic").show();
    },

    showpingdata: function (id, type)
    {
        let sdata = {
            id: id, type: type,
        };

        let request = {

            url: "PollingStatistic",

            data: sdata,

            callback: monitorcallback.showpingdata,
        }
        helperMain.ajaxpost(request);

        $("#myModalStatistic").show();
    },

    emailalerts : function ()
    {
        $("#myalertsmodal").show();
    },
    closealert : function ()
    {
        $("#myalertsmodal").hide();
    },
    close: function ()
    {
        $("#myModalStatistic").hide();

        $("#matrix1").html("<h3>CPU Usage: Unknown</h3>");

        $("#matrix2").html("<h3>Memory Usage: Unknown</h3>");

        $("#matrix3").html("<h3>Disk Usage: Unknown</h3>");

        $("#matrix4").html("<h3>UpTime: Unknown</h3>");

    },

    pie: function (y)
    {
        let xValues = ["Up", "Down"];

        let yValues = y;

        let barColors = ["blue", "orange"];

        if (pie)
        {
            pie.destroy();
        }

        pie = new Chart("pie", {

            type: "doughnut",

            data: {
                labels: xValues, datasets: [{
                    backgroundColor: barColors, data: yValues
                }]
            },

            options: {
                title: {
                    display: true, text: "Availability"
                }
            }
        });
    },

    bar: function (data)
    {
        let xValues2 = data.x;

        let yValues2 = data.y;

        let barColors2 = ["orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange"];

        if (bar)
        {
            bar.destroy();
        }

        bar = new Chart("bar", {

            type: "bar",

            data: {
                labels: xValues2, datasets: [{
                    backgroundColor: barColors2, data: yValues2
                }]
            },

            options: {

                legend: {display: false},

                title: {
                    display: true, text: data.title
                },

                scales: {
                    xAxes: [{
                        display: true, scaleLabel: {
                            display: true, labelString: 'Time'
                        },
                    }], yAxes: [{
                        display: true, scaleLabel: {
                            display: true, labelString: data.xlabel,
                        }, ticks: {
                            min: 0, max: data.maxSize, stepSize: data.step,
                        }
                    }]
                }
            }
        });
    },

    deleteconfirm: function (that)
    {
        let id = $(that).data("id");

        $("#deleteidm").text(id);

        $("#deleteboxm").show();

    },

    yes: function ()
    {
        id = $("#deleteidm").text();

        monitormain.deletemonitor(id);

        $("#deleteboxm").hide();
    },

    no: function ()
    {
        $("#deleteboxm").hide();
    }
};

var monitorcallback = {

    onload: function (data)
    {
        table = $('#monitors').DataTable({lengthMenu: [5, 10, 20, 50, 100, 200, 500]});

        monitorhelper.adddata(data, table);
    },

    deletemonitor: function (data)
    {
        profilemain.monitor();

        toastr.success(data.status);
    },

    showsshdata: function (data)
    {
        monitorhelper.chartssh(data);

        $("#matrix1").html("<h3>CPU Usage: " + data.sshStatistic.matrix[0] + "%</h3>");

        $("#matrix2").html("<h3>Memory Usage: " + data.sshStatistic.matrix[1] + "%<br><br>Memory:" + data.sshStatistic.matrix[5] + "G</h3>");

        $("#matrix3").html("<h3>Disk Usage: " + data.sshStatistic.matrix[2] + "%<br><br>Disk:" + data.sshStatistic.matrix[4] + "</h3>");

        $("#matrix4").html("<h3>" + data.sshStatistic.matrix[3] + "</h3>");
    },

    showpingdata: function (data)
    {
        monitorhelper.chartping(data);

        $("#matrix1").html("<h3>Sent Packet: " + data.pingStatistic.matrix[0] + "</h3>");

        $("#matrix2").html("<h3>Receive Packet: " + data.pingStatistic.matrix[1] + "</h3>");

        $("#matrix3").html("<h3>Packet Loss: " + data.pingStatistic.matrix[2] + "</h3>");

        $("#matrix4").html("<h3>RTT(ms): " + data.pingStatistic.matrix[3] + "</h3>");
    }
}

