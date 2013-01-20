<?php
   require('../config/common.inc.php');
   //引入db-data-config的xml模板
   include '../config/db-data-config.php';
   //页面加载flag初始化
   $loadflag =false;
   //取得提交Flag
   if (isset($_REQUEST['flag'])){
         $loadflag =$_REQUEST['flag'];
   }
   
   //页面显示内容初始化
   $configdata =array('sip'=>"",
                      'spt'=>"",
                      'dbn'=>"",
                      'dbu'=>"",
                      'dbp'=>"",
                      'sqy'=>"",
                      'fcl1'=>"",
                      'fcl2'=>"",
                      'fcl3'=>"",
                      'fcl4'=>"",
                      'fcl5'=>"",);
   //check错误信息
   $error = array('sip'=>"",
                  'spt'=>"",
                  'dbn'=>"",
                  'dbu'=>"",
                  'dbp'=>"",
                  'sqy'=>"",
                  'fcl1'=>"",
                  'fcl2'=>"",
                  'fcl3'=>"",
                  'fcl4'=>"",
                  'fcl5'=>"",);
   
	  //用户提交页面的场合进行以下处理
	  if(isset($_REQUEST['sip'])){
	        $sip = $_REQUEST['sip'];
	        if (empty($sip)){
	           $error['sip']="Server Ip Is Empty";
	           $emptyFlag = true;
	        }
	  }
	  if(isset($_REQUEST['spt'])){
	        $spt = $_REQUEST['spt'];
	        if (empty($spt)){
	           $error['spt']="Server Port Is Empty";
	           $emptyFlag = true;
	        }
	  }
	  if(isset($_REQUEST['dbn'])){
	        $dbn = $_REQUEST['dbn'];
	        if (empty($dbn)){
	           $error['dbn']="Database Name Is Empty";
	           $emptyFlag = true ;
	           $emptyFlag = true;
	        }
	  }
	  if(isset($_REQUEST['dbu'])){
	        $dbu = $_REQUEST['dbu'];
	        if (empty($dbu)){
	           $error['dbu']="Database User Is Empty";
	           $emptyFlag = true;
	        }
	  }
	  if(isset($_REQUEST['dbp'])){
	        $dbp = $_REQUEST['dbp'];
	        if (empty($dbp)){
	           $error['dbp']="Database Password Is Empty";
	           $emptyFlag = true;
	        }
	  }
	   if(isset($_REQUEST['sqy'])){
	        $sqy = $_REQUEST['sqy'];
	        if (empty($sqy)){
	           $error['sqy']="Select Query Is Empty";
	           $emptyFlag = true;
	        }
	  }
	  if(isset($_REQUEST['fcl1'])){
	        $fcl1 = $_REQUEST['fcl1'];
	        if (empty($fcl1)){
	           $error['fcl1']="Field Column(1) Is Empty";
	           $emptyFlag = true ;
	        }
	  }
	  if(isset($_REQUEST['fcl2'])){
	        $fcl2 = $_REQUEST['fcl2'];
	        if (empty($fcl2)){
	           $error['fcl2']="Field Column(2) Is Empty";
	           $emptyFlag = true;
	        }
	  }
	  if(isset($_REQUEST['fcl3'])){
	        $fcl3 = $_REQUEST['fcl3'];
	        if (empty($fcl3)){
	           $error['fcl3']="Field Column(3) Is Empty";
	           $emptyFlag = true ;
	        }
	  }
	   if(isset($_REQUEST['fcl4'])){
	        $fcl4 = $_REQUEST['fcl4'];
	        if (empty($fcl4)){
	           $error['fcl4']="Field Column(4) Is Empty";
	           $emptyFlag = true;
	        }
	  }
	  if(isset($_REQUEST['fcl5'])){
	        $fcl5 = $_REQUEST['fcl5'];
	        if (empty($fcl5)){
	           $error['fcl5']="Field Column(5) Is Empty";
	           $emptyFlag = true;
	        }
	  }
	  
	//设置参数完成后，进行配置
    if ((!$emptyFlag) && $loadflag){
       	//输入数check
	    if(saveDbconfig::saveCheck($sip, $spt, $dbn, $dbu, $dbp, $sqy, $fcl1, $fcl2, $fcl3, $fcl4, $fcl5, $error)){
	       $dbxml = new SimpleXMLElement($dbconfigxml);
		   $xmlAttribute=createConfig::getParam($sip, $spt, $dbn,$dbu, $dbp, $sqy, $fcl1, $fcl2, $fcl3, $fcl4, $fcl5);
		   createConfig::createDbDataConfig($dbxml, $xmlAttribute);
		   //solr读取url
	       $saveurl="http://".SOLR_HOST.":".SOLR_PORT.SOLR_NAME."/dataimport?command=save-config&wt=json";
		   //保存xml
		   saveDbconfig::dbconfigSave($saveurl, $dbxml->asXML());	       
	    }  
    }
    
    //solr读取url
    $geturl="http://".SOLR_HOST.":".SOLR_PORT.SOLR_NAME."/dataimport?command=show-config";
    //读取solr的db-config.xml文件初始化页面数据
    loadDbconfig::getDbconfigData($geturl,$configdata);
    
    //传递参数设置
    $setParam=array("configdata"=>$configdata,
                    "errordata"=>$error,);
    //传递参数
    HLTemplate::Assign($setParam);
    //调用模板
    HLTemplate::Display('serverconfig.tpl');
  
  