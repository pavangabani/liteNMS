var socket = {

    createSocket: function ()
    {
        var webSocket = new WebSocket("wss://localhost:8443/liteNMS/server-endpoint");

        webSocket.onmessage = function (message)
        {
            sockethelper.processMessage(message);
        };

        webSocket.onerror = function (message)
        {
            sockethelper.processError(message);
        };

        webSocket.onclose = function ()
        {
            socket.createSocket();
        };
    }
};
var sockethelper = {

    processMessage: function (message)
    {
        if (message.data.includes("-1"))
        {
            toastr.error(message.data.substring(2));

        } else if (message.data.includes("+1"))
        {
            toastr.success(message.data.substring(2));

        } else if (message.data.includes("+0"))
        {
            toastr.error(message.data.substring(2));

        } else
        {
            toastr.error(message.data.substring(2));
        }
    },

    processError: function (message)
    {
        toastr.info(message.data);
    }
};