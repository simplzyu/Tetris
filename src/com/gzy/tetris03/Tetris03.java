/**
 * 俄罗斯方块3.0
 * 功能：
 * 1.画出方块
 * 2.方块移动
 * 3.方块能够判断是否能够继续移动,翻转
 * 4.能够生成多种方块
 * 5.能够消行
 * 6.能够结束游戏
 */
package com.gzy.tetris03;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Tetris03 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyPanel myPanel;
//	JButton jButton;

	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("行数：");
//		int row = scanner.nextInt();
//		System.out.println("列数：");
//		int col = scanner.nextInt();
		new Tetris03(30,20);
	}

	public Tetris03(int row, int col) {
		myPanel = new MyPanel(row,col);
//		jButton = new JButton("暂停");

		this.add(myPanel);

		// 注册监听器
		this.addKeyListener(myPanel);

		this.setTitle("俄罗斯方块3.0");
		this.setSize(430, 500);
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		// 开启线程
		Thread thread = new Thread(myPanel);
		thread.start();
	}
}

class MyPanel extends JPanel implements KeyListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	int[][] matrix;
	int gameX = 14;
	int gameY = 30;
	BoxUtil boxUtil = new BoxUtil();

	// playPan[33][25] 30*20的俄罗斯方块，但是每边需要多出三格来辅助判断（左边只要多出两格即可)
	int[][] playPan;
	Box box = new Box();
	Thread threadBox = new Thread(box);

	public MyPanel(int gameY, int gameX) {
		// 初始化matrix
		this.gameX = gameX;
		this.gameY = gameY;
		playPan = new int[this.gameY+3][this.gameX+5];
		//此处的matrix 是从boxUtil中得到的
//		box.matrix = boxUtil.getMatrix();

		for (int i = 0; i < playPan.length; i++) {
			playPan[i][0] = 1;
			playPan[i][1] = 1;
			playPan[i][playPan[0].length - 3] = 1;
			playPan[i][playPan[0].length - 2] = 1;
			playPan[i][playPan[0].length - 1] = 1;
		}
		for (int i = 0; i < playPan[0].length; i++) {
			playPan[playPan.length - 3][i] = 1;
			playPan[playPan.length - 2][i] = 1;
			playPan[playPan.length - 1][i] = 1;
		}

		// 将playPan与matrix传给box
		box.setPlayPanel(playPan);
		
		//因为matrix是从boxUtil得到的，所以box中matrix还是空指针,
		//即setMatrix必需有（当然可以改写程序使得matrix从box得到）
		/*
		 * 将matrix变量从MyPanel类中删除
		 */
//		box.setMatrix(matrix);

		
		// 方块线程最后开，如果构造函数开始就开线程会造成box.setMatrix()还没调用，
		// 则在box线程中matrix是空指针
		threadBox.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		// g.drawLine(5, 0, 5, 500);
		// Test90Rotate.print(matrix);
		// Test90Rotate.print(matrix);
		
		//画游戏区域
		g.setColor(new Color(230,206,230));
		g.fillRect(5, 5, this.gameX*15, this.gameY*15);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, this.gameX*15, this.gameY*15);
		g.setColor(new Color(206,162,206));
		for(int i = 0; i < this.gameX-1; i++){
			g.drawLine(20 + i*15, 6, 20 + i*15, this.gameY*15+4);
		}
		g.setColor(Color.BLACK);
		
		//画下一个方块的出现区域
		g.drawRect((this.gameX * 15)+30, 40, 60, 60);
//		g.setFont(new Font("宋体",Font.BOLD,12));
		g.drawString("下一个方块", 330, 25);
		
		//画分数
		g.drawString("分数", 330, 300);
		g.drawString(box.getScore() + "", 330, 320);
		
		if (!box.getIsGameOver()) {
			this.drawPanel(this.playPan, g);
			//画下一个方块
			drawNextMatrix(box.getNextMatrix(), g, (this.gameX*15) + 30, 40);
			//画方块
			drawMatrix(box.getNowMatrix(), g, box.getStartX(), box.getStartY());
		}else{
			g.setColor(Color.BLACK);
			g.setFont(new Font("华文彩云",Font.BOLD,28));
			g.drawString("Game Over!",70, 225);
		}
	}

	// 画背景
	public void drawPanel(int[][] playPanel, Graphics g) {

		for (int i = 0; i < playPanel.length - 3; i++)
			for (int j = 2; j < playPanel[0].length - 3; j++)
				if (playPanel[i][j] == 1){
					g.setColor(Color.CYAN);
					g.fillRect(5 + (j - 2) * 15, 5 + i * 15, 15, 15);
					g.setColor(Color.BLACK);
					g.drawRect(5 + (j - 2) * 15, 5 + i * 15, 15, 15);
				}
	}

	// 画方块
	public void drawMatrix(int[][] matrix, Graphics g, int x, int y) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] == 1){
					g.setColor(Color.CYAN);
					g.fillRect(x + j * 15, y + i * 15, 15, 15);
					g.setColor(Color.BLACK);
					g.drawRect(x + j * 15, y + i * 15, 15, 15);
				}
			}
	}
	
	//画下一个方块
	public void drawNextMatrix(int[][] nextMatrix, Graphics g, int x, int y){
		for(int i = 0; i < 4; i++)
			for(int j = 0; j <4 ;j++){
				if(nextMatrix[i][j] == 1){
					g.setColor(Color.CYAN);
					g.fillRect(x + 15*j, y + 15*i, 15, 15);
					g.setColor(Color.BLACK);
					g.drawRect(x + 15*j, y + 15*i, 15, 15);
				}
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			box.moveDown();
			this.repaint();
			break;
		case KeyEvent.VK_LEFT:
			box.moveLeft();
			this.repaint();
			break;
		case KeyEvent.VK_RIGHT:
			box.moveRight();
			this.repaint();
			break;
		case KeyEvent.VK_P:
			box.setIsPause(true);
//			System.out.println("P");
			break;
		case KeyEvent.VK_O:
			box.setIsPause(false);
			break;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			box.pressUp();
			this.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}
}
