package com.gzy.tetris05.optimize;

import java.util.Random;

public class Box implements Runnable {
	BoxUtil boxUtil = new BoxUtil();
	
	boolean isBegin = false;
	boolean isDownMove = true;
	boolean isGameOver = false;
	boolean isLeftMove = true;
	boolean isPause = false;
	boolean isRightMove = true;
	
	boolean isRotate = true;
	private int moveSpace = 15;
	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	private int startX = 125;
	private int startY = 5;
	int[][] playPanel;
	private int score = 0;
	
	Matrix curMatrix = null;
	Matrix nextMatrix = null;
	// 构造函数
	public Box(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
		curMatrix = new Matrix(startX, startY);
		nextMatrix = new Matrix(startX, startY);
		curMatrix.next = nextMatrix;
	}
	
	public void clsLine() {
		int level = 0, sum = 0;
		boolean flag = true;
		for (int i = this.playPanel.length - 4; i >= 0 && flag; i--) {
			flag = false;
			for (int j = 2; j < this.playPanel[0].length - 3; j++) {
				if (this.playPanel[i][j] == 1) {
					flag = true;
					sum++;
				}
			}
			if (sum == this.playPanel[0].length - 5) {
				level++;
				boxUtil.clearLine(playPanel, i++);
				// System.out.println("clear:"+ i);
			}
			sum = 0;
		}
		this.score += level * 100;
	}
	
//	private int type = 1;
//	private int changeType = 0;

	public boolean getIsBegin(){
		return this.isBegin;
	}
	public boolean getIsGameOver() {
		return this.isGameOver;
	}
	public boolean getIsPause() {
		return this.isPause;
	}


	public int getScore() {
		return this.score;
	}
	
	public void init() {
		this.moveSpace = 15;
		this.initMove();
	}

	// 初始化方向值
	public void initMove() {
		this.isRotate = true;
		this.isDownMove = true;
		this.isLeftMove = true;
		this.isRightMove = true;
	}


	// 方块下移
	public void moveDown() {
		// this.initMove();
		// Test90Rotate.print(playPanel);
		if(this.isPause)
			return ;
		this.isDownMove = true;
		int x = (this.curMatrix.getMovX() - 5) / this.moveSpace + 2; // 第0列 为墙
		int y = (this.curMatrix.getMovY() - 5) / this.moveSpace;
		// System.out.println(x + " " + y);
		int tryX = x;
		int tryY = y + 1;
		// System.out.println(x + " " + y);
		outer: for (int i = 3; i >= 0; i--) {
			for (int j = 0; j < 4; j++) {
				// System.out.print(this.playPanel[tryY + i][tryX + j] + " ");
				// System.out.println(matrix[i][j]);
				if ((this.playPanel[tryY + i][tryX + j] + this.curMatrix.matrix[i][j]) > 1) {
					// System.out.println("enter");
					this.isDownMove = false;
					break outer;
				}
			}
		}

		if (isDownMove)
			this.curMatrix.movY += this.moveSpace;
	}

	// 方块移动函数
	// 方块左移
	public void moveLeft() {
		// this.initMove();
		if(this.isPause)
			return ;
		this.isLeftMove = true;
		int x = (this.curMatrix.getMovX() - 5) / this.moveSpace + 2;
		int y = (this.curMatrix.getMovY() - 5) / this.moveSpace;
		int tryX = x - 1;
		int tryY = y;
		outer: for (int j = 0; j < 4; j++) { // 只需检测第一列和第二列，因为这两列必有方块
			for (int i = 0; i < 4; i++) {
				// System.out.println(this.movX);
				// System.out.println((this.movX -5)/this.moveSpace);
				if ((this.playPanel[tryY + i][tryX + j] + this.curMatrix.matrix[i][j]) > 1) {
					this.isLeftMove = false;
					// System.out.print(this.isLeftMove + " ");
					break outer;
				}
			}
		}
		// System.out.println(this.leftRectify);
		// System.out.println(this.isLeftMove);
		if (isLeftMove) {
			this.curMatrix.movX -= this.moveSpace;
		}

	}

