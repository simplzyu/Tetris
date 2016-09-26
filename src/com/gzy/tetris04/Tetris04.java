/**
 * ����˹����4.0
 * ���ܣ�
 * 1.��������
 * 2.�����ƶ�
 * 3.�����ܹ��ж��Ƿ��ܹ������ƶ�,��ת
 * 4.�ܹ����ɶ��ַ���
 * 5.�ܹ�����
 * 6.�ܹ�������Ϸ
 */
package com.gzy.tetris04;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;

public class Tetris04 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyPanel myPanel;

	// JButton jButton;

	public static void main(String[] args) {
		// Scanner scanner = new Scanner(System.in);
		// System.out.println("������");
		// int row = scanner.nextInt();
		// System.out.println("������");
		// int col = scanner.nextInt();
		new Tetris04(20, 12);
	}

	public Tetris04(int row, int col) {
		myPanel = new MyPanel(row, col, this);
		// jButton = new JButton("��ͣ");

		this.add(myPanel);

		// ע�������
		this.addKeyListener(myPanel);
		// this.addWindowFocusListener(new MyWindowListener(this));
		this.setTitle("����˹����4.0");
		this.setSize(col * 15 + 130, row * 15 + 50);
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.requestFocusInWindow();

		// �����߳�
		Thread thread = new Thread(myPanel);
		thread.start();
	}
}

