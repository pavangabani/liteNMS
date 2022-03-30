var loginmain = {

    login: function ()
    {
        let username = $("#username").val();

        let password = $("#password").val();

        let sdata = {

            username: username,

            password: password
        };

        if (loginhelper.validate(username, password))
        {
            let request = {

                data: sdata,

                url: "Login",

                callback: logincallback.login,
            };
            helperMain.ajaxpost(request);

        } else
        {
            loginhelper.customalert("#failure");
        }
    },

    register: function ()
    {
        $("#register").show();
    },

    submit: function ()
    {
        let registerusername = $("#registerusername").val();

        let registerpassword = $("#registerpassword").val();

        let sdata = {

            username: registerusername,

            password: registerpassword
        };
        if (loginhelper.validate(registerusername, registerpassword))
        {
            let request = {

                data: sdata,

                url: "Register",

                callback: logincallback.register,
            }
            helperMain.ajaxpost(request);

            $("#registerform").trigger("reset");

            $("#register").hide();

        } else
        {
            loginhelper.customalert("#registerfailure");
        }
    },

};

var loginhelper = {

    customalert: function (id)
    {
        $(id).show();

        setTimeout(function ()
        {
            $(id).hide();

        }, 2000);
    },

    validate: function (username, password)
    {
        if (username == "" || password == "")
        {
            return false;

        } else
        {
            return true;
        }
    },

    close: function ()
    {
        $("#register").hide();
    },

};

var logincallback = {

    login: function (data)
    {
        if (data.status == "Success")
        {
            window.location.href ='Navigation';

        } else
        {
            loginhelper.customalert("#failure")
        }
    },

    register: function (data)
    {
        alert(data.status);
    }
}
