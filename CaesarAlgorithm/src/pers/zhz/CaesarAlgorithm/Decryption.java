package pers.zhz.CaesarAlgorithm;

import java.util.ArrayList;
import java.util.Iterator;
import com.alibaba.fastjson.JSONObject;

/**
 * 凯撒解密算法
 * 
 * @author zhz
 */

public class Decryption {

	/**
	 * 传入密文，返回明文
	 * 
	 * @param ciphertext 密文
	 * @return plaintexts 明文
	 * @throws Exception
	 */
	public static ArrayList<String> caesarDecryption(String ciphertext) throws Exception {
		ArrayList<String> plaintexts = new ArrayList<String>();// 返回的明文
		JSONObject jsonObject = null;
		YouDaoDictAPI youDaoDictAPI = new YouDaoDictAPI();

		String tmp = "";
		for (int i = 1; i > -26; i--) {// 根据整句句子的翻译挑选出符合的项
			tmp = Encryption.caesarEncryption(ciphertext, i);
			jsonObject = youDaoDictAPI.getResult(tmp);// 调用有道API获取翻译结果
			if (!jsonObject.getString("translation").contains(tmp)) {
				plaintexts.add(tmp);
			}

		}

		String[] words = null;
		Iterator<String> iterator = plaintexts.iterator();
		while (iterator.hasNext()) {// 根据每个单词挑选出符合的项
			String plaintext = iterator.next();
			words = plaintext.split(" ");
			for (int i = 0; i < words.length; i++) {
				jsonObject = youDaoDictAPI.getResult(words[i]);
				if (jsonObject.getString("isWord").contains("false") && jsonObject.getString("web") == null) {
					iterator.remove();
					break;
				}
			}
		}

		return plaintexts;
	}

}