	// 方块右移
	public void moveRight() {
		// this.initMove();
		if(this.isPause)
			return ;
		this.isRightMove = true;
		int x = (this.curMatrix.getMovX() - 5) / this.moveSpace + 2;
		int y = (this.curMatrix.getMovY() - 5) / this.moveSpace;
		int tryX = x + 1;
		int tryY = y;
		outer: for (int j = 3; j >= 0; j--) {
			for (int i = 0; i < 4; i++) {
				if ((this.playPanel[tryY + i][tryX + j] + this.curMatrix.matrix[i][j]) > 1) {
					this.isRightMove = false;
					break outer;
				}
			}
		}
		if (this.isRightMove)
			this.curMatrix.movX += this.moveSpace;
	}

	// 方块旋转
	public void pressUp() {
		// this.initMove();
//		System.out.println("now" + this.nowChangeType + " next" + this.nextChangeType);
//		System.out.println("nowType" + this.nowType);
		if(this.isPause)
			return ;
		
		this.isRotate = true;
		int[][] tryMatrix;
		
		//这里主要是解决第六种和第七种图形旋转后不能恢复的问题
		if ((this.curMatrix.getType() == 6 || this.curMatrix.getType() == 7) && this.curMatrix.getChangeType() == 1) {
			int tryX = (this.curMatrix.getMovX() - 5) / this.moveSpace + 2;
			int tryY = (this.curMatrix.getMovY() - 5) / this.moveSpace;
			tryMatrix = new int[4][4];
			BoxUtil.copyMatrix(tryMatrix, this.curMatrix.matrix);
			boxUtil.rotateAntiMatrix(tryMatrix);
			outer:for(int i = 0; i < 4; i++)
				for(int j = 0; j < 4; j++){
					if((this.playPanel[tryY+i][tryX+j] + tryMatrix[i][j]) > 1){
						this.isRotate = false;
						break outer;
					}
				}
			if(isRotate){
				boxUtil.rotateAntiMatrix(this.curMatrix.matrix);
				this.curMatrix.setChangeType(0);
			}
		} else if (!(this.curMatrix.getType() == 5)) {
			tryMatrix = new int[4][4];
			BoxUtil.copyMatrix(tryMatrix, this.curMatrix.matrix);
			boxUtil.rotateMatrix(tryMatrix);
			int tryX = (this.curMatrix.getMovX() - 5) / this.moveSpace + 2;
			int tryY = (this.curMatrix.getMovY() - 5) / this.moveSpace;
			outer: for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if ((this.playPanel[tryY + i][tryX + j] + tryMatrix[i][j]) > 1) {
						this.isRotate = false;
						break outer;
					}
				}
			}
			if (isRotate){
				boxUtil.rotateMatrix(this.curMatrix.matrix);
				if(this.curMatrix.getType() == 6 || this.curMatrix.getType() == 7)
					this.curMatrix.setChangeType(1);
			}
		}
	}

	public void setIsBegin(boolean isBegin){
		this.isBegin = isBegin;
	}
	
	public void setIsPause(boolean isPause) {
		this.isPause = isPause;
	}

	public void setPlayPanel(int[][] playPanel) {
		this.playPanel = playPanel;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}
