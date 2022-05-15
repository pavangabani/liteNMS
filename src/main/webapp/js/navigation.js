$(document).ready(function ()
{
    profilemain.dashboard();
});

var profilemain = {

    profile: function ()
    {
        $("#profile").show();

        let request = {

            url: "Profile",

            data: "",

            callback: profilecallback.profilecallback,
        }
        helperMain.ajaxpost(request);
    },

    close: function ()
    {
        $("#profile").hide();
    },

    logout: function ()
    {
        profileName = $("#profilename").text();

        let sdata = {
            profileName: profileName,
        };
        let request = {

            url: "Logout",

            data: sdata,

            callback: profilecallback.logoutcallback,
        }
        helperMain.ajaxpost(request);
    },

    discovery: function ()
    {
        $("#mainDiv").html("<body><link rel=\"stylesheet\" href=\"css/discoverystyle.css\"><div class=\"monitorTitle\"> <h1 style=\"color: black\">DISCOVERY</h1></div><div id=\"autodiscovery\" class=\"allalert\" style=\"display:block;\"><input id='autodiscoveryb' type=\"button\" value=\"Auto Discovery\" onclick=\"discoveryhelper.autodiscover()\"/></div><div style=\"background-color:#f2f2f2;padding: 10px;\"> <table id=\"monitors\"> <thead> <tr> <th>Name</th> <th>IP</th> <th>Type</th> <th>Tag</th> <th>Operation</th> </tr> </thead> <tbody id=\"tablebody\"> </tbody> </table></div><a href=\"#\" class=\"float\" id=\"floatbtn\" onclick=\"discoveryhelper.floatbtn()\"> <i class=\"fa fa-plus my-float\"></i></a><div id=\"myModalDiscover\" class=\"modal\"> <div class=\"modal-content\"> <span id=\"close\" onclick=\"discoveryhelper.closediscover()\">&times;</span> <h3>Auto Discovery</h3> <form> <input id=\"ipwithcider\" name=\"name\" type=\"text\" placeholder=\"IP with CIDER\"><br><br> <button type=\"button\" value=\"Check\" onclick=\"discoverymain.autodiscover()\">Discover</button> </form> </div></div><div id=\"myModal\" class=\"modal\"> <div class=\"modal-content\"> <span id=\"close\" onclick=\"discoveryhelper.closeadd()\">&times;</span> <h3>Add Monitor</h3> <div class=\"alert failure\" style=\"display:none;\"> <strong>Failure! </strong> Wrong password or username. </div> <form name=\"monitor\" id=\"monitor\"> <input id=\"name\" name=\"name\" type=\"text\" placeholder=\"Name\"><br> <input id=\"ip\" name=\"ip\" type=\"text\" placeholder=\"IP\"><br> <select name=\"type\" id=\"type\" onchange=\"discoveryhelper.showssh()\"> <option value=\"ping\">Ping</option> <option value=\"ssh\">SSH</option> </select><br> <div id=\"sshdivision\" style=\"display:none;\"> <input id=\"username\" name=\"username\" type=\"text\" placeholder=\"Username\"> <input id=\"password\" name=\"password\" type=\"password\" placeholder=\"Password\"><br> </div> <input id=\"tag\" name=\"tag\" type=\"text\" placeholder=\"Tag\"><br> <button type=\"button\" value=\"Check\" onclick=\"discoverymain.add()\">Add</button> </form> </div></div><div id=\"myModalUpdate\" class=\"modal\"> <div class=\"modal-content\"> <span id=\"close2\" onclick=\"discoveryhelper.closeupdate()\">&times;</span> <h3>Update</h3> <div class=\"alert failure\" style=\"display:none;\"> <strong>Failure! </strong> Wrong password or username. </div> <form name=\"monitor\" id=\"updatemonitor\"> <input id=\"updatename\" name=\"name\" type=\"text\" placeholder=\"Name\"><br> <input id=\"updateip\" name=\"ip\" type=\"text\" placeholder=\"IP\"><br> <select name=\"type\" id=\"updatetype\" onchange=\"discoveryhelper.showssh()\" placeholder=\"Type\"> <option value=\"ping\">Ping</option> <option value=\"ssh\">SSH</option> </select><br> <div id=\"updatesshdivision\" style=\"display:none;\"> <input id=\"updateusername\" name=\"username\" type=\"text\" placeholder=\"Username\"> <input id=\"updatepassword\" name=\"password\" type=\"password\" placeholder=\"Password\"><br> </div> <input id=\"updatetag\" name=\"tag\" type=\"text\" placeholder=\"Tag\"><br> <button type=\"button\" value=\"Check\" onclick=\"discoverymain.update()\">Update</button> <p id=\"rawid\" style=\"visibility: hidden\"></p> </form> </div></div></body>");

        discoverymain.onload();

        socket.createSocket();
    },

    monitor: function ()
    {
        $("#mainDiv").html("<body><link rel=\"stylesheet\" href=\"css/monitorstyle.css\"><div class=\"monitorTitle\"> <h1 style=\"color: black\">MONITORS</h1></div><div id=\"emailalerts\" class=\"allalert\" style=\"display:block;\"><input id='emailalertsb' type=\"button\" value=\"Email For Alerts\" onclick=\"monitorhelper.emailalerts()\"/></div></div><div style=\"background-color:#f2f2f2;padding: 10px;\"> <table id=\"monitors\"> <thead> <tr> <th>Name</th> <th>IP</th> <th>Type</th> <th>Tag</th> <th>Availability</th> <th>Operation</th> </tr> </thead> <tbody id=\"tablebody\"> </tbody> </table></div><div id=\"myalertsmodal\" class=\"modale\"> <div class=\"modal-contente\"> <span id=\"close\" onclick=\"monitorhelper.closealert()\">&times;</span> <h3>Email Alerts</h3> <form> <input id=\"email\" name=\"name\" type=\"text\" placeholder=\"Email\"><br><br> <button type=\"button\" value=\"Check\" onclick=\"monitormain.emailalerts()\">Set</button> </form> </div></div><div id=\"myModalStatistic\" class=\"modal\" style=\"display: none\"> <div class=\"modal-content\"> <span id=\"close\" onclick=\"monitorhelper.close()\">&times;</span><br> <div style=\"margin-left: 650px;font-weight: bold\">Live Data</div> <div class=\"wrapper\"> <div id=\"pieDiv\"> <canvas id=\"pie\" style=\"width:100%;max-width:500px;height: 50%;max-height:500px;\"></canvas> </div> <div class=\"matrix\" id=\"matrix1\"> <h3>Packet loss : 0%</h3> </div> <div class=\"matrix\" id=\"matrix2\"> <h3>RTT(ms) : 0</h3> </div> <div class=\"matrix\" id=\"matrix3\"> <h3 i>Sent packet : 4</h3> </div> <div class=\"matrix\" id=\"matrix4\"> <h3>Receive packet : 0</h3> </div> <div id=\"barDiv\"> <canvas id=\"bar\" style=\"width:100%;max-width:800px;height: 100%;max-height:300px \"></canvas> </div> </div> </div></div></body>");

        monitormain.onload();
    },

    dashboard: function ()
    {
        $("#mainDiv").html("<body><link rel=\"stylesheet\" href=\"css/dashboardstyle.css\"><div style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <div class=\"tabletitle\"> <h2 style=\"color: black\">Monitors Availability</h2> </div> <div class=\"wrapper\"> <div id=\"one\"><label style=\"font-size: 30px;font-weight: bold\">Unknown:</label></div> <div id=\"two\"><label style=\"font-size: 30px;font-weight: bold\">UP:</label></div> <div id=\"three\"><label style=\"font-size: 30px;font-weight: bold\">Down:</label></div> <div id=\"four\"><label style=\"font-size: 30px;font-weight: bold\">Total:</label></div> </div></div><div style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <div class=\"tabletitle\"> <h2 style=\"color: black\">Monitors Health</h2> </div> <div class=\"wrapper\"> <div id=\"one1\"><label style=\"font-size: 30px;font-weight: bold\">Unreachable:</label></div> <div id=\"two1\"><label style=\"font-size: 30px;font-weight: bold\">Critical:</label></div> <div id=\"three1\"><label style=\"font-size: 30px;font-weight: bold\">Warning:</label></div> <div id=\"four1\"><label style=\"font-size: 30px;font-weight: bold\">Clear:</label></div> </div></div><div class=\"healthwrapper\"> <div id=\"toprtt\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <div class=\"tabletitle\"> <h3 style=\"color: black\">Top 5 Monitors By Response Time (ms)</h3> </div> <table> <tr> <th>IP</th> <th>RTT(ms)</th> </tr> <tbody id=\"tbodytoprtt\"></tbody> </table> </div> <div id=\"monitorgroup\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <table> <div class=\"tabletitle\"> <h3 style=\"color: black\">Monitors Group</h3> </div> <tr> <th>Type</th> <th>Total</th> <th>UP</th> <th>Down</th> </tr> <tbody id=\"tbodymonitorgroup\"></tbody> </table> </div> <div id=\"topcpu\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <table> <div class=\"tabletitle\"> <h3 style=\"color: black\">Top 5 Monitors By CPU(%)</h3> </div> <tr> <th>Name</th> <th>CPU</th> </tr> <tbody id=\"tbodytopcpu\"></tbody> </table> </div> <div id=\"topmemory\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <table> <div class=\"tabletitle\"> <h3 style=\"color: black\">Top 5 Monitors By Memory(%)</h3> </div> <tr> <th>Name</th> <th>Memory</th> </tr> <tbody id=\"tbodytopmemory\"></tbody> </table> </div> <div id=\"topdisk\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <table> <div class=\"tabletitle\"> <h3 style=\"color: black\">Top 5 Monitors By Disk(%)</h3> </div> <tr> <th>Name</th> <th>Disk</th> </tr> <tbody id=\"tbodytopdisk\"></tbody> </table> </div></div></body>");

        dashboardmain.onload();
    },

    alerts: function ()
    {
        $("#mainDiv").html("<body><link rel=\"stylesheet\" href=\"css/alerts.css\"><script type=\"text/javascript\" src=\"js/alerts.js\"></script><div class=\"monitorTitle\"> <h1 style=\"color: black\">ALERTS</h1></div><div style=\"background-color:#f2f2f2;padding: 10px;\"> <table id=\"monitors\"> <thead> <tr> <th>Name</th> <th>Description</th> <th>Operation</th> </tr> </thead> <tbody id=\"tablebodyalert\"> </tbody> </table></div><a href=\"#\" class=\"float\" id=\"floatbtn\" onclick=\"alertshelper.floatbtn()\"> <i class=\"fa fa-plus my-float\"></i></a><div id=\"myModal\" class=\"modal\"> <div class=\"modal-content\"> <span id=\"close\" onclick=\"alertshelper.closeadd()\">&times;</span> <h3>Create Alert</h3> <div class=\"alert failure\" style=\"display:none;\"> <strong>Failure! </strong> Wrong password or username. </div> <form name=\"monitor\" id=\"monitor\"> <input id=\"name\" name=\"name\" type=\"text\" placeholder=\"Name\"><br> <input name=\"status\" id=\"status\" type=\"text\" placeholder=\"Description\"/><br> <p style=\"font-weight: bold\">Type :</p> <select name=\"type\" id=\"type\" onchange=\"alertshelper.show()\"> <option value=\"ping\">Ping</option> <option value=\"ssh\">SSH</option> </select><br> <p style=\"font-weight: bold\">Metric :</p> <div id=\"pingshow\" style=\"display:block;\"> <select name=\"matric\" id=\"sshmetric\"> <option value=\"RTT\">RTT (ms)</option> </select><br> </div> <div id=\"sshshow\" style=\"display:none;\"> <select name=\"metric\" id=\"pingmetric\"> <option value=\"RTT\">RTT (ms)</option> <option value=\"CPU\">CPU (%)</option> <option value=\"Memory\">Memory (%)</option> <option value=\"Disk\">Disk (%)</option> </select><br> </div> <p style=\"font-weight: bold\">Threshold :</p> <input id=\"critical\" name=\"critical\" type=\"text\" placeholder=\"Critical [Greater Than]\"><br> <input id=\"warning\" name=\"warning\" type=\"text\" placeholder=\"Warning [Greater Than]\"><br> <input id=\"clear\" name=\"clear\" type=\"text\" placeholder=\"Clear [Less Than]\"><br> <button type=\"button\" value=\"Check\" onclick=\"alertsmain.add()\">Add</button> </form> </div></div></body>");

        alertsmain.onload();
    },

};

var profilecallback = {

    profilecallback: function (data)
    {
        $("#profilename").text(data.profileName);
    },

    logoutcallback: function ()
    {
        window.location = "LoginPage";
    },
};