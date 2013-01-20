<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type">
    <script src="js/jquery.js"></script>  
    <script src="js/jquery.autocomplete.js"></script> 
    <script src="js/thinking_index.js"></script> 
    <link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 
    <link rel="stylesheet" href="css/index.css" type="text/css" />
</head>
<body>
	<p id="u">
		<a href="#">搜索设置</a>
		&nbsp;|&nbsp;
		<a href="#">登录</a>
	</p>
	<p id="lm"></p>
	<div id="m">
		<p id="lg">
			<img width="270" height="129" usemap="#mp" src="image/solr.png">
		</p>
		<div id="fm">
			<form action="search.php" name="f">
				<input id="kw" type="text" maxlength="100" name="wd" autocomplete="off" />
				<span class="btn_wr">
                   <input id="su" class="btn" type="submit" onmouseout="this.className='btn'" onmousedown="this.className='btn btn_h'" value="搜索一下">
				</span>
			</form>
		</div>
	</div>
	<div>
		<p id="lm"></p>
		<p id="lm"></p>
	</div>
	<div>
		<p id="cp">
          <{include file="footer.tpl"}>
		</p>
	</div>
</body>
</html>
