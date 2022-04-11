var socket = {

    createSocket: function ()
    {
        let protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://';

        let host = window.location.host;

        let path = '/liteNMS/server-endpoint';

        let webSocket = new WebSocket(protocol + host + path);

        webSocket.onopen = function ()
        {
            webSocket.send(sessionStorage.getItem("sessionId"));
        };

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
        if(message===""){
            toastr.info(message.data);
        }
    },
};