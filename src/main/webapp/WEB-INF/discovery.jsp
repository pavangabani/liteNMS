<html>
<body>

<link rel="stylesheet" href="css/discoverystyle.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/a29a3484d3.js" crossorigin="anonymous" type="text/javascript"></script>
<script type="text/javascript" src="js/discovery.js"></script>

<jsp:include page="navigation.jsp"/>

<div class="monitorTitle">
    <h1 style="color: black">Discovery</h1>
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

<div id="myModal" class="modal">
    <div class="modal-content">
        <span id="close">&times;</span>
        <h3>Add Monitor</h3>
        <form name="monitor">
            <input id="name" name="name" type="text" placeholder="Name"><br>
            <input id="ip" name="ip" type="text" placeholder="IP"><br>
            <select name="type" id="type" onchange="showssh()">
                <option value="ping">Ping</option>
                <option value="ssh">SSH</option>
            </select><br>
            <div id="sshdivision" style="display:none;">
                <input id="username" name="username" type="text" placeholder="Username">
                <input id="password" name="password" type="password" placeholder="Password"><br>
            </div>
            <input id="tag" name="tag" type="text" placeholder="Tag"><br>
            <button type="button" value="Check" onclick="add()">Add</button>
        </form>
    </div>
</div>

<div id="myModalUpdate" class="modal">
    <div class="modal-content">
        <span id="close2">&times;</span>
        <h3>Update</h3>
        <form name="monitor">
            <input id="updatename" name="name" type="text" placeholder="Name"><br>
            <input id="updateip" name="ip" type="text" placeholder="IP"><br>
            <select name="type" id="updatetype" onchange="showssh()" placeholder="Type">
                <option value="ping">Ping</option>
                <option value="ssh">SSH</option>
            </select><br>
            <div id="updatesshdivision" style="display:none;">
                <input id="updateusername" name="username" type="text" placeholder="Username">
                <input id="updatepassword" name="password" type="password" placeholder="Password"><br>
            </div>
            <input id="updatetag" name="tag" type="text" placeholder="Tag"><br>
            <button type="button" value="Check" onclick="update()">Update</button>
            <p id="rawid" style="visibility: hidden"></p>
        </form>
    </div>
</div>

</body>
</html>