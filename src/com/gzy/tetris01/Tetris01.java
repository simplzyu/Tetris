package com.gzy.tetris01;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import com.gzy.test.Test90Rotate;

public class Tetris01 extends JFrame {

	MyPanel myPanel;

	public static void main(String[] args) {
		new Tetris01();
	}

	public Tetris01() {
		myPanel = new MyPanel();

		this.add(myPanel);

		// ×¢²á¼àÌýÆ÷
		this.addKeyListener(myPanel);

		this.setSize(500, 500);
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//¿ªÆôÏß³Ì
		Thread thread = new Thread(myPanel);
		thread.start();
	}
}

class MyPanel extends JPanel implements KeyListener,Runnable {

	int[][] matrix = { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 },
			{ 0, 0, 0, 0 } };
	
	int[][] playPan = new int[32][22];
	Box box = null;

	public MyPanel() {
		box = new Box();
		Thread threadBox = new Thread(box);
		threadBox.start();
		
		
		for(int i = 0; i < playPan.length; i++){
//			playPan[i][0] = 1;
			playPan[i][playPan[0].length - 2] = 1;
			playPan[i][playPan[0].length - 1] = 1;
		}
		for(int i = 0; i < playPan[0].length; i++){
			playPan[playPan.length-2][i] = 1;
			playPan[playPan.length-1][i] = 1;
		}
		box.setPlayPanel(playPan);
		box.setMatrix(matrix);
	}

	public void paint(Graphics g) {
		super.paint(g);
		// g.drawLine(5, 0, 5, 500);
		// Test90Rotate.print(matrix);
		// Test90Rotate.print(matrix);
		g.drawRect(5, 5, 300, 450);
		drawMatrix(matrix, g, box.getStartX(), box.getStartY());
//		System.out.println("\n");
	}

	public void drawMatrix(int[][] matrix, Graphics g, int x, int y) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] == 1)
					g.drawRect(x + j * 15, y + i * 15, 15, 15);
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			box.startY += 15;
			this.repaint();
			break;
		case KeyEvent.VK_LEFT:
			box.startX -= 15;
			this.repaint();
			break;
		case KeyEvent.VK_RIGHT:
			box.startX += 15;
			this.repaint();
			break;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			BoxUtil.rotateMatrix(matrix);
			this.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.repaint();
		}
	}
}
