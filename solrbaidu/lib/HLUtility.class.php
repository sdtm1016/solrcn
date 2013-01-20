<?php
/**
 * @package JiWai.de
 * @copyright AKA Inc.
 * @author shwdai@gmail.com
 */
class HLUtility {
	
	const HTTP_URL_REGIX = '/http(s)?:\/\/[0-9a-zA-Z.]*/';

	static public function Option($a=array(), $v=null, $all=null)
	{
		$option = null;
		if ( $all ){
			$selected = ($v) ? null : 'selected';
			$option .= "<option value='' $selected>$all</option>";
		}

		$v = explode(',', $v);
		settype($v, 'array');
		foreach( $a AS $key=>$value )
		{
			$selected = in_array($key, $v) ? 'selected' : null;
			$option .= "<option value='$key' $selected>$value</option>";
		}
		
		return $option;
	}

	static public function SortArray($a=array(), $s=array(), $key=null)
	{
		if ( $key ) $a = self::GetColumn($a, $key, false);
		$ret = array();
		foreach( $s AS $one ) 
		{
			if ( isset($a[$one]) )
				$ret[$one] = $a[$one];
		}
		return $ret;
	}

	static public function GetColumn($a=array(), $column=array('id'), $null=false, $uniq=2)
	{
		if(empty($a)) return array();
		$ret = array();
		HLDB::CheckArray( $column );
		if(1>=count($column))
		{
			$col = $column[0];
			foreach( $a AS $one )
			{  
				$one[ $col ] = isset($one[ $col ]) ? $one[ $col ] : null;
				if ( $null || $one[ $col ] )
					$ret[] = $one[ $col ];
			}   
			if(1==$uniq)
				$ret = array_unique($ret);
			else if(2==$uniq)
				$ret = array_values(array_unique($ret));
		}
		else
		{
			foreach( $a AS $one )
			{  
				foreach($column as $col)
				{
					$one[ $col ] = isset($one[ $col ]) ? $one[ $col ] : null;
					if ( $null || $one[ $col ] )
						$ret[$col][] = $one[ $col ];
				}
			}  

			foreach($column as $col)
			{
				if(1==$uniq)
					$ret[$col] = isset($ret[$col]) ? array_unique($ret[$col]) : array();
				else if(2==$uniq)
					$ret[$col] = isset($ret[$col]) ? array_values(array_unique($ret[$col])) : array();
			}
		}

		return $ret;
	}

	/* support 2-level now */
	static public function AssColumn($a=array(), $column='id', $scolumn=null)
	{
		if(empty($a)) return array();
		$two_level = is_null($scolumn) ? false : true ;

		$ret = array();
		if ( false == $two_level )
		{   
			foreach( $a AS $one )
			{   
				$ret[ $one[$column] ] = $one;
			}   
		}   
		else
		{   
			foreach( $a AS $one )
			{   
				$ret[ $one[$column] ][ $one[$scolumn] ] = $one;
			}
		}
		return $ret;
	}

	/* support 1-level now */
	static public function SetColumn($a=array(), $columns=array('id'))
	{
		HLDB::CheckArray($columns);

		$ret = array();
		foreach( $a AS $one )
		{   $row = array();
			foreach($columns as $column)
			$row[$column] = $one;

			array_push($ret, $row);
		}

		return $ret;
	}

	/* support 1-level now */
	static public function ArrayUnique($a=array())
	{
		$ret = array();
		$values = array();
		foreach( $a AS $k=>$one)
		{
			if(!in_array($one, $values))
			{
				$ret[$k] = $one;
				array_push($values, $one);
			}
		}

		return $ret;
	}

