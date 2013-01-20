<?php
//截取Url
class getUrlData {
     public static  function  getUrl($L_url,$L_start,$L_len){
     	  //临时变量
     	  $L_temp ="" ;
     	  //url长度取得
     	  $L_str_len = strlen($L_url);
     	  //url判断
     	  if($L_len < 1) return "";
     	  //url截取
          if ($L_str_len > $L_len){
			  $L_temp = mb_substr($L_url, $L_start,$L_len)." ...";
		  }else{
			  $L_temp = $L_url;
		  }
		  //url返回
		  return $L_temp;
     }
}