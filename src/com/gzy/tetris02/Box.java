package com.gzy.tetris02;

public class Box implements Runnable {
	int startX = 125;
	int startY = 5;
	int speed = 15;

	boolean isRotate = true;
	boolean isLeftMove = true;
	boolean isRightMove = true;
	boolean isDownMove = true;
	BoxUtil boxUtil = new BoxUtil();

	int[][] playPanel;
	int[][] matrix;

	// 初始化方块
	public void init() {
		this.startX = 125;
		this.startY = 5;
		this.speed = 15;
		this.initMove();
	}

	// 初始化方向值
	public void initMove() {
		this.isRotate = true;
		this.isDownMove = true;
		this.isLeftMove = true;
		this.isRightMove = true;
	}

	public void setPlayPanel(int[][] playPanel) {
		this.playPanel = playPanel;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int[][] getMatrix() {
		return this.matrix;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	// 方块旋转
	public void pressUp() {
		// this.initMove();
			this.isRotate = true;
			int[][] tryMatrix = new int[4][4];
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++)
					tryMatrix[i][j] = this.matrix[i][j];
			boxUtil.rotateMatrix(tryMatrix);
			int tryX = (this.startX - 5) / this.speed + 2;
			int tryY = (this.startY - 5) / this.speed;
			outer: for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if ((this.playPanel[tryY + i][tryX + j] + tryMatrix[i][j]) > 1) {
						this.isRotate = false;
						break outer;
					}
				}
			}
			if (isRotate)
				boxUtil.rotateMatrix(this.matrix);
		
	}

	// 方块移动函数
	// 方块左移
	public void moveLeft() {
		// this.initMove();
		this.isLeftMove = true;
		int x = (this.startX - 5) / this.speed + 2;
		int y = (this.startY - 5) / this.speed;
		int tryX = x - 1;
		int tryY = y;
		outer: for (int j = 0; j < 4; j++) { // 只需检测第一列和第二列，因为这两列必有方块
			for (int i = 0; i < 4; i++) {
				// System.out.println(this.startX);
				// System.out.println((this.startX -5)/this.speed);
				if ((this.playPanel[tryY + i][tryX + j] + this.matrix[i][j]) > 1) {
					this.isLeftMove = false;
					break outer;
				}
			}
		}
		// System.out.println(this.leftRectify);
		if (isLeftMove) {
			this.startX -= this.speed;
		}

	}

	// 方块右移
	public void moveRight() {
		// this.initMove();
		this.isRightMove = true;
		int x = (this.startX - 5) / this.speed + 2;
		int y = (this.startY - 5) / this.speed;
		int tryX = x + 1;
		int tryY = y;
		outer: for (int j = 3; j >= 0; j--) {
			for (int i = 0; i < 4; i++) {
				if ((this.playPanel[tryY + i][tryX + j] + this.matrix[i][j]) > 1) {
					this.isRightMove = false;
					break outer;
				}
			}
		}
		if (this.isRightMove)
			this.startX += this.speed;
	}

	// 方块下移
	public void moveDown() {
		// this.initMove();
		// Test90Rotate.print(playPanel);
		this.isDownMove = true;
		int y = (this.startY - 5) / this.speed;
		int x = (this.startX - 5) / this.speed + 2; // 第0列 为墙
		// System.out.println(x + " " + y);
		int tryX = x;
		int tryY = y + 1;
		// System.out.println(x + " " + y);
		outer: for (int i = 3; i >= 0; i--) {
			for (int j = 0; j < 4; j++) {
				// System.out.print(this.playPanel[tryY + i][tryX + j] + " ");
				// System.out.println(matrix[i][j]);
				if ((this.playPanel[tryY + i][tryX + j] + matrix[i][j]) > 1) {
					// System.out.println("enter");
					this.isDownMove = false;
					break outer;
				}
			}
		}

		if (isDownMove)
			this.startY += this.speed;
	}
	
	public void clsLine(){
		int level = 0, sum = 0;
		boolean flag = true;
		for(int i = this.playPanel.length-4; i>=0 && flag; i--){
			flag = false;
			for(int j = 1; j < this.playPanel[0].length-4; j++){
				if(this.playPanel[i][j] == 1){
					flag = true;
					sum++;
				}
			}
			if(sum == 20){
				level ++;
				boxUtil.clearLine(playPanel, i++);
				System.out.println("clear:"+ i);
			}
			sum = 0;
		}
	}

	// 线程函数
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		Test90Rotate.print(matrix);
		int x = 0, y = 0;
		while (true) {
			y = (this.startY - 5) / this.speed;
			x = (this.startX - 5) / this.speed + 2; // 第0列 为墙
			// System.out.println(x + " " + y);
			int tryX = x;
			int tryY = y + 1;
			// System.out.println(x + " " + y);
			// System.out.println(y + " " + x);
			outer: for (int i = 3; i >= 0; i--) {
				for (int j = 0; j < 4; j++) {
					// System.out.print(this.playPanel[tryY + i][tryX + j] +
					// " ");
					// System.out.println(matrix[i][j]);
					if ((this.playPanel[tryY + i][tryX + j] + matrix[i][j]) > 1) {
						// System.out.println("enter");
						this.isDownMove = false;
						break outer;
					}
				}
			}
			// System.out.println("y = " + y);
			// System.out.println(this.isDownMove);
			if (this.isDownMove) {
				// System.out.println("enter2");
				this.startY += this.speed;
			} else {
				for (int i = 0; i < 4; i++)
					for (int j = 0; j < 4; j++) {
						// System.out.println(this.playPanel[y+i][x+j] + " " +
						// this.matrix[i][j] );
						this.playPanel[y + i][x + j] += this.matrix[i][j];
					}
				this.init();
				this.clsLine();
//				Test90Rotate.print(this.playPanel);
				this.matrix = boxUtil.getMatrix();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println((this.startY-5)/15);
		}
	}

}