	static public function Redirect($url=null, $err='')
	{
		if ( null==$url )
			$url = isset($_SERVER['HTTP_REFERER']) ? $_SERVER['HTTP_REFERER'] : null;

		if ( null==$url )
			$url = 'index.php';

		/**
		XXX useless?
		if ( preg_match( '/reg/', $url ) && HLLogin::IsLogined() )
			$url = 'index.php';

**/
		if ( $err )
		{
			die( 'ERROR<br/><a href="javascript:history.go(-1)">back</a>' );
			die( HLTemplate::Render( 'redirect', array( 'reason' => $err, 'url' => $url)) );
		}
	
		$https_to_http = isset($_SERVER['HTTPS']) && 'http:'==mb_substr($url, 0, 5) ? 1 : 0 ;
		$GLOBALS['https_to_http'] = $https_to_http;
		if(!isset($https_to_http) || 0>=$https_to_http)
		{
			Header("Location: $url");
		}
		else
		{
		HLUtility::HttpsRedirect($url);
		}
		die;
	}

	static public function Redirect404($url='/err/404.php', $err='', $srvname='')
	{
		if(empty($srvname))
			$srvname=WWW_SRVNAME;
		self::Redirect("$srvname$url", $err);
	}

	/**
	 * 跳转到提醒页面.
	 */
	static public function RedirectNotice($url='/err/notice.php', $err='', $srvname='') {
		if(empty($srvname))
			$srvname=WWW_SRVNAME;
		self::Redirect("$srvname$url", $err);
	}


	static public function GetRemoteIp($is_strip=false, $default='')
	{
		/*
		error_log('HTTP_CLIENT_IP: '.print_r(getenv('HTTP_CLIENT_IP'), 1)."\n", 3, '/home/d139/logs/www/ip_'.date('Y-m-d').'.log');
		error_log('HTTP_X_FORWARDED_FOR: '.print_r(getenv('HTTP_X_FORWARDED_FOR'), 1)."\n", 3, '/home/d139/logs/www/ip_'.date('Y-m-d').'.log');
		error_log('REMOTE_ADDR: '.print_r(getenv('REMOTE_ADDR'), 1)."\n", 3, '/home/d139/logs/www/ip_'.date('Y-m-d').'.log');
		*/

		if(defined('STRIP_INNER_IP'))
			$is_strip = STRIP_INNER_IP;
		$ip = explode(',', getenv('HTTP_X_FORWARDED_FOR').','.getenv('REMOTE_ADDR').','.getenv('HTTP_CLIENT_IP'));
		foreach($ip as $v)
		{
			if($is_strip && in_array(substr($v, 0, 3), array('10.','127','172','192','224','010')))
				continue;
			if (!in_array($v, array('58.252.73.143', '59.151.107.14')) && preg_match ('/\d+\.\d+\.\d+\.\d+/', $v, $matches) )
				return $matches[0];
		}
		if($is_strip)
			return $default;
		else
			return '127.0.0.1';
	}

	static public function GetHumanTime($time=null, $stress=1)
	{
		$now = time();
		$time = is_numeric($time) ? $time : strtotime($time);

		$interval = $now - $time;

        switch( $stress )
        {
            case 1:
                if ( $interval > 31536000){//365*86400
                    return floor($interval/(31536000)).'年前';
                }
                else if ( $interval > 2592000){//30*86400
                    return floor($interval/(2592000)).'月前';
                }
                else if ( $interval > 604800){////7*86400
                    return floor($interval/(604800)).'周前';
                }
                else if ( $interval > 86400 ){
                    return floor($interval/(86400)).'天前';
                }
                else if ( $interval > 3600 ){
                    return floor($interval/(3600)).'小时前';
                }
                else if ( $interval > 60 ){
                    return floor($interval/(60)).'分钟前';
                }
                else if ( $interval > 0 ) { 
                        return $interval.'秒前';
                }
                else
                    return '就在刚才';
            case 2:
                return date('Y-m-d', $time);
            case 3:
                return date('Y-m-d H:i', $time);
		}
	}

