<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <title>Send Value to Struts Action Class using Ajax</title>
    <script>
        function sendMyValue() {
            var name = $("#name").val();
            $.ajax({
                type : "GET",
                url : "sendvaluetoaction",
                data : "name=" + name,
                success : function(data) {
                    var html = "<br>" + data.msg;
                    $("#info").html(html);
                },
                error : function(data) {
                    alert("Some error occured.");
                }
            });
        }
    </script>
</head>
<body>
<h1>Send Value to Struts Action Class using Ajax</h1>
<input type="text" id="name" placeholder="Enter your name">
<button type="submit" onclick="sendMyValue()" style="background-color: #008CBA;">Submit</button>
<div id="info" style="color: green; font-size: 35px;"></div>
</body>
</html>