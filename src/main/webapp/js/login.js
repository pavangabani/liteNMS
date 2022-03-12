$(document).ready(function (){
    $("#loginsubmit").click(function (event){
        var username=$("#username").val();
        var password=$("#password").val();
        if(validate(username,password)){
            login(username,password);
        }
        else {
            customalert("#failure");
        }
        event.preventDefault();
    });
});

function login(username,password){
    $.ajax({
        type: "POST",
        data: "username="+username+"&password="+password,
        url: "Login.action",
        success: function(data){
            if(data.status=="Success"){
                window.location.replace('Dashboard.action');
            }
            else {
                customalert("#failure")
            }
        },
        error: function(data) {
            alert("Some error occured.");
        }
    });
}
function customalert(id) {
    $(id).show();
    setTimeout(function () {
        $(id).hide();
    }, 2000);
}
function validate(username,password){
    if(username=="" || password==""){
        return false;
    } else {
        return true;
    }
}