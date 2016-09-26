package com.gzy.tetris04;

import java.util.Random;

public class Box implements Runnable {
	int startX = 125;
	int startY = 5;
	
	int movX = 125;
	int movY = 5;
	int moveSpace = 15;
	int score = 0;
	int type = 1;
	int nextType = 1;
	
	int nowType = 1;
	int nowChangeType = 1;
	int changeType = 1;
	int nextChangeType = 1;

	boolean isRotate = true;
	boolean isLeftMove = true;
	boolean isRightMove = true;
	boolean isDownMove = true;
	boolean isGameOver = false;
	boolean isPause = false;
	boolean isBegin = false;
	
	BoxUtil boxUtil = new BoxUtil();
	
	//���巽�����״
	private int[][] matrix1 = {{0,1,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}};
	private int[][] matrix2 = {{1,0,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}};
	private int[][] matrix3 = {{0,0,0,0},{1,1,1,0},{1,0,0,0},{0,0,0,0}};
	private int[][] matrix4 = {{0,0,0,0},{1,1,1,1},{0,0,0,0},{0,0,0,0}};
	private int[][] matrix5 = {{0,0,0,0},{0,1,1,0},{0,1,1,0},{0,0,0,0}};
	private int[][] matrix6 = {{0,0,0,0},{1,1,0,0},{0,1,1,0},{0,0,0,0}};
	private int[][] matrix7 = {{0,0,0,0},{0,1,1,0},{1,1,0,0},{0,0,0,0}};
	
//	private int type = 1;
//	private int changeType = 0;

	int[][] playPanel;
	int[][] nowMatrix;
	int[][] nextMatrix;

	// ��ʼ������
	public void init() {
		this.movX = this.startX;
		this.movY = this.startY;
		this.moveSpace = 15;
		this.initMove();
	}

	// ���캯��
	public Box(int startX, int startY) {
		nextMatrix = new int[4][4];
		nowMatrix = new int[4][4];
		
		this.movX = startX;
		this.movY = startY;
		
		this.startX = startX;
		this.startY = startY;
		
		boxUtil.copyMatrix(nowMatrix, this.getMatrix());
//		this.matrix = boxUtil.getMatrix();
		boxUtil.getStartMatrix(nowMatrix, this.type, this.changeType);
		this.nowType = this.type;
		this.nowChangeType = this.changeType;

//		this.nextMatrix = boxUtil.getMatrix();
		boxUtil.copyMatrix(nextMatrix, this.getMatrix());
		this.nextType = this.type;
		this.nextChangeType = this.changeType;
	}

	// ��ʼ������ֵ
	public void initMove() {
		this.isRotate = true;
		this.isDownMove = true;
		this.isLeftMove = true;
		this.isRightMove = true;
	}

	public void setPlayPanel(int[][] playPanel) {
		this.playPanel = playPanel;
	}

	// public void setMatrix(int[][] matrix) {
	// this.matrix = matrix;
	// }
	
	public int[][] getNowMatrix(){
		return this.nowMatrix;
	}

	public int[][] getMatrix() {
		Random random = new Random();
		this.type = (Math.abs(random.nextInt()))%7 + 1;
		this.changeType = (Math.abs(random.nextInt()))%4;
		switch(type){
		case 1: return this.matrix1;
		case 2: return this.matrix2;
		case 3: return this.matrix3;
		case 4: return this.matrix4;
		case 5: return this.matrix5;
		case 6: return this.matrix6;
		default: return this.matrix7;
		}
	}

	// �õ�Matrix�����Ͻǵĺ�����
	public int getMovX() {
		return movX;
	}

