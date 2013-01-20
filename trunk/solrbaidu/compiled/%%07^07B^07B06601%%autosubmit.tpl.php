<?php /* Smarty version 2.6.19, created on 2011-10-24 22:26:28
         compiled from autosubmit.tpl */ ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/json.js"></script>
</head>
<body onload="login()">
  <form name="autosave" id="autosave" method="post" action="">
  <input type="hidden" name="url" value="<?php echo $this->_tpl_vars['url']; ?>
"/>
  	<textarea name="dataConfig" rows="20" cols="80" style="display:none"><?php echo $this->_tpl_vars['xml']; ?>
</textarea>
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