	static public function FieldsFilter($fields, $checked, $ignore_case = true) {
		$ret = array();

		if (is_array($fields) && is_array($checked)) {
			foreach ($fields as $k=>$v) {
				$key = ($ignore_case) ? strtolower($k) : $k;
				if (in_array($key, $checked))
					$ret[$key] = $v;
			}
		}

		return $ret;
	}

	static public function MysqlDate($time = null) {
		return (!$time)
			? date('Y-m-d H:i:s')
			: date('Y-m-d H:i:s', $time);
	}

	static public function MysqlTime($time = null) {
		return (!$time) ? time() : $time;
	}

	static public function BuildUrl($table, $id) {
		$urlmap = array (
			'user'	=> '/profile.php',
		);

		if ( array_key_exists($table, $urlmap) ) {
			return $urlmap[$table] . '?id=' . intval($id);
		}
	}

	static public function Javascript($javascript)
	{
		return '<script type="text/javascript">{$javascript}</script>';
	}

	static public function JsAlert($message)
	{
		return self::Javascript("alert(\"{$message}\");");
	}

	static public function JsGoBack($num = -1)
	{
		return self::Javascript("history.go($num);");
	}

	static public function JsRedirect($url)
	{
		return self::Javascript("window.location.href=\"{$url}\";");
	}

	static public function Charset($character = 'utf-8')
	{
		header("Content-type:text/html;charset={$character}");
	}


	/**
	 * Return the mime-type
	 * @param string $filename file name
	 * @return string MimeType
	 */
	static public function GetMimeType($filename, $ext=null) {
		if(empty($ext))
		{
			preg_match('/\.(.*?)$/', $filename, $m);    # Get File extension for a better match
				$ext = $m[1];
		}
		switch($ext){
			case 'js': return 'application/javascript';
			case 'json': return 'application/json';
			case 'jpg': case 'jpeg': case 'jpe': return 'image/jpg';
			case 'png': case 'gif': case 'bmp': return 'image/'.strtolower($m[1]);
			case 'css': return 'text/css';
			case 'xml': return 'application/xml';
			case 'html': case 'htm': case 'php': return 'text/html';
			default: 
			if(function_exists('mime_content_type')){ # if mime_content_type exists use it.
				$m = mime_content_type($filename);
			}else if(function_exists('')){    # if Pecl installed use it
				$finfo = finfo_open(FILEINFO_MIME);
				$m = finfo_file($finfo, $filename);
				finfo_close($finfo);
			}else{    # if nothing left try shell
				if(strstr($_SERVER[HTTP_USER_AGENT], 'Windows')){ # Nothing to do on windows
					return ''; # Blank mime display most files correctly especially images.
				}
				if(strstr($_SERVER[HTTP_USER_AGENT], 'Macintosh')){ # Correct output on macs
					$m = trim(exec('file -b --mime '.escapeshellarg($filename)));
				}else{    # Regular unix systems
					$m = trim(exec('file -bi '.escapeshellarg($filename)));
				}
			}
			$m = explode(';', $m);
			return trim($m[0]);
		}
	}

    /**
     * 将大五码转化为国标码，可能带来错误的BUG
     * 暂时先这样用着；
     * @param string $in
     * @return string
     */
    static public function BigToGb( $in )
    {
        $ulen = mb_strlen($in, 'UTF-8');
        $in1 = @iconv('UTF-8//IGNORE', 'BIG5//IGNORE', $in);
        $blen = mb_strlen($in1, 'BIG5');
        if ( $blen !== $ulen )
        {
            return $in;
        }
        $in2 = @iconv('BIG5//IGNORE', 'GB2312//IGNORE', $in1);
        $out = @iconv('GB2312//IGNORE', 'UTF-8//IGNORE', $in2);
        return $out;
    }

	/**
	 * 统一前台js和后台php的字数统计
	 * @param string $string
	 * @return string
	 */
	static public function ReplaceCr($string)
	{
		return str_replace("\r\n", "\n", $string);
	}

