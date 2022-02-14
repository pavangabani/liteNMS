<html>

<body>
    <script src="https://kit.fontawesome.com/a29a3484d3.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="css/discoverystyle.css">
    <jsp:include page="navigation.jsp" />
    <br><br>
    <div class="monitorTitle">
        <h1 style="color: black">Monitors</h1>
    </div><br>
    <table id="monitors">
        <tr>
            <th>Name</th>
            <th>IP</th>
            <th>Type</th>
            <th>Tag</th>
            <th>Operation</th>
        </tr>
        <tr>
            <td>Pavan</td>
            <td>10.20.40.139</td>
            <td>SSH</td>
            <td>Linux</td>
            <td>
                <button class="btn"><i class="fa-solid fa-plus"></i></button>
                <button class="btn"><i class="fa-solid fa-play"></i></button>
                <button class="btn"><i class="fa-solid fa-pen"></i></button>
                <button class="btn"><i class="fa-solid fa-trash-can"></i></button>
            </td>
        </tr>
        <tr>
            <td>Pavan</td>
            <td>10.20.40.139</td>
            <td>SSH</td>
            <td>Linux</td>
            <td>
                <button class="btn"><i class="fa-solid fa-plus"></i></button>
                <button class="btn"><i class="fa-solid fa-play"></i></button>
                <button class="btn"><i class="fa-solid fa-pen"></i></button>
                <button class="btn"><i class="fa-solid fa-trash-can"></i></button>
            </td>
        </tr>
        <tr>
            <td>Pavan</td>
            <td>10.20.40.139</td>
            <td>SSH</td>
            <td>Linux</td>
            <td>
                <button class="btn"><i class="fa-solid fa-plus"></i></button>
                <button class="btn"><i class="fa-solid fa-play"></i></button>
                <button class="btn"><i class="fa-solid fa-pen"></i></button>
                <button class="btn"><i class="fa-solid fa-trash-can"></i></button>
            </td>
        </tr>

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
                Name: <input name="name" type="text"><br><br>
                IP: <input name="ip" type="text"><br><br>
                Type:
                <select name="type" onchange="showssh()">
                    <option value="ping">Ping</option>
                    <option value="ssh">SSH</option>
                </select><br><br>
                <div id="sshdivision" style="display:none;">
                    Username: <input name="username" type="text">
                    Password: <input name="password" type="password"><br><br>
                </div>
                Tag: <input name="tag" type="text"><br><br>
                <button type="button" value="Check" onclick="validateForm()">Add</button>
            </form>
        </div>

    </div>
    <script type="text/javascript" src="js/discoveryscript.js"></script>
</body>

</html>