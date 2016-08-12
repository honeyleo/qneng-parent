package cn.lfy.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String sign(String text) {
		byte[] data = getContentBytes(text, "UTF-8");
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			String sign = Hex.encodeHexString(digest.digest(data));
			return sign;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5 Exception:"
					+ charset);
		}
	}
	public static void main(String[] args) {
		System.out.println(MD5.sign("app_id=3072663&buy_amount=1&cp_order_id=56efcf041f3b9db8ec000001&create_time=1458556738326&notify_id=1458556763595&notify_time=2016-03-21 18:39:15&order_id=16032121014151863&partner_id=116300634&pay_time=1458556755000&pay_type=0&product_id=com.climaxtech.lod.meizu.01&product_per_price=1.0&total_price=6.0&trade_status=3&uid=116939955&user_info=4001:e0BHlouGpSIn94JKBQXRvbhpqKoJ9ZP9"));
	}
}
