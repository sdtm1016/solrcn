<?php
//取得分词后内容高亮
class setKeywordHighlight{
	public static function setHighlight($response){
		 //检索结果
	     $solrdatadoc = $response->response->docs;
	     //显示高亮结果
	     $solrdatahighlight = $response->highlighting;
	     //取得高亮内容
		 foreach ($solrdatadoc as $doc ){
		 	   //取得id
		 	   $id = $doc->id;
		 	   //title初始化
		 	   $title = null;
		 	   //description初始化
		 	   $description = null;
		 	   //取得高亮的title和description
		 	   self::getHighlightValue($solrdatahighlight,$id,$title,$description);
		 	   //高亮title设定
		 	   if ($title != null){
		 	      $doc->title= $title[0];
		 	   }
		 	   //高亮description设定
		 	   if ($description != null){
		 	      $doc->description= $description[0];
		 	   }
		 	   //释放资源
		 	   //title初始化
		 	   $title = null;
		 	   //description初始化
		 	   $description = null;
		    }
	}
	//根据Id取得高亮内容
	private function  getHighlightValue($solrdatahighlight,$id,&$title,&$description){
		 foreach ($solrdatahighlight as $key=>$value ){
		 	//根据Id查找高亮内容
		 	switch ($key){
		 		case $id:
		 			 //取得高亮Title
			 	     if($value->title != null){
			            $title = $value->title;
			          }
			         //取得高亮Description
			         if($value->description != null){
			            $description = $value->description;
			          }
			          break ;
		 		default:
		 			break;
		 	}
		 }
	}
}