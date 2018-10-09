function remove(element){
	element.parentNode.parentNode.parentNode.removeChild(element.parentNode.parentNode);
}

function input(element){
	var inp = element.value;
	if(inp.search(/\d{4}-\d{4}-\d{4}-\d{4}/) !== -1){
		var child = document.createElement("input");
		child.type="text";
		child.name = "card";
		child.handleEvent = function(event){
			switch(event.type){
		    	case "change":
		    		change(this);
		    		break;
		    	case "input":
		    		input(this);
		    		break;
			}
		}
		child.addEventListener('input', child, true);
		child.addEventListener('change', child, true);
		child.focus();
		var td1 = document.createElement("td");
		var td2 = document.createElement("td");
		var tr = document.createElement("tr");
		tr.appendChild(td1);
		td2.appendChild(child);    
		tr.appendChild(td2);
		element.parentNode.parentNode.parentNode.appendChild(tr);
		
		var i = document.createElement("i");
		i.setAttribute("class", "material-icons");
		var text = document.createTextNode("close");
		i.appendChild(text);
		i.handleEvent = function(event){
	    	remove(this);
	    }
	    i.addEventListener('click', i, true);
		element.parentNode.appendChild(i);
		
		document.getElementsByTagName("form")[0].getElementsByTagName("input")[document.getElementsByTagName("input").length - 1].focus();
	}
	else{
		while(inp.search(/\d{4}/) === 0){
			if(inp.length === 4){
				element.value += "-";
				break;
			}
			inp = inp.slice(5);
	    }
	}
}

function change(elem){
	var inp = elem.value;
	if(inp.length === 0){
		var inputs = document.getElementsByTagName("form")[0].getElementsByTagName("input");
	    for(i = 0; i < inputs.length; i ++){
	    	if(inputs[i] == elem && (i + 1) !== inputs.length){
	    		elem.parentNode.parentNode.parentNode.removeChild(elem.parentNode.parentNode);
	    		break;
	    	}
	    }
	    inputs[inputs.length - 1].focus();
	}
}

function check(){
	var inputs = document.getElementsByTagName("form")[1].getElementsByTagName("input");
	if(inputs[1].value === inputs[2].value){
		
		return false;
	}
	return true;
}