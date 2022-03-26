<html>
<body>

<link rel="stylesheet" href="css/dashboardstyle.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="js/dashboard.js"></script>

<jsp:include page="navigation.jsp"/>

<div class="monitorTitle">
    <h1 style="color: black">AVAILABILITY</h1>
</div>
<div class="wrapper">
    <div id="one"><label style="font-size: 30px;font-weight: bold">Unknown:</label></div>
    <div id="two"><label style="font-size: 30px;font-weight: bold">UP:</label></div>
    <div id="three"><label style="font-size: 30px;font-weight: bold">Down:</label></div>
    <div id="four"><label style="font-size: 30px;font-weight: bold">Total:</label></div>
</div>
<div class="availabilitywrapper">
    <div id="toprtt" style="background-color:aliceblue;padding: 10px;margin: 10px">
        <div class="tabletitle">
            <h3 style="color: black">Top 5 Monitors By Response Time (ms)</h3>
        </div>
        <table>
            <tr>
                <th>Name</th>
                <th>RTT(ms)</th>
            </tr>
            <tr>
                <td>Griffin</td>
                <td>$100</td>
            </tr>
            <tr>
                <td>Griffin</td>
                <td>$150</td>
            </tr>
            <tr>
                <td>Swanson</td>
                <td>$300</td>
            </tr>
            <tr>
                <td>Brown</td>
                <td>$250</td>
            </tr>
            <tr>
                <td>Brown</td>
                <td>$250</td>
            </tr>
        </table>
    </div>
    <div style="background-color:aliceblue;padding: 10px;margin: 10px">
        <table>
            <div class="tabletitle">
                <h3 style="color: black">Top 5 Monitors By Response Time</h3>
            </div>
            <tr>
                <th>Type</th>
                <th>Total</th>
                <th>UP</th>
                <th>Down</th>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
        </table>
    </div>
</div>
<div class="healthwrapper">
    <div style="background-color:aliceblue;padding: 10px;margin: 10px">
        <table>
            <div class="tabletitle">
                <h3 style="color: black">Top 5 Monitors By CPU(%)</h3>
            </div>
            <tr>
                <th>Name</th>
                <th>CPU</th>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>

        </table>
    </div>
    <div style="background-color:aliceblue;padding: 10px;margin: 10px">
        <table>
            <div class="tabletitle">
                <h3 style="color: black">Top 5 Monitors By Memory(%)</h3>
            </div>
            <tr>
                <th>Name</th>
                <th>Memory</th>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>

        </table>
    </div>
    <div style="background-color:aliceblue;padding: 10px;margin: 10px">
        <table>
            <div class="tabletitle">
                <h3 style="color: black">Top 5 Monitors By Disk(%)</h3>
            </div>
            <tr>
                <th>Name</th>
                <th>Disk</th>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>
            <tr>
                <td>Peter</td>
                <td>Griffin</td>
            </tr>

        </table>
    </div>
</div>
</div>

</html>
</html>