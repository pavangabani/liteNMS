function login(){
    var username=$("#username").val();
    var password=$("#password").val();
    $.ajax({
        type : "GET",
        url : "Login.action",
        data : "username="+username+"&password="+password,
        success : function(data) {
            alert(data.status);
            if(data.status=="Success"){
                window.location.replace('welcome.jsp');
            }
        },
        error : function(data) {
            alert("Some error occured.");
        }
    });
}

