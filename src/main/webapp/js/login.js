var main = {

    login: function ()
    {
        let username = $("#username").val();

        let password = $("#password").val();

        if (helper.validate(username, password))
        {
            let sdata = {

                username: username,

                password: password
            };
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

        } else
        {
            helper.customalert("#failure");
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
        if (helper.validate(registerusername, registerpassword))
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

            $("#registerform").trigger("reset");

            $("#register").hide();

        } else
        {
            helper.customalert("#registerfailure");
        }
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

    close: function ()
    {
        $("#register").hide();
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
            error: function ()
            {
                alert("Some error occured.");
            },
            timeout:10000
        });
    }
};
