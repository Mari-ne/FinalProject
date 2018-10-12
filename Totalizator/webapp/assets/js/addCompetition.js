function teamsCheck(){
	var sel, team1, team2;
	sel = document.getElementById("team1");
	team1 = sel.options[sel.selectedIndex].value;
	sel = document.getElementById("team2");
	team2 = sel.options[sel.selectedIndex].value;
	if(team1 === team2){
		return false;
	}
	return true;
}

function dateCheck(){
	var start, finish;
	start = document.getElementById("start").value;
	finish = document.getElementById("finish").value;
	if(start === '' || finish === ''){
		return false;
	}
	if(start > finish){
		return false;
	}
	var now = new Date(); //to get current date and time
	var date = new Date(start);
	date.setHours(date.getHours() + 4); //between creating of new competition and it's start must be at least 4 hours
	if(now > date){
		return false;
	}
	return true;
}

function check(){
	var err = document.getElementById("error");
	var text="";
	var result = true;
	if(!teamsCheck()){
		result = false;
		text += "<fmt:message key='error.addCompetition.same' bundle='${mes}' />";
	}
	if(!dateCheck()){
		text += "<br>";
		result = false;
		text += "<fmt:message key='error.addCompetition.time' bundle='${mes}' />";
	}
	err.innerHTML = text;
	return result;
}

function teamsOptions(){
	var list = getList();
	var sport = document.getElementById("sport");
	sport = sport.options[sport.selectedIndex].value;
	var select1 = document.getElementById("team1");
	while (select1.firstChild) {
		select1.removeChild(select1.firstChild);
	}
	var select2 = document.getElementById("team2");
	while (select2.firstChild) {
		select2.removeChild(select2.firstChild);
	}
	for(i = 0; i < list.length; i ++){
		var obj = list[i];
		if(obj.sport_id === sport){
			var opt1 = document.createElement("option");
			opt1.value = obj.id;
			var text = document.createTextNode(obj.name);
			opt1.appendChild(text);
			var opt2 = document.createElement("option");
			opt2.value = obj.id;
			text = document.createTextNode(obj.name);
			opt2.appendChild(text);
			select1.appendChild(opt1);
			select2.appendChild(opt2);
		}
	}
}

function getList(){
	var data = document.getElementsByClassName("data");
	var list = [];
	for(i = 0; i < data.length; i++){
		list.push({id: data[i].id, name: data[i].name, sport_id: data[i].value});
	}
	return list;
}

teamsOptions();