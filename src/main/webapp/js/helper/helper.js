var helperMain={

    ajaxpost: function (request)
    {
        $.ajax({

            type: 'POST',

            url: request.url,

            data: request.data,

            success: function (data)
            {
                let callbacks;

                if(request.callback!==undefined)
                {
                    callbacks = $.Callbacks();

                    callbacks.add(request.callback);

                    callbacks.fire(data);

                    callbacks.remove(request.callback);
                }
            },
            error: function ()
            {
                alert("Some error occured.");
            },
            timeout: 15000
        });
    },
};