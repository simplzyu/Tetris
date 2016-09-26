package com.gzy.tetris01;

import com.gzy.test.Test90Rotate;

public class Box implements Runnable {
	int startX = 200;
	int startY = 5;
	boolean isLive = true;
	int rectify = 0;
	int[][] playPanel;
	int[][] matrix;

	public void setPlayPanel(int[][] playPanel) {
		this.playPanel = playPanel;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int x, y = 0;
		while (true) {
		//	Test90Rotate.print(matrix);
			y = (this.startY - 5) / 15 + 3;
			x = (this.startX - 5) / 15;
			System.out.println(y + " " + x);
			outer: for (int i = 3; i >= 1&& this.isLive; i--) {
				for (int j = 0; j < 4; j++) {
					if ((this.playPanel[y][x] + matrix[i][j]) > 1) {
	//				System.out.println(y);
						System.out.println("enter" + y);
						this.rectify = 3 - i;
						this.isLive = false;
						break outer;
					}
				}
			}
		System.out.println("y = " + y);
		
			if (!this.isLive && (--this.rectify)<=0){
				for(int i = 0; i < 4; i++){
					for(int j = 0; j < 4; j++){
						playPanel[y-3+i][x+j] += matrix[i][j];
					}
				}
				
				Test90Rotate.print(playPanel);
				break;
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.startY += 15;
			// System.out.println((this.startY-5)/15);
		}
	}

}
