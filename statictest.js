
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
	return '<tr style="display:none" class="'+type+'"> \
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
var clickArg;
var data = {
		addFriend: ["id1", "username1", "id2", "username2"],
		appendUser: ["id", "username", "phone", "address"],
		courierScan: ["courierid", "courierusername", "indentid"],
		createIndent: ["fromuserid", "touserid", "fromphone", "tophone", "fromaddress", "toaddress", "price"],
		getArgs: [],
		getCourier: ["id", "username"],
		getFriend: ["id", "username"],
		getIndent: ["id"],
		getReceivedIndent: ["username", "id"],
		getSendedIndent: ["username", "id"],
		getUser: ["id", "username"],
		loginUser: ["username", "password"],
		registerCourier: ["username", "password", "name", "phone"],
		registerUser: ["username", "password", "gender", "name", "phone", "address"],
		remainingIndent: ["id", "username"],
		setUser: ["id", "username", "password", "name", "phone"],
		userScan: ["userid", "userusername", "indentid"]
};

function initInterKeys() {
//	$.ajax({
//		type: "get",
//		dataType: "json",
//		url: "getArgs",
//		success: function(data) {
//			webpageInit(data);
//		}
//	});
	webpageInit(data);
}

function webpageInit(data) {
	
	var interfaceUl = $("#interfaceChooseId").find("ul");
	var firstKey = null;
	for(var i in data) {
		if(firstKey === null) firstKey = i;
		interfaceUl.append(liaString(i));
	}
	
	interfaceUl.find("a").hover(function(){$(this).css("cursor","pointer");}, function(){$(this).css("cursor","default");});
	
	$("#addButtonId").click(function() {
		var thisType = nextType(lastType);
		$("tbody").append(trString(thisType));
		lastType = thisType;

		var appendedTr = $("tbody").children().get($("tbody").children().length-1);
		var ul = $(appendedTr).find("ul");
		var nowInterface = clickArg.now;
		var cnt = clickArg.cnt;
		for(var i in data[nowInterface]) {
			ul.append(liaString(data[nowInterface][parseInt(i)]));
		}
		$(appendedTr).find("input[disabled='true']").val(data[nowInterface][cnt]);
		ul.find("a").hover(function(){$(this).css("cursor","pointer");}, function(){$(this).css("cursor","default");});
		ul.find("a").each(function() {
			$(this).click(function() {
				$(appendedTr).find("input[disabled='true']").val($(this).html());
			});
		});
        $(appendedTr).fadeIn('slow');
		$(appendedTr).find("button").not("[data-toggle='dropdown']").click(function() {
			$(appendedTr).fadeOut('slow', function() { $(appendedTr).remove(); });  
		});
	});
	
	$("#submitButtonId").click(function() {
		var stringBuffer = $("#interfaceChooseId").find("input[disabled='true']").val() + "?";
		
		$("#warningDivId").animate({opacity:1},800);
		setTimeout(function() {
			$("#warningDivId").animate({opacity:0},2000);
		}, 5000); 
		
		var first = true;
		var args = new Array();
		$("tbody").find("tr").each(function() {
			var thisTr = $(this);
			var key = thisTr.find("input[disabled='true']").val();
			var value = thisTr.find("input").not(":disabled").val();
			if(value !== "") {
				args[key] = value;
			}
		});
		for(var key in args) {
			if(!first) stringBuffer = stringBuffer + "&";
			var value = encodeURIComponent(args[key]);
			stringBuffer = stringBuffer + key + "=" + value;
			first = false;
		}
		
		$("#urlCodeId").html(stringBuffer);
		$("#resultId").html("This execution can only be conducted when server is deployed. The static webpage on GitHub is only for showing the end front. Thank you for understanding.");
//		sendAjax(stringBuffer);
	});
	
	interfaceUl.find("a").each(function() {
		$(this).click(function() {
			var nowInterface = $(this).html();
			$("#interfaceChooseId").find("input[disabled='true']").val(nowInterface);
			$("tbody tr").remove();
			for (var i = 0; i < data[nowInterface].length; i++) {
				clickArg = {now: nowInterface, cnt: i};
				$("#addButtonId").click();
			}
			clickArg = {now: nowInterface, cnt: 0};
		});
	});
	
	interfaceUl.find("li:first-child a").click();
    
    $("#labelLinkId").click(function() { /*window.location.href="interface.html";*/ window.open("interface.html", "_blank"); });
    $("#labelLinkId").hover(function(){$(this).css("cursor","pointer");}, function(){$(this).css("cursor","default");});
}

function sendAjax(stringBuffer) {
//	$.ajax({
//		type: "get",
//		dataType: "json",
//		url: stringBuffer,
//		success: function(data) {
//			$("#resultId").html(JSON.stringify(data));
//		},
//		error: function(XMLHttpRequest, textStatus, errorThrown) {
//			alert(XMLHttpRequest);
//		}
//	});
}

$(document).ready(function() {
	initInterKeys();
});


