	static public function MbStrlen(&$string, $trim=1)
	{
		if($trim)
			return mb_strlen(str_replace("\r\n", "\n", trim(html_entity_decode($string))));
		else
			return mb_strlen(str_replace("\r\n", "\n", html_entity_decode($string)));
	}

	static public function GetArrayByPage($arr = array(), $options=array())
	{
		$page_no = isset($options['page_no']) ? HLDB::CheckInt($options['page_no'], 1) : 1;
		$page_size = isset($options['page_size']) ? HLDB::CheckInt($options['page_size'], 1) : 20;
		if($page_size)
		{
			if(!$page_no)
				$page_no = 1;
			$k = array_slice(array_keys($arr), ($page_no-1)*$page_size, $page_size, true);
			$v = array_slice(array_values($arr), ($page_no-1)*$page_size, $page_size, true);
			if(empty($k) || empty($v))
				return array();
			return array_combine($k, $v);
		}
		else
			return $arr;
	}
	
	/**
	 * 截取字符串
	 *
	 * @param $string
	 * @param $length
	 * @param $etc
	 * @param $break_words
	 * @param $middle
	 * @return string
	 */
	static public function Truncateutf8($string, $length = 80, $etc = '...', $break_words = false, $middle = false){
		if ($length == 0)
		return '';

		// 不要使用html_entity_decode，发现严重安全漏洞
		//$string = html_entity_decode($string,ENT_QUOTES,'UTF-8');
		$string = strip_tags($string);

		mb_internal_encoding('UTF-8');
		$string=str_replace('&nbsp;','',$string);
		if (mb_strlen($string) > $length) {
			//$length -= min($length, mb_strlen($etc));
			if (!$break_words && !$middle) {
				$string = preg_replace('/\s+?(\S+)?$/', '', mb_substr($string, 0, $length+1));
			}
			if(!$middle) {
				return mb_substr($string, 0, $length) . $etc;
			} else {
				return mb_substr($string, 0, $length/2) . $etc . mb_substr($string, -$length/2);
			}
		} else {
			return $string;
		}
	}

	
	/**
	 * 计算microtime格式的时间差
	 *
	 * @param string $time_start
	 * @param string $time_end
	 * @return string
	 */
	static public function GetTimeDiff($time_start, $time_end)
	{
		$c = explode(' ', $time_start);
		$d = explode(' ', $time_end);
		return $d[1]-$c[1]+$d[0]-$c[0];
	}
	
	/**
	 * 取等级的相对地址
	 *
	 * @param int $rank_id
	 * @param string $type
	 * @return string
	 */
	static public function RankImgurl($rank_id, $type)
	{
		$grade = ceil($rank_id/3);
		
		if($grade == 0)
		{
			$grade =1;
		}
		
		if($type=='2')
		{
			$path = '/images/rank/large/'.$grade.'.gif';
		}
		else
		{
			$path = '/images/rank/small/'.$grade.'.gif';
		}
		
		return $path;
	}
	/**
	 * 传cookie获取信息
	 *
	 * @param unknown_type $url
	 * @param unknown_type $cookie
	 * @return unknown
	 */
	static public function Proxy($url,$cookie)
	{
		$curl = curl_init();
		$header[] = "Cookie: {$cookie}"; 
		curl_setopt($curl, CURLOPT_URL, $url);
		curl_setopt($curl, CURLOPT_HTTPHEADER, $header);
		curl_setopt($curl, CURLOPT_AUTOREFERER, true);
		curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($curl, CURLOPT_TIMEOUT, 20);
		$html = curl_exec($curl);
		curl_close($curl);
		return $html;
	}

