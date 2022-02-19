<html>
<body>

<link rel="stylesheet" href="css/discoverystyle.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="js/discovery.js"></script>
<script src="https://kit.fontawesome.com/a29a3484d3.js" crossorigin="anonymous" type="text/javascript"></script>

<jsp:include page="navigation.jsp"/>

<div class="monitorTitle">
    <h1 style="color: black">Monitors</h1>
</div>

<table id="monitors">
    <tr>
        <th>Name</th>
        <th>IP</th>
        <th>Type</th>
        <th>Tag</th>
        <th>Operation</th>
    </tr>
    <tbody id="tablebody">
    </tbody>
</table>

<a href="#" class="float" id="myBtn">
    <i class="fa fa-plus my-float"></i>
</a>

<!-- The Modal -->
<div id="myModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <h3>Add Monitor</h3>
        <form name="monitor">
            Name: <input id="name" name="name" type="text"><br><br>
            IP: <input id="ip" name="ip" type="text"><br><br>
            Type:
            <select name="type" id="type" onchange="showssh()">
                <option value="ping">Ping</option>
                <option value="ssh">SSH</option>
            </select><br><br>
            <div id="sshdivision" style="display:none;">
                Username: <input id="username" name="username" type="text">
                Password: <input id="password" name="password" type="password"><br><br>
            </div>
            Tag: <input id="tag" name="tag" type="text"><br><br>
            <button type="button" value="Check" onclick="add()">Add</button>
        </form>
    </div>
</div>

</body>

</html>