<?php
class getSearchDataHtml{
    //取得检索结果并转化为HTML
	public static function getSearchHtml($wd,&$page,$solr,$kind){
	    
	    //检索用变量
	    $count = 0 ;  //检索结果总数量
	    $pagesize = 10 ; //检索数量--每页显示条数
	    //分页用变量
	    $page_count = 0 ;  //分页数量
	    $offset = 0;  //检索起始位置
	    $offset=$pagesize*($page-1);
	    
		//取得检索结果
	    $solrdata = self::getSolrData($wd, $solr, $count, $pagesize,$page_count,$offset,$kind);
	    //取得检索结果HTML
	    $solrHtml = self::getSolrHtml($solrdata);
	    //取得分页HTML
	    $pageHtml = self::getPageHtml($count,$page_count,$page,$pagesize);
	    //取得返回HTML
	    $searchdata.=$solrHtml;
	    $searchdata.=$pageHtml;
	    //检索结果返回	 
	    return  $searchdata;
	}

	//solr中取得检索结果
	public  function getSolrData($wd,$solr,&$count,$pagesize,&$page_count,$offset,$kind){
		//检索条件  
		$queries = array(
		    $wd
		  );
		  
	    //取得检索结果条数
		if ($count==0){
			foreach ( $queries as $query ) { 
			    //检索数据
			     $response = $solr->search( $query, $kind ,$offset, $pagesize); 

			     //判定检索结果，得到的HTTP状态码
			     if ($response->getHttpStatus() == 200 ) { 
			     	  //取得结果记录集
				      $count=$response->response->numFound ;
				      //取得分页数
				 	  $page_count =ceil($count/$pagesize);
				      if ( $response->response->numFound > 0) { 
				          //检索结果
				          setKeywordHighlight::setHighlight($response);
					      $solrdata =$response->response->docs;
				         }
				      else{
				          //检索结果为空
				          $solrdata.="检索结果为空<br/>";
				      }
				  }else { 
				      //输出异常HTTP请求信息
				      $solrdata.=$response->getHttpStatusMessage(); 
				  }
			} 
		 }else{
			 foreach ( $queries as $query ) { 
				    //检索数据
				     $response = $solr->search( $query, $offset, $pagesize); 
				     //判定检索结果，得到的HTTP状态码
				     if ( $response->getHttpStatus() == 200 ) { 
					      if ( $response->response->numFound > 0) { 
					          //检索结果
					          setKeywordHighlight::setHighlight($response);
						      $solrdata =$response->response->docs;
					      } 
					      else{
					        //检索结果为空
					        $solrdata.="检索结果为空<br/>";
					      }
					  }else { 
					      //输出异常HTTP请求信息
					      $solrdata.= $response->getHttpStatusMessage(); 
					  } 
			     }
		 }
		 
		 //返回检索结果
		 return $solrdata;
	}

