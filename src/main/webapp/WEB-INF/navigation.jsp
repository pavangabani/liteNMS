<link rel="stylesheet" href="css/navigationstyle.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/a29a3484d3.js" crossorigin="anonymous" type="text/javascript"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
<script src="js/navigation.js"></script>
<script src="js/dashboard.js"></script>
<script src="js/discovery.js"></script>
<script src="js/monitor.js"></script>
<script src="js/alerts.js"></script>
<script src="js/helper/helper.js"></script>
<script src="js/helper/socket.js"></script>


<%
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); // HTTP 1.0
    response.setHeader("Expires","0"); // Proxies
%>

<nav class="navbar">
    <div class="logo">&nbsp;&nbsp;LiteNMS</div>
    <ul class="nav-links">
        <div class="menu">
            <a onclick="profilemain.discovery()" style="text-decoration: none;">Discovery</a>
            <a onclick="profilemain.monitor()" style="text-decoration: none;">Monitors</a>
            <a onclick="profilemain.alerts()" style="text-decoration: none;">Alerts</a>
            <a onclick="profilemain.dashboard()" style="text-decoration: none;">Dashboard</a>
            <a onclick="profilemain.profile()" style="margin-right: 20px">Profile</a>
        </div>
    </ul>
</nav>
<div id="profile" class="modalupdate" style="display: none">
    <div class="updatemodal-content">
        <span id="close" onclick="profilemain.close()">&times;</span>
        <h2>
            <p style="font-weight: bold;margin-right: 10px">Hello!</p>
            <p id="profilename"></p>
        </h2>
        <form id="Logout">
            <div class="form-field">
                <input class="profile" type="button" value="Logout" onclick="profilemain.logout()">
            </div>
        </form>
    </div>
</div>
<div id="status" class="allalert" style="display:none;">
    <p id="message" style="padding-left:30px;padding-right:50px;color: black"></p>
</div>
<div id="deletebox" class="allalert" style="display:none;position: absolute;">
    <span>Do you want to delete?</span>
    <input type="button" id="yes" value="Yes" onclick="discoveryhelper.yes()"/>
    <input type="button" id="no" value="No" onclick="discoveryhelper.no()"/>
    <p style="display: none" id="deleteid"></p>
</div>

<div id="deleteboxm" class="allalert" style="display:none;">
    <span>Do you want to delete?</span>
    <input type="button"  value="Yes" onclick="monitorhelper.yes()"/>
    <input type="button"  value="No" onclick="monitorhelper.no()"/>
    <p style="display: none" id="deleteidm"></p>
</div>
<div id="autodiscovery" class="allalert" style="display:none;"><input type="button"  value="Auto Discovery" onclick="monitorhelper.yes()"/></div>
<div id="mainDiv"></div>

