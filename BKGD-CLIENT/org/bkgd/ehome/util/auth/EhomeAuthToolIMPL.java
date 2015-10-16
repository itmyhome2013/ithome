package org.bkgd.ehome.util.auth;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class EhomeAuthToolIMPL implements EhomeAuthToolInter {
	private static final String key = "MjEzMzQxNDMyMTI0MzEzMTIzNDU2NzIxMjEzMzQxNDMyMTI0MzEzMTIzNDU2NzIxMjEzMzQxNDMyMTI0MzEzMTIzNDU2NzIx";
	private int ip_hash;

	@SuppressWarnings("unused")
	private EhomeAuthToolIMPL() {

	}

	/**
	 * 
	 * @param ip
	 *            192.168.1.1
	 */
	public EhomeAuthToolIMPL(String ip) {
		this.ip_hash = Math.abs(ip.hashCode());
	}

	@Override
	public String deCode(String ciphertext) {
		ciphertext = ciphertext.replaceAll("A1W9C4F2", "=");
		int num = ip_hash;
		int inNum = num % 16;
		int inNumEnd = ciphertext.length() - (16 - inNum);
		String midleStr = ciphertext.substring(inNum, inNumEnd);
		byte[] midleByte = Base64.decodeBase64(midleStr.getBytes());
		String out = null;
		try {
			out = new String(midleByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "编码异常";
		}
		return out;
	}

	@Override
	public String enCode(String plaintext) {
		int num = ip_hash;
		String keyPart = key.substring(num % 32, num % 32 + 16);
		int inNum = num % 16;
		String plainStr = null;
		try {
			plainStr = new String(Base64.encodeBase64((plaintext).getBytes()),
					"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			return "编码异常";
		}
		String plain2 = keyPart.substring(0, inNum) + plainStr
				+ keyPart.substring(inNum);
		plain2 = plain2.replaceAll("=", "A1W9C4F2");
		return plain2;
	}

	public static void main(String[] args) {
		System.out.println(new EhomeAuthToolIMPL("127.0.5.3").enCode("12345"));
		System.out.println(new EhomeAuthToolIMPL("127.0.5.1")
				.deCode(new EhomeAuthToolIMPL("127.0.5.1").enCode("1234")));
	}
}
