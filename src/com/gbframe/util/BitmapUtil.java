package com.gbframe.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import android.graphics.Bitmap;

public class BitmapUtil {

	/**
	 * 
	 * @param bm
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String Bitmap2String(Bitmap bm) {

		return Bitmap2String(bm, Charset.defaultCharset());

	}

	public static String Bitmap2String(Bitmap bm, String encoding) {

		return Bitmap2String(bm, Charset.forName(encoding),
				Bitmap.CompressFormat.JPEG);

	}

	public static String Bitmap2String(Bitmap bm, Charset encoding) {

		return Bitmap2String(bm, encoding, Bitmap.CompressFormat.JPEG);

	}

	public static String Bitmap2String(Bitmap bm, Charset encoding,
			Bitmap.CompressFormat mime) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(mime, 100, baos);
		String str = new String(baos.toByteArray(), encoding);

		return str;

	}

}
