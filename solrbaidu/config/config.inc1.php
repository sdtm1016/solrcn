<?php
error_reporting(E_ERROR);

/* for class file */
define('LIB', ROOT.'lib/');
define('CLS', ROOT.'class/');

/* for template */
define('TPL_COMPILED_DIR', ROOT.'compiled');
define('TPL_TEMPLATE_DIR', ROOT.'template');

/* solr配置信息 */
// solr地址
define('SOLR_HOST', "localhost");
//define('SOLR_HOST', "1.202.150.73");
// solr端口
define('SOLR_PORT', "8080");
// solr应用名
define('SOLR_NAME', "/solr/tipask");	

/* 显示配置信息 */
//标题
define("SOLR_TITLE", "title");
//摘要
define("SOLR_SUMMARY", "description");
//链接
define("SOLR_LINK", "url");
//时间
define("SOLR_DATE", "timestamp");

/* 分页Url拼接用 */
//page信息
define("PAGE_KEY", "page");


/* 检索条件 */
//拼写检查
define("SOLR_SPELL", "spellchecker");
//拼写检查返回数量
define("SOLR_SPELL_NUM", 10);
//聚类检查
define("SOLR_CLUS", "/clustering");
//聚类检查检索条数
define("CLUS_NUM", 100);

/** 摘要信息 */
//取得摘要长度
define("SUMMARY_LEN", 360);
//取得摘要其实位置
define("SUMMARY_START", 0);

/** URL信息 */
//取得摘要长度
define("LINK_LEN", 50);
//取得摘要其实位置
define("LINK_START", 0);
