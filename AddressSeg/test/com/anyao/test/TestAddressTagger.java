package com.anyao.test;

import java.util.ArrayList;

import org.nlp.address.AddressTagger;
import org.nlp.address.AddressToken;




public class TestAddressTagger {

	public static void main(String[] args) {
		String addressStr = //"山锟斤拷省太原锟叫革拷锟斤拷锟斤拷32锟斤拷";
			//"太原锟叫帮拷锟铰讹拷锟斤拷49锟斤拷";
		//"锟斤拷锟斤拷锟�锟斤拷";
		//"锟较猴拷锟斤拷卢锟斤拷锟斤拷思锟斤拷路113锟斤拷锟斤拷锟斤拷锟斤拷锟�;
			//"锟较猴拷锟斤拷锟斤拷山锟斤拷一路1250锟脚猴拷";
			//"锟斤拷路175弄44锟斤拷602";
			//"锟姐东省锟斤拷莞锟斤拷莞锟斤拷路锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷路";
			//"锟叫癸拷锟叫筹拷锟斤拷锟斤拷锟藉北路锟斤拷2锟斤拷盈锟斤拷锟斤拷锟斤拷锟教筹拷一锟斤拷109锟斤拷元";
			//"山锟斤拷省锟斤拷强锟叫筹拷山锟斤拷112锟斤拷";
			//"锟侥达拷省锟斤拷锟斤拷锟叫斤拷锟截匡拷锟斤拷锟津东猴拷路锟铰讹拷1锟斤拷";
			//"锟姐东省锟斤拷锟斤拷锟叫从伙拷锟斤拷锟斤拷锟斤拷锟斤拷颖锟铰�8锟斤拷";
			//"锟姐东省锟斤拷锟斤拷锟叫从伙拷锟斤拷锟斤拷锟斤拷锟�;
			//"锟斤拷锟斤拷锟叫筹拷锟斤拷锟斤拷锟阶拷锟斤拷锟斤拷锟紸锟斤拷1402";
			//"锟斤拷锟斤拷锟叫凤拷台锟斤拷锟斤拷锟斤拷路29锟脚碉拷锟斤拷5锟斤拷锟斤拷锟斤拷锟斤拷窑站B锟斤拷锟斤拷锟斤拷";
			//"锟接憋拷省锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷";
			//"锟较猴拷锟叫猴拷锟斤拷锟斤拷拇锟斤拷锟铰�146锟斤拷";
			//"锟较猴拷锟叫猴拷锟斤拷锟斤拷锟铰�9锟斤拷(锟斤拷欧锟斤拷路)";
			//"锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟狡斤拷锟斤拷锟斤拷锟�6楼217锟斤拷";
			//"锟斤拷锟斤拷锟叫达拷锟剿癸拷业锟斤拷锟斤拷锟斤拷泰锟叫伙拷园3-15-204";
			//"锟叫癸拷 锟斤拷锟斤拷 锟斤拷锟斤拷锟斤拷 锟斤拷台锟斤拷锟斤拷锟斤拷锟斤拷路锟铰伙拷锟斤拷锟斤拷1锟斤拷";
			//"锟斤拷";
			"锟斤拷锟斤拷锟叫筹拷锟斤拷锟斤拷";
			//"锟斤拷锟斤拷锟叫凤拷台锟斤拷锟斤拷锟斤拷锟斤拷路锟铰伙拷锟斤拷锟斤拷1锟斤拷";
		ArrayList<AddressToken> ret =  AddressTagger.tag(addressStr);
		
		StringBuilder sb = new StringBuilder();
        for (int i =1; i <(ret.size()-1) ; ++i)
        {
        	AddressToken token = ret.get(i);
        	//System.out.println("token:"+token);
        	sb.append(token.termText+"|"+token.type+" ");
        }
        System.out.println(sb.toString());
	}
}