class MyPanel extends JPanel implements KeyListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton pauseButton = null;
	JButton startButton = null;

	int gameX = 14;
	int gameY = 30;
	int highestScore = 0;

	BoxUtil boxUtil = new BoxUtil();
	SaveDocument saveDocument = new SaveDocument();

	// playPan[33][25] 30*20�Ķ���˹���飬����ÿ����Ҫ��������������жϣ����ֻҪ������񼴿�)
	int[][] playPan;
	Box box = null;
	Thread threadBox = null; 

	public MyPanel(int gameY, int gameX, Tetris04 tetris) {
		// ��ʼ��matrix
		this.gameX = gameX;
		this.gameY = gameY;
		
		//threadBox = new Thread(box);��֮ǰһ��Ҫ�о����box����box!=null)
		box = new Box((this.gameX / 2 - 2) * 15 + 5, 5);
		threadBox = new Thread(box);
		this.highestScore = Integer.parseInt(this.saveDocument.getScore());
		
		// ���÷����ʼλ��
		// box.setStartX((this.gameX/2-1)*15 + 5);
		// box.setStartY(5);

		this.pauseButton = new JButton("��ͣ");
		this.startButton = new JButton("��ʼ");

		this.setLayout(null);
		this.add(this.pauseButton);
		this.add(this.startButton);
		this.pauseButton.setBounds((this.gameX * 15) + 30, 160, 60, 30);
		this.startButton.setBounds((this.gameX * 15) + 30, 120, 60, 30);

		// ע�������
		MyButtonListener myButtonListener = new MyButtonListener(this, tetris);
		this.pauseButton.addActionListener(myButtonListener);
		this.startButton.addActionListener(myButtonListener);

		playPan = new int[this.gameY + 3][this.gameX + 5];
		// �˴���matrix �Ǵ�boxUtil�еõ���
		// box.matrix = boxUtil.getMatrix();

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

		// ��playPan��matrix����box
		box.setPlayPanel(playPan);

		// ��Ϊmatrix�Ǵ�boxUtil�õ��ģ�����box��matrix���ǿ�ָ��,
		// ��setMatrix�����У���Ȼ���Ը�д����ʹ��matrix��box�õ���
		/*
		 * ��matrix������MyPanel����ɾ��
		 */
		// box.setMatrix(matrix);

		// �����߳���󿪣�������캯����ʼ�Ϳ��̻߳����box.setMatrix()��û���ã�
		
		// ����box�߳���matrix�ǿ�ָ��
		threadBox.start();
	}

	public void paint(Graphics g) {

		super.paint(g);
		// g.drawLine(5, 0, 5, 500);
		// Test90Rotate.print(matrix);
		// Test90Rotate.print(matrix);

		// ����Ϸ����
		g.setColor(new Color(230, 206, 230));
		g.fillRect(5, 5, this.gameX * 15, this.gameY * 15);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, this.gameX * 15, this.gameY * 15);
		g.setColor(new Color(206, 162, 206));
		for (int i = 0; i < this.gameX - 1; i++) {
			g.drawLine(20 + i * 15, 6, 20 + i * 15, this.gameY * 15 + 4);
		}
		g.setColor(Color.BLACK);

		// ����һ������ĳ�������
		g.drawRect((this.gameX * 15) + 30, 40, 60, 60);
		// g.setFont(new Font("����",Font.BOLD,12));
		g.drawString("��һ������", (this.gameX * 15) + 30, 25);
		
		//����߷�
		g.drawString("��߷�",(this.gameX * 15) + 30, (this.gameY * 15) - 80);
		g.drawString(String.valueOf(this.highestScore), (this.gameX * 15) + 30, (this.gameY * 15) - 60);

		// ������
		g.drawString("����", (this.gameX * 15) + 30, (this.gameY * 15) - 30);
		g.drawString(box.getScore() + "", (this.gameX * 15) + 30,
				(this.gameY * 15) - 10);
		
		//������߷�
		if (this.highestScore < box.getScore()) {
			this.highestScore = box.getScore();
			try {
				saveDocument.saveScore(String.valueOf(box.getScore()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//�ж�����Ϸ��ʼ
		if (box.getIsBegin()) {
			if (!box.getIsGameOver()) {
				this.drawPanel(this.playPan, g);
				// ����һ������
				drawNextMatrix(box.getNextMatrix(), g, (this.gameX * 15) + 30,
						40);
				// ������
				drawMatrix(box.getNowMatrix(), g, box.getMovX(), box.getMovY());
			} else {
				// recorderOperate.save(box.getScore() + "");
				g.setColor(Color.BLACK);
				g.setFont(new Font("���Ĳ���", Font.BOLD, this.gameX*2));
				g.drawString("Game Over!", (this.gameX*15)/6, (this.gameY/2)*15);
			}
		}
	}

	// ������
	public void drawPanel(int[][] playPanel, Graphics g) {

		for (int i = 0; i < playPanel.length - 3; i++)
			for (int j = 2; j < playPanel[0].length - 3; j++)
				if (playPanel[i][j] == 1) {
					g.setColor(Color.CYAN);
					g.fillRect(5 + (j - 2) * 15, 5 + i * 15, 15, 15);
					g.setColor(Color.BLACK);
					g.drawRect(5 + (j - 2) * 15, 5 + i * 15, 15, 15);
				}
	}

	// ������
	public void drawMatrix(int[][] matrix, Graphics g, int x, int y) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] == 1) {
					g.setColor(Color.CYAN);
					g.fillRect(x + j * 15, y + i * 15, 15, 15);
					g.setColor(Color.BLACK);
					g.drawRect(x + j * 15, y + i * 15, 15, 15);
				}
			}
	}

	// ����һ������
	public void drawNextMatrix(int[][] nextMatrix, Graphics g, int x, int y) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if (nextMatrix[i][j] == 1) {
					g.setColor(Color.CYAN);
					g.fillRect(x + 15 * j, y + 15 * i, 15, 15);
					g.setColor(Color.BLACK);
					g.drawRect(x + 15 * j, y + 15 * i, 15, 15);
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

class MyButtonListener implements ActionListener {

	private MyPanel panel = null;
	private Tetris04 tetris = null;

	public MyButtonListener(MyPanel panel, Tetris04 tetris) {
		this.panel = panel;
		this.tetris = tetris;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("��ͣ")) {
			this.panel.box.setIsPause(true);
			this.panel.pauseButton.setText("����");
			tetris.requestFocusInWindow();
		} else if (e.getActionCommand().equals("����")) {
			this.panel.box.setIsPause(false);
			this.panel.pauseButton.setText("��ͣ");
			tetris.requestFocusInWindow();
		} else {
			this.panel.box.setIsBegin(true);
			tetris.requestFocus();
		}
	}
}