var socket = {

    createSocket: function ()
    {
        var webSocket = new WebSocket("wss://localhost:8443/liteNMS/server-endpoint");

        webSocket.onmessage = function (message)
        {
            processMessage(message);
        };

        webSocket.onerror = function (message)
        {
            processError(message);
        };

        webSocket.onclose = function ()
        {
            socket.createSocket();
        };

        function processMessage(message)
        {
            if (message.data == "-1")
            {
                toastr.info("Monitor Already Added!");

            } else if (message.data == "1")
            {
                toastr.success("Monitor Added!");

            } else if (message.data == "0")
            {
                toastr.error("Monitor is not added!");

            } else
            {
                toastr.error("Discovery Fails!");
            }
        }

        function processError(message)
        {
            toastr.info(message.data);
        }
    }
}