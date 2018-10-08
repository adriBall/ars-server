function onMessage(event) {
	var message = JSON.parse(event.data);
	if (message.action === "REF_CHECKED") {
        if(message.resolution === "SUCCESS") {
        	socket.close();
        	window.location.replace("monitoring.html");
        }
        else if(message.resolution === "FAIL") {
        	 document.getElementById("checkRefForm").reset();
        	 document.getElementById("reflabel").innerHTML = "Not valid";
        	 document.getElementById("reflabel").style.color = "red";
        }
    }
}

function formSubmit() {
    var ref = document.getElementById("ref").value;
    sessionStorage.setItem("ref",ref);
    
    var RefAction = {
        action: "CHECK_REFERENCE",
        ref: ref
    };
    socket.send(JSON.stringify(RefAction));
    return false;
}

function initSocket(){
//	keepalive, not necessary
	setInterval(() => 
		socket.send(JSON.stringify({ action: "RaR_RULES" }))
	, 10000);
}

function errorSocket() {
	alert("Server connection error");
}

socket = new WebSocket("ws://" + window.location.host + "/monitor");
socket.onmessage = onMessage;
socket.onopen = initSocket;
socket.onerror = errorSocket;
window.onbeforeunload = () => socket.close();
