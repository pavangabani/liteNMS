var profilemain = {

    profile: function ()
    {
        $("#profile").show();

        let request = {
            url: "Profile",
            data: "",
            runfunction: function (data)
            {
                $("#profilename").text(data.profileName);
            }
        };
        profilemain.ajaxpost(request);

    },

    close: function ()
    {
        $("#profile").hide();
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
            timeout: 10000
        });
    },

    logout: function (){

        window.location="Logout";

    }

};