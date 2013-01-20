<?php
   session_start();
   require('./config/common.inc.php');

//测试
echo getPinyin::ToPinyin('s新浪，百度','gb2312'); //第二个参数“1”可随意设置即为utf8编码
?>
