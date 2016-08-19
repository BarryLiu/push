package com.push.admin.tags;

import java.util.List;

@SuppressWarnings("rawtypes")
public class RgkTag {
	
	public static boolean contains(List list, Object o) {
		if(list == null) {
			return false;
		} else {
			return list.contains(o);
		}
	}
	
	public static int len(List list) {
		if(list == null) {
			return -1;
		} else {
			return list.size();
		}
	}
}
