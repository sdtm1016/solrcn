<?php
/**
 * @package HomeLink.com.cn
 * @copyright HomeLink Inc.
 * @author lulige@gmail.com
 */

/**
 * 生成/检查crumb
 */
class HLCrumb
{

	static $expire 	= 10800; // 3 hours

	static $key		= '#d3FzZW1jL$NwYWNlcy5@';

	const RAND_KEY	= '4a112b0b';
    

	const LOGINED 	= 1;
	const NOT_LOGIN	= 0;

	/**
	 * 生成crumb
	 *
	 * @return string the crumb
	 */
	static public function getCrumb($userId = 0) {
		$loginTime 	= dechex(time());
		if($userId) {
			$idUser		= dechex($userId);
			$text		= $loginTime . $idUser;
			return bin2hex(mcrypt_encrypt(MCRYPT_RIJNDAEL_128, self::$key, $text, MCRYPT_MODE_ECB, self::_getIv()));
		} else {
			$text		= $loginTime . self::RAND_KEY;
			return bin2hex(mcrypt_encrypt(MCRYPT_RIJNDAEL_128, self::$key, $text, MCRYPT_MODE_ECB, self::_getIv()));
		}
	}

	/**
	 * 检查crumb
	 * 
	 * @param string $crumb
	 * @return bool
	 */
	static public function checkCrumb($crumb, $userId = 0) {
		if(empty($crumb))
			return false;

        if (defined("DEBUG_NO_CRUMB_CHECK") && DEBUG_NO_CRUMB_CHECK === true){
            return true;
        }

		if(!$userId) {
			$userId = hexdec(self::RAND_KEY);
		}

		$data		= pack('H' . strlen($crumb), $crumb);
		$text		= mcrypt_decrypt(MCRYPT_RIJNDAEL_128, self::$key, $data, MCRYPT_MODE_ECB, self::_getIv());
		$loginTime	= substr($text, 0, 8);
		$idUser		= substr($text, 8);
		$loginTime	= hexdec($loginTime);
		$idUser		= hexdec($idUser);

		if($idUser != $userId)
			return false;

		return (($loginTime + self::$expire) > time());
	}

	static private function _getIv() {
		$ivSize 	= mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_ECB);
		return mcrypt_create_iv($ivSize, MCRYPT_RAND);
	}
}
