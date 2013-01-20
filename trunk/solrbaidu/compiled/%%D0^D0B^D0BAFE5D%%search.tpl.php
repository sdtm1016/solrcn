<?php /* Smarty version 2.6.19, created on 2012-09-14 19:10:39
         compiled from search.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'escape', 'search.tpl', 40, false),)), $this); ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=7">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <title>搜索_<?php echo $this->_tpl_vars['wd']; ?>
</title>
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
			<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "searchhead.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?><br>
			<div id="container">
			  <?php if (( ! empty ( $this->_tpl_vars['searchKind'] ) )): ?>
				  <div class="lei-box" >
				      <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "searchkind.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
				  </div>
				  <div class="lei-warp"></div>
			  <?php endif; ?>
			  <?php if (( ! $this->_tpl_vars['isempty'] )): ?>
			     <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "searchnothing.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
			  <?php else: ?>
			     <?php if (( $this->_tpl_vars['existFlag'] )): ?>
			         <?php echo $this->_tpl_vars['searchdata']; ?>

			     <?php else: ?>
			         <p style="margin:0 15px 10px 0">
					   <strong class="f14">
					              您要找的是不是:
					       <span class="jc">
					         <a href="search.php?wd=<?php echo ((is_array($_tmp=$this->_tpl_vars['solraboutword'])) ? $this->_run_mod_handler('escape', true, $_tmp, 'url') : smarty_modifier_escape($_tmp, 'url')); ?>
"><?php echo $this->_tpl_vars['solraboutword']; ?>
</a>
					       </span>
					   </strong>
					   <br>
					 </p>
					 <?php echo $this->_tpl_vars['searchdata']; ?>

			     <?php endif; ?>
			  <?php endif; ?>
			  </div>
		    </div>
		    <?php if (( ! empty ( $this->_tpl_vars['searchabout'] ) )): ?>
		       <div id="rs">
		         <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "searchabout.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
		       </div>
		    <?php endif; ?>
		    
			<?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "searchfoot.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
	</div>
</div>
</body>
</html>