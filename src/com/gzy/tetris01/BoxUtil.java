package com.gzy.tetris01;

import com.gzy.test.Test90Rotate;

class BoxUtil{
	private int[][] matrix;
	private int type, changType;
	
//	public BoxUtil(int type, int changType){
//		this.type = type;
//		this.changType = changType;
//	}
	
	public int[][] getMatrix(int type, int changType){
		switch(type){
		case 1:
		case 2:
		case 3:
		case 4:
		}
		
		return this.matrix;
	}
	
	public static int[][] rotateMatrix(int[][] a){
		int len,temp = 0;
		int n = a.length - 1;
		for(int i = 0; i < n/2; i++){
			len = n -i -1;
			for(int j = i; j < len; j++){
				temp = a[i][j];
				a[i][j] = a[n-1-j][i];
				a[n-1-j][i] = a[n-1-i][n-1-j];
				a[n-1-i][n-1-j] = a[j][n-1-i];
				a[j][n-1-i] = temp;
				
			}
			
			temp = a[3][1];
			a[3][1] = a[1][3];
			a[1][3] = temp;
		}
		return a;
	}
}

