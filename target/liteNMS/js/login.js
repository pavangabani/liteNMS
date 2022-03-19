$(document).ready(function ()
{
    main.onload()
});

var main = {

    onload: function ()
    {
        $("#loginsubmit").click(function (event)
        {
            let username = $("#username").val();
            let password = $("#password").val();
            if (helper.validate(username, password))
            {
                main.login(username, password);
            } else
            {
                helper.customalert("#failure");
            }
            event.preventDefault();
        });
        $("#close").click(function ()
        {
            $("#register").hide();
        });
    },

    login: function (username, password)
    {
        let sdata = {username: username, password: password};
        let request = {
            data: sdata,
            url: "Login",
            runfunction: function (data)
            {
                if (data.status == "Success")
                {
                    window.location = 'Dashboard';
                } else
                {
                    helper.customalert("#failure")
                }
            }
        };
        helper.ajaxpost(request);
    },

    register: function ()
    {
        $("#register").show();
    },

    submit: function ()
    {
        let registerusername = $("#registerusername").val();
        let registerpassword = $("#registerpassword").val();
        let sdata = {username: registerusername, password: registerpassword};
        if (helper.validate(registerusername, registerusername))
        {
            let request = {
                data: sdata,
                url: "Register",
                runfunction: function (data)
                {
                    alert(data.status);
                }
            }
            helper.ajaxpost(request);
        } else
        {
            helper.customalert("#registerfailure");
        }
        $("#registerform").trigger("reset");
        $("#register").hide();
    },

};

var helper = {

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

    ajaxpost: function (request)
    {
        $.ajax({
            type: 'POST',
            url: request.url,
            data: request.data,
            success: function (data)
            {
                request.runfunction(data);
            },
            error: function (data)
            {
                alert("Some error occured.");
            }
        });
    }
};