	//检索结果HTML
	private function getSolrHtml($solrdata){
	      //循环输出检索结果
	      foreach ($solrdata as $doc ){
	      	    //取得显示内容：标题、摘要、链接、日期
		      	foreach ($doc as $key=>$value){
			         switch ($key){
			         	//标题
			      	    case SOLR_TITLE:
			      	    	$title = $value ;
			      	    	//高亮设置
			      	    	$title = str_replace("<em>", "<font color=red>", $title);
			      	    	$title = str_replace("</em>", "</font>", $title);
			      	    	break ;
			      	    //摘要
			      	    case SOLR_SUMMARY:
			      	    	$summary = $value ;
			      	    	//高亮设置
         	      	    	$summary = str_replace("<em>", "<font color=red>", $summary);
			      	    	$summary = str_replace("</em>", "</font>", $summary);
			      	        break ;
			      	    //链接
			      	    case SOLR_LINK :
			      	    	$link = $value ;
			      	    	break ;
			      	    //日期
			      	    case SOLR_DATE:
			      	    	$date =date("Y-m-d",strtotime($value));
			      	    	break;
		             }
	      	   }
	      	   //转换为HTML
	         	$solrHtml.="<table cellpadding='0' cellspacing='0' class='result' id='1'>";
				  $solrHtml.="<tr>";		
					$solrHtml.="<td class=f>" ;		
						$solrHtml.="<h3 class='t'>";		
							$solrHtml.="<a href='$link' target='_blank'>$title</a>";
					    $solrHtml.="</h3>";	
					    //摘要显示判定
	      				$mysummary =getSummaryData::getSummary($summary, SUMMARY_START, SUMMARY_LEN);					    
	      				$solrHtml.="<font size=-1>$mysummary<br></font>";
					    //链接地址显示判断
					    $mylink = getUrlData::getUrl($link, LINK_START, LINK_LEN);
					    $solrHtml.="<font color='#008000'><span class='g'>$mylink</span>&nbsp;&nbsp;&nbsp;";
					    $solrHtml.="<span class='g'>$date</span></font>";
					$solrHtml.="</td>";
				  $solrHtml.="</tr>";
		    $solrHtml.="</table><br>";
	      }
	      //取得检索结果HTML
	      return $solrHtml;
	}

    //检索结果分页HTML
	private  function getPageHtml($count,$page_count,&$page,$pagesize){
		 //检索结果判断
		 if($count==0){
		 	//检索结果返回	 
	        return  $pagedata;
		 }elseif ($count<=$pagesize){
		 	//检索结果为一页情况下，不显示页码
		 	$pagedata.="<br clear=all>";
		 	$pagedata =$pagedata."<p id='page'>";
		 	$pagedata =$pagedata."<span class='nums' style='margin-left:120px'>找到相关结果约".$count."个</span>";
		    $pagedata =$pagedata."</p>";
		    //检索结果返回	 
	        return  $pagedata;
		 }
	     
		 $pagedata.="<br clear=all>";
		 
		 //显示分页页码
		 $init=1;
		 $page_len=7;
		 $max_p=$page_count;
		 $pages=$page_count;
		 $page_len = ($page_len%2)?$page_len:$pagelen+1;//页码个数
		 $pageoffset = ($page_len-1)/2;//页码个数左右偏移量
		 
		 //取得Url参数
		 $urlParam = getUrlParam::getUrlParamter();
		
		 $key="<p id='page'>";
		 if($page!=1){
			 $key.="<a href=\"".$_SERVER['PHP_SELF']."?".$urlParam."&page=".($page-1)."\" class='n'>上一页</a>"; //上一页
		 }
		
		 if($pages>$page_len){
			 //如果当前页小于等于左偏移
			 if($page<=$pageoffset){
				 $init=1;
				 $max_p = $page_len;
			 }else{//如果当前页大于左偏移
				 //如果当前页码右偏移超出最大分页数
				 if($page+$pageoffset>=$pages+1){
				 	$init = $pages-$page_len+1;
				 }else{
					 //左右偏移都存在时的计算
					 $init = $page-$pageoffset;
					 $max_p = $page+$pageoffset;
			     }
			 }
		  }
		 for($i=$init;$i<=$max_p;$i++){
			 if($i==$page){
			 	$key.=' <span>'.$i.'</span>';
			 } else {
			 	$key.=" <a href=\"".$_SERVER['PHP_SELF']."?".$urlParam."&page=".$i."\">[".$i."]</a>";
			 }
		  }
		 if($page!=$pages){
			 $key.=" <a href=\"".$_SERVER['PHP_SELF']."?".$urlParam."&page=".($page+1)."\" class='n'>下一页</a> ";//下一页
		 }
		 
		 $pagedata.=$key;
		 $pagedata =$pagedata."<span class='nums' style='margin-left:120px'>找到相关结果约".$count."个</span>";
		 $pagedata =$pagedata."</p>";
		 //返回分页HTML
		 return $pagedata;
     }

}