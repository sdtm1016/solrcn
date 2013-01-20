	<table cellpadding="3">
		<tbody>
			<tr>
			    <{foreach from=$searchKind key=key item=aboutItem name =foo}>
				    <{if $smarty.foreach.foo.index % 10 == 0  }>
				      </tr>
				      <tr>
				    <{/if}>
					<th align=left class="lei-th">
					  <{if $smarty.foreach.foo.index == 0}>
					     <a href="search.php?wd=<{$wd|escape:'url'}>" class="lei-a"><span class="lei-wrap"><{$key}></span><span class="ft9">(<{$aboutItem}>)</span></a>
					  <{else}>
				          <a href="search.php?wd=<{$wd|escape:'url'}>&kind=<{$key|escape:'url'}>" class="lei-a"><span class="lei-wrap"><{$key}></span><span class="ft9">(<{$aboutItem}>)</span></a>
				      <{/if}>
				    </th>
				    <td></td>
				  <{if $smarty.foreach.name.index == 9  }>
			         </tr>
			      <{/if}>
				 <{/foreach}>
		</tbody>
	</table>