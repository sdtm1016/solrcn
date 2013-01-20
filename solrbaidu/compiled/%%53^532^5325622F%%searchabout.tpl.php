<?php /* Smarty version 2.6.19, created on 2012-09-12 16:41:33
         compiled from searchabout.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'escape', 'searchabout.tpl', 11, false),)), $this); ?>
<table cellpadding="0">
	<tbody>
		<tr>
			<th class="tt" rowspan="2">相关搜索</th>
		    <?php $_from = $this->_tpl_vars['searchabout']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['foo'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['foo']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['key'] => $this->_tpl_vars['aboutItem']):
        $this->_foreach['foo']['iteration']++;
?>
			    <?php if (($this->_foreach['foo']['iteration']-1) == 5): ?>
			      </tr>
			      <tr>
			    <?php endif; ?>
				<th>
			       <a href="search.php?wd=<?php echo ((is_array($_tmp=$this->_tpl_vars['aboutItem'])) ? $this->_run_mod_handler('escape', true, $_tmp, 'url') : smarty_modifier_escape($_tmp, 'url')); ?>
"><?php echo $this->_tpl_vars['aboutItem']; ?>
</a>
			    </th>
			    <td></td>
			  <?php if (($this->_foreach['name']['iteration']-1) == 4): ?>
		         </tr>
		      <?php endif; ?>
			 <?php endforeach; endif; unset($_from); ?>
	</tbody>
</table>