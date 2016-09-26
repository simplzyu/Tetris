package com.gzy.tetris05.optimize;

public class BoxUtil{
	
//	public BoxUtil(int type, int changType){
//		this.type = type;
//		this.changType = changType;
//	}
//	private int[][] matrix1 = {{0,1,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}};
//	private int[][] matrix2 = {{1,0,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}};
//	private int[][] matrix3 = {{0,0,0,0},{1,1,1,0},{1,0,0,0},{0,0,0,0}};
//	private int[][] matrix4 = {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}};
//	private int[][] matrix5 = {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,0,0,0}};
//	private int[][] matrix6 = {{0,0,0,0},{1,1,0,0},{0,1,1,0},{0,0,0,0}};
//	private int[][] matrix7 = {{0,0,0,0},{0,1,1,0},{1,1,0,0},{0,0,0,0}};
//	private int type = 1;
//	private int changeType = 0;
	
	public int[][] getStartMatrix(int[][] matrix, int type, int changeType){
		switch(type){
		case 5: 
			return matrix;
		case 6:
			int temp = changeType%2;
			for(int i = 0; i < temp; i++)
				rotateMatrix(matrix);
			return matrix;
		case 7:
			for(int i = 0; i < changeType%2; i++)
				rotateMatrix(matrix);
			return matrix;
		default:
			for(int i = 0; i < changeType; i++)
				rotateMatrix(matrix);
			return matrix;
		}
	}
	
	public int[][] rotateMatrix(int[][] a){
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
	
	public int[][] rotateAntiMatrix(int[][] a){	
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
	
	public int[][] clearLine(int[][] playPanel, int line){
		for(int i = line; i > 0; i--){
			for(int j = 2; j < playPanel[0].length -3; j++){
				playPanel[i][j] = playPanel[i-1][j];
			}
		}
		
		//j从二开始很重要，如果从1开始则第一列会开始出现0，即墙被破坏了
		for(int j = 2; j < playPanel[0].length -4; j++)
			playPanel[0][j] = 0;
		
		return playPanel;
	}
	
	public static void copyMatrix(int[][] resultmatrix, int[][] originalMatrix){
		for(int i = 0; i < originalMatrix.length; i++)
			for(int j = 0; j < originalMatrix[0].length; j++)
				resultmatrix[i][j] = originalMatrix[i][j];
	}
}

