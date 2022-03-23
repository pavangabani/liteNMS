<link rel="stylesheet" href="css/navigationstyle.css">

<script src="js/navigation.js"></script>

<nav class="navbar">
    <div class="logo">&nbsp;&nbsp;LiteNMS</div>
    <ul class="nav-links">
        <div class="menu">
            <a href="Discovery" style="text-decoration: none;">Discovery</a>
            <a href="Monitor" style="text-decoration: none;">Monitors</a>
            <a href="Dashboard" style="text-decoration: none;">Dashboard</a>
            <a onclick="profilemain.profile()" style="margin-right: 20px" >Profile</a>
        </div>
    </ul>
</nav>
<div id="profile" class="modalupdate" style="display: none">
    <div class="updatemodal-content">
        <span id="close" onclick="profilemain.close()">&times;</span>
        <h2><p style="font-weight: bold;margin-right: 10px">Hello!</p><p id="profilename">Pavan</p></h2>
        <form id="Logout">
            <div class="form-field">
                <input class="profile" type="button" value="Logout" onclick="profilemain.logout()">
            </div>
        </form>
    </div>
</div>

