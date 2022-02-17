<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>

<link rel="stylesheet" href="css/loginstyle.css">
<script src="js/login.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<div id="bg"></div>

<form method="POST">
    <div class="form-field">
        <input name="username" id="username" type="text" placeholder="Email / Username" required/>
    </div>
    <div class="form-field">
        <input name="password" id="password" type="password" placeholder="Password" required/>
    </div>
    <div class="form-field">
        <button class="btn" type="submit" onclick="login()">Log in</button>
    </div>
</form>

</body>

</html>

