<?php
class createConfig{
	//接受参数
	public static  function getParam($sip,$spt,$dbn,$dbu,$dbp,$sqy,$fcl1,$fcl2,$fcl3,$fcl4,$fcl5){
		$dataSourcekey = "dataSource";
		$dataSourcevalues = array('driver'=>"com.mysql.jdbc.Driver",
		                          'url'=>"jdbc:mysql://".$sip.":".$spt."/".$dbn,
		                          'user'=>$dbu,
		                          'password'=>$dbp,);
		
		$entitykey = "entity";
		$entityvalues = array('pk'=>"id",
		                      'query'=>$sqy,
		                     );
		$fieldkey = "field";                                  
		$fieldvalues = array('id'=>$fcl1,
			                 'title'=>$fcl2,
			                 'description'=>$fcl3,
							 'url'=>$fcl4,
			                 'updatetime'=>$fcl5,);
		
		$xmlAttribute = array($dataSourcekey=>$dataSourcevalues,
		                      $documentkey=>$documentvalues,
		                      $entitykey=>$entityvalues,
		                      $fieldkey=>$fieldvalues,);
		      
		return $xmlAttribute;
	}
	//设置xml属性
	public static  function createDbDataConfig(&$xml,$xmlAttribute){
		foreach ($xmlAttribute as $keyword=> $attributeValues){
		  switch ($keyword){
		  	case "dataSource":
			    foreach ($attributeValues as $key => $values){
			        $xml->dataSource[$key]=$values;
			    }
			    break;
		    case "entity":
		  		foreach ($attributeValues as $key => $values){
			        $xml->document->entity[$key]=$values;
			    }
			    break;
			case "field":
		  		foreach ($attributeValues  as $key =>$values){
                      switch ($key){
                      	case 'id':
                      		$xml->document->entity->field[0]['column']=$values;
                      		break;
                        case 'title':
                        	$xml->document->entity->field[1]['column']=$values;
                      		break;
                      	case 'description':
                      		$xml->document->entity->field[2]['column']=$values;
                      		break;
                      	case 'url':
                      		$xml->document->entity->field[3]['column']=$values;
                      		break;
                      	case 'updatetime':
                      		$xml->document->entity->field[4]['column']=$values;
                      		break;
                      }
			    }
			    break;
		  }
		}
	}
}