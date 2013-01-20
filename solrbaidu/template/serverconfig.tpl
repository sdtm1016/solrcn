<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<link rel="stylesheet" href="../css/install.css" type="text/css" />
</head>
<body>
    <{include file="installhead.tpl"}>
    <br clear="all">
	<form method="POST" action="serverconfig.php?flag=true" accept-charset="UTF-8">
		<table>
			<tr>
			  <td>
			    <strong>DB Server IP</strong>
			  </td>
			  <td>
			    <input class="std" name="sip" type="text" value="<{$configdata.sip}>">
			  </td>
			  <td><font color=red><{$errordata.sip}></font></td>
			</tr>
			<tr>
			  <td>
			    <strong>DB Server Port</strong>
			  </td>
			  <td>
			    <input class="std" name="spt" type="text" value="<{$configdata.spt}>">
			  </td>
			  <td><font color=red><{$errordata.spt}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>DB User</strong>
			  </td>
			  <td>
			  <input class="std" name="dbu" type="text" value="<{$configdata.dbu}>">
			  </td>
			  <td><font color=red><{$errordata.dbu}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>DB Password</strong>
			  </td>
			  <td>
			  <input class="std" name="dbp" type="text" value="<{$configdata.dbp}>">
			  </td>
			  <td><font color=red><{$errordata.dbp}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>DataBase Name</strong>
			  </td>
			  <td>
			  <input class="std" name="dbn" type="text" value="<{$configdata.dbn}>">
			  </td>
			  <td><font color=red><{$errordata.dbn}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Solr Query</strong>
			  </td>
			  <td>
			  <textarea class="std" rows="10" cols="70" name="sqy"><{$configdata.sqy}></textarea>
			  </td>
			  <td><font color=red><{$errordata.sqy}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(1)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl1" type="text" value="<{$configdata.fcl1}>">
			  </td>
			  <td><font color=red><{$errordata.fcl1}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(2)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl2" type="text" value="<{$configdata.fcl2}>">
			  </td>
			  <td><font color=red><{$errordata.fcl2}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(3)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl3" type="text" value="<{$configdata.fcl3}>">
			  </td>
			  <td><font color=red><{$errordata.fcl3}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(4)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl4" type="text" value="<{$configdata.fcl4}>">
			  </td>
			  <td><font color=red><{$errordata.fcl4}></font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(5)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl5" type="text" value="<{$configdata.fcl5}>">
			  </td>
			  <td><font color=red><{$errordata.fcl5}></font></td>
			</tr>
			<tr>
			  <td>
			  </td>
			  <td>
			  <input class="stdbutton" type="submit" value="Save To Config">
			  </td>
			  <td></td>
			</tr>
		</table>
	</form>
</body>
</html>