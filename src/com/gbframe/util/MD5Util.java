package com.gbframe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String getMD5(byte[] info) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(info);
			byte[] encryption = md5.digest();

			return ByteUtil.bytes2Hex(encryption);
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	public static String getMD5(String info) {
		return getMD5(info, Charset.defaultCharset());
	}

	public static String getMD5(String info, Charset charset) {
		return info == null ? "" : getMD5(info.getBytes(charset));
	}

	public static String getMD5(String info, String encode) {
		return getMD5(info, Charset.forName(encode));
	}

	public static String getFileMD5(String path) throws FileNotFoundException {
		return getFileMD5(new File(path));
	}

	public static String getFileMD5(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			value = ByteUtil.bytes2Hex(md5.digest());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

}
