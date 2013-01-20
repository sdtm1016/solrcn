<?php /* Smarty version 2.6.19, created on 2013-01-20 19:44:18
         compiled from searchhead.tpl */ ?>
		<div id="head">
		     <div class="nv">
		        <a href="index.php" class="logo" onmousedown="return c({'fm':'tab','tab':'logo'})">
		          <img src="./image/solr_small.png" width="117" height="38" border="0" alt="到搜索首页">
		        </a>
		    </div>
		    <form name="f" action="search.php"  class="fm">
				<input name='wd' id='wd' class='i' maxlength='100' value="<?php echo $this->_tpl_vars['wd']; ?>
"> 
		        <span class="btn_wr">
		        <input type="submit" id="su" value="搜索一下" class="btn" onmousedown="this.className='btn btn_h'" onmouseout="this.className='btn'">
		        </span>
		    </form>
		</div>