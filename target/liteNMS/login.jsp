<html>
<body>

<link rel="stylesheet" href="css/loginstyle.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="js/login.js"></script>
<script src="js/helper/helper.js"></script>


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
        <input type="button" class="btn" value="Login"  style="text-indent: 0px" onclick="loginmain.login()"/>
    </div>
    <h2 style="margin-left: 130px"><p onclick="loginmain.register()" style="font-weight: bold">Register</p></h2>


</form>

<div id="register" class="modal" style="display: none">
    <div class="modal-content">
        <span id="close" onclick="loginhelper.close()">&times;</span>
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
                <input class="btn" type="button" value="Register" onclick="loginmain.submit()">
            </div>
        </form>
    </div>
</div>
<div id="status" class="allalert" style="display:none;">
    <p id="message" style="padding-left:50px;padding-right:50px;color: black;font-weight: bold"></p>
</div>
</body>
</html>

