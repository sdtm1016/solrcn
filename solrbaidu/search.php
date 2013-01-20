<?php
   session_start();
   require('./config/common.inc.php');
   
    //变量初始化
    $existFlag = false;
   
    //接收参数
    if(isset($_REQUEST['wd'])){
        $wd = $_REQUEST['wd'];
        //参数为空的情况返回搜索页面
        if (empty($wd)){
           header("location:./");
        }
     }
    //判断当前页码
	 if(empty($_GET['page'])||$_GET['page']<0){
	 	  //页面初始化的场合，默认第一页
	      $page=1;
	 }else {
	      $page=$_GET['page'];
	 }
	 //取得分类
	 if(isset($_REQUEST['kind'])){
	   $kind = $_REQUEST['kind'];
	   //参数为空的情况返回搜索页面
	   if(empty($kind)){
	      header("location:./");
	   }
	 }

	 //分类取得
	 $searchKind = getSearchKind::getSearchKindData($wd);
	 
     //检索结果取得
     getSearchData::getSearchResult($wd, $page, $isEmpty, $existFlag, 
                                    $searchdata, $searchabout, $solraboutword,$kind);

     //模板参数
     $searchParam  = array('wd' => $wd,  //检索关键字
                           'isempty' => $isEmpty , //检索结果flag
                           'existFlag' => $existFlag , //拼写检查是否存在flag
                           'searchdata' => $searchdata,  //显示结果内容
                           'searchabout' => $searchabout, //相关搜索内容
                           'solraboutword' => $solraboutword, //拼写检查提示纠错
                           'searchKind' => $searchKind,
                     );
                     
     //调用模板
	 HLTemplate::Assign($searchParam);
	 HLTemplate::Display('search.tpl');

