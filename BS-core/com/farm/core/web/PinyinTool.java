package com.farm.core.web;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinTool {

	/**
	 * @param args
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static void main(String[] args)
			throws BadHanyuPinyinOutputFormatCombination {
		System.out.println(turnPinyin("王东"));
	}
	public static String turnPinyin(String str) {
		String returnStr = "";
		try {
			char[] strA = str.toCharArray();
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			for (char chr : strA) {
				returnStr=returnStr+PinyinHelper.toHanyuPinyinStringArray(chr, format)[0];
			}
		} catch (Exception e) {
			String rnum=String.valueOf(Math.random());
			if(rnum.length()>9){
				rnum=rnum.substring(0,9);
			}
			if(rnum.indexOf(".")>=0){
				rnum=rnum.replace(".","");
			}
			return rnum; 
		}
		return returnStr;
	}

}
