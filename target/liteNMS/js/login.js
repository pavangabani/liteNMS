$(document).ready(function (){
    $("#loginsubmit").click(function (event){
        login();
        event.preventDefault();
    });
});

function login(){
    var username=$("#username").val();
    var password=$("#password").val();
    $.ajax({
        type: "GET",
        data: "username="+username+"&password="+password,
        url: "Login.action",
        success: function(data){
            if(data.status=="Success"){
                window.location.replace('Dashboard.action');
            }
            else {
                $("#failure").show();
                setTimeout(function (){
                    $("#failure").hide();
                },2000);
            }

        },
        error: function(data) {
            alert("Some error occured.");
        }
    });

}

