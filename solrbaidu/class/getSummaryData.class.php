<?php
//取得摘要
class getSummaryData{
    public  static function getSummary($S_str,$S_start,$S_len){
    	    //临时变量
	        $S_tmep = "";
	        //摘要长度
			$S_str_len = strlen($S_str);
			//摘要长度判断，当为空的场合
			if($S_len < 1) return "";
			//摘要不为空的场合，一下处理
			$S_start = $S_start == 0 ? $S_start : (ord(substr($S_str, $S_start+1, 1)) > 0xa0 ? $S_start+1 : $S_start);
			for($i = $S_start; $i < $S_str_len; $i++){
				if($i >= ($S_start+$S_len)) break;
				$S_size = ord(substr($S_str, $i, 1)) > 0xa0 ? 2 : 1;
				$S_tmep .= substr($S_str, $i, $S_size);
				$i += $S_size > 1 ? 1 : 0;
			}
			if($S_start+$S_len < $S_str_len){$S_tmep.="...";}
			//摘要取得结果
			return $S_tmep;
    }
    
   //去掉特殊字符，取得摘要
    protected function summaryDo($S_str){
       $S_searh = array ("'<script[^>]*?>.*?</script>'si",  
		                 "'<[\/\!]*?[^<>]*?>'si",           	
		                 "'([\r\n])[\s]+'",                 	
		                 "'&(quot|#34);'i",                 	
		                 "'&(amp|#38);'i",
		                 "'&(lt|#60);'i",
		                 "'&(gt|#62);'i",
		                 "'&(nbsp|#160);'i",
		                 "'&(iexcl|#161);'i",
		                 "'&(cent|#162);'i",
		                 "'&(pound|#163);'i",
		                 "'&(copy|#169);'i",
		                 "'&#(\d+);'e");                    	
		$S_reple = array ("",
		                  "",
		                  "\\1",
		                  "\"",
		                  "&",
		                  "<",
		                  ">",
		                  " ",
		                  chr(161),
		                  chr(162),
		                  chr(163),
		                  chr(169),
		                  "chr(\\1)");
		return trim(addslashes(nl2br(stripslashes(preg_replace($S_searh,$S_reple,$S_str)))));
    }
}