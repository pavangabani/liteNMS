var modal = document.getElementById("myModal");

        // Get the button that opens the modal
        var btn = document.getElementById("myBtn");

        // Get the <span> element that closes the modal
        var span = document.getElementsByClassName("close")[0];

        // When the user clicks on the button, open the modal
        btn.onclick = function () {
            modal.style.display = "block";
        }

        // When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal.style.display = "none";
        }

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
        function showssh() {
            var type = document.monitor.type.value;
            if (type == "ssh") {
                document.getElementById("sshdivision").style.display = "block";
            }
            else {
                document.getElementById("sshdivision").style.display = "none";
            }
        }
        function validateForm() {
            var name = document.monitor.name.value;
            var ip = document.monitor.ip.value;
            var type = document.monitor.type.value;
            var tag = document.monitor.tag.value;
            var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

            if (name == "" || name == null) {
                alert("Enter Name Of Monitor");
            }
            else if (ip == "" || ip == null) {
                alert("Enter IP");
            }
            else if (!ipformat.test(ip)) {
                alert("Enter Valid IP");
            }
            else if (tag == "" || tag == null) {
                alert("Enter Tag");
            }
            else {
                alert("Success");
            }

        }