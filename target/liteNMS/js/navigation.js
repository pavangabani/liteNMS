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
        helperMain.ajaxpost(request);

    },

    close: function ()
    {
        $("#profile").hide();
    },

    logout: function ()
    {
        window.location = "Logout";
    }

};