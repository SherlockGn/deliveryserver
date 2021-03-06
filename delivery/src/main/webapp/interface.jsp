﻿<html>
<head>
<title>智能快递系统应用接口文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="scripts/bootstrap.min.css" rel="stylesheet">
<script src="scripts/jquery.min.js"></script>
<script src="scripts/bootstrap.min.js"></script>
<script src="scripts/interface.js"></script>
</head>
<body>

<div id="tabs" class="container col-sm-3" style="margin-top:50px;margin-left:40px">
<ul class="nav nav-pills nav-stacked">
</ul>
</div>

<div class="panel-group col-sm-8" id="accordion" style="margin-top:50px">
</div>

</body>
</html>

<script>

var accordionString = function(title, content, href, type) {
    return '<div class="panel panel-' + type + '">\
      <div class="panel-heading"> \
         <h4 class="panel-title"> \
            <a data-toggle="collapse" data-parent="#accordion" \
               href="#' + href + '"> \
               '+ title +' \
            </a> \
         </h4> \
      </div> \
      <div id="' + href + '" class="panel-collapse collapse in"> \
         <div class="panel-body"> \
            ' + content + ' \
         </div> \
      </div> \
   </div>'
}

var tabString = function(title, number, id) {
    return '<li id="' + id + '"> \
    <a> \
    <span class="badge pull-right">' + number + '</span> \
    ' + title + ' \
    </a> \
    </li>'
}

var types = ['default', 'success', 'info', 'warning'];

var mainTitles = ['用户相关接口', '快递员相关接口', '好友关系相关接口', '订单相关接口', '用户或快递员扫描时相关接口'];


var titles = [
      [
          '为一个用户注册 registerUser',
          '为一个用户进行登录操作 loginUser',
          '更改一个用户的信息 setUser',
          '获取一个用户的信息 getUser',
          '为一个用户添加手机号或者住址信息 appendUser'
      ],
      [
          '为一个快递员注册 registerCourier',
          '为一个快递员进行登录操作 loginCourier',
          '更改一个快递员的信息 setCourier',
          '获取一个快递员的信息 getCourier'
      ],
      [
          '给两个用户添加好友关系 addFriend',
          '获取一个用户的所有好友 getFriend'
      ],
      [
          '创建一个新的订单 createIndent',
          '获取一个订单的信息 getIndent',
          '获取用户发过的订单 getSendedIndent',
          '获取用户接受的订单 getReceivedIndent',
          '获取快递员还未处理的订单 remainingIndent'
      ],
      [
          '快递员处理订单时扫描二维码 courierScan',
          '用户领取快递时扫描二维码 userScan'
      ]

];

$(document).ready(function() {
    
    var ul = $("#tabs ul");
    for(var i = 0; i < mainTitles.length; i++) {
        var li = tabString(mainTitles[i], titles[i].length, "liid" + i);
        ul.append(li);
    }

    $("#tabs a").click(function() {
        $("#tabs a").parent(".active").removeClass("active");
        $(this).parent().addClass("active");
        var i = parseInt($(this).parent().attr("id")[4]);
        $("#accordion").children().remove();
        for(var j = 0; j < titles[i].length; j++) {
            var content = (i < contents.length && j < contents[i].length)? contents[i][j]: 'content';
            $("#accordion").append(accordionString(titles[i][j], content, "href" + j, types[j % types.length]));
            $('#href' + j).collapse('hide');
        }
    });
    $("#tabs li:first-child a").click();
    $("#tabs a").hover(function() {
        $(this).css("cursor","pointer");
    }, function() {
        $(this).css("cursor","default");
    });
});

</script>