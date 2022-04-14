var dashboardmain = {

    onload: function ()
    {
        let request = {

            url: "LoadDashboard",

            data: "",

            callback: dashboardcallback.onload,
        };
        helperMain.ajaxpost(request);
    },
};

var dashboardhelper = {

    adddata: function (data)
    {
        $("#one").append("<h1>" + data.availability[0] + "</h1>");

        $("#two").append("<h1>" + data.availability[1] + "</h1>");

        $("#three").append("<h1>" + data.availability[2] + "</h1>");

        $("#four").append("<h1>" + data.availability[3] + "</h1>");

        $("#one1").append("<h1>" + data.health[0] + "</h1>");

        $("#two1").append("<h1>" + data.health[1] + "</h1>");

        $("#three1").append("<h1>" + data.health[2] + "</h1>");

        $("#four1").append("<h1>" + data.health[3] + "</h1>");

        let tdata="";

        $.each(data.topRtt, function (){

            tdata+="<tr>"+
                "<td>"+this.ip+"</td>"+
                "<td>"+this.rtt+"</td>"+
                "</tr>";
        });

        $("#tbodytoprtt").append(tdata);

        tdata="";

        $.each(data.monitorGroup, function (){

            tdata+="<tr>"+
                "<td>"+this.type+"</td>"+
                "<td>"+this.total+"</td>"+
                "<td>"+this.up+"</td>"+
                "<td>"+this.down+"</td>"+
                "</tr>";
        });

        $("#tbodymonitorgroup").append(tdata);

        tdata="";

        $.each(data.topCpu, function (){

            tdata+="<tr>"+
                "<td>"+this.ip+"</td>"+
                "<td>"+this.cpu+"%</td>"+
                "</tr>";
        });

        $("#tbodytopcpu").append(tdata);

        tdata="";

        $.each(data.topMemory, function (){

            tdata+="<tr>"+
                "<td>"+this.ip+"</td>"+
                "<td>"+this.memory+"%</td>"+
                "</tr>";
        });

        $("#tbodytopmemory").append(tdata);

        tdata="";

        $.each(data.topDisk, function (){

            tdata+="<tr>"+
                "<td>"+this.ip+"</td>"+
                "<td>"+this.disk+"%</td>"+
                "</tr>";
        });

        $("#tbodytopdisk").append(tdata);
    },
};

var dashboardcallback = {

    onload: function (data)
    {
        dashboardhelper.adddata(data);
    },
}