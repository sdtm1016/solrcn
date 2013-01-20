<?php
class getSearchClustering{
    public  static  function getSearchClusteringData($wd, $solr){
    	//变量定义
    	$solrdata = array();
        //检索条件  
		$queries = array(
		    $wd
		  );
	    //检索数据
		$response = $solr->quary($queries,SOLR_CLUS,CLUS_NUM); 
		//判定检索结果，得到的HTTP状态码
		if ($response->getHttpStatus() == 200 ) { 
			//取得相关搜索结果
			$solrclusterdata =$response->clusters;
		}else { 
			//输出异常HTTP请求信息
			$solrclusterdata.=$response->getHttpStatusMessage(); 
		}
		//取得相关搜索结果
    	foreach ($solrclusterdata as $document){
    		if ($document->score > 0){
    		    foreach ($document->labels as $key=>$value){
    			   $solrdata[] =$value;
    			}
    		}
    	}
    	
    	//取相关搜索前10条显示
    	while (count($solrdata)>10){
    	   //保留前10条信息，删除多余信息
    	   array_pop($solrdata);
    	}
    	
		//返回检索结果
		return $solrdata;
    }
}