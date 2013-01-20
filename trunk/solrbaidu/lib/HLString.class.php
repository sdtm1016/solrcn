<?php
class HLString {
	/**
	 * 不同编码的字符串中的中文对应的字节数
	 */    
	static public function GetBytesLen($sCharset = "UTF-8") {
		empty($sCharset) && $sCharset = "UTF-8";
		strtoupper($sCharset) == "GB2312" && $sCharset = "GBK";
		$sCharset = strtoupper($sCharset);

		$aChineseBytesLen = array(
				'UTF-8' => 3,
				'GBK'   => 2
				);
		return array_key_exists($sCharset, $aChineseBytesLen) ? $aChineseBytesLen[$sCharset] : 3;
	}

	/**
	 * 　 截取部分字符串，暂时支持GBK,UTF-8编码，如果需要支持其它的字符编码，则需要作相应的调整
	 * 
	 * for example
	 *     $title = "中华a人民3共和国";
	 * 
	 *     截取字符的长度
	 *     debug('subString', Tools::subString($title, 3, false); //返回subString:|中华a...|     
	 *     debug('subString', Tools::subString($title, 3, false) //返回subString:|中华a...|
	 * 
	 *     截取字节的长度，不足一个汉字时截取到一个完整的汉字,3个字节表示1.5个汉字长度，取整则为1个汉字的长度($bAddFlag为false)或2个汉字的长度($bAddFlag为true)
	 *     debug('subString', Tools::subString($title, 3, true)); //返回subString:|中华...|
	 *     debug('subString', Tools::subString($title, 3, true, false)); //返回subString:|中...|
	 *      
	 *     debug('subString', Tools::subString($title, 3, true)) //返回subString:|中华...|
	 *     debug('subString', Tools::subString($title, 3, true, false)) //返回subString:|中...|
	 * 
	 * @param string $sMsg      要被截取的字符串
	 * @param string $iCutSize  要截取的字符的长度或字节的长度,参考参数$bByteFlag说明
	 * @param bool   $bByteFlag 要截取的是字节的长度还是字符的长度，true:表示字节数的长度(此时汉字看作两个字节，数字、字母看作1个字节)，false:表示字符的长度，此时汉字、字母、数字等都看作1个字节的长度
	 * @param bool   $bAddflag  当截取的字符串是某个汉字的一部分时，是完整截取此汉字(true)，还是不截取此字符串(false)
	 * @param string $sCharset  $sMsg的编码
	 * @param string $sSuffix   当$sMsg的长度大于$iCutSize时在后面显示的字符
	 * 
	 * @version 1.0 03/02/2007
	 */
	static public function SubString($sMsg, $iCutSize, $bByteFlag = true, $bAddFlag = true, $sCharset = "UTF-8", $sSuffix = "...") {
		if ($iCutSize <= 0) return $sMsg;
		if (empty($sMsg)) return false;        
		$sCharset = strtoupper($sCharset);

		/**
		 * $iCharLen主要用在与变量$han相乘
		 */
		$iCharLen = $bByteFlag ? 2 : 1;

		/**
		 * 根据参数$bByteFlag的值计算字符串的长度
		 */
		$iTotalLen = $bByteFlag ? self::Strlen($sMsg, 3, $sCharset) : self::Strlen($sMsg, 2, $sCharset);

		/**
		 * $sMsg的长度小于要截取的长度，返回整个字符串
		 */
		if ($iTotalLen < $iCutSize) {
			return $sMsg;
		}

		/**
		 * 如果要获取的是字符的长度，则直接用mb_substr截取即可，用mb_substr不存在半个汉字的问题
		 */
		if (!$bByteFlag) {
			$str = mb_substr($sMsg, 0, $iCutSize, $sCharset);
			if ($iCutSize < $iTotalLen) $str .= $sSuffix;
			return $str;
		}

		//$i表示循环时指针的偏移量
		//$iCutSize <= $iTotalLen
		$i = 1;
		while ($i <= $iTotalLen) {
			if (ord($sMsg[$i - 1]) > 127) {
				$han++;
				// utf-8每个汉字是3个长度，gbk是2个长度
				$i += self::GetBytesLen($sCharset);
			} else {
				// 数字、字符是1个长度
				$eng++;
				$i = $i + 1;
			}

			//2007-3-26,当$i>=$iCutSize时必须中止循环，否则整个循环一起循环完，这样导致$eng计算出来的值不准确，出现错误
			if ($i > $iCutSize) break;
		}

		//$iChinese表示汉字的字节数,$eng表示截取长度为$iCutSize的字符串中数字及字出现的次数
		$iChinese = $iCutSize - $eng;
		$iRemain = $iChinese % self::GetBytesLen($sCharset);
		//确保截取的是完整的汉字，不能是半角
		if ($iRemain != 0) {
			if ($bAddFlag) {
				$iCutSize += self::GetBytesLen($sCharset) - $iRemain;
			} else {
				$iCutSize -= $iRemain;
			}
		}
		$iCutSize = ceil($iCutSize);
		for ($i = 0; $i < $iCutSize; $i++) {
			$str .= $sMsg[$i];
		}

		/**
		 * 如果要截取的字符串的长度小于原字符串的长度，则加上$sSuffix
		 */        
		if ($iCutSize < $iTotalLen) $str .= $sSuffix;
		return $str;
	}

