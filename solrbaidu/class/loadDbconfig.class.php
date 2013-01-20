<?php
class loadDbconfig{
     public static function getDbconfigData($url,&$configdata){
          //读取solr的db-config.xml文件
          //取得db-config.xml文件内容
          $strdbconfig =file_get_contents($url);
          //判断取得内容
          if(!empty($strdbconfig)){
             //读取内容转化为xml
             $xml = new SimpleXMLElement($strdbconfig);
             //读取db-config.xml中的属性值
             self::getConfigAttribute($xml, $configdata);
          }
          //返回取得config属性
          return $configdata;
     }
  
     private function getConfigAttribute($xml,&$configdata){
          $url=$xml->dataSource[url];
          self::getDbinfo($url, $sip, $spt, $dbn);
          $configdata['sip']=$sip;
          $configdata['spt']=$spt;
          $configdata['dbn']=$dbn;
          $configdata['dbu']=(string)$xml->dataSource[user];
          $configdata['dbp']=(string)$xml->dataSource[password];
          $configdata['sqy']=(string)$xml->document->entity[query];
          foreach ($xml->document->entity->field as $values){
             $fields.= $values[column].":";
          }
          self::getFieldinfo($fields, $fcl1, $fcl2, $fcl3, $fcl4, $fcl5);
          $configdata['fcl1']=$fcl1;
          $configdata['fcl2']=$fcl2;
          $configdata['fcl3']=$fcl3;
          $configdata['fcl4']=$fcl4;
          $configdata['fcl5']=$fcl5;
     }
     //取得数据库配置信息
     private function getDbinfo($url,&$sip,&$spt,&$dbn){
         $strarray1 = split("://", $url);
         $strarray2= split(":", $strarray1[1]);
         $sip = $strarray2[0];
         $strarray3= split("/", $strarray2[1]);
         $spt = $strarray3[0];
         $dbn = $strarray3[1];
     }
     //取得Field信息
     private function getFieldinfo($fields,&$fcl1,&$fcl2,&$fcl3,&$fcl4,&$fcl5){
         $strarray = split(":",$fields);
	     $fcl1 = $strarray[0];
         $fcl2 = $strarray[1];
         $fcl3 = $strarray[2];
         $fcl4 = $strarray[3];
         $fcl5 = $strarray[4];
     } 
}  
