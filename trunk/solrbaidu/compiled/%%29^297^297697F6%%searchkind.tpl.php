<?php /* Smarty version 2.6.19, created on 2011-09-01 17:19:05
         compiled from searchkind.tpl */ ?>
<?php require_once(SMARTY_CORE_DIR . 'core.load_plugins.php');
smarty_core_load_plugins(array('plugins' => array(array('modifier', 'escape', 'searchkind.tpl', 11, false),)), $this); ?>
	<table cellpadding="3">
		<tbody>
			<tr>
			    <?php $_from = $this->_tpl_vars['searchKind']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['foo'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['foo']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['key'] => $this->_tpl_vars['aboutItem']):
        $this->_foreach['foo']['iteration']++;
?>
				    <?php if (($this->_foreach['foo']['iteration']-1) % 10 == 0): ?>
				      </tr>
				      <tr>
				    <?php endif; ?>
					<th align=left class="lei-th">
					  <?php if (($this->_foreach['foo']['iteration']-1) == 0): ?>
					     <a href="search.php?wd=<?php echo ((is_array($_tmp=$this->_tpl_vars['wd'])) ? $this->_run_mod_handler('escape', true, $_tmp, 'url') : smarty_modifier_escape($_tmp, 'url')); ?>
" class="lei-a"><span class="lei-wrap"><?php echo $this->_tpl_vars['key']; ?>
</span><span class="ft9">(<?php echo $this->_tpl_vars['aboutItem']; ?>
)</span></a>
					  <?php else: ?>
				          <a href="search.php?wd=<?php echo ((is_array($_tmp=$this->_tpl_vars['wd'])) ? $this->_run_mod_handler('escape', true, $_tmp, 'url') : smarty_modifier_escape($_tmp, 'url')); ?>
&kind=<?php echo ((is_array($_tmp=$this->_tpl_vars['key'])) ? $this->_run_mod_handler('escape', true, $_tmp, 'url') : smarty_modifier_escape($_tmp, 'url')); ?>
" class="lei-a"><span class="lei-wrap"><?php echo $this->_tpl_vars['key']; ?>
</span><span class="ft9">(<?php echo $this->_tpl_vars['aboutItem']; ?>
)</span></a>
				      <?php endif; ?>
				    </th>
				    <td></td>
				  <?php if (($this->_foreach['name']['iteration']-1) == 9): ?>
			         </tr>
			      <?php endif; ?>
				 <?php endforeach; endif; unset($_from); ?>
		</tbody>
	</table>