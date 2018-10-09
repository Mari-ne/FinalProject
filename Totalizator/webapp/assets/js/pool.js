var lang = document.getElementsByTagName("html")[0].lang;
var error1 = {"ru":"Сумма должна равняться 100", 
				"en":"Sum should be equal to 100",
				"jp":"合計は１００でなければなりません"};

var error2 = {"ru":"Часть пула не может быть 0", 
			"en":"Pool part can't be 0",
			"jp":"プールの一部は０にできません"};

function manage(){
	var a = document.getElementsByClassName("pool-info");
	var aText = [];
	for(var i = 0; i < a.length; i ++){
		aText.push(a[i].innerHTML);
		a[i].innerHTML="";
		var elem = document.createElement("input");
		elem.type="number";
		elem.setAttribute("class", "pool");
		elem.required = true;
		elem.name = "pool";
		elem.value = aText[i];
		elem.addEventListener('input', change);
		a[i].appendChild(elem);
	}
	var body = document.getElementsByTagName("tbody")[0];
	var tr = document.createElement("tr");
	for(i = 0; i < 3; i ++){
		var td = document.createElement("td");
		if(i === 1){
			td.id = "count";
			var text = document.createTextNode(count(aText));
			td.appendChild(text);
		}
		tr.appendChild(td);
	}
	body.appendChild(tr);
	document.getElementById("manage").style.display = "none";
	document.getElementById("submit").style.display = "initial";
	document.getElementById("reset").style.display = "initial";
}

function change(){
	var c = document.getElementById("count");
	var a = document.getElementsByClassName("pool");
	var aText = [];
	for(var i = 0; i < a.length; i ++){
		aText.push(a[i].value);
	}
	c.innerHTML = count(aText);
}

function count(items){
	var result = 0;
	for(var i = 0; i < items.length; i ++){
		result += parseInt(items[i], 10);
	}
	return result;
}

function check(){
	var c = document.getElementById("count");
	if(parseInt(c.innerHTML, 10) !== 100){
		document.getElementById("error").innerHTML = error1[lang];
		return false;
	}
	var pool = document.getElementsByClassName("pool");
	for(var i = 0; i < pool.length; i ++){
		if(parseInt(pool[i].value, 10) === 0){
			document.getElementById("error").innerHTML = error2[lang];
			return false;
		}
	}
	return true;
}