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
        window.location = "Logout";
    },

    discovery: function ()
    {
        $("#change").html("<body><link rel=\"stylesheet\" href=\"css/discoverystyle.css\"><div class=\"monitorTitle\"> <h1 style=\"color: black\">DISCOVERY</h1></div><div style=\"background-color:#f2f2f2;padding: 10px;\"> <table id=\"monitors\"> <thead> <tr> <th>Name</th> <th>IP</th> <th>Type</th> <th>Tag</th> <th>Operation</th> </tr> </thead> <tbody id=\"tablebody\"> </tbody> </table></div><a href=\"#\" class=\"float\" id=\"floatbtn\" onclick=\"discoveryhelper.floatbtn()\"> <i class=\"fa fa-plus my-float\"></i></a><div id=\"myModal\" class=\"modal\"> <div class=\"modal-content\"> <span id=\"close\" onclick=\"discoveryhelper.closeadd()\">&times;</span> <h3>Add Monitor</h3> <div class=\"alert failure\" style=\"display:none;\"> <strong>Failure! </strong> Wrong password or username. </div> <form name=\"monitor\" id=\"monitor\"> <input id=\"name\" name=\"name\" type=\"text\" placeholder=\"Name\"><br> <input id=\"ip\" name=\"ip\" type=\"text\" placeholder=\"IP\"><br> <select name=\"type\" id=\"type\" onchange=\"discoveryhelper.showssh()\"> <option value=\"ping\">Ping</option> <option value=\"ssh\">SSH</option> </select><br> <div id=\"sshdivision\" style=\"display:none;\"> <input id=\"username\" name=\"username\" type=\"text\" placeholder=\"Username\"> <input id=\"password\" name=\"password\" type=\"password\" placeholder=\"Password\"><br> </div> <input id=\"tag\" name=\"tag\" type=\"text\" placeholder=\"Tag\"><br> <button type=\"button\" value=\"Check\" onclick=\"discoverymain.add()\">Add</button> </form> </div></div><div id=\"myModalUpdate\" class=\"modal\"> <div class=\"modal-content\"> <span id=\"close2\" onclick=\"discoveryhelper.closeupdate()\">&times;</span> <h3>Update</h3> <div class=\"alert failure\" style=\"display:none;\"> <strong>Failure! </strong> Wrong password or username. </div> <form name=\"monitor\" id=\"updatemonitor\"> <input id=\"updatename\" name=\"name\" type=\"text\" placeholder=\"Name\"><br> <input id=\"updateip\" name=\"ip\" type=\"text\" placeholder=\"IP\"><br> <select name=\"type\" id=\"updatetype\" onchange=\"discoveryhelper.showssh()\" placeholder=\"Type\"> <option value=\"ping\">Ping</option> <option value=\"ssh\">SSH</option> </select><br> <div id=\"updatesshdivision\" style=\"display:none;\"> <input id=\"updateusername\" name=\"username\" type=\"text\" placeholder=\"Username\"> <input id=\"updatepassword\" name=\"password\" type=\"password\" placeholder=\"Password\"><br> </div> <input id=\"updatetag\" name=\"tag\" type=\"text\" placeholder=\"Tag\"><br> <button type=\"button\" value=\"Check\" onclick=\"discoverymain.update()\">Update</button> <p id=\"rawid\" style=\"visibility: hidden\"></p> </form> </div></div></body>");

        discoverymain.onload();
    },

    monitor: function ()
    {
        $("#change").html("<body><link rel=\"stylesheet\" href=\"css/monitorstyle.css\"><div class=\"monitorTitle\"> <h1 style=\"color: black\">MONITORS</h1></div><div style=\"background-color:#f2f2f2;padding: 10px;\"> <table id=\"monitors\"> <thead> <tr> <th>Name</th> <th>IP</th> <th>Type</th> <th>Tag</th> <th>Availability</th> <th>Operation</th> </tr> </thead> <tbody id=\"tablebody\"> </tbody> </table></div><div id=\"myModalStatistic\" class=\"modal\" style=\"display: none\"> <div class=\"modal-content\"> <span id=\"close\" onclick=\"monitorhelper.close()\">&times;</span><br> <div style=\"margin-left: 650px;font-weight: bold\">Live Data</div> <div class=\"wrapper\"> <div id=\"pieDiv\"> <canvas id=\"pie\" style=\"width:100%;max-width:500px;height: 50%;max-height:500px;\"></canvas> </div> <div class=\"matrix\" id=\"matrix1\"> <h3>Packet loss : 0%</h3> </div> <div class=\"matrix\" id=\"matrix2\"> <h3>RTT(ms) : 20</h3> </div> <div class=\"matrix\" id=\"matrix3\"> <h3 i>Sent packet : 4</h3> </div> <div class=\"matrix\" id=\"matrix4\"> <h3>Receive packet : 4</h3> </div> <div id=\"barDiv\"> <canvas id=\"bar\" style=\"width:100%;max-width:800px;height: 100%;max-height:300px \"></canvas> </div> </div> </div></div></body>");

        monitormain.onload();
    },

    dashboard: function ()
    {
        $("#change").html("<body><link rel=\"stylesheet\" href=\"css/dashboardstyle.css\"><div style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <div class=\"tabletitle\"> <h2 style=\"color: black\">Monitors Availability</h2> </div> <div class=\"wrapper\"> <div id=\"one\"><label style=\"font-size: 30px;font-weight: bold\">Unknown:</label></div> <div id=\"two\"><label style=\"font-size: 30px;font-weight: bold\">UP:</label></div> <div id=\"three\"><label style=\"font-size: 30px;font-weight: bold\">Down:</label></div> <div id=\"four\"><label style=\"font-size: 30px;font-weight: bold\">Total:</label></div> </div></div><div class=\"healthwrapper\"> <div id=\"toprtt\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <div class=\"tabletitle\"> <h3 style=\"color: black\">Top 5 Monitors By Response Time (ms)</h3> </div> <table> <tr> <th>IP</th> <th>RTT(ms)</th> </tr> <tbody id=\"tbodytoprtt\"></tbody> </table> </div> <div id=\"monitorgroup\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <table> <div class=\"tabletitle\"> <h3 style=\"color: black\">Monitors Group</h3> </div> <tr> <th>Type</th> <th>Total</th> <th>UP</th> <th>Down</th> </tr> <tbody id=\"tbodymonitorgroup\"></tbody> </table> </div> <div id=\"topcpu\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <table> <div class=\"tabletitle\"> <h3 style=\"color: black\">Top 5 Monitors By CPU(%)</h3> </div> <tr> <th>Name</th> <th>CPU</th> </tr> <tbody id=\"tbodytopcpu\"></tbody> </table> </div> <div id=\"topmemory\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <table> <div class=\"tabletitle\"> <h3 style=\"color: black\">Top 5 Monitors By Memory(%)</h3> </div> <tr> <th>Name</th> <th>Memory</th> </tr> <tbody id=\"tbodytopmemory\"></tbody> </table> </div> <div id=\"topdisk\" style=\"background-color:#f2f2f2;padding: 10px;margin: 10px\"> <table> <div class=\"tabletitle\"> <h3 style=\"color: black\">Top 5 Monitors By Disk(%)</h3> </div> <tr> <th>Name</th> <th>Disk</th> </tr> <tbody id=\"tbodytopdisk\"></tbody> </table> </div></div></body>");

        dashboardmain.onload();
    },

    allalert: function (message)
    {
        $("#status").show();

        $("#message").text(message);

        setTimeout(function ()
        {
            $("#status").hide();

        }, 2000);
    },
};

var profilecallback = {

    profilecallback: function (data)
    {
        $("#profilename").text(data.profileName);
    },
};