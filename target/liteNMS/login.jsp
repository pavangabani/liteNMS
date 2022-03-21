<html>
<body>

<link rel="stylesheet" href="css/loginstyle.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="js/login.js"></script>

<div id="bg"></div>
<form method="POST">
    <div class="form-field">
        <input name="username" id="username" type="text" placeholder="Username"/>
    </div>
    <div class="form-field">
        <input name="password" id="password" type="password" placeholder="Password"/>
    </div>
    <div id="failure" class="alert" style="display:none;">
        <strong>Failure! </strong> Wrong password or username.
    </div>
    <div class="form-field">
        <input type="button" class="btn" value="Login"  style="text-indent: 0px" onclick="main.login()"/>
    </div>
    <h2 style="margin-left: 130px"><p onclick="main.register()" style="font-weight: bold">Register</p></h2>


</form>

<div id="register" class="modal" style="display: none">
    <div class="modal-content">
        <span id="close" onclick="helper.close()">&times;</span>
        <form id="registerform">
            <div class="form-field">
                <input name="username" id="registerusername" type="text" placeholder="Username"/>
            </div>
            <div class="form-field">
                <input name="password" id="registerpassword" type="password" placeholder="Password"/>
            </div>
            <div id="registerfailure" class="alert" style="display:none;">
                <strong>Failure! </strong> Enter Username or Password.
            </div>
            <div class="form-field">
                <input class="btn" type="button" value="Register" onclick="main.submit()">
            </div>
        </form>
    </div>
</div>
</body>
</html>

