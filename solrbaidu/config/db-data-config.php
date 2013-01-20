<?php
$dbconfigxml = <<<XML
<?xml version="1.0" encoding="UTF-8"?>
<dataConfig>  
    <dataSource type="JdbcDataSource" batchSize="-1"/> 
    <document name="mysolrname">  
         <entity name="mysolrtable" transformer="HTMLStripTransformer,TemplateTransformer,RegexTransformer,LogTransformer">
            <field name="id" />
            <field name="title" />
            <field name="description" />
            <field name="url" />
            <field name="updatetime" />
         </entity>
    </document>  
</dataConfig>  
XML;
