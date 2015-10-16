package org.bkgd.ehome.util.auth;

/**
 * 加密解密算法（用于用户id在cookie中的加密）
 * 
 * @author Administrator
 * 
 */
public interface EhomeAuthToolInter {
	/**
	 * 编码
	 * 
	 * @param plaintext
	 *            明文
	 * @return
	 */
	public String enCode(String plaintext);

	/**
	 * 解码
	 * 
	 * @param ciphertext
	 *           密文
	 * @return
	 */
	public String deCode(String ciphertext);
}
