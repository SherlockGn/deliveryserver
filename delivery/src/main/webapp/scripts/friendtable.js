var nowpage = 1;
var totalPage;
$(document).ready(function() {

	totalPage = parseInt($.ajax({
		url : 'getFriendCount',
		async : false
	}).responseText) / parseInt($.ajax({
		url : 'getPage',
		async : false
	}).responseText);
	totalPage = Math.ceil(totalPage);

	var pageUl = $("ul.pagination");
	pageUl.append(liaString("leftPage", "&laquo;"));
	for (var i = 0; i < totalPage; i++) {
		pageUl.append(liaString(null, i + 1));
	}
	pageUl.append(liaString("rightPage", "&raquo;"));
	$("a").hover(function() {
		$(this).css("cursor", "pointer");
	}, function() {
		$(this).css("cursor", "default");
	});
	pageUl.find("a").each(function() {
		$(this).click(function() {
			var tab = $(this); // tab == a
			if (tab.parent().attr("id") === "leftPage") {
				getPageTab(nowpage - 1).find("a").click();
			} else if (tab.parent().attr("id") === "rightPage") {
				getPageTab(nowpage + 1).find("a").click();
			} else {
				nowpage = parseInt(tab.html());
				refreshTabs();
			}
		});
	});

	setToggle();

	$(pageUl.children().get(1)).find("a").click();
});

function liaString(id, content) {
	return '<li' + (id === null ? "" : (" id=" + id)) + '><a>' + content
			+ '</a></li>';
}

function refreshTabs() {
	$("ul.pagination li").removeClass("active");
	$("ul.pagination li").removeClass("disabled");
	getPageTab(nowpage).addClass("active");
	if (nowpage === 1)
		getPageTab("leftPage").addClass("disabled");
	if (nowpage === totalPage)
		getPageTab("rightPage").addClass("disabled");

	refreshTables();
}

function getPageTab(number) {
	var pageUl = $("ul.pagination");
	if (number === "leftPage")
		return $("#leftPage");
	if (number === "rightPage")
		return $("#rightPage");
	return $(pageUl.children().get(parseInt(number)));
}

function getTableLine(number) {
	return $($("tbody").children().get(parseInt(number)));
}

function userTrLineString(user) {
	return '<tr style="display:none">' + '<td>' + user.id1 + '</td>' + '<td>'
			+ user.id2 + '</td>' + '<td>' + user.time + '</td>' + '</tr>'
}

function show(i, length) {
	if (i >= length)
		return;
	getTableLine(i).slideDown(200, function() {
		show(i + 1, length);
	});
}

function refreshTables() {
	$.ajax({
		url : "getFriendPage?nowpage=" + nowpage,
		dataType : "json",
		success : function(users) {
			$("tbody").children().remove();
			for (var i = 0; i < users.length; i++) {
				$("tbody").append(userTrLineString(users[i]));
			}
			$("tbody").children().show('slow');
			// show(0, users.length);
		}
	});
}

function setToggle() {
	var links = $(".dropdown-menu a");
	var tooltipOption = {
		position : {
			my : "left top",
			at : "right top",
			using : function(position, feedback) {
				$(this).css(position);
				$("<div>").addClass(feedback.vertical).addClass(
						feedback.horizontal).appendTo(this);
			}
		},
		show : {
			effect : "slideDown",
			delay : 250
		}
	};
	$.ajax({
		url : "getDBConfig",
		dataType : "json",
		success : function(config) {
			var confs = config.split("|");
			$(links.get(0)).attr("title", confs[0]);
			$(links.get(0)).tooltip(tooltipOption);
			$(links.get(1)).attr("title", confs[1]);
			$(links.get(1)).tooltip(tooltipOption);
			$(links.get(2)).attr("title", confs[2]);
			$(links.get(2)).tooltip(tooltipOption);
			$(links.get(3)).attr("title", confs[3]);
			$(links.get(3)).tooltip(tooltipOption);
		}
	});
}