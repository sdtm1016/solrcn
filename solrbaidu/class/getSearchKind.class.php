<?php
class getSearchKind{
    public static function getSearchKindData($wd){
         //solr对象取得
		 $solr = conn::getConn();
		 //检索条件
		 $queries = array(
		    $wd
		  );
		//取得分类
         $response = $solr->spell($queries);
        //取得结果判断
        if ($response->getHttpStatus() == 200 ) { 
           $allcount = $response->response->numFound;
           //判断取得结果
           if ($allcount>0){
	           $allary = array('全部'=>$allcount,);
	 	       $result = $response->facet_counts->facet_fields->Source;
	 	       //取得分类结果
	           foreach ($result as $key=>$value){
				   $allary[$key] = $value;
			   }
           }
 	     }
 	    //结果返回
 	    return $allary;
    }
}