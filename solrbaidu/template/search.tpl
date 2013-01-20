<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=7">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <title>搜索_<{$wd}></title>
    <script src="js/jquery.js"></script>  
    <script src="js/jquery.autocomplete.js"></script> 
    <script src="js/thinking_search.js"></script> 
    <link href="css/jquery.autocomplete.css" rel="stylesheet" type="text/css"> 
    <link rel="stylesheet" href="./css/search.css" type="text/css" />
</head>
<body link="#0000cc">
<div id="out">
	<div id="in">
		<div id="wrapper">
			<p id="u">
			        <a href="#">搜索设置</a>
			        &nbsp;|&nbsp;
			        <a href="#">登录</a>
			</p>
			<{include file="searchhead.tpl"}><br>
			<div id="container">
			  <{if (!empty($searchKind))}>
				  <div class="lei-box" >
				      <{include file="searchkind.tpl"}>
				  </div>
				  <div class="lei-warp"></div>
			  <{/if}>
			  <{if (!$isempty)}>
			     <{include file="searchnothing.tpl"}>
			  <{else}>
			     <{if ($existFlag)}>
			         <{$searchdata}>
			     <{else}>
			         <p style="margin:0 15px 10px 0">
					   <strong class="f14">
					              您要找的是不是:
					       <span class="jc">
					         <a href="search.php?wd=<{$solraboutword|escape:'url'}>"><{$solraboutword}></a>
					       </span>
					   </strong>
					   <br>
					 </p>
					 <{$searchdata}>
			     <{/if}>
			  <{/if}>
			  </div>
		    </div>
		    <{if (!empty($searchabout))}>
		       <div id="rs">
		         <{include file="searchabout.tpl"}>
		       </div>
		    <{/if}>
		    
			<{include file="searchfoot.tpl"}>
	</div>
</div>
</body>
</html>