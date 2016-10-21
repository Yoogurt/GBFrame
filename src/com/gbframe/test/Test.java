package com.gbframe.test;

import java.util.Arrays;

import com.gbframe.util.SortUtil;
import com.gbframe.util.SortUtil.Pridictor;

public class Test {

	public static void main(String[] args) {
		Object[] src = { 12, 2, 33, 4, 52, 16, 39 };

		SortUtil.quickSort(src, 0, src.length - 1, new Pridictor() {

			@Override
			public boolean compare(Object targetOne, Object targetTwo) {
				System.out.println("targetOne : " + targetOne + "  targetTwo : " + targetTwo);
				return (int) targetOne > (int) targetTwo;
			}
		});
		System.out.println(123);
	}

}
