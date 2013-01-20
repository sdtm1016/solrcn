<?php
header('Content-type: text/html; charset=utf-8');
define('ROOT', dirname(__FILE__) . '/../');
require('config.inc.php');

/*
echo SUPER_ROOT."<br>";
echo ROOT."<br>";
echo WEB_ROOT;
*/
if(function_exists('date_default_timezone_set')) date_default_timezone_set('Etc/GMT-8');

if(!function_exists('autoload'))
{
		function autoload($classname)
		{
			$filename = str_replace('_', '/',$classname) . '.class.php';

			$filepath = CLS . $filename;

			if(file_exists($filepath))
				return require($filepath);

			$filepath = LIB . $filename;

			if(file_exists($filepath))
				return require($filepath);
			//throw new Exception( $filepath . ' NOT FOUND IN PRJ_CLS');
		}
}

//reload longmai autoload
spl_autoload_register('autoload');