	 static public function GetFileNameInfo($file_path, $is_url=false)
	 {
		 if(!$is_url)
			 $file_name = $file_path;
		 else
		 {
			 $pos = strrpos($file_path, '/');
			 if(false!==$pos)
			 {
				 $file_name = substr($file_path, $pos+1);
			 }
			 else
				 $file_name = '';
		 }
		 if(empty($file_name))
			 return array('name'=>'', 'extension'=>'', 'path'=>$file_name); 
		 $pos = strrpos($file_name, '.');
		 if(false!==$pos)
		 {
			 return array('name'=>substr($file_name, 0, $pos), 'extension'=>substr($file_name, $pos+1), 'path'=>$file_name);
		 }
		 else
			 return array('name'=>$file_name, 'extension'=>'', 'path'=>$file_name);
	 }


	 static public function IsSameDomain($url) {
		 $tmp = parse_url($url);
		 if(isset($tmp['scheme']) and !empty($tmp['scheme'])) {
			$domain	= strstr(HOSTNAME, '.');
			$len 	= strlen($domain);
			return (substr($tmp['host'], -$len) == substr($domain, -$len));
		 }
		 return true;
	 }
	/**
	 * 获取用户的浏览器信息
	 * @return 浏览器类型
	 */
	 static public function GetUserExplorer()
	 {
	 	$os=$_SERVER['HTTP_USER_AGENT'];// 浏览者操作系统及浏览器
	 	if(strpos($os,'NetCaptor'))	$explorer='NetCaptor';
		elseif(strpos($os,'Opera'))	$explorer='Opera';
		elseif(strpos($os,'Firefox'))	$explorer='Firefox';
		elseif(strpos($os,'MSIE 6'))	$explorer='MSIE 6.x';
		elseif(strpos($os,'MSIE 5'))	$explorer='MSIE 5.x';
		elseif(strpos($os,'MSIE 4'))	$explorer='MSIE 4.x';
		elseif(strpos($os,'Netscape'))	$explorer='Netscape';
		else	$explorer='Other';
		return $explorer;
	 }
	 
	/**
	 * 获取用户的操作系统
	 * @return 操作系统类型
	 */
	 static public function GetUserOs()
	 {
	 	$os=$_SERVER['HTTP_USER_AGENT'];// 浏览者操作系统及浏览器
		// 分析操作系统
		if(strpos($os,'Windows NT 5.0'))$os='Windows 2000';
		elseif(strpos($os,'Windows NT 5.1'))$os='Windows XP';
		elseif(strpos($os,'Windows NT 5.2'))$os='Windows 2003';
		elseif(strpos($os,'Windows NT'))$os='Windows NT';
		elseif(strpos($os,'Windows 9'))$os='Windows 98';
		elseif(strpos($os,'unix'))$os='Unix';
		elseif(strpos($os,'linux'))$os='Linux';
		elseif(strpos($os,'SunOS'))$os='SunOS';
		elseif(strpos($os,'BSD'))$os='FreeBSD';
		elseif(strpos($os,'Mac'))$os='Mac';
		else $os='Other';
		return $os;
	 }
	/**
	 * Return the raw POST vars
	 * @return   dict   {<post-param-1>: <val>, <post-param-2>: <val>, ...}
	 *
	 * PHP does some weird things with POST var names.  Two examples of this are:
	 * 1.  If you have vars named like this x[0], x[1], etc., then PHP will
	 * put those into an array for you.
	 * 2.  If you any dots (.) in your post var names, then PHP will replace
	 * those with underscores.
	 *
	 * This function returns the POST vars without any of those transformations
	 * applied.  It may be useful to do the same thing for GET parameters.
	 *
	 * Note that the vars returned by this function will never be slash-escaped,
	 * regardless of whether you have magic quotes on or off.  yay.
	 *
	 * IMPORTANT NOTE: this function currently fails to handle 2 things being POSTed
	 * with the same value.
	 *
	 */
	static public function PhpInputRawPostVars() {
	  global $PHP_INPUT_RAW_POST_VARS;
	  if (isset($PHP_INPUT_RAW_POST_VARS)) {
		return $PHP_INPUT_RAW_POST_VARS;
	  }

	  $post_string = file_get_contents('php://input');
	  $assignments = empty($post_string) ? array() : explode('&', $post_string);
	  $post_vars = array();
	  foreach ($assignments as $asst) {
		if (strstr($asst, '=')) {
		  list($param_e, $val_e) = explode('=', $asst, 2);
		  $param = urldecode($param_e);
		  $val = urldecode($val_e);
		} else {
		  $param = urldecode($asst);
		  $val = '';
		}
		$post_vars []= array($param, $val);
	  }

	  return ($PHP_INPUT_RAW_POST_VARS = $post_vars);

	}