// 线程函数
		@Override
		public void run() {
			// Test90Rotate.print(matrix);
			int x = 0, y = 0;
			while (true) {
				if (!isPause && isBegin) {
					x = (this.curMatrix.getMovX() - 5) / this.moveSpace + 2; // 第0列 为墙
					y = (this.curMatrix.getMovY() - 5) / this.moveSpace;
//					System.out.print(x + " " + y);
					int tryX = x;
					int tryY = y + 1;
					outer: for (int i = 3; i >= 0; i--) {
						for (int j = 0; j < 4; j++) {
							if ((this.playPanel[tryY + i][tryX + j] + this.curMatrix.matrix[i][j]) > 1) {
								// System.out.println("enter");
								this.isDownMove = false;
								break outer;
							}
						}
					}
//					System.out.println( "   " + y + " " + this.isDownMove);
					if (this.isDownMove) {
						this.curMatrix.movY += this.moveSpace;
					} else {
			//			System.out.println(y);
						for (int i = 0; i < 4; i++)
							for (int j = 0; j < 4; j++) {
								this.playPanel[y + i][x + j] += this.curMatrix.matrix[i][j];
							}
						curMatrix = curMatrix.next;
						nextMatrix = new Matrix(startX, startY);
						curMatrix.next = nextMatrix;
						this.clsLine();
						this.init();
						if (y < 2) {
							this.isGameOver = true;
							break;
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// System.out.println((this.movY-5)/15);
				}else{
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
}


class Matrix{
	private int changeType = 1;
	
	BoxUtil boxUtil = null;
	
	int[][] matrix = null;
	//定义方块的形状
	private int[][] matrix1 = {{0,1,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}};
	private int[][] matrix2 = {{1,0,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}};
	private int[][] matrix3 = {{0,0,0,0},{1,1,1,0},{1,0,0,0},{0,0,0,0}};
	private int[][] matrix4 = {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}};
	private int[][] matrix5 = {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,0,0,0}};
	private int[][] matrix6 = {{0,0,0,0},{1,1,0,0},{0,1,1,0},{0,0,0,0}};
	private int[][] matrix7 = {{0,0,0,0},{0,1,1,0},{1,1,0,0},{0,0,0,0}};
	
	int movX = 125;

	int movY = 5;

	Matrix next = null;

	private int startX = 125;

	private int startY = 5;

	private int type = 1;
	
	public Matrix(){
		boxUtil = new BoxUtil();
		matrix = this.getRandomMatrix();
	}

	public Matrix(int startX, int startY){
		this.startX = startX;
		this.startY = startY;
		this.movX = startX;
		this.movY = startY;
		boxUtil = new BoxUtil();
		matrix = this.getRandomMatrix();
	}

	public int getChangeType() {
		return changeType;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public int getMovX() {
		return movX;
	}

	public int getMovY() {
		return movY;
	}

	public Matrix getNext() {
		return next;
	}

	public int[][] getRandomMatrix() {
		Random random = new Random();
		this.type = (Math.abs(random.nextInt()))%7 + 1;
		this.changeType = (Math.abs(random.nextInt()))%4;
		switch(type){
		case 1:{
			for(int i = 0; i < this.changeType; i++)
				boxUtil.rotateMatrix(this.matrix1);
			return this.matrix1;
		}
		case 2:{
			for(int i = 0; i < this.changeType; i++)
				boxUtil.rotateMatrix(this.matrix2);
			return this.matrix2;
		}
		case 3:{
			for(int i = 0; i < this.changeType; i++)
				boxUtil.rotateMatrix(this.matrix3);
			return this.matrix3;
		}
		case 4:{ 
			for(int i = 0; i < this.changeType; i++)
				boxUtil.rotateMatrix(this.matrix4);
			return this.matrix4;
		}
		case 5:{ 
			return this.matrix5;
		}
		case 6:{
			this.changeType %= 2;
			for(int i = 0; i < this.changeType; i++)
				boxUtil.rotateMatrix(this.matrix6);
			return this.matrix6;
		}
		default:{
			this.changeType %= 2;
			for(int i = 0; i < this.changeType; i++)
				boxUtil.rotateMatrix(this.matrix7);
			return this.matrix7;
		}
		}
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getType() {
		return type;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	public void setMovX(int movX) {
		this.movX = movX;
	}
	public void setMovY(int movY) {
		this.movY = movY;
	}
	public void setNext(Matrix next) {
		this.next = next;
	}
	
	public void setStartX(int startX) {
		this.startX = startX;
	}
	
	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
}
