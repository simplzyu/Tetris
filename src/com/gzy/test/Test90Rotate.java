package com.gzy.test;

import java.util.Random;

public class Test90Rotate {
    	public static void main(String[] args) {
    	int[][] matrix = {{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}};
//		int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
//		print(matrix);
//		System.out.println();
//		print(symmRotate(matrix));
//		print(Rotate90(matrix, 3));
//		print(Rotate_Anti_90(matrix, 4));
//		print(Rotate90(matrix, 4));
//		print(RotateAntiMatrix(matrix));
//		System.out.println();
//		print(RotateAntiMatrix(matrix));
//		System.out.println();
//		print(Rotate90(matrix, 4));
//		System.out.println();
//    	print(getMatrix());
    	
    	Random random = new Random();
    	System.out.println(Math.abs(random.nextInt())%4);
    	System.out.println(Math.abs(random.nextInt())%4);
	}	
	public static int[][] symmRotate(int[][] a){
		int temp = 0;
		for(int i = 0; i < 3; i ++)
			for(int j = i; j < 3; j++){
				temp = a[i][j];
				a[i][j] = a[j][i];
				a[j][i] = temp;
			}
		return  a;
	}
	
	public static int[][] Rotate90(int[][] a, int N){	
		int temp = 0,len;
		int n = a.length;
		for(int i = 0; i < n/2; i++){
			len = n - i;
			for(int j = i; j < len - 1; j++){
				temp = a[i][j];
				a[i][j] = a[n-1-j][i];
				a[n-1-j][i] = a[n-1-i][n-1-j];
				
				a[n-1-i][n-1-j] = a[j][n-1-i];
				a[j][n-1-i] = temp;
			}
		}
		return a;
	}
	
	public static int[][] Rotate_Anti_90(int[][] a, int N){	
		int temp = 0,len;
		int n = a.length;
		for(int i = 0; i < n/2; i++){
			len = n - i - 1;
			for(int j = i; j < len; j++){
				temp = a[i][j];
				a[i][j] = a[j][n-1-i];
				a[j][n-1-i] = a[n-1-i][n-1-j];
				a[n-1-i][n-1-j] = a[n-1-j][i];
				a[n-1-j][i] = temp;
			}
		}
		return a;
	}
	
	public static int[][] RotateAntiMatrix(int[][] a){	
		int temp = 0,len;
		int n = a.length - 1;
		for(int i = 0; i < n/2; i++){
			len = n - i - 1;
			for(int j = i; j < len; j++){
				temp = a[i][j];
				a[i][j] = a[j][n-1-i];
				a[j][n-1-i] = a[n-1-i][n-1-j];
				a[n-1-i][n-1-j] = a[n-1-j][i];
				a[n-1-j][i] = temp;
			}
		}
		temp = a[3][1];
		a[3][1] = a[1][3];
		a[1][3] = temp;
		return a;
	}
	
	public static void print(int[][] a){
		for(int i = 0; i < a.length; i++){
			for(int j = 0; j <a[0].length; j++){
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	public static int[][] getMatrix(){
		int[][] matrix = { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 },{ 0, 0, 0, 0 } };
		return matrix;
	}
}



