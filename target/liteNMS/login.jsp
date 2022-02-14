<%@ taglib prefix="s" uri="/struts-tags"%>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="css/loginstyle.css">
</head>
<body>
<div id="bg"></div>

<form action="Login">
    <div class="form-field">
        <input name="username" type="text" placeholder="Email / Username" required/>
    </div>

    <div class="form-field">
        <input name="password" type="password" placeholder="Password" required/>
    </div>

    <div class="form-field">
        <button class="btn" type="submit">Log in</button>
    </div>
</form>

</body>
</html>

