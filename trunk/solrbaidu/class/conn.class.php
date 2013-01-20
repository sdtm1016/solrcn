<?php
//连接solr基础类
require_once( './Apache/Solr/Service.php' );
//连接Solr  
class Conn {
	public static function getConn() {
		 // 创建一个服务类                                                  主机                        端口          应用名   
		 $solr = new Apache_Solr_Service( SOLR_HOST, SOLR_PORT, SOLR_NAME );   
		 //检查服务是否可用   
		 if ( ! $solr->ping() ) {   
		    return  null;   
		  } else{
		    return  $solr;
		  }
	}
}