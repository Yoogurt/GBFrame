package com.gbframe.util;

public class UnitUtil {

	/**
	 *  transform form byte to standard unit , for example , 1024 B -> 1.00 KB
	 */
	public static String transformByteToStandardUnit(long length) {

		String unit;

		float final_length;

		if (length > 1 << 10)
			if (length > 1 << 20)
				if (length > 1 << 30) {
					unit = "GB";
					final_length = Math.round((length * 100.0 / (1 << 30))) * 1.0f / 100;
				} else {
					unit = "MB";
					final_length = Math.round((length * 100.0 / (1 << 20))) * 1.0f / 100;
				}
			else {
				unit = "KB";
				final_length = Math.round((length * 100.0 / (1 << 10))) * 1.0f / 100;
			}
		else {
			unit = "B";
			final_length = length;
		}
		return final_length + unit;
	}
}
