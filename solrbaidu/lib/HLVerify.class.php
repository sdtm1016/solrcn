<?php
/**
 * @package	JiWai.de
 * @copyright	AKA Inc.
 * @author  	glinus@jiwai.com
 */

/**
 * JiWai.de Verify Class
 */
class HLVerify {
	
	
	/*
	 * Check139Email 使用的参数
	 * 1-手机号@139.com
	 * 2-别名@139.com
	 */
	const	TYPE_MOBILE	= 1;
	const	TYPE_ALIAS	= 2;
	

    /**
     * Check if the number in range
     * @param int value
     * @param int min
     * @param int max
     * @return boolean
     */
    static public function CheckNumeric($number, $min = null, $max = null) {
        $n = intval($number);
        $regexp = '/^\d+$/';
        if (! preg_match($regexp, $number))
            return false;
        elseif (null != $min && $n < $min)
            return false;
        elseif (null != $max && $n > $max)
            return false;
        return true;
    }

    /**
     * Check if the email is valid
     * @param string email
     * @return boolean
     */
    static public function CheckEmail($email) {
		$regexp = '/^[\w\-\.]+@[\w\-]+(\.[\w\-]+)*(\.[a-z]{2,})$/';
        if (strlen($email) <= 32 && preg_match($regexp, $email)) {
            return true;
        }
        return false;
    }

    /**
     * Check if the email is valid
     * @param string email
     * @return boolean
     */
    static public function CheckZipcode($email) {
        $regexp = '/^\d{6}$/';
        if (preg_match($regexp, $email)) {
            return true;
        }
        return false;
    }

    /**
     * Check if the name is valid
     * @param string email
     * @return boolean
     */
    static public function CheckName($username, $filter=1) {
			$username = urldecode(trim($username));
			if(empty($username) || !preg_match('/^([\x{4e00}-\x{9fa5}]){2,4}$/u', $username)){
				//if(!preg_match('/^([\x81-\xfe][\x40-\xfe]){2,4}$/', $username))
				//if(!ereg('^[\u4e00-\u9fa5]{2,4}$', $username))
				return '1;真实姓名必须为2到4个汉字';
			}
			if($filter)
			{
				if(!LMFilterKeyword::FilterContent($username, LMFilterKeyword::TYPE_USERNAME, '', 1))
					return '1;请输入真实姓名，如有疑问，请<a href=\''.SRVNAME.'/feedback/\'>在线反馈</a>';

				//检查是否含有限制字符，如 ”移动，移动互联，客服，一三九“等
				//This 4 line is commited by haohang for #6984
				//$badNameArray=self::GetForbiddenWords();
				//if(in_array($username,$badNameArray)){
				//return '1;请输入真实姓名，如有疑问，请<a href="/feedback/">在线反馈</a>';
				//}

				if(2==$filter)
				{
					$badLastName = self::GetBadLastName();
					$is_err = true;
					foreach($badLastName as $v)
					{
						if(0===strncasecmp($username, $v, strlen($v)))
						{
							$is_err = false;
							break;
						}
					}
					if($is_err)
						return '1;请输入真实姓名，如有疑问，请<a href=\''.SRVNAME.'/feedback/\'>在线反馈</a>';
				}
			}
	    return '0';
    }

    /**
     * Check if the mobile is valid
     * @param string email
     * @return boolean
     */
    static public function CheckMobile($mobile) {
        return preg_match('/^1[3|5|8][0-9]{9}$/', $mobile);
    }

    /**
     * Check if the phone is valid
     * @param string email
     * @return boolean
     */
    static public function CheckPhone($email) {
        $regexp = '/^\d[0-9-]+\d$/';
        if (preg_match($regexp, $email)) {
            return true;
        }
        return false;
    }

    /**
     * Check if China Mobile
     * @param string mobile phone
     * @return boolean
     */
	 static public function CheckChinaMobile($mobile) {
       //return preg_match('/^1((3[4-9])|4([7])|(5[0-27-9])|8[27-8])[0-9]{8}$/', $mobile);
	   if (preg_match('/^1((3[4-9]|47|5[012789]|8[278])\\d{8})$/', $mobile)) return 'CM'; //中国移动
       elseif (preg_match('/^1((3[0-2]|5[56]|8[56])\\d{8})$/', $mobile)) return 'CU'; //中国联通
       elseif (preg_match('/^1((19|33|53|8[09])\\d{8})$/', $mobile)) return 'CT'; //中国电信
       else return 0;
    }

