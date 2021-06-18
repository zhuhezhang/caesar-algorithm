package pers.zhz.CaesarAlgorithm;

/**
 * 凯撒加密/解密算法主函数
 * 
 * @author zhz
 */

public class Main {

	private static final String plaintext = "Always believe that something wonderful is about to happen";// 明文
	private static final int key = 34;// 密钥

	public static void main(String[] args) throws Exception {
		System.out.println("明文：\n" + plaintext);
		String ciphertext = Encryption.caesarEncryption(plaintext, key);// 输出密文
		System.out.println("\n密文：\n" + ciphertext);
		System.out.println("\n以下为解密算法得出的明文：");
		for (String p : Decryption.caesarDecryption(ciphertext)) {// 遍历输出解密出可能的明文
			System.out.println(p.substring(0, p.length()));
		}
	}

}
