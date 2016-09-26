/**
 * ����˹����2.0
 * ���ܣ�
 * 1.��������
 * 2.�����ƶ�
 * 3.�����ܹ��ж��Ƿ��ܹ������ƶ�,��ת
 * 4.�ܹ����ɶ��ַ���
 * 5.�ܹ�����
 */
package com.gzy.tetris02;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Tetris02 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyPanel myPanel;

	public static void main(String[] args) {
		new Tetris02();
	}

	public Tetris02() {
		myPanel = new MyPanel();

		this.add(myPanel);

		// ע�������
		this.addKeyListener(myPanel);
		
		this.setTitle("����˹����2.0");
		this.setSize(400, 500);
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//�����߳�
		Thread thread = new Thread(myPanel);
		thread.start();
	}
}

class MyPanel extends JPanel implements KeyListener,Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int[][] matrix ;
	BoxUtil boxUtil = new BoxUtil();
	
	//30*20�Ķ���˹���飬����ÿ����Ҫ��������������жϣ����ֻҪ���һ�񼴿�)
	int[][] playPan = new int[33][25];	Box box = null;

	public MyPanel() {
		box = new Box();
		//��ʼ��matrix
		matrix = boxUtil.getMatrix();
		
		for(int i = 0; i < playPan.length; i++){
			playPan[i][0] = 1;
			playPan[i][1] = 1;
			playPan[i][playPan[0].length - 3] = 1;
			playPan[i][playPan[0].length - 2] = 1;
			playPan[i][playPan[0].length - 1] = 1;
		}
		for(int i = 0; i < playPan[0].length; i++){
			playPan[playPan.length-3][i] = 1;
			playPan[playPan.length-2][i] = 1;
			playPan[playPan.length-1][i] = 1;
		}
		
		//��playPan��matrix����box
		box.setPlayPanel(playPan);
		box.setMatrix(matrix);
		
		//�����߳���󿪣�������캯����ʼ�Ϳ��̻߳����box.setMatrix()��û���ã�
		//����box�߳���matrix�ǿ�ָ��
		Thread threadBox = new Thread(box);
		threadBox.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		// g.drawLine(5, 0, 5, 500);
		// Test90Rotate.print(matrix);
		// Test90Rotate.print(matrix);
		g.drawRect(5, 5, 300, 450);
		this.drawPanel(this.playPan, g);
		drawMatrix(box.getMatrix(), g, box.getStartX(), box.getStartY());
//		System.out.println("\n");
	}
	
	//������
	public void drawPanel(int[][] playPanel, Graphics g){
		for(int i = 0; i < playPanel.length-3; i++)
			for(int j = 2; j < playPanel[0].length-3; j++)
				if(playPanel[i][j] == 1)
					g.drawRect(5+(j-2)*15, 5+i*15, 15, 15);
	}

	//������
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
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			box.pressUp();
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
