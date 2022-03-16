$(document).ready(function () {
    main.onload()
});

let main = {

    onload: function () {
        $("#loginsubmit").click(function (event) {
            let username = $("#username").val();
            let password = $("#password").val();
            if (helper.validate(username, password)) {
                main.login(username, password);
            } else {
                helper.customalert("#failure");
            }
            event.preventDefault();
        });
        $("#close").click(function () {
            $("#register").hide();
        });
    },

    login: function (username, password) {
        let request = {
            data: "username=" + username + "&password=" + password,
            url: "Login.action",
            runfunction: function (data) {
                if (data.status == "Success") {
                    window.location='Dashboard.action';
                } else {
                    helper.customalert("#failure")
                }
            }
        };
        helper.ajaxpost(request);
    },

    register: function (){
        $("#register").show();
    },

    submit: function (){
        let registerusername=$("#registerusername").val();
        let registerpassword=$("#registerpassword").val();
        if(helper.validate(registerusername, registerusername)){
            let request={
                data:"username="+registerusername+"&password="+registerpassword,
                url:"Register.action",
                runfunction: function (data){
                    alert(data.status);
                }
            }
            helper.ajaxpost(request);
        }else {
            helper.customalert("#registerfailure");
        }
        $("#registerform").trigger("reset");
        $("#register").hide();
    },

};

let helper = {

    customalert: function (id) {
        $(id).show();
        setTimeout(function () {
            $(id).hide();
        }, 2000);
    },

    validate: function (username, password) {
        if (username == "" || password == "") {
            return false;
        } else {
            return true;
        }
    },

    ajaxpost: function (request) {
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
};
