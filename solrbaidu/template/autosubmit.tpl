<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/json.js"></script>
</head>
<body onload="login()">
  <form name="autosave" id="autosave" method="post" action="">
  <input type="hidden" name="url" value="<{$url}>"/>
  	<textarea name="dataConfig" rows="20" cols="80" style="display:none"><{$xml}></textarea>
  </form>
</body>
<script language="javascript">
function login()
{
	var url = document.autosave.url.value;
	var dataConfig = document.autosave.dataConfig.value;
	$.ajax({
	    type: "POST",
	    url: url,
	    data: "dataConfig="+ dataConfig,
	    beforeSend: function(){
	    },
	    success: function(xml){
			var jsondata =JSON.parse(xml);   
			alert("DB Congig Xml Save "+jsondata["status"]);
			history.back();
	    }
	});
}
</script>
</html>