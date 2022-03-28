<html>
<body>

<link rel="stylesheet" href="css/discoverystyle.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/a29a3484d3.js" crossorigin="anonymous" type="text/javascript"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/discovery.js"></script>
<script src="js/helper/helper.js"></script>

<jsp:include page="navigation.jsp"/>

<div class="monitorTitle">
    <h1 style="color: black">DISCOVERY</h1>
</div>
<div style="background-color:#f2f2f2;padding: 10px;">
    <table id="monitors">
        <thead>
        <tr>
            <th>Name</th>
            <th>IP</th>
            <th>Type</th>
            <th>Tag</th>
            <th>Operation</th>
        </tr>
        </thead>
        <tbody id="tablebody">
        </tbody>
    </table>
</div>

<a href="#" class="float" id="floatbtn" onclick="helper.floatbtn()">
    <i class="fa fa-plus my-float"></i>
</a>

<div id="myModal" class="modal">
    <div class="modal-content">
        <span id="close" onclick="helper.closeadd()">&times;</span>
        <h3>Add Monitor</h3>
        <div class="alert failure" style="display:none;">
            <strong>Failure! </strong> Wrong password or username.
        </div>
        <form name="monitor" id="monitor">
            <input id="name" name="name" type="text" placeholder="Name"><br>
            <input id="ip" name="ip" type="text" placeholder="IP"><br>
            <select name="type" id="type" onchange="helper.showssh()">
                <option value="ping">Ping</option>
                <option value="ssh">SSH</option>
            </select><br>
            <div id="sshdivision" style="display:none;">
                <input id="username" name="username" type="text" placeholder="Username">
                <input id="password" name="password" type="password" placeholder="Password"><br>
            </div>
            <input id="tag" name="tag" type="text" placeholder="Tag"><br>
            <button type="button" value="Check" onclick="main.add()">Add</button>
        </form>
    </div>
</div>

<div id="myModalUpdate" class="modal">
    <div class="modal-content">
        <span id="close2" onclick="helper.closeupdate()">&times;</span>
        <h3>Update</h3>
        <div class="alert failure" style="display:none;">
            <strong>Failure! </strong> Wrong password or username.
        </div>
        <form name="monitor" id="updatemonitor">
            <input id="updatename" name="name" type="text" placeholder="Name"><br>
            <input id="updateip" name="ip" type="text" placeholder="IP"><br>
            <select name="type" id="updatetype" onchange="helper.showssh()" placeholder="Type">
                <option value="ping">Ping</option>
                <option value="ssh">SSH</option>
            </select><br>
            <div id="updatesshdivision" style="display:none;">
                <input id="updateusername" name="username" type="text" placeholder="Username">
                <input id="updatepassword" name="password" type="password" placeholder="Password"><br>
            </div>
            <input id="updatetag" name="tag" type="text" placeholder="Tag"><br>
            <button type="button" value="Check" onclick="main.update()">Update</button>
            <p id="rawid" style="visibility: hidden"></p>
        </form>
    </div>
</div>

</body>
</html>