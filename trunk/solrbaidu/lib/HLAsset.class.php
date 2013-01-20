<?php
/**
 * @package	JiWai.de
 * @copyright	AKA Inc.
 * @author  	glinus@jiwai.com
 */

/**
 * JiWai.de Asset Class
 */
class HLAsset {

	/**
	 * path_config
	 *
	 * @var
	 */
	static private $msAssetRoot;

	static private $msAssetCounter 	= 0;
	static private $msAssetMax 	= 3;

	/*
	 * Get asset url with timestamp
	 *	@param	path		the path of asset.jiwai.de/$path
	 *	@return	URL			the url ( domain name & path )
	 */
	static public function GetAssetUrl($absUrlPath, $mtime=true, $type=1) {
		return MI_Asset::getUrl($absUrlPath, $mtime, $type);
	}

	static public function GetUploadUrl($absUrl) {
		return 'http://' . UPLOAD_HOSTNAME . $absUrl;
	}

	/**
	 * Get combo asset url 
	 *
	 *	@param	path		array of the path, asset.jiwai.de/$path
	 *	@return	URL			the url ( domain name & path )
	 */
	static public function GetComboUrl($absUrlPaths, $mtime=true) {
		return MI_Asset::getComboUrl($absUrlPaths, $mtime);
	}


	/**
	 * Get asset server number 
	 *
	 *	@param	path		path of the asset, asset.jiwai.de/$path
	 *	@return	int 		the number
	 */
	static public function GetAssetServer($absUrlPath, $is_http = true)
	{
		$n = sprintf('%u',crc32($absUrlPath));
		$n %= self::$msAssetMax;
		if($is_http)
			return 'http://asset' . $n . '.' . ASSET_HOSTNAME;
		else
			return 'asset' . $n . '.' . ASSET_HOSTNAME;
	}

	/**
	 * Get file timestamp
	 *
	 *	@param	path		path of the asset, asset.jiwai.de/$path
	 *	@return	int 		the timestamp 
	 */
	static private function _getTimestamp($absUrlPath)
	{
		$asset_path	= PUB_ROOT . '/domain/asset/';
		$abs_file 	= "$asset_path$absUrlPath";
		$timestamp 	= file_exists($abs_file) ? filemtime($abs_file) : 0;
		return $timestamp;
	}

	static public function DefaultUrl($id, $size='48x48', $r=array(), $reg=1)
	{
		if(1==$reg)
		$default_url = HLAsset::GetUploadUrl( '/images/no_'.$size.'.jpg');
        elseif(2==$reg)
		$default_url = HLAsset::GetAssetUrl( '/images/no_'.$size.'.png', 1 , 1);  //HLAsset::GetUploadUrl( '/images/no_'.$size.'.jpg');
		else
		$default_url = HLAsset::GetUploadUrl( '/images/noreg_'.$size.'.jpg');
		if (!is_array($id) )
			return $default_url;

		foreach ( $id AS $one )
		{
			if ( !isset($r[$one]) )
				$r[$one] = $default_url;
		}
		return $r;

	}

	/**
	 * 根据 file_id ，获取一个/一组文件的访问地址
	 * @param int/array $id file
	 * @param string $thumb 
	 */
	static public function GetUrlByName($file_info, $thumb='48x48', $type='avatar', $reg=1)
	{
		if(is_numeric($file_info)) return HLAsset::GetAssetUrl('/images/red.jpg');
		list($user_id, $file_name) = explode('_', $file_info, 2);
		if(empty($user_id) || empty($file_name))
			return HLAsset::DefaultUrl(0, $thumb, '', $reg);
		if(0==strncmp($file_name, 'http://', 7))
		{
			return $file_name;
			/*$size = getimagesize($file_name);
			if(false===$size || !is_array($size) || empty($size))
				return HLAsset::DefaultUrl(0, $thumb);*/
			/*if(file_get_contents($file_name))
				return $file_name;
			else*/
		}
		$file_path = join('/', array( '/system', $type, $user_id , $thumb, $file_name));
		return HLAsset::GetUploadUrl($file_path);
	}

}