	// �õ�Matrix�����Ͻǵ�������
	public int getMovY() {
		return movY;
	}
	
	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	// ������ת
	public void pressUp() {
		// this.initMove();
//		System.out.println("now" + this.nowChangeType + " next" + this.nextChangeType);
//		System.out.println("nowType" + this.nowType);
		if(this.isPause)
			return ;
		
		this.isRotate = true;
		int[][] tryMatrix;
		
		//������Ҫ�ǽ�������ֺ͵�����ͼ����ת���ָܻ�������
		if ((this.nowType == 6 || this.nowType == 7) && (this.nowChangeType%2) ==1) {
			int tryX = (this.movX-5)/this.moveSpace + 2;
			int tryY = (this.movY-5)/this.moveSpace;
			tryMatrix = new int[4][4];
			for(int i = 0; i < 4; i++)
				for(int j = 0; j < 4; j++)
					tryMatrix[i][j] = this.nowMatrix[i][j];
			boxUtil.rotateAntiMatrix(tryMatrix);
			outer:for(int i = 0; i < 4; i++)
				for(int j = 0; j < 4; j++){
					if((this.playPanel[tryY+i][tryX+j] + tryMatrix[i][j]) > 1){
						this.isRotate = false;
						break outer;
					}
				}
			if(isRotate){
				boxUtil.rotateAntiMatrix(this.nowMatrix);
				this.nowChangeType--;
			}
		} else if (!(this.nowType == 5)) {
			tryMatrix = new int[4][4];
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++)
					tryMatrix[i][j] = this.nowMatrix[i][j];
			boxUtil.rotateMatrix(tryMatrix);
			int tryX = (this.movX - 5) / this.moveSpace + 2;
			int tryY = (this.movY - 5) / this.moveSpace;
			outer: for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if ((this.playPanel[tryY + i][tryX + j] + tryMatrix[i][j]) > 1) {
						this.isRotate = false;
						break outer;
					}
				}
			}
			if (isRotate){
				boxUtil.rotateMatrix(this.nowMatrix);
				this.nowChangeType++;
			}
		}
	}

	// �����ƶ�����
	// ��������
	public void moveLeft() {
		// this.initMove();
		if(this.isPause)
			return ;
		this.isLeftMove = true;
		int x = (this.movX - 5) / this.moveSpace + 2;
		int y = (this.movY - 5) / this.moveSpace;
		int tryX = x - 1;
		int tryY = y;
		outer: for (int j = 0; j < 4; j++) { // ֻ�����һ�к͵ڶ��У���Ϊ�����б��з���
			for (int i = 0; i < 4; i++) {
				// System.out.println(this.movX);
				// System.out.println((this.movX -5)/this.moveSpace);
				if ((this.playPanel[tryY + i][tryX + j] + this.nowMatrix[i][j]) > 1) {
					this.isLeftMove = false;
					// System.out.print(this.isLeftMove + " ");
					break outer;
				}
			}
		}
		// System.out.println(this.leftRectify);
		// System.out.println(this.isLeftMove);
		if (isLeftMove) {
			this.movX -= this.moveSpace;
		}

	}

	// ��������
	public void moveRight() {
		// this.initMove();
		if(this.isPause)
			return ;
		this.isRightMove = true;
		int x = (this.movX - 5) / this.moveSpace + 2;
		int y = (this.movY - 5) / this.moveSpace;
		int tryX = x + 1;
		int tryY = y;
		outer: for (int j = 3; j >= 0; j--) {
			for (int i = 0; i < 4; i++) {
				if ((this.playPanel[tryY + i][tryX + j] + this.nowMatrix[i][j]) > 1) {
					this.isRightMove = false;
					break outer;
				}
			}
		}
		if (this.isRightMove)
			this.movX += this.moveSpace;
	}

	// ��������
	public void moveDown() {
		// this.initMove();
		// Test90Rotate.print(playPanel);
		if(this.isPause)
			return ;
		this.isDownMove = true;
		int y = (this.movY - 5) / this.moveSpace;
		int x = (this.movX - 5) / this.moveSpace + 2; // ��0�� Ϊǽ
		// System.out.println(x + " " + y);
		int tryX = x;
		int tryY = y + 1;
		// System.out.println(x + " " + y);
		outer: for (int i = 3; i >= 0; i--) {
			for (int j = 0; j < 4; j++) {
				// System.out.print(this.playPanel[tryY + i][tryX + j] + " ");
				// System.out.println(matrix[i][j]);
				if ((this.playPanel[tryY + i][tryX + j] + this.nowMatrix[i][j]) > 1) {
					// System.out.println("enter");
					this.isDownMove = false;
					break outer;
				}
			}
		}

		if (isDownMove)
			this.movY += this.moveSpace;
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

	public boolean getIsGameOver() {
		return this.isGameOver;
	}
	
	public boolean getIsBegin(){
		return this.isBegin;
	}
	
	public void setIsBegin(boolean isBegin){
		this.isBegin = isBegin;
	}

	public int[][] getNextMatrix() {
		return this.nextMatrix;
	}

	public int getScore() {
		return this.score;
	}

	public void setIsPause(boolean isPause) {
		this.isPause = isPause;
	}

	public boolean getIsPause() {
		return this.isPause;
	}

	// �̺߳���
	@Override
	public void run() {
		// Test90Rotate.print(matrix);
		int x = 0, y = 0;
		while (true) {
			if (!isPause && isBegin) {
				y = (this.movY - 5) / this.moveSpace;
				x = (this.movX - 5) / this.moveSpace + 2; // ��0�� Ϊǽ
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
						if ((this.playPanel[tryY + i][tryX + j] + this.nowMatrix[i][j]) > 1) {
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
					this.movY += this.moveSpace;
				} else {
					for (int i = 0; i < 4; i++)
						for (int j = 0; j < 4; j++) {
							// System.out.println(this.playPanel[y+i][x+j] + " "
							// +
							// this.matrix[i][j] );
							this.playPanel[y + i][x + j] += this.nowMatrix[i][j];
						}
					// this.matrix = boxUtil.getMatrix();
					
					boxUtil.copyMatrix(this.nowMatrix, this.nextMatrix);
					this.nowType = this.nextType;
					this.nowChangeType = this.nextChangeType;
					// System.out.println("set");
					// Test90Rotate.print(matrix);

					/*
					 * �˴�������������Ŀ������˵��۾��ķ�Ӧʱ��Ϊ50ms��
					 * ������getMatrix()�����ȵõ�ͼ�Σ��������������д��� ���ڼ���ͬʱִ��(<50)����ͼ�β����ص� try
					 * { Thread.sleep(50); } catch (InterruptedException e) { //
					 */

					this.init();
					this.clsLine();
					// System.out.println();
					// Test90Rotate.print(this.playPanel);
					if (y < 2) {
						this.isGameOver = true;
						break;
					}
		//			this.nextMatrix = boxUtil.getMatrix();
					boxUtil.copyMatrix(nextMatrix, this.getMatrix());
					this.nextType = this.type;
					this.nextChangeType = this.changeType;
					boxUtil.getStartMatrix(nextMatrix, this.nextType, this.nextChangeType);
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
