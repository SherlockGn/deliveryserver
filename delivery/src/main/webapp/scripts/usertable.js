

var nowpage = 1;
var totalPage;
$(document).ready(function() {
    
    totalPage = parseInt($.ajax({url: 'getUserCount', async:false}).responseText) /
    			parseInt($.ajax({url: 'getPage', async:false}).responseText);
    totalPage = Math.ceil(totalPage);
    alert(totalPage);
    
    var pageUl = $("ul.pagination");
    pageUl.append(liaString("leftPage", "&laquo;"));
    for(var i = 0; i < totalPage; i++) {
        pageUl.append(liaString(null, i + 1));
    }
    pageUl.append(liaString("rightPage", "&raquo;"));
    $("a").hover(function(){$(this).css("cursor","pointer");}, function(){$(this).css("cursor","default");});
    pageUl.find("a").each(function() {
        $(this).click(function() {
            var tab = $(this);      // tab == a
            if(tab.parent().attr("id") === "leftPage") {
                getPageTab(nowpage - 1).find("a").click();
            } else if(tab.parent().attr("id") === "rightPage") {
                getPageTab(nowpage + 1).find("a").click();
            } else {
                nowpage = parseInt(tab.html());
                refreshTabs();
            }
        });
    });
});

function liaString(id,content) {
    return '<li'+(id===null?"":(" id=" + id))+'><a>'+content+'</a></li>';
}

function refreshTabs() {
    $("ul.pagination li").removeClass("active");
    $("ul.pagination li").removeClass("disabled");
    getPageTab(nowpage).addClass("active");
    if(nowpage === 1)
        getPageTab("leftPage").addClass("disabled");
    if(nowpage === totalPage)
        getPageTab("rightPage").addClass("disabled");
    
    refreshTables();
}

function getPageTab(number) {
    var pageUl = $("ul.pagination");
    if(number === "leftPage") return $("#leftPage");
    if(number === "rightPage") return $("#rightPage");
    return $(pageUl.children().get(parseInt(number)));
}

function getTableLine(number) {
	return $(
			$("tbody").children().get(parseInt(number))
	);
}

function userTrLineString(user) {
	return '<tr style="display:none">' + '<td>' + user.id + '</td>' + 
					'<td>' + user.username + '</td>' + 
					'<td>' + user.password + '</td>' + 
					'<td>' + user.gender + '</td>' + 
					'<td>' + user.name + '</td>' + 
					'<td>' + user.phone + '</td>' + 
					'<td>' + user.address + '</td>' + 
					'<td>' + user.time + '</td>' +  '</tr>'
}

function show(i, length) {
	if(i >= length) return;
	getTableLine(i).fadeIn(100, function() {
		show(i + 1, length);
	});
}

function refreshTables() {
	$.ajax({
		url: "getUserPage?nowpage=" + nowpage,
		dataType: "json",
		success: function(users) {
			$("tbody").children().remove();
			for(var i = 0; i < users.length; i++) {
				$("tbody").append(userTrLineString(users[i]));
			}
			show(0, users.length);
		}
	});
}