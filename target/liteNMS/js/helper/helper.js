var helperMain={

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
            timeout: 15000
        });
    },
};