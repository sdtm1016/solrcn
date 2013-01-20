<?php /* Smarty version 2.6.19, created on 2011-08-30 23:47:12
         compiled from test.tpl */ ?>
<link rel="stylesheet" href="css/search.css" type="text/css" />
<div class="lei-box" >
	<table cellpadding="0">
		<tbody>
			<tr>
			    <?php $_from = $this->_tpl_vars['test']; if (!is_array($_from) && !is_object($_from)) { settype($_from, 'array'); }$this->_foreach['foo'] = array('total' => count($_from), 'iteration' => 0);
if ($this->_foreach['foo']['total'] > 0):
    foreach ($_from as $this->_tpl_vars['key'] => $this->_tpl_vars['aboutItem']):
        $this->_foreach['foo']['iteration']++;
?>
				    <?php if (($this->_foreach['foo']['iteration']-1) % 10 == 0): ?>
				      </tr>
				      <tr>
				    <?php endif; ?>
					<th align=left class="lei-th">
				       <a href="search.php?wd=<?php echo $this->_tpl_vars['key']; ?>
" class="lei-a"><span class="lei-wrap"><?php echo $this->_tpl_vars['key']; ?>
</span><span class="ft9">(<?php echo $this->_tpl_vars['aboutItem']; ?>
)</span></a>
				    </th>
				    <td></td>
				  <?php if (($this->_foreach['name']['iteration']-1) == 9): ?>
			         </tr>
			      <?php endif; ?>
				 <?php endforeach; endif; unset($_from); ?>
		</tbody>
	</table>
</div>