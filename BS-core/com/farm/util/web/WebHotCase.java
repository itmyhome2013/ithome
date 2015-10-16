package com.farm.util.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.org.apache.commons.collections.MapUtils;

class hotCase {
	public String key;
	public int num;

	public hotCase(String key, int num) {
		this.key = key;
		this.num = num;
	}
}

/**
 * 控制用户访问的计数器工具
 * 
 * @author Administrator
 * 
 */
public class WebHotCase {
	private static List<hotCase> HOT_CASE = new ArrayList<hotCase>();
	private static Map<String, Integer> HOT_CASE_MAP = new HashMap<String, Integer>();
	private static int maxNum = 1000;
	private static int minNum = 500;
	private static int index = 0;

	/**
	 * 录入一个查询用例
	 * 
	 * @param caseStr
	 */
	public static void putCase(String caseStr) {
		Integer num = HOT_CASE_MAP.get(caseStr);
		if (num == null) {
			num = 0;
		}
		index++;
		HOT_CASE_MAP.put(caseStr, num + 1);
		if (HOT_CASE_MAP.size() >= maxNum || index == 1 || index == 5
				|| index == 10 || index == 50 || index == 200) {
			clearHotCase();
		}
	}

	/**
	 * 获得查询case
	 * 
	 * @param caseStr
	 */
	public static List<String> getCases(int num) {
		List<String> list = new ArrayList<String>();
		for (int n = 0; n < HOT_CASE.size() && n < num; n++) {
			list.add(HOT_CASE.get(n).key);
		}
		return list;
	}

	/**
	 * 清理map
	 */
	private static void clearHotCase() {
		// 清空list
		HOT_CASE.clear();
		// map放入list
		for (String key : HOT_CASE_MAP.keySet()) {
			int num = HOT_CASE_MAP.get(key);
			HOT_CASE.add(new hotCase(key, num));
		}
		// list排序
		Collections.sort(HOT_CASE, new Comparator<hotCase>() {
			public int compare(hotCase o1, hotCase o2) {
				return o1.num - o2.num;
			};
		});
		// list截串
		if (HOT_CASE.size() >= minNum) {
			HOT_CASE = HOT_CASE.subList(0, minNum);
		}
		if (index >= minNum) {
			index = minNum;
		}
		// 装回map
		HOT_CASE_MAP.clear();
		for (hotCase hc : HOT_CASE) {
			int cnum = hc.num;
			// 每次清理 热度都会衰减一半
			if (cnum > 0) {
				cnum = cnum / 2;
			}
			HOT_CASE_MAP.put(hc.key, cnum);
		}
	}

}
