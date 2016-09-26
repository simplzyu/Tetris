package com.gzy.tetris02;

import java.util.Random;

class BoxUtil{
	
//	public BoxUtil(int type, int changType){
//		this.type = type;
//		this.changType = changType;
//	}
	private int[][] matrix1= { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 },{ 0, 0, 0, 0 } };;
	private int[][] matrix2={{1,0,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}};
	private int[][] matrix3={{0,0,0,0},{1,1,1,0},{1,0,0,0},{0,0,0,0}};
	private int[][] matrix4={{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}};
	private int[][] matrix5={{1,1,0,0},{1,1,0,0},{0,0,0,0},{0,0,0,0}};

	public int[][] getMatrix(){
		Random random = new Random();
		int type  = Math.abs(random.nextInt())%5;
		int changeType = Math.abs(random.nextInt())%4;
		switch(type){
		case 1: 
			for(int i = 0; i <changeType; i++)
				rotateMatrix(this.matrix1);
			return this.matrix1;
		case 2: 
			for(int i = 0; i < changeType; i++)
				rotateMatrix(this.matrix2);
			return this.matrix2;
		case 3: 
			for(int i = 0; i < changeType; i++)
				rotateMatrix(this.matrix3);
			return this.matrix3;
		case 4:
			for(int i = 0; i < changeType; i++)
				rotateMatrix(this.matrix4);
			return this.matrix4;
		default: 
			return this.matrix5;
		}
	}
	
	public int[][] rotateMatrix(int[][] a){
		if(a[0][0] ==1 && a[0][1] == 1 && a[1][0] ==1 && a[1][1] == 1)
			return a;
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
			for(int j = 1; j < playPanel[0].length -4; j++){
				playPanel[i][j] = playPanel[i-1][j];
			}
		}
		for(int j = 1; j < playPanel[0].length -4; j++)
			playPanel[0][j] = 0;
		
		return playPanel;
	}
}

