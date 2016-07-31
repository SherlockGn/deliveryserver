
function nextType(now) {
	var list = ["default", "active", "success", "warning", "danger"];
	var length = list.length;
	for (var i in list) {
		if(now === list[i]) {
			i = parseInt(i);
			var next = (i + 1) % length;
			return list[next];
		}
	}
	return "default";
}

function trString(type) {
	return '<tr class="'+type+'"> \
      <td> \
        <button type="button" class="btn btn-'+type+'">delete this row</button> \
       </td> \
      <td> \
       <div style="width:300px"> \
        <div class="input-group"> \
         <div class="input-group-btn"> \
          <button type="button" class="btn btn-'+type+' dropdown-toggle" data-toggle="dropdown"> choose keys <span class="caret"></span> </button> \
          <ul class="dropdown-menu"> \
          </ul> \
         </div> \
         <input type="text" disabled="true" class="form-control" /> \
        </div> \
       </div> \
       </td> \
      <td> \
       <input type="text" class="form-control" /> \
       </td> \
     </tr>'
}
function liaString(content) {
	return "<li><a>" + content + "</a></li>";
}

var lastType = "danger";
var interfaces = ['interface1', 'interface2', 'interface3', 'interface4', 'interface5'];
var keys = ['key1', 'key2', 'key3', 'key4', 'key5'];
$(document).ready(function() {
	
	var interfaceUl = $("#interfaceChooseId").find("ul");
	for(var i in interfaces) {
		interfaceUl.append(liaString(interfaces[i]));
	}
	$("#interfaceChooseId").find("input[disabled='true']").val(interfaces[0]);
	interfaceUl.find("a").each(function() {
		$(this).click(function() {
			$("#interfaceChooseId").find("input[disabled='true']").val($(this).html());
		});
	});
	interfaceUl.find("a").hover(function(){$(this).css("cursor","pointer");}, function(){$(this).css("cursor","default");});
	
	$("#addButtonId").click(function() {
		var thisType = nextType(lastType);
		$("tbody").append(trString(thisType));
		lastType = thisType;
		
		var appendedTr = $("tbody").children().get($("tbody").children().length-1);
		var ul = $(appendedTr).find("ul");
		for(var i in keys) {
			ul.append(liaString(keys[i]));
		}
		$(appendedTr).find("input[disabled='true']").val(keys[0]);
		ul.find("a").hover(function(){$(this).css("cursor","pointer");}, function(){$(this).css("cursor","default");});
		ul.find("a").each(function() {
			$(this).click(function() {
				$(appendedTr).find("input[disabled='true']").val($(this).html());
			});
		});
		$(appendedTr).find("button").not("[data-toggle='dropdown']").click(function() {
			$(appendedTr).remove();
		});
	});
	
	$("#submitButtonId").click(function() {
		var stringBuffer = $("#interfaceChooseId").find("input[disabled='true']").val() + "?";
		
		$("#warningDivId").animate({opacity:1},800);
		setTimeout(function() {
			$("#warningDivId").animate({opacity:0},2000);
		}, 5000);
		
		var first = true;
		$("tbody").find("tr").each(function() {
			if(!first) stringBuffer = stringBuffer + "&";
			var thisTr = $(this);
			var key = thisTr.find("input[disabled='true']").val();
			var value = thisTr.find("input").not(":disabled").val();
			value = encodeURIComponent(value);
			stringBuffer = stringBuffer + key + "=" + value;
			first = false;
		});
		
		$("#urlCodeId").html(stringBuffer);
		$("#resultId").html("This execution can only be conducted when server is deployed. The static webpage on GitHub is only for showing the end front. Thank you for understanding.");
	});
	
	$("#addButtonId").click();
});


















