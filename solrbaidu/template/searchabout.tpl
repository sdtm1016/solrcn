<table cellpadding="0">
	<tbody>
		<tr>
			<th class="tt" rowspan="2">相关搜索</th>
		    <{foreach from=$searchabout key=key item=aboutItem name =foo}>
			    <{if $smarty.foreach.foo.index == 5  }>
			      </tr>
			      <tr>
			    <{/if}>
				<th>
			       <a href="search.php?wd=<{$aboutItem|escape:'url'}>"><{$aboutItem}></a>
			    </th>
			    <td></td>
			  <{if $smarty.foreach.name.index == 4  }>
		         </tr>
		      <{/if}>
			 <{/foreach}>
	</tbody>
</table>