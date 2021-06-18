package pers.zhz.CaesarAlgorithm;

/**
 * 凯撒加密算法
 * 
 * @author zhz
 */

public class Encryption {

	/**
	 * 传入明文（密文）、密钥，输出密文（明文）
	 * 
	 * @param plaintext 明文（密文）
	 * @param key       密钥
	 * @return ciphertext 密文（明文）
	 * @throws Exception
	 */
	public static String caesarEncryption(String plaintext, int key) throws Exception {
		String ciphertext = "";// 返回的密文
		char c;
		for (int i = 0; i < plaintext.length(); i++) {
			c = plaintext.charAt(i);
			if (c >= 'a' && c <= 'z')// 是小写字母
			{
				c += key % 26;
				if (c < 'a') {
					c += 26;
				}
				if (c > 'z') {
					c -= 26;
				}
			} else if (c >= 'A' && c <= 'Z')// 是大写字母
			{
				c += key % 26;
				if (c < 'A') {
					c += 26;
				}
				if (c > 'Z') {
					c -= 26;
				}
			}
			ciphertext += c;
		}
		return ciphertext;
	}

}