package com.farm.core.autoform;

/**
 * 生成随机数字
 * @author hxx
 *
 */
public class NumberTools {
	public static char[] generateRandomArray(int num) {
		String chars = "0123456789";
		char[] rands = new char[num];
		for (int i = 0; i < num; i++) {
			int rand = (int) (Math.random() * 10);
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}
}
