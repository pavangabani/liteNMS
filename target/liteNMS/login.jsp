<html>
<body>

<link rel="stylesheet" href="css/loginstyle.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/login.js"></script>

<div id="bg"></div>
<form method="POST">
    <div class="form-field">
        <input name="username" id="username" type="text" placeholder="Email / Username" />
    </div>
    <div class="form-field">
        <input name="password" id="password" type="password" placeholder="Password" />
    </div>
    <div id="failure" class="alert" style="display:none;">
        <strong>Failure! </strong> Wrong password or username.
    </div>
    <div class="form-field">
        <button class="btn" id="loginsubmit">Log in</button>
    </div>
</form>
</body>
</html>