	static function HtmlizeFilters($var, $encode = 1) {
	  // NB: we do '<3 ' to '&hearts;' (note space to no space)
	  $filtered = str_replace(array('&#34;', '&nbsp;', '&AMP;', '&amp;', '<br />', '<br>', '<BR>', '<p>' , '<P>' , '<3 '),
                          array('"'    , ' '     , '&'    , '&'    , "\n"    , "\n"  , "\n"  , "\n\n", "\n\n", '&hearts;'),
                          $var);

	  if ($encode) {
		$filtered = self::Txt2Html($filtered);
	  }
	  // make it look right in display
	  $filtered = str_replace(array("\r\n",   "\n",     '&amp;hearts;'),
							  array('<br />', '<br />', '&hearts;'),
							  $filtered);

	  return $filtered;
	}

	static function Txt2Html($str) {
	  return htmlspecialchars(HLString::Utf8Sanitize($str), ENT_QUOTES, 'UTF-8');
	}

	// this is for cases (html_hyperlink, intern stuff that already his it right)
	//    where we already using htmlize at the point of conversion
	// this is convenient instead of doing htmlize(htmlize_filters($x)) and also
	//    necessary in the case of html_hyperlink since that takes a function
	//    and php doesn't do first-class-functions/lambdas
	function Htmlize($var) {
	  $filtered = str_replace(array('&#34;', '&nbsp;', '&AMP;', '&amp;', '<br />', '<br>', '<BR>', '<p>' , '<P>' , '<3 '),
							  array('"'    , ' '     , '&'    , '&'    , "\n"    , "\n"  , "\n"  , "\n\n", "\n\n", '&hearts;'),
							  $var);
	  $filtered = self::Txt2Html($filtered);
	  return str_replace(array("\r\n",   "\n",     '&amp;hearts;'),
						 array('<br />', '<br />', '&hearts;'),
						 $filtered);
	}


	/**
	 * Tells if this request includes a POSTed multipart form
	 * @return    bool    true if the request includes a multipart form
	 *
	 */
	static public function IsMultipartForm() {
	  if (!isset($_SERVER['CONTENT_TYPE'])) {
		return false;
	  }
	  return (strpos($_SERVER['CONTENT_TYPE'], 'multipart/form-data') === 0);
	}

	/**
	* get random md5 string
	*
	**/	
	static public function GetRandomMD5(){
		return md5( rand() );
	}

	static public function FormatUrl($matches) {
                $url = $matches[0];
                return "<a href='$url'>$url</a>";
        }
	
	static public function FormatUrlInText( $text ){
		return preg_replace_callback( self::HTTP_URL_REGIX, 'HLUtility::FormatUrl', $text );
	}

	static public function GetHeaderNameByUrl($url='')
	{
		if(empty($url))
			$url = isset($_SERVER['SCRIPT_NAME']) ? $_SERVER['SCRIPT_NAME'] : '';

		if (0===strncmp($url,'/home/', 6))
			return 'home';
		elseif (0===strncmp($url,'/index.php', 10) && $_SERVER['HTTP_HOST'] == WWW_HOSTNAME) 
			return 'nologinhome';
		else
			return '';
	}

	static public function TruncateStr($string, $length = 80, $etc = '...')
	{
		$string = html_entity_decode($string,ENT_QUOTES,'UTF-8');
		$len = mb_strlen($string);
		$ret = mb_substr($string, 0, $length);
		if($len<=$length)
			return htmlspecialchars($ret);
		return htmlspecialchars($ret).$etc;
	}
	