    /**
     * Check if the email is 139Email
     * Alias format of 139Email:
     * 5-15个的字符组合（必须以英文字母开头。可使用的字符范围：0~9，a~z，“.”，“_”，“-”），如peter_zhang
     * 
     * @param string $email
     * @return  Type of 139Email(1-mobile@139.com; 2-alias@139.com) OR flase
     * @author Zhan Wei zhanwei901@hotmail.com
     */
    
    function Check139Email($email)
	{
		$regexp_real1 = '/^13[4-9][0-9]{8}+@139.com$/';
		$regexp_real2 = '/^15[8|9][0-9]{8}+@139.com$/';
		$regexp_alias = '/^[a-zA-Z][\w\-\.]{5,15}+@139.com$/';
		
		if(preg_match($regexp_real1, $email) || preg_match($regexp_real2, $email))
			return self::TYPE_MOBILE;
		if(preg_match($regexp_alias, $email))
			return self::TYPE_ALIAS;
			
		return false;
	}
    
    
    /**
     * Bulk check
     * @param array string => type
     * @return array (value,type,valid)
     */
    static public function Check($bulk = array()) {
        if (! is_array($bulk)) return null;
        $result = array();
        foreach ($bulk as $k=>$v) {
            $v = ucfirst($v);
            $result[] = array(
                    'value' => $k,
                    'type'  => $v,
                    'valid' => call_user_func(array('HLVerify', 'Check' . $v), $k),
                    );
        }

        return $result;
    }
    /**
     * 通用密码检查
     *
     * @param String $password
     * @param Array $condition
     * @return Boolean
     */
    static public function CheckPassword($password, $condition=array('MinLength'=>6 ,'MaxLength'=>16, 'Custom'=>array()))
    {
		if (strlen($password) < $condition['MinLength'])
			return false;
			
		if (strlen($password) > $condition['MaxLength'])
			return false;
			
		if (preg_match("#\s#", $password))
			return false;
		
		return true;
    }
    
    static public function GetForbiddenWords(){
    	return array('曹长青','曹刚川','陈破空','陈希同','迟浩田','达赖','戴相龙','邓小平','丁关根',
						'丁子霖','杜智富','费良勇','封从德','郭伯雄','郭罗基','郭岩华','韩联潮','何德普',
						'洪哲胜','胡锦涛','黄慈萍','贾育台','江罗犯罪集团','江泽民','疆独','黎安友','李长春',
						'李大师','李登辉','李洪志','李兰菊','李鹏','李瑞环','李少民','李旺阳','李文斌','李月月鸟',
						'李志绥','李总统','李岚清','梁光烈','梁擎墩','林保华','林长盛','林慎立','林樵清','刘宾深',
						'刘宾雁','刘华清','吕京花','吕秀莲','罗礼诗','马大维','毛泽东','倪育贤','司马璐','司徒华',
						'魏京生','尉健行','温家宝','吴邦国','吴官正','徐邦秦','徐才厚','张万年','朱镕基',
						'北美自由论坛','春夏自由论坛','大中国论坛','大中华论坛','大众真人真事','邓笑贫','迪里夏提',
						'丁子霖','东北独立','东南西北论谈','东土耳其斯坦','獨立臺灣','独立台湾','杜智富','发正念',
						'法　轮　功','法lun','法谪','方励之','斐得勒','费良勇','风雨神州论坛','封从德','佛展千手法',
						'高文谦','高自联','郭罗基','韩东方','何德普','红色恐怖','宏法','洪传','洪吟','洪哲胜',
						'护士24点','华通时事','华岳时事论坛','黄慈萍','鸡毛信文汇','姬胜德','积克馆','激情陪聊',
						'激情视频','贾育台','建国党','江罗','江戏子','江贼','江贼民','姜泽民','僵贼','僵贼民',
						'揭批书','金尧如','黎安友','李红痔','李宏志','李洪志','李兰菊','李少民','李旺阳',
						'李月月鸟','李志绥','梁擎墩','两岸三地论坛','林保华','林长盛','林樵清','林慎立','刘宾雁',
						'吕京花','馬鹿か韓国犬','媚日求荣','蒙独','蒙古独','南大自由论坛','倪育贤','轻舟快讯',
						'热比娅','热站政论网','人民内情真相','人民之声论坛','屎球の粪坑','司马璐','司徒华','台獨',
						'台盟','台湾独','台湾狗','台湾建国','台湾青年独立联盟','台湾自由','臺灣獨','臺灣狗',
						'臺灣建國','臺灣青年獨立聯盟','臺灣政論','臺灣自由','退出共党','退出中共','万维读者论坛',
						'吾尔开希','西藏独','新观察论坛','新华通论坛','远华案黑幕','真善忍','正义党论坛',
						'政治反对派','指点江山论坛','中国复兴论坛','中国社会进步党','中国社会论坛','中国问题论坛',
						'中国真实内容','中华养生益智功','中华真实报','钟山风雨论坛','周天法','自由民主论坛',
						'自由中国','法轮','法轮功','西藏独立','裸照','辦證','办证','朱鎔基','台湾独立','台独',
						'司马璐','张伯笠','郭开智','换妻','成人文学','成人交友','温家宝','温总理','客服',
						'中国移动','移动运营','139运营','管理员','139官方','管理员','139官方','广东移动',
						'移动集团','客户部','一三九','移动互联','139移动','移动客服'
			);
    }

