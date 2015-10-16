package com.farm.doc.exception;

import com.farm.doc.DocI18N;

/**
 * 没有修改权限异常
 * 
 * @author Administrator
 * 
 */
public class CanNoWriteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CanNoWriteException(String message) {
		super(message);
	}

	public CanNoWriteException() {
		super(DocI18N.getData("title.com.farm.doc.exception.nowrite"));
	}
}
