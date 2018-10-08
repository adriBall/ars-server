var counter;
var time;
var gamificationTimeout;

function onMessage(event) {
	var message = JSON.parse(event.data);
	if (message.action === "SEND_QUESTION") {
		if (gamificationTimeout != null) {
			clearTimeout(gamificationTimeout);
		}
		showQuestion(message);
	}
	if (message.action === "QUESTION_FINISHED") {
		showQuestionResults(message);
	}
	if (message.action === "REF_CHECKED") {
		if(message.resolution === "SUCCESS") {
        	checkQuestion()
        }
        if(message.resolution === "FAIL") {
        	socket.close();
        	window.location.replace("index.html");
        }
    }
    if (message.action === "LECTURE_FINISHED") {
		socket.close();
		alert("Lesson finished. Bye!");
        window.location.replace("index.html");
    }
    if (message.action === "SEND_GAMIFICATION") {
    	prepareGamification(message);
    }

}

function showQuestionResults(message) {
	clearInterval(counter);
	time = 0;
	document.getElementById("countdown").innerHTML = 0 + "s";
	
	var Aprog = document.getElementById("Aprog");
	var Bprog = document.getElementById("Bprog");
	var Cprog = document.getElementById("Cprog");
	var Dprog = document.getElementById("Dprog");
	var numAnswersA = new Number(message.numAnswersA);
	var numAnswersB = new Number(message.numAnswersB);
	var numAnswersC = new Number(message.numAnswersC);
	var numAnswersD = new Number(message.numAnswersD);
	var totalAnswers = numAnswersA + numAnswersB + numAnswersC + numAnswersD;
	if (totalAnswers == 0) {
		totalAnswers = 1;
	}
	
	if (message.correctAnswer === "A") {
		document.getElementById("panelA").style.setProperty("background-color", "#00FF44", "important");
	}
	if (message.correctAnswer === "B") {
		document.getElementById("panelB").style.setProperty("background-color", "#00FF44", "important");
	}
	if (message.correctAnswer === "C") {
		document.getElementById("panelC").style.setProperty("background-color", "#00FF44", "important");
	}
	if (message.correctAnswer === "D") {
		document.getElementById("panelD").style.setProperty("background-color", "#00FF44", "important");
	}

	Aprog.innerHTML = '<div style="height: 20px" class="progress col s10"><div class="determinate" style="width: ' + ((numAnswersA/totalAnswers))*100 + '%">' + numAnswersA + '</div></div>';
	Bprog.innerHTML = '<div style="height: 20px" class="progress col s10"><div class="determinate" style="width: ' + ((numAnswersB/totalAnswers))*100 + '%">' + numAnswersB + '</div></div>';
	Cprog.innerHTML = '<div style="height: 20px" class="progress col s10"><div class="determinate" style="width: ' + ((numAnswersC/totalAnswers))*100 + '%">' + numAnswersC + '</div></div>';
	Dprog.innerHTML = '<div style="height: 20px" class="progress col s10"><div class="determinate" style="width: ' + ((numAnswersD/totalAnswers))*100 + '%">' + numAnswersD + '</div></div>';
}

function showQuestion(message) {
	var oReq = new XMLHttpRequest();

	oReq.open("GET", "resources/questionContent.html", true);
	oReq.onload = function(e) {
		var myText = oReq.responseText; 
		document.getElementById("question-panel").innerHTML = myText;
		var question = document.getElementById("question");
		var answerA = document.getElementById("answerA");
		var answerB = document.getElementById("answerB");
		var answerC = document.getElementById("answerC");
		var answerD = document.getElementById("answerD");
		if(message.question.trim() === "") {
			question.innerHTML = "Oral question";
		}
		else {
			question.innerHTML = message.question;
		}
			
		answerA.innerHTML = message.answerA;
		answerB.innerHTML = message.answerB;
		answerC.innerHTML = message.answerC;
		answerD.innerHTML = message.answerD;
		
		counter = setInterval(decreaseTime, 1000);
		time = Math.round(message.remainingTimeMillis/1000);
		document.getElementById("countdown").innerHTML = time + "s";
		document.getElementById("refPanel").innerHTML = "Reference: " + sessionStorage.getItem("ref"); 
	}
	oReq.send();
}

function prepareGamification(message) {
	var content = '<div class="container"><div class="row"><h5 class="col s2" id="refPanel" align="left">Reference:</h5><h3 align="left" id="question" class="col s8 header center black-text">Gamification</h3><h1 align="right" id="countdown" class="col s2"></h1></div></div><table class="striped"><thead><tr><th style="padding-left: 40px" data-field="id">User</th><th data-field="name">Success</th><th data-field="price">Fail</th></tr></thead><tbody>';
	
	var rows = message.gamification.split(message.splitRow);
	for (var i = 0; i < rows.length; i++) {
		if (rows[i] !== "") {
			var column = rows[i].split(message.splitColumn);
			content += '<tr><td style="padding-left: 40px">' + column[0] + '</td><td>' + column[1] + '</td><td>' + column[2] + '</td></tr>';
		}
			
	}
	
	content += '</tbody></table>';

	setTimeout(() => {
		if(time === 0) {
			document.getElementById("question-panel").innerHTML = content;
			document.getElementById("refPanel").innerHTML = "Reference: " + sessionStorage.getItem("ref"); 
		}
	}, 6000);
}

function checkQuestion() {
	var QuestionAction = {
		action: "GET_CURRENT_QUESTION"
	};
	socket.send(JSON.stringify(QuestionAction));
}

function initSocket(){
	var RefAction = {
		action: "CHECK_REFERENCE",
		ref: sessionStorage.getItem("ref")
	};
	socket.send(JSON.stringify(RefAction));
	document.getElementById("refPanel").innerHTML = "Reference: " + sessionStorage.getItem("ref"); 
	
//	keepalive, not necessary
	setInterval(() => 
		socket.send(JSON.stringify({ action: "RaR_RULES" }))
	, 10000);
}

function errorSocket() {
	alert("Server connection error");
	window.location.replace("index.html");
}

function decreaseTime() {
	time = time - 1;
	var countdown = document.getElementById("countdown");
	countdown.innerHTML = time + "s";
	if(time == 0) {
		clearInterval(counter);
	}
}

socket = new WebSocket("ws://"+window.location.host+"/monitor");
socket.onmessage = onMessage;
socket.onopen = initSocket;
socket.onerror = errorSocket;
window.onbeforeunload = () => socket.close();
