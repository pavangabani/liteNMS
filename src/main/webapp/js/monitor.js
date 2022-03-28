$(document).ready(function ()
{
    main.onload()
});

var bar;

var pie;

var main = {

    onload: function ()
    {
        let request = {

            url: "LoadPolling",

            data: "",

            runfunction: function (data)
            {
                table=$('#monitors').DataTable({lengthMenu: [5, 10, 20, 50, 100, 200, 500]});

                helper.adddata(data,table);
            }
        }
        helperMain.ajaxpost(request);
    },

    showstatistic: function (that)
    {
        let id = $(that).data("id");

        let type = $(that).data("type");

        if (type == "ssh")
        {
            helper.showsshdata(id, type);

        } else
        {
            helper.showpingdata(id, type);
        }
    },

    deletemonitor: function (that)
    {
        let id = $(that).data("id");

        let sdata = {
            id: id
        };

        if (confirm("Do you want to delete?"))
        {
            let request = {

                url: "DeletePolling",

                data: sdata,

                runfunction: function (data)
                {
                    alert(data.status);
                },
            };
            helperMain.ajaxpost(request);

            location.reload();
        }
    }
};

var helper = {

    adddata: function (data,table)
    {
        $.each(data.pollingMonitorBeanList, function ()
        {
            table.row.add([this.name,this.ip,this.type,this.tag,this.availability,"<button onclick='main.showstatistic(this)' data-id='" + this.id + "' data-type='" + this.type + "' className='btn' style='margin-left: 5px'>Show</button><button onclick='main.deletemonitor(this)' data-id='" + this.id + "' className='btn' style='margin-left: 5px'>Delete</button>"]).draw();
        });
    },

    chartping: function (data)
    {
        let xValues = ["Up", "Down"];

        let yValues = data.pingStatistic.pie;

        let barColors = ["blue", "orange"];

        if (pie)
        {
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

        //---------------------------------------------------------------------------------------------->

        let xValues2 = data.pingStatistic.barx;

        let yValues2 = data.pingStatistic.bary;

        let barColors2 = ["orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange"];

        if (bar)
        {
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
                            stepSize: 1
                        }
                    }]
                }
            }
        });
    },

    chartssh: function (data)
    {
        let xValues = ["Up", "Down"];

        let yValues = data.sshStatistic.pie;

        let barColors = ["blue", "orange"];

        if (pie)
        {
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

        //---------------------------------------------------------------------------------------------->

        let xValues2 = data.sshStatistic.barx;

        let yValues2 = data.sshStatistic.bary;

        let barColors2 = ["orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange"];

        if (bar)
        {
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
                            stepSize: 10
                        }
                    }]
                }

            }
        });
    },

    showsshdata: function (id, type)
    {

        let sdata = {
            id: id,
            type: type
        };

        let request = {

            url: "PollingStatistic",

            data: sdata,

            runfunction: function (data)
            {
                helper.chartssh(data);

                $("#matrix1").html("<h3>CPU Usage: " + data.sshStatistic.matrix[0] + "%</h3>");

                $("#matrix2").html("<h3>Memory Usage: " + data.sshStatistic.matrix[1] + "%<br><br>Memory:" + data.sshStatistic.matrix[5] + "G</h3>");

                $("#matrix3").html("<h3>Disk Usage: " + data.sshStatistic.matrix[2] + "%<br><br>Disk:" + data.sshStatistic.matrix[4] + "</h3>");

                $("#matrix4").html("<h3>" + data.sshStatistic.matrix[3] + "</h3>");
            }
        }
        helperMain.ajaxpost(request);

        $("#myModalStatistic").show();
    },

    showpingdata: function (id, type)
    {
        let sdata = {
            id: id,
            type: type,
        };

        let request = {

            url: "PollingStatistic",

            data: sdata,

            runfunction: function (data)
            {
                helper.chartping(data);

                $("#matrix1").html("<h3>Sent Packet: " + data.pingStatistic.matrix[0] + "</h3>");

                $("#matrix2").html("<h3>Receive Packet: " + data.pingStatistic.matrix[1] + "</h3>");

                $("#matrix3").html("<h3>Packet Loss: " + data.pingStatistic.matrix[2] + "</h3>");

                $("#matrix4").html("<h3>RTT(ms): " + data.pingStatistic.matrix[3] + "</h3>");
            }
        }
        helperMain.ajaxpost(request);

        $("#myModalStatistic").show();
    },

    close: function ()
    {
        $("#myModalStatistic").hide();
    },
};

