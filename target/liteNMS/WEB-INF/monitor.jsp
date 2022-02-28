<html>
    <body>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://kit.fontawesome.com/a29a3484d3.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/monitorstyle.css">
        <script src="js/pollingmonitor.js"></script>

        <jsp:include page="navigation.jsp" />

        <div class="monitorTitle2">
            <h1 style="color: black">Monitors</h1>
        </div><br>

        <table id="monitors">
            <tr>
                <th>Name</th>
                <th>IP</th>
                <th>Type</th>
                <th>Tag</th>
                <th>Availability</th>
                <th>Operation</th>
            </tr>
            <tbody id="tablebody">
            </tbody>
        </table>

    </body>
</html>