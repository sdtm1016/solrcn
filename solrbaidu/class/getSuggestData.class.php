<?php
class getSuggestData{
  //检索SOLR
     public static function getSuggestResult($wd,&$searchdata){
		 //solr对象取得
		 $solr = conn::getConn();

		 //检索条件  
		$queries = array(
		    $wd
		  );
	    //检索数据
		$solrdata = self::getSolrData($wd,$solr);
		//var_dump($solrdata);
        //联想字符串
	    $titles = "[";
		//循环输出检索结果
        foreach($solrdata as $value){
			$title =  $value ;
			$titles .= "{name:'" . $title ."'}" . ",";
		 }  
		 //去掉最后的，
         $titles = substr($titles,0,-1);
		 $titles .= "]";
		//返回联想结果
		 echo $titles;
	 }

	//solr中取得联想结果
	private  function getSolrData($wd,$solr){
		//联想条件  
		$queries = array(
		    $wd
		  );
		  
	    //取得检索结果条数
		foreach ( $queries as $query ) { 
			    //检索数据
			     $response = $solr->suggest($query); 
				// var_dump($response->spellcheck->suggestions->$queries[0]->numFound);
			     //判定检索结果，得到的HTTP状态码
			     if ($response->getHttpStatus() == 200 ) { 
				      if ( $response->spellcheck->suggestions->$queries[0]->numFound > 0) { 
				          //检索结果
					      $solrdata =$response->spellcheck->suggestions->$queries[0]->suggestion;
				         }
				      else{
				          //检索结果为空
				          $solrdata.="";
				      }
				  }else { 
				      //输出异常HTTP请求信息
				      $solrdata.=$response->getHttpStatusMessage(); 
				  }
			} 
		 //返回检索结果
		 return $solrdata;
	}
}