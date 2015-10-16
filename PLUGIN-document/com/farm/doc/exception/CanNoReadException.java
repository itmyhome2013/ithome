package com.farm.doc.exception;

import com.farm.doc.DocI18N;

/**
 * 没有读取权限异常
 * 
 * @author Administrator
 * 
 */
public class CanNoReadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CanNoReadException(String message) {
		super(message);
	}

	public CanNoReadException() {
		super(DocI18N.getData("title.com.farm.doc.exception.noread"));
	}
}
