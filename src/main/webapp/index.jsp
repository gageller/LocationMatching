<html>
<head>
<link rel="stylesheet" type="text/css" href="./css/lmStyle.css">
</head>
<body>
<script>
	<!--
		function submitButtonClick(action, method){
			document.forms("homeForm").action = action;
			document.forms("homeForm").method = method;

			document.forms("homeForm").submit();
		}
	-->
</script>
<title>Location Matching</title>
<h2>Location Matching</h2>
<br/>
<form name="homeForm" action="" method="POST">
<!-- 	<input type="hidden" name="_method"/> -->
	<table border=0 width="1200">
		<tr>
			<th align="left" width="598">Location Provider</th>
			<th rowspan="9" width="333"><div align="center" style="width:3px; height:250px; background-color: #000000"></div></th>
			<th align="left" width="598">Location Scout</th>
		</tr>
		<tr>
			<td class="errorMessageSmaller">${userProviderAlreadyExistsMessage}</td>
			<td class="errorMessageSmaller">${userScoutAlreadyExistsMessage}</td>
		</tr>
		<tr>
			<td>Login</td>
			<!-- <td>&nbsp;</td> -->
			<td>Login</td>
		</tr>
		<tr>
			<td valign="bottom">User Name</td>
			<!-- <td>&nbsp;</td> -->
			<td valign="bottom">User Name</td>
		</tr>
		<tr>
			<td valign="top"><input type="text" name="providerUserName"/></td>
			<!-- <td>&nbsp;</td> -->
			<td><input type="text" name="scoutUserName"/></td>
		</tr>
		<tr>
			<td>Password</td>
			<!-- <td>&nbsp;</td> -->
			<td>Password</td>
		</tr>
		<tr>
			<td><input type="password" name="providerPassword"/></td>
			<!-- <td>&nbsp;</td> -->
			<td><input type="password" name="scoutPassword"/></td>
		</tr>
		<tr>
			<td><input type="button" name="providerSubmit" value="Submit" onClick='submitButtonClick("providerNavigation.request", "POST")'/></td>
			<!-- <td>&nbsp;</td> -->
			<td><input type="button" name="scoutSubmit" value="Submit" onClick='submitButtonClick("scout.request", "POST")'/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><input type="button" name="newProviderBttn" value="Create New Location Provider..." onClick='submitButtonClick("createNewProvider.request", "GET")'/><!-- <a href="createNewProvider.request">Register for a new Provider account</a> --></td>
			<td>&nbsp;</td> 
			<td><input type="button" name="newScoutBttn" value="Create New Location Scout..." onClick='submitButtonClick("createNewScout.request", "GET")'/></td>
		</tr>
	</table>
	<a href="iFrames.jsp">iFrames</a>
</form>
</body>
</html>
