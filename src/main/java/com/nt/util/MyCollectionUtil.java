package com.nt.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyCollectionUtil {

	public static Map<Integer,String>convertListToMap(List<Object[]> list){
		Map<Integer,String> map=new LinkedHashMap<>();
		//JDK 1.8 Static method in Interface
		//Map<Integer,String> map=list.stream().collect(Collectors.toMap(ob->Integer.valueOf(ob.toString()),ob->ob.toString()));
		
		     //or
		for(Object[] ob:list) {
			map.put(Integer.valueOf(ob[0].toString()), ob[1].toString());
		}
		
		return map;
	}
}
