package com.gbframe.util;

public class SortUtil {

	public static void quickSort(Object[] src, int startIndex, int endIndex,
			Pridictor comparor) {

		if (startIndex >= endIndex)
			return;

		final int mLeft = startIndex;
		final int mRight = endIndex;
		final Object firstValue = src[startIndex];

		while (startIndex < endIndex) {

			while (startIndex < endIndex
					&& comparor.compare(firstValue, src[endIndex]))
				endIndex--;

			src[startIndex] = src[endIndex];

			while (startIndex < endIndex
					&& !comparor.compare(firstValue, src[startIndex]))
				startIndex++;

			src[endIndex] = src[startIndex];

		}

		src[startIndex] = firstValue;

		quickSort(src, mLeft, startIndex - 1, comparor);
		quickSort(src, startIndex + 1, mRight, comparor);
	}

	public static void quickSort(Object[] src, Pridictor comparor) {
		quickSort(src, 0, src.length - 1, comparor);
	}

	public static void mergeSort(Object[] src, int startIndex, int endIndex,
			Pridictor comparor) {

		if (src.length <= 1 || startIndex >= endIndex || endIndex < 0
				|| startIndex < 0)
			return;

		final int middleIndex = (startIndex + endIndex) >> 1;
		final Object[] left = new Object[middleIndex + 1];
		final Object[] right = new Object[endIndex - middleIndex];

		System.arraycopy(src, startIndex, left, 0, middleIndex - startIndex + 1);
		System.arraycopy(src, middleIndex - startIndex + 1, right, 0, endIndex
				- middleIndex);

		mergeSort(left, 0, left.length - 1, comparor);
		mergeSort(right, 0, right.length - 1, comparor);

		int left_ptr = 0;
		int right_ptr = 0;
		int target_ptr = startIndex;

		for (; left_ptr < left.length && right_ptr < right.length;)

			if (comparor.compare(left[left_ptr], right[right_ptr]))
				src[target_ptr++] = left[left_ptr++];
			else
				src[target_ptr++] = right[right_ptr++];

		System.arraycopy(left, left_ptr, src, target_ptr, left.length
				- left_ptr);
		System.arraycopy(right, right_ptr, src, target_ptr, right.length
				- right_ptr);

	}

	public interface Pridictor {

		/**
		 * return true represent targetOne is Bigger than targetTwo and it's
		 * ordered descendly
		 */
		boolean compare(Object targetOne, Object targetTwo);

	}

}
