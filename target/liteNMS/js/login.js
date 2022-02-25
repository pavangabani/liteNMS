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
            alert(data.status);
            if(data.status=="Success"){
                window.location.replace('Discovery.action');
            }
            else {
                window.location.replace('LoginFail.action');
            }

        },
        error: function(data) {
            alert("Some error occured.");
        }
    });

}

