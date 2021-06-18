package pers.zhz.CaesarAlgorithm;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 有道词典文本翻译API
 * 
 * @author zhz
 */

public class YouDaoDictAPI {

	private static Logger logger = LoggerFactory.getLogger(YouDaoDictAPI.class);
	private static final String YOUDAO_URL = "https://openapi.youdao.com/api";
	private static final String APP_KEY = "";
	private static final String APP_SECRET = "";

	/**
	 * 设置调用API需要向接口发送的字段来访问服务，传入翻译的英文返回获取的JSON格式数据
	 */
	public JSONObject getResult(String q) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		String salt = String.valueOf(System.currentTimeMillis());
		params.put("from", "en");
		params.put("to", "zh-CHS");
		params.put("signType", "v3");
		String curtime = String.valueOf(System.currentTimeMillis() / 1000);
		params.put("curtime", curtime);
		String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
		String sign = getDigest(signStr);
		params.put("appKey", APP_KEY);
		params.put("q", q);
		params.put("salt", salt);
		params.put("sign", sign);
		/** 处理结果 */
		return requestForHttp(YOUDAO_URL, params);
	}

	private static JSONObject requestForHttp(String url, Map<String, String> params) throws IOException {
		/** 创建HttpClient */
		CloseableHttpClient httpClient = HttpClients.createDefault();

		/** httpPost */
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> en = it.next();
			String key = en.getKey();
			String value = en.getValue();
			paramsList.add(new BasicNameValuePair(key, value));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		try {
			//Header[] contentType = httpResponse.getHeaders("Content-Type");
			//logger.info("Content-Type:" + contentType[0].getValue());

			HttpEntity httpEntity = httpResponse.getEntity();
			String jsonString = EntityUtils.toString(httpEntity, "UTF-8");
			EntityUtils.consume(httpEntity);
			//logger.info(jsonString);
			return JSONObject.parseObject(jsonString);

		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				logger.info("## release resouce error ##" + e);
			}
		}
	}

	/**
	 * 生成加密字段
	 */
	private static String getDigest(String string) {
		if (string == null) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
		try {
			MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String truncate(String q) {
		if (q == null) {
			return null;
		}
		int len = q.length();
		return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
	}
}