$(document).ready(function () {
    var request = {
        url: "LoadDashboard.action",
        data: "",
        runfunction: function (data) {
            adddata(data);
        },
    };
    ajaxpost(request);
})
function adddata(data){

    $("#one").html("<h1> Unrechable: "+data.availability[0]+"</h1>");
    $("#two").html("<h1> Up: "+data.availability[1]+"</h1>");
    $("#three").html("<h1> Down: "+data.availability[2]+"</h1>");
    $("#four").html("<h1> Total: "+data.availability[3]+"</h1>");


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