	/*
	 * 返回由对象属性组成的关联数组
	 */
	static public function object_to_array($obj)
	{
		if(is_object($obj)|| is_array($obj))
		{
			$_arr = is_object($obj) ? get_object_vars($obj) : $obj;
			if(is_array($_arr))
			{
				$ret_arr =array();
				foreach ($_arr as $key => $val)
				{
					$ret_arr[$key] = self::object_to_array($val);
				}
				return empty($ret_arr)?null:$ret_arr;
			}
			else
			{
				return $_arr;
			}
		}
		else
		{
			return $obj;
		}
	}

	static public function CallbackText($text='', $json=0)
	{
		$callback = isset($_REQUEST['callback']) ? $_REQUEST['callback'] : '';
		$callback = mb_substr($callback, 0, 5)==='jsonp' && is_numeric(mb_substr($callback, 5)) ? $callback : '';
		$text = str_replace('\'', '\\\'', $text);
		if(0<$json)
			return $callback.'('.$text.')';
		else
			return $callback.'(\''.$text.'\')';
	}

	static public function GetRequestUri($type = 0)
	{
		$host = isset($_SERVER['HTTP_HOST']) ? $_SERVER['HTTP_HOST'] : WWW_HOSTNAME;
		$uri = isset($_SERVER['REQUEST_URI']) ? $_SERVER['REQUEST_URI'] : '';
		$script = isset($_SERVER['SCRIPT_NAME']) ? $_SERVER['SCRIPT_NAME'] : '';
		$proto = !isset($_SERVER['HTTPS']) ? 'http://' : 'https://';

		if(0==$type)
		{
			return $host.$uri;
		}
		elseif(1==$type)
		{
			return $host.$script;
		}
		elseif(2==$type)
		{
			return $proto.$host.$uri;
		}
		else
		{
			return $proto.$host.$script;
		}
	}

	static public function GetDebugMode()
	{
		if(defined('DEBUG_MODE') && DEBUG_MODE)
			return 1;
		return 0;
	}

	static public function CardEncrypt($plain_text)
	{
		$msg_key = defined('CARD_ENCRYPT_KEY') ? CARD_ENCRYPT_KEY : '';
		$plain_text = trim($plain_text);
		$iv = substr(md5($msg_key), 0,mcrypt_get_iv_size (MCRYPT_CAST_256,MCRYPT_MODE_CFB));
		$c_t = mcrypt_cfb (MCRYPT_CAST_256, $key, $plain_text, MCRYPT_ENCRYPT, $iv);
		return trim(urlencode(chop(base64_encode($c_t))));
	}

	static public function GetHeaderTypeByUrl($url='')
	{
		global $current_user_active;
		if(0>=strlen($url))
			$url = HLUtility::GetHeaderNameByUrl();
		if(!in_array($url, array('register', 'help', 'talker')))
		{
			return 0>=$current_user_active ? 1 : 2;
		}
		return 0;
	}

	static public function HttpsRedirect($url='')
	{
		//用于　https　返回　http　的特殊跳转
		HLTemplate::Instance()->Assign(array(
			'url' => $url
			))
		->SetTitle('登录后跳转')
		->Display('section/https_redirect.tpl');
	}

    static public function Convert2UTF8($from_string, $from_encoding='GBK', $to_encoding='UTF-8')
    {
        $to_string = iconv($from_encoding, $to_encoding.'//TRANSLIT', $from_string);
        if($from_string===iconv($to_encoding, $from_encoding.'//TRANSLIT', $to_string))
			return $to_string;

        $to_string = mb_convert_encoding($from_string, $to_encoding, $from_encoding);
        if($from_string===mb_convert_encoding($to_string, $from_encoding, $to_encoding))
			return $to_string;
        return $from_string;
    }
}
