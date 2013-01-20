<?php
class getSearchData{
  //检索SOLR
     public static function getSearchResult($wd,&$page,&$isEmpty,&$existFlag,&$searchdata,&$searchabout,&$solraboutword,$kind){
		 //solr对象取得
		 $solr = conn::getConn();
		
		 //取得建议搜索
	      $searchabout = getSearchSpell::getSearchSpellData($wd, $solr, $existFlag,SOLR_SPELL_NUM);
      
         //搜索纠错判断
	     if (!$existFlag){
	     	 //搜索关键字不存在的情况下
	     	 //判定是否有相近词
	     	 if(count($searchabout)<1){
	     	    //没有相近词情况
	     	    $existFlag=true;
	     	    //取得搜索结果
	     	    $searchdata = getSearchDataHtml::getSearchHtml($wd, $page, $solr,$kind);
	     	    //取得相关搜索-聚类
	     	    $searchabout = getSearchClustering::getSearchClusteringData($wd, $solr);
	     	 }else{
	     	    //有相近词情况
	     	    //取得相近关键词
	            $solraboutword = getSearchSpell::getSpellWord($searchabout);
                //取得搜索结果
	     	    $searchdata = getSearchDataHtml::getSearchHtml($solraboutword, $page, $solr,$kind);
	            //取得相关搜索-聚类
	     	    $searchabout = getSearchClustering::getSearchClusteringData($wd, $solr);
	     	 }
	     	 //判断检索结果
		     if ($searchdata == null){
		        //检索结果为空
		        $isEmpty = false;
		     }else{
		     	 //检索结果存在
		         $isEmpty = true;
		     }
	     }else{
	         //搜索关键词存在的情况下
	         //取得搜索结果
	         $searchdata = getSearchDataHtml::getSearchHtml($wd, $page, $solr,$kind);
	         //取得相关搜索-聚类
	     	 $searchabout = getSearchClustering::getSearchClusteringData($wd, $solr);
	     	 //判断检索结果
		     if ($searchdata == null){
		        //检索结果为空
		        $isEmpty = false;
		     }else{
		     	 //检索结果存在
		         $isEmpty = true;
		     }
	     }
	}
}