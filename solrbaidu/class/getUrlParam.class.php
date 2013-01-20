<?php
//Url参数取得
class getUrlParam {
    public  static  function getUrlParamter(){
    	//取得参数
    	foreach($_GET as $key=>$value){
	        if ($key != PAGE_KEY)
	        //参数拼接
	        $param .= $key . '=' . urlencode($value) . '&';
	    }
	    //Url参数
	    $param = substr($param,0,strlen($param)-1);
	    //Url参数返回
        return $param;
    }
}