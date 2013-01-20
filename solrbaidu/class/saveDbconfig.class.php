<?php
class saveDbconfig{
	//保存前check
	public static function saveCheck($sip,$spt,$dbn,$dbu,$dbp,$sqy,$fcl1,$fcl2,$fcl3,$fcl4,$fcl5,&$error){
	    //判断结果初始化
		$flag =true;
		//数据连接判断
		if(!self::checkConn($sip, $spt, $dbn, $dbu, $dbp, $error)){
		   return false;
		}
		//check检索SQL
		if(!self::checkSql($sip, $spt, $dbn, $dbu, $dbp,$sqy, $error)){
		   return false;
		}
		//check设定索引参数
		if(!self::checkParam($sip, $spt, $dbn, $dbu, $dbp,$sqy,$fcl1, $fcl2, $fcl3, $fcl4, $fcl5, $error)){
		   return false;
		}
		//返回check结果
		return $flag;
	}
	
	//保存xml
    public static function dbconfigSave($url,$xml){
	    //传递参数设置
	    $setParam=array("url"=>$url,
	                    "xml"=>$xml,);
	    //传递参数
	    HLTemplate::Assign($setParam);
	    //调用模板
	    HLTemplate::Display('autosubmit.tpl');
    }
    
	//check数据库连接
	private  function checkConn($sip,$spt,$dbn,$dbu,$dbp,&$error){
		//判断结果初始化
		$flag =true;
		//连接数据库
	    $conn = @mysql_connect($sip.":".$spt,$dbu,$dbp);
	    if($conn){
	      //数据库连接成功
	      $db = mysql_select_db($dbn,$conn);
	      if(!$db){
	      	 $error['dbn']= "Database Is Not Exist";
	         $flag= false;
	      }
	      //关闭数据库连接
	      mysql_close($conn);
	    }else{
	      //数据库连接失败
	      $error['dbp']= "Mysql Connect Param Is Error";
	      $flag= false;
	    }
	    //返回判断结果
	    return  $flag;
	}
	//check检索SQL
	private  function checkSql($sip,$spt,$dbn,$dbu,$dbp,$sqy,&$error){
	   //判断结果初始化
	   $flag =true;
	   //连接数据库
	   $conn = @mysql_connect($sip.":".$spt,$dbu,$dbp);
	   mysql_select_db($dbn,$conn);
	   //设置编码
       mysql_query("set names 'UTF8'");
   	   //检索用SQL
	   $sql =$sqy." limit 1";
       //进行数据检索
       $result = mysql_query($sql); 
       //检索结果判断
       if(!$result){
       	  $error['sqy']= "Select SQL Is Error ";
          $flag = false;
       }
       //关闭数据库连接
	   mysql_close($conn);
       //返回判断结果
	   return  $flag;
	}
	//check设定索引参数
	private  function checkParam($sip, $spt, $dbn, $dbu, $dbp,$sqy,$fcl1,$fcl2,$fcl3,$fcl4,$fcl5,&$error){
	   //连接数据库
	   $conn = @mysql_connect($sip.":".$spt,$dbu,$dbp);
	   mysql_select_db($dbn,$conn);
	   //设置编码
       mysql_query("set names 'UTF8'");
   	   //检索用SQL
	   $sql =$sqy." limit 1";
       //进行数据检索
       $result = mysql_query($sql); 
       //列名集合数组
       $celarray = array();
  	   //检索结果判断
	   while ($row = mysql_fetch_array($result, MYSQL_BOTH)) {
         foreach ($row as $key=>$value){
      	    if(!is_numeric($key)){
      	      $celarray[]=$key;
      	    }
         }
	   } 
	   //关闭数据库
	   mysql_close($conn);
	   //判断Flag初始化
	   $flag = true;
	   //判断Field1
	   if(!self::checkIsexist($fcl1, $celarray)){
	      $error['fcl1'] = "Field1 Is Not Exist";
	      $flag = false;
	   }
	   //判断Field1
	   if(!self::checkIsexist($fcl2, $celarray)){
	      $error['fcl2'] = "Field2 Is Not Exist";
	      $flag = false;
	   }
	   //判断Field1
	   if(!self::checkIsexist($fcl3, $celarray)){
	      $error['fcl3'] = "Field3 Is Not Exist";
	      $flag = false;
	   }
	   //判断Field1
	   if(!self::checkIsexist($fcl4, $celarray)){
	      $error['fcl4'] = "Field4 Is Not Exist";
	      $flag = false;
	   }
	   //判断Field1
	   if(!self::checkIsexist($fcl5, $celarray)){
	      $error['fcl5'] = "Field5 Is Not Exist";
	      $flag = false;
	   }
	   //返回判断结果
	   return $flag;
	}
    //判断索引内容是否存在
    private function checkIsexist($key,$searcharray){
       //判断是否存在
       $result = array_search($key,$searcharray);
       if (!$result){
          //不存在
          return false;
       }else{
          //存在
          return true;
       }
    }
}