<?php
/**
 * @package	 JiWai.de
 * @copyright   AKA Inc.
 * @author	  zixia@zixia.net
 * @version	 $Id$
 */

/**
 * HLSession
 */

Class HLSession {
	/**
	 * Instance of this singleton
	 *
	 * @var HLSession
	 */
	static private $msInstance;

	/**
	 * Instance of this singleton class
	 *
	 * @return HLSession
	 */
	static public function &Instance($use_cookie=true)
	{
		if (!isset(self::$msInstance)) {
			$class = __CLASS__;
			if($use_cookie == false)
			{
				self::$msInstance = new $class($use_cookie);
			}
			else
			{
				self::$msInstance = new $class;
			}
		}
		return self::$msInstance;
	}


	function __construct($use_cookie=true) {
		if( defined('CONSOLE') && CONSOLE == true )
			return;
		
		if($use_cookie == false)
		{
			ini_set('session.use_cookies',0);
		}
		else
		{
			ini_set('session.use_cookies',1);
			ini_set('session.cookie_path','/');
		}
		
	/*	
		if (!empty($_SERVER['HTTP_HOST'])) {
			$domain = '.'.$_SERVER['HTTP_HOST'];
			if (preg_match('/(\.[^\.]+\.[^\.]+)$/', $domain, $m)) $domain = $m[1];
		} else {
			$domain = '.'.$_SERVER['SERVER_NAME'];
			if (preg_match('/(\.[^\.]+\.[^\.]+)$/', $domain, $m)) $domain = $m[1];
		}
		ini_set('session.cookie_domain', $domain);
	*/
		//ini_set('session.gc_maxlifetime',);
		ini_set('session.cookie_domain', COOKIE_DOMAIN);

		$key = ini_get('session.name');
		if( isset( $_GET[$key] )) {
			if( preg_match( '/^[0-9A-Za-z]+$/', $_GET[$key] ) ) {
				session_id( $_GET[$key] );
			}
			if(empty($_GET[$key]))
			{
				unset($_GET[$key]);
			}
		}
		session_start();
	}


	public static function SetInfo($infoType, $data)
	{
		if ( empty($data) )
			return;

		switch ($infoType)
		{
			case 'error':
				$_SESSION["__Aspire__Info__$infoType"] = $data;
				break;
			case 'alert':
				$_SESSION["__Aspire__Info__$infoType"] = $data;;
				break;
			case 'info':
				$_SESSION["__Aspire__Info__$infoType"] = $data;;
				break;
			case 'reset_password':
				//fall down
			case 'invitation_id':
				$_SESSION["__Aspire__Info__$infoType"] = $data;;
				break;
			case 'inviter_id':
				$_SESSION["__Aspire__Info__$infoType"] = $data;;
				break;
			case 'pay_order_id': //订单号
				$_SESSION["__Aspire__Info__$infoType"] = $data;;
				break;
			case 'pay_order_info': //订单信息
				$_SESSION["__Aspire__Info__$infoType"] = $data;;
				break;
			case 'pay_result': //订单处理结果
				$_SESSION["__Aspire__Info__$infoType"] = $data;;
				break;
			default:
				throw new Exception('info type not support');
		}


	}

	public static function Destory() {
		if(isset($_COOKIE[session_name()])) 
			setcookie(session_name(), '', time()-42000, '/', ini_get('session.cookie_domain'));
		session_destroy();
		$_SESSION 	= array();
	}

	public static function Refresh() {
		session_regenerate_id($deleteOld = true);
	}

	/*
	 * @param useOnce: delete info after get if set true.
	 */
	public static function GetInfo($infoType='err', $useOnce=true)
	{
		if ( isset($_SESSION["__Aspire__Info__$infoType"]) )
		{
			$info_str = $_SESSION["__Aspire__Info__$infoType"];

			if ( $useOnce )
				unset ($_SESSION["__Aspire__Info__$infoType"]);

			return $info_str;
		}

		return null;
	}
}
