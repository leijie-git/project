package com.gw.util;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 编码与解码操作工具类
 *
 * @author AllenPang
 *
 * @date 2016-08-17
 *
 */
public class CodecUtil {

	private static Logger logger = LoggerFactory.getLogger(CodecUtil.class);

	/**
	 * 将 URL 编码
	 */
	public static String encodeURL(String str) {
		String target;
		try {
			target = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			logger.error("编码出错！", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * 将 URL 解码
	 */
	public static String decodeURL(String str) {
		String target;
		try {
			target = URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			logger.error("解码出错！", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * 将字符串 Base64 编码
	 */
	public static String encodeBASE64(String str) {
		String target;
		try {
			target = Base64.encodeBase64URLSafeString(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("编码出错！", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * 将字符串 Base64 解码
	 */
	public static String decodeBASE64(String str) {
		String target;
		try {
			target = new String(Base64.decodeBase64(str), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("解码出错！", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * 将字符串 MD5 加密
	 */
	public static String encryptMD5(String str) {
		return DigestUtils.md5Hex(str);
	}

	/**
	 * 将字符串 SHA 加密
	 */
	public static String encryptSHA(String str) {
		return DigestUtils.sha1Hex(str);
	}
	
	/**
	 * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
	 * @param str
	 *            待转换字符串
	 * @return 转换后字符串
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiConvert1(String str)
			throws UnsupportedEncodingException {
		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb,
						"[[" + URLEncoder.encode(matcher.group(1), "UTF-8")
								+ "]]");
			} catch (UnsupportedEncodingException e) {
				// LOG.error("emojiConvert error", e);
				throw e;
			}
		}
		matcher.appendTail(sb);
		// LOG.debug("emojiConvert " + str + " to " + sb.toString()
		// + ", len：" + sb.length());
		return sb.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		 System.out.println(emojiRecovery2("W6HTwAMNfpIh5zSjCdtzjWLiQ4QbTa"));
	}

	/**
	 * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
	 * @param str
	 *            转换后的字符串
	 * @return 转换前的字符串
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiRecovery2(String str)
			throws UnsupportedEncodingException {
		if (Util.isEmpty(str)) {
			str = "";
		}
		String patternString = "\\[\\[(.*?)\\]\\]";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb,
						URLDecoder.decode(matcher.group(1), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// LOG.error("emojiRecovery error", e);
				throw e;
			}
		}
		matcher.appendTail(sb);
		// LOG.debug("emojiRecovery " + str + " to " + sb.toString());
		return sb.toString();
	}

	public static Key DEFAULT_KEY;
	public static final String DEFAULT_SECRET_KEY1 = "W6HTwAMNfpIh5zSjCdtzjWLiQ4QbTa";
	public static final String DEFAULT_SECRET_KEY = DEFAULT_SECRET_KEY1;
	public static final String DES = "DES";
	public static final Base32 base32 = new Base32();
	static {
		DEFAULT_KEY = obtainKey(DEFAULT_SECRET_KEY);
	}

	/**
	 * 获得key
	 **/
	public static Key obtainKey(String key) {
		if (key == null) {
			return DEFAULT_KEY;
		}
		KeyGenerator generator = null;
		try {
			generator = KeyGenerator.getInstance(DES);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		generator.init(new SecureRandom(key.getBytes()));
		Key key1 = generator.generateKey();
		return key1;
	}

	/**
	 * 加密<br>
	 * String明文输入,String密文输出
	 */
	public static String encode(String str) {
		return encode64(null, str);
	}

	/**
	 * 加密<br>
	 * String明文输入,String密文输出
	 */
	public static String encode64(String key, String str) {
		return Base64.encodeBase64URLSafeString(obtainEncode(key, str.getBytes()));
	}

	/**
	 * 加密<br>
	 * String明文输入,String密文输出
	 */
	public static String encode32(String key, String str) {
		return base32.encodeAsString(obtainEncode(key, str.getBytes())).replaceAll("=", "");
	}

	/**
	 * 加密<br>
	 * String明文输入,String密文输出
	 */
	public static String encode16(String key, String str) {
		return Hex.encodeHexString(obtainEncode(key, str.getBytes()));
	}

	/**
	 * 解密<br>
	 * 以String密文输入,String明文输出
	 */
	public static String decode(String str) {
		return decode64(null, str);
	}

	/**
	 * 解密<br>
	 * 以String密文输入,String明文输出
	 */
	public static String decode64(String key, String str) {
		return new String(obtainDecode(key, Base64.decodeBase64(str)));
	}

	/**
	 * 解密<br>
	 * 以String密文输入,String明文输出
	 */
	public static String decode32(String key, String str) {
		return new String(obtainDecode(key, base32.decode(str)));
	}

	/**
	 * 解密<br>
	 * 以String密文输入,String明文输出
	 */
	public static String decode16(String key, String str) {
		try {
			return new String(obtainDecode(key, Hex.decodeHex(str.toCharArray())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密<br>
	 * 以byte[]明文输入,byte[]密文输出
	 */
	private static byte[] obtainEncode(String key, byte[] str) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			Key key1 = obtainKey(key);
			cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.ENCRYPT_MODE, key1);
			byteFina = cipher.doFinal(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteFina;
	}

	/**
	 * 解密<br>
	 * 以byte[]密文输入,以byte[]明文输出
	 */
	private static byte[] obtainDecode(String key, byte[] str) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			Key key1 = obtainKey(key);
			cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.DECRYPT_MODE, key1);
			byteFina = cipher.doFinal(str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
}
