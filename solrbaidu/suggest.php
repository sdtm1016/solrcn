<?php
   session_start();
   require('./config/common.inc.php');
    
   //接收参数
    if(isset($_REQUEST['q'])){
        $wd = $_REQUEST['q'];
        //参数为空的情况返回搜索页面
        if (empty($wd)){
           header("location:./");
        }
     }

	  $solrdata = getSuggestData::getSuggestResult($wd);
     
	 /* test data
      $json="[{ name:'新闻联播'},{ name:'机车美女' },{ name:'美女和野兽'},{ name:'醉酒后的美女' }]";  
  
	 */