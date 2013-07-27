<html>
<body>
<script language="javascript">
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
	<table border=0>
		<tr>
			<th>Location Provider</th>
			<th rowspan="9" width="200"><div align="center" style="width:3px; height:250px; background-color: #000000"></div></th>
			<th>Location Scout</th>
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
			<td><input type="button" name="providerSubmit" value="Submit" onClick='submitButtonClick("provider.request", "POST")'/></td>
			<!-- <td>&nbsp;</td> -->
			<td><input type="button" name="scoutSubmit" value="Submit" onClick='submitButtonClick("scout.request")'/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><input type="button" name="newProviderBttn" value="Create New Location Provider..." onClick='submitButtonClick("createNewProvider.request", "GET")'/><!-- <a href="createNewProvider.request">Register for a new Provider account</a> --></td>
			<!-- <td>&nbsp;</td> -->
			<td><a href="newScout.request">Register for a new Scout account</a></td>
		</tr>
	</table>
</form>
</body>
</html>
