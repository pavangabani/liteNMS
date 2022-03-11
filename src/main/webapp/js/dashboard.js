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

    $("#one").append("<h1>"+data.availability[0]+"</h1>");
    $("#two").append("<h1>"+data.availability[1]+"</h1>");
    $("#three").append("<h1>"+data.availability[2]+"</h1>");
    $("#four").append("<h1>"+data.availability[3]+"</h1>");

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