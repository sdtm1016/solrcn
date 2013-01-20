<?php /* Smarty version 2.6.19, created on 2011-10-24 22:32:59
         compiled from serverconfig.tpl */ ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<link rel="stylesheet" href="../css/install.css" type="text/css" />
</head>
<body>
    <?php $_smarty_tpl_vars = $this->_tpl_vars;
$this->_smarty_include(array('smarty_include_tpl_file' => "installhead.tpl", 'smarty_include_vars' => array()));
$this->_tpl_vars = $_smarty_tpl_vars;
unset($_smarty_tpl_vars);
 ?>
    <br clear="all">
	<form method="POST" action="serverconfig.php?flag=true" accept-charset="UTF-8">
		<table>
			<tr>
			  <td>
			    <strong>DB Server IP</strong>
			  </td>
			  <td>
			    <input class="std" name="sip" type="text" value="<?php echo $this->_tpl_vars['configdata']['sip']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['sip']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			    <strong>DB Server Port</strong>
			  </td>
			  <td>
			    <input class="std" name="spt" type="text" value="<?php echo $this->_tpl_vars['configdata']['spt']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['spt']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>DB User</strong>
			  </td>
			  <td>
			  <input class="std" name="dbu" type="text" value="<?php echo $this->_tpl_vars['configdata']['dbu']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['dbu']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>DB Password</strong>
			  </td>
			  <td>
			  <input class="std" name="dbp" type="text" value="<?php echo $this->_tpl_vars['configdata']['dbp']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['dbp']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>DataBase Name</strong>
			  </td>
			  <td>
			  <input class="std" name="dbn" type="text" value="<?php echo $this->_tpl_vars['configdata']['dbn']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['dbn']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Solr Query</strong>
			  </td>
			  <td>
			  <textarea class="std" rows="10" cols="70" name="sqy"><?php echo $this->_tpl_vars['configdata']['sqy']; ?>
</textarea>
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['sqy']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(1)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl1" type="text" value="<?php echo $this->_tpl_vars['configdata']['fcl1']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['fcl1']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(2)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl2" type="text" value="<?php echo $this->_tpl_vars['configdata']['fcl2']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['fcl2']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(3)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl3" type="text" value="<?php echo $this->_tpl_vars['configdata']['fcl3']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['fcl3']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(4)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl4" type="text" value="<?php echo $this->_tpl_vars['configdata']['fcl4']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['fcl4']; ?>
</font></td>
			</tr>
			<tr>
			  <td>
			  <strong>Field Column(5)</strong>
			  </td>
			  <td>
			  <input class="std" name="fcl5" type="text" value="<?php echo $this->_tpl_vars['configdata']['fcl5']; ?>
">
			  </td>
			  <td><font color=red><?php echo $this->_tpl_vars['errordata']['fcl5']; ?>
</font></td>
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