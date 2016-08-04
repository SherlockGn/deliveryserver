<!DOCTYPE html>
<html>
<head>
<title>Bootstrap</title>
<link href="scripts/bootstrap.min.css" rel="stylesheet">
<link href="scripts/jquery-ui.min.css" rel="stylesheet">
<script src="scripts/jquery.min.js"></script>
<script src="scripts/bootstrap.min.js"></script>
<script src="scripts/jquery-ui.min.js"></script>
<script src="scripts/friendtable.js"></script>

<style>
.ui-tooltip {
background: white;
width: auto;
height: auto;
padding: 10px;
color: black;
border-radius: 10px;
font-size: 10px;
font-family: Helvetica Neue;
}
</style>

</head>
<body>
	<div class="container"
		style="width: 80%:margin-left:auto; margin-right: auto; margin-top: 30px;">
		<ul class="nav nav-tabs">
			<li><a href="usertable.jsp">User</a></li>
			<li><a href="couriertable.jsp">Courier</a></li>
			<li class="active"><a>Friend</a></li>
			<li><a href="indenttable.jsp">Indent</a></li>

			<li class="dropdown" style="margin-left: 30px">
			<a	class="dropdown-toggle" data-toggle="dropdown">view DB config
					<span class="caret"></span>
			</a>
				<ul class="dropdown-menu">
					<li><a>driver name</a></li>
					<li><a>url</a></li>
					<li><a>username</a></li>
					<li><a>password</a></li>
				</ul></li>
		</ul>
	</div>

	<div class="container" style="width: 70%; margin-top: 40px;">
		<div class="table-responsive"  style="overflow-x: auto;">
			<table class="table table-hover">
				<caption style="text-align: center">
					<h2>Friend Information Table</h2>
				</caption>
				<thead>
					<tr class="info">
						<th>id1</th>
						<th>id2</th>
						<th>time</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>

	<div class="container"
		style="margin-left: auto; margin-right: auto; text-align: center">
		<ul class="pagination">
		</ul>
	</div>

</body>
</html>