	public static function Strlen($sStr, $iType = 1, $sCharset = 'UTF-8') {
		if (empty($sStr)) return false;        
		$sCharset = strtoupper($sCharset);        
		switch ($iType) {
			case 1:
				/**
				 * $i表示循环时指针的偏移量
				 * $iLen表示字符串的长度
				 */
				$i = 1;
				while ($i <= strlen($sStr)) {
					if (ord($sStr[$i - 1]) > 127) {
						$iLen += 2;
						$i += self::GetBytesLen($sCharset);
					} else {
						$iLen += 1;
						$i += 1;
					}
				}
				break;
			case 2:
				$iLen = mb_strlen($sStr, $sCharset);
				break;
			case 3:
				$iLen = strlen($sStr);
				break;
		}
		return $iLen;
	}

	/**
	 * Returns true if the first arg starts with the second arg
	 * @param    string    $big_string
	 * @param    string    $little_string
	 * @return   true or false
	 */
	static public function StartWith($big_string, $little_string) {
		return !($len = strlen($little_string)) ||
			isset($big_string[$len - 1]) &&
			substr_compare($big_string, $little_string, 0, $len) === 0;
	}

	static public function StripslashesRecursive($a) {
		if (!is_array($a)) {
			return stripslashes($a);
		}
		$ret = array();
		foreach ($a as $key => $val) {
			$ret[stripslashes($key)] = self::StripslashesRecursive($val);
		}
		return $ret;
	}


	/**
	 * Undoes any magic quote slashing from an array, like the GET or POST
	 * @param    array    $a    Probably either $_GET or $_POST or $_COOKIES
	 * @return   array    The array with all of the values in it noslashed
	 *
	 * In many cases, this can be a drop-in replacement for stripslashes_recursive
	 * since this is what we typically use stripslashes_recursive for.  This is
	 * somewhat different in that if we ever turn off magic quotes, it will still
	 * behave correctly and not double stripslashes.
	 *
	 */
	static public function NoslashesRecursive($a) {
		if (get_magic_quotes_gpc()) {
			$a = self::StripslashesRecursive($a);
		}
		return $a;
	}

	/**
	 * Sanitizes a string to make sure it is valid UTF that will not break in
	 * json_encode or something else dastaradly like that.
	 *
	 * @param string $str String with potentially invalid UTF8
	 * @return string Valid utf-8 string
	 */
	static public function Utf8Sanitize($str) {
		return iconv('utf-8', 'utf-8//IGNORE', $str);
	}

	/**
	 * Escapes text to make it safe to use with Javascript
	 *
	 * It is usable as, e.g.:
	 *  echo '<script>alert(\'begin'.escape_js_quotes($mid_part).'end\');</script>';
	 * OR
	 *  echo '<tag onclick="alert(\'begin'.escape_js_quotes($mid_part).'end\');">';
	 * Notice that this function happily works in both cases; i.e. you don't need:
	 *  echo '<tag onclick="alert(\'begin'.txt2html_old(escape_js_quotes($mid_part)).'end\');">';
	 * That would also work but is not necessary.
	 *
	 * @param  string $str    The data to escape
	 * @param  bool   $quotes should wrap in quotes (isn't this kind of silly?)
	 * @return string         Escaped data
	 */
	static public function EscapeJsQuotes($str, $quotes=false) {
		if ($str === null) {
			return;
		}
		$str = strtr($str, array('\\'=>'\\\\', "\n"=>'\\n', "\r"=>'\\r', '"'=>'\\x22', '\''=>'\\\'', '<'=>'\\x3c', '>'=>'\\x3e', '&'=>'\\x26'));
		return $quotes ? '"'. $str . '"' : $str;
	}

}

