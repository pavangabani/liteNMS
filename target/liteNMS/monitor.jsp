<html>
    <body>

        <script src="https://kit.fontawesome.com/a29a3484d3.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/monitorstyle.css">

        <jsp:include page="navigation.jsp" />


        <div class="monitorTitle2">
            <h1 style="color: black">Polling Monitors</h1>
        </div><br>

        <table id="monitors">
            <tr>
                <th>Name</th>
                <th>IP</th>
                <th>Type</th>
                <th>Tag</th>
                <th>Operation</th>
                <th>Status</th>
            </tr>
            <tr>
                <td>Pavan</td>
                <td>10.20.40.139</td>
                <td>SSH</td>
                <td>Linux</td>
                <td>
                    <button class="btn"><i class="fa-solid fa-pen"></i></button>
                    <button class="btn"><i class="fa-solid fa-trash-can"></i></button></td>
                <td>UP | Critical</td>
            </tr>
        </table>

    </body>
</html>