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
	var lang = document.getElementsByTagName("html")[0].lang;
	var text="";
	var result = true;
	if(!teamsCheck()){
		result = false;
		switch(lang){
		case "ru":
			text += "Должны быть указаны различные команды";
			break;
		case "en":
			text += "You must choose different teams";
			break;
		case "jp":
			text += "異なるチームがなければなりません";
			break;
		}
	}
	if(!dateCheck()){
		text += "<br>"
		result = false;
		switch(lang){
		case "ru":
			text += "Соревнование должно начинаться не раньше чем через 4 часа, относительно нынешнего времени";
			break;
		case "en":
			text += "Competition should start not earlier than 4 hours, relative to the current time";
			break;
		case "jp":
			text += "競争は、げんざいの時刻と比較して4時間より早く開始してならない";
			break;
		}
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