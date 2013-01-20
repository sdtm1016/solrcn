<?php
//索引数据的取得
class getSearchSpell{
   public static function  getSearchSpellData($wd,$solr,&$existFlag,$countnum){
        //检索条件  
		$queries = array(
		    $wd
		  );
	    //检索数据
		$response = $solr->quary($queries,SOLR_SPELL,$countnum); 
		//var_dump($response);
		//判定检索结果，得到的HTTP状态码
		if ($response->getHttpStatus() == 200 ) { 
			//取得相关拼写检查结果
			//solr4.x之前版本
			if($response->suggestions->$queries[0]->numFound > 0){
				$solrdata =$response->suggestions;
			}
			//solr4.x
			if($response->spellcheck->suggestions->$queries[0]->numFound > 0){
				$solrdata =$response->spellcheck;
			}
		}else { 
			//输出异常HTTP请求信息
			$solrdata.=$response->getHttpStatusMessage(); 
		}
		
		//拼写检查Flag
		if ($response->exist == "true"){
			//搜索关键词存在，拼写检查存在
		     $existFlag = true ;
		}else{
			//搜索关键词不存在，使用拼写检查
		     $existFlag = false ; 
		}
		//返回检索结果
		return $solrdata;
   }
  
   //纠错关键词
   public static function getSpellWord($searchabout){
	     $e = self::objectToArray($searchabout);
		 $b = array_pop(array_pop($e));
		 //var_dump($b['suggestion'][0]);
   	    //返回建议词，拼写检查第一个词
        return $b['suggestion'][0];
   }

  //对象转换为数组
  private static function objectToArray($e){ 
	    $e=(array)$e; 
		foreach($e as $k=>$v){ 
			if( gettype($v)=='resource' ) return; 
			if( gettype($v)=='object' || gettype($v)=='array' ) 
				$e[$k]=(array)(self::objectToArray($v)); 
		} 
		return $e;
  }

}