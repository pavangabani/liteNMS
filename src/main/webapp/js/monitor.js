$(document).ready(function () {
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
            "<button onclick='show(this)' className='btn' style='margin-left: 5px'>Show</button>" +
            "<button onclick='delete(this)' className='btn' style='margin-left: 5px'>Delete</button>" +
            "</td>" +
            "</tr>";
    });
    $("#tablebody").html(tabledata);
}