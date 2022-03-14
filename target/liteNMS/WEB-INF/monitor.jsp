<html>
<body>

<link rel="stylesheet" href="css/monitorstyle.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
<script src="js/monitor.js"></script>

<jsp:include page="navigation.jsp"/>

<div class="monitorTitle">
    <h1 style="color: black">MONITORS</h1>
</div>

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

<div id="myModalStatistic" class="modal" style="display: none">
    <div class="modal-content">
        <span id="close">&times;</span>
        <div style="float:right;">Live Data</div>
        <div class="wrapper">
            <div id="pieDiv">
                <canvas id="pie" style="width:100%;max-width:500px;height: 50%;max-height:500px;"></canvas>
            </div>
            <div class="matrix" >
                <h3 id="matrix1">Packet loss : 0%</h3>
            </div>
            <div class="matrix" >
                <h3 id="matrix2">RTT(ms) : 20</h3>
            </div>
            <div class="matrix" >
                <h3 id="matrix3">Sent packet : 4</h3>
            </div>
            <div class="matrix" >
                <h3 id="matrix4">Receive packet : 4</h3>
            </div>
            <div id="barDiv">
                <canvas id="bar" style="width:100%;max-width:800px;height: 100%;max-height:300px "></canvas>
            </div>
        </div>
    </div>
</div>
</body>
</html>