	static public function GetBadLastName()
	{
		$bad1 = array(
		'赵','钱','孙','李','周','吴','郑','王','冯','陈','褚','卫','蒋','沈','韩','杨','朱','秦','尤','许','何','吕','施','张','孔','曹','严','华','金','魏','陶','姜','戚','谢','邹','喻','柏','水','窦','章','云','苏','潘','葛','奚','范','彭','郎','鲁','韦','昌','马','苗','凤','花','方','俞','任','袁','柳','酆','鲍','史','唐','费','廉','岑','薛','雷','贺','倪','汤','滕','殷','罗','毕','郝','邬','安','常','乐','于','时','傅','皮','卞','齐','康','伍','余','元','卜','顾','孟','平','黄','和','穆','萧','尹','姚','邵','湛','汪','祁','毛','禹','狄','米','贝','明','臧','计','伏','成','戴','谈','宋','茅','庞','熊','纪','舒','屈','项','祝','董','梁','杜','阮','蓝','闵','席','季','麻','强','贾','路','娄','危','江','童','颜','郭','梅','盛','林','刁','锺','徐','邱','骆','高','夏','蔡','田','樊','胡','凌','霍','虞','万','支','柯','昝','管','卢','莫','经','房','裘','缪','干','解','应','宗','丁','宣','贲','邓','郁','单','杭','洪','包','诸','左','石','崔','吉','钮','龚','程','嵇','邢','滑','裴','陆','荣','翁','荀','羊','於','惠','甄','麴','家','封','芮','羿','储','靳','汲','邴','糜','松','井','段','富','巫','乌','焦','巴','弓','牧','隗','山','谷','车','侯','宓','蓬','全','郗','班','仰','秋','仲','伊','宫','宁','仇','栾','暴','甘','钭','历','戎','祖','武','符','刘','景','詹','束','龙','叶','幸','司','韶','郜','黎','蓟','溥','印','宿','白','怀','蒲','邰','从','鄂','索','咸','籍','赖','卓','蔺','屠','蒙','池','乔','阳','郁','胥','能','苍','双','闻','莘','党','翟','谭','贡','劳','逄','姬','申','扶','堵','冉','宰','郦','雍','却','璩','桑','桂','濮','牛','寿','通','边','扈','燕','冀','僪','浦','尚','农','温','别','庄','晏','柴','瞿','阎','充','慕','连','茹','习','宦','艾','鱼','容','向','古','易','慎','戈','廖','庾','终','暨','居','衡','步','都','耿','满','弘','匡','国','文','寇','广','禄','阙','东','欧','殳','沃','利','蔚','越','夔','隆','师','巩','厍','聂','晁','勾','敖','融','冷','訾','辛','阚','那','简','饶','空','曾','毋','沙','乜','养','鞠','须','丰','巢','关','蒯','相','查','后','荆','红','游','竺','权','逮','盍','益','桓','公','万俟','司马','上官','欧阳','夏侯','诸葛','闻人','东方','赫连','皇甫','尉迟','公羊','澹台','公冶','宗政','濮阳','淳于','单于','太叔','申屠','公孙','仲孙','轩辕','令狐','钟离','宇文','长孙','慕容','司徒','司空','召','有','舜','叶赫那拉','丛','岳','寸','贰','皇','侨','彤','竭','端','赫','实','甫','集','象','翠','狂','辟','典','良','函','芒','苦','其','京','中','夕','之','章佳','那拉','冠','宾','香','果','依尔根觉罗','依尔觉罗','萨嘛喇','赫舍里','额尔德特','萨克达','钮祜禄','他塔喇','喜塔腊','讷殷富察','叶赫那兰','库雅喇','瓜尔佳','舒穆禄','爱新觉罗','索绰络','纳喇','乌雅','范姜','碧鲁','张廖','张简','图门','太史','公叔','乌孙','完颜','马佳','佟佳','富察','费莫','蹇','称','诺','来','多','繁','戊','朴','回','毓','税','荤','靖','绪','愈','硕','牢','买','但','巧','枚','撒','泰','秘','亥','绍','以','壬','森','斋','释','奕','姒','朋','求','羽','用','占','真','穰','翦','闾','漆','贵','代','贯','旁','崇','栋','告','休','褒','谏','锐','皋','闳','在','歧','禾','示','是','委','钊','频','嬴','呼','大','威','昂','律','冒','保','系','抄','定','化','莱','校','么','抗','祢','綦','悟','宏','功','庚','务','敏','捷','拱','兆','丑','丙','畅','苟','随','类','卯','俟','友','答','乙','允','甲','留','尾','佼','玄','乘','裔','延','植','环','矫','赛','昔','侍','度','旷','遇','偶','前','由','咎','塞','敛','受','泷','袭','衅','叔','圣','御','夫','仆','镇','藩','邸','府','掌','首','员','焉','戏','可','智','尔','凭','悉','进','笃','厚','仁','业','肇','资','合','仍','九','衷','哀','刑','俎','仵','圭','夷','徭','蛮','汗','孛','乾','帖','罕','洛','淦','洋','邶','郸','郯','邗','邛','剑','虢','隋','蒿','茆','菅','苌','树','桐','锁','钟','机','盘','铎','斛','玉','线','针','箕','庹','绳','磨','蒉','瓮','弭','刀','疏','牵','浑','恽','势','世','仝','同','蚁','止','戢','睢','冼','种','涂','肖','己','泣','潜','卷','脱','谬','蹉','赧','浮','顿','说','次','错','念','夙','斯','完','丹','表','聊','源','姓','吾','寻','展','出','不','户','闭','才','无','书','学','愚','本','性','雪','霜','烟','寒','少','字','桥','板','斐','独','千','诗','嘉','扬','善','揭','祈','析','赤','紫','青','柔','刚','奇','拜','佛','陀','弥','阿','素','长','僧','隐','仙','隽','宇','祭','酒','淡','塔','琦','闪','始','星','南','天','接','波','碧','速','禚','腾','潮','镜','似','澄','潭','謇','纵','渠','奈','风','春','濯','沐','茂','英','兰','檀','藤','枝','检','生','折','登','驹','骑','貊','虎','肥','鹿','雀','野','禽','飞','节','宜','鲜','粟','栗','豆','帛','官','布','衣','藏','宝','钞','银','门','盈','庆','喜','及','普','建','营','巨','望','希','道','载','声','漫','犁','力','贸','勤','革','改','兴','亓','睦','修','信','闽','北','守','坚','勇','汉','练','尉','士','旅','五','令','将','旗','军','行','奉','敬','恭','仪','母','堂','丘','义','礼','慈','孝','理','伦','卿','问','永','辉','位','让','尧','依','犹','介','承','市','所','苑','杞','剧','第','零','谌','招','续','达','忻','六','鄞','战','迟','候','宛','励','粘','萨','邝','覃','辜','初','楼','城','区','局','台','原','考','妫','纳','泉','老','清','德','卑','过','麦','曲','竹','百','福','言','第五','佟','爱','年','笪','谯','哈','墨','南宫','赏','伯','佴','佘','牟','商','西门','东门','左丘','梁丘','琴','后','况','亢','缑','帅','微生','羊舌','海','归','呼延','南门','东郭','百里','钦','鄢','汝','法','闫','楚','晋','谷梁','宰父','夹谷','拓跋','壤驷','乐正','漆雕','公西','巫马','端木','颛孙','子车','督','仉','司寇','亓官','鲜于','锺离','盖','逯','库','郏','逢','阴','薄','厉','稽','闾丘','公良','段干','开','光','操','瑞','眭','泥','运','摩','伟','铁','迮'
		);
		$bad2 =  array(
		'马','莽','梅','孟','墨','明','穆','钮','那','南','聂','年','宁','朴','庞','齐','钱','祁','强','屈','邱','石','沙','山','沈','胜','赛','桑','索','苏','孙','松','舒','萨   ','唐','田','佟','童','陶','王','吴','万','汪','魏','温','线','奚','邢','萧','郗','叶','伊','于','鱼','岳','异','余','颜','杨','阎','姚','赵','张','章','朱','左','祖','詹','郑'
		);
		$bad3 =  array(
		'络','扎','次','才','泽','巴','斯','益','斯','多','德','尼','达','米','拉','普','巴','边'
		);
		return $bad1+$bad2+$bad3;
	}
}
