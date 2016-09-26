package com.gzy.test;

import com.gzy.tetris03.*;

public class Test1 {
	public static void main(String[] args) {
		int[][] matrix6 = { { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 1, 0 },
				{ 0, 0, 0, 0 } };
		BoxUtil boxUtil = new BoxUtil();
		Test90Rotate.print(matrix6);
		System.out.println();
		Test90Rotate.print(boxUtil.rotateMatrix(matrix6));
	}
}
