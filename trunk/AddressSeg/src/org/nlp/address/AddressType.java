package org.nlp.address;

//TODO: add 
public enum AddressType {
	Country //国家
	,Municipality //直辖市
	,SuffixMunicipality //特别行政区后缀
	,Province //省
	,City //市
	,County //区
	,Town //镇
	,Street //街
	,StreetNo //街门牌号
	,No //编号
	,Symbol //字母符号
	,LandMark //地标建筑 例如 ** 大厦  门牌设施
	,RelatedPos //相对位置
	,Crossing //交叉路
	,DetailDesc //详细描述
	,childFacility//子设施
	,Village //村
	,VillageNo //村
	,BuildingNo //楼号
	,BuildingUnit //楼单元
	,SuffixBuildingUnit //楼单元后缀
	,SuffixBuildingNo //楼号后缀
	,Start //开始状态
	,End   //结束状态
	,StartSuffix//(
	,EndSuffix//)
	,Unknow
	,Other
	,SuffixProvince //
	,SuffixCity //市后缀
	,SuffixCounty //区后缀
	,District //区域
	,SuffixDistrict //区域后缀
	,SuffixTown //镇后缀
	,SuffixStreet //街后缀
	,SuffixLandMark  //地标建筑后缀
	,SuffixVillage //村后缀
	,SuffixIndicationFacility//指示性设施后缀
	,IndicationFacility//指示性设施
	,SuffixIndicationPosition//指示性设施方位后缀
	,IndicationPosition//指示性设施方位
	,Conj //连接词
}
