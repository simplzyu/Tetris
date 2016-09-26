/**
 * 俄罗斯方块6.0
 * 功能：
 * 1.画出方块
 * 2.方块移动
 * 3.方块能够判断是否能够继续移动,翻转
 * 4.能够生成多种方块
 * 5.能够消行
 * 6.能够结束游戏
 * 7.能够存盘并且读取
 * 8.代码进行了一些优化
 */
package com.gzy.tetris05.optimize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Tetris05 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyPanel myPanel;

	JMenuBar jMenuBar = null;
	JMenu jMenu1 = null;
	JMenuItem jMenuItemSave = null;
	JMenuItem jMenuItemContinue = null;

	public static void main(String[] args) {
		// Scanner scanner = new Scanner(System.in);
		// System.out.println("行数：");
		// int row = scanner.nextInt();
		// System.out.println("列数：");
		// int col = scanner.nextInt();
		new Tetris05(20, 12);
	}

	public Tetris05(int row, int col) {
		myPanel = new MyPanel(row, col, this);
		jMenuBar = new JMenuBar();
		jMenu1 = new JMenu("File(F)");
		jMenu1.setMnemonic('F');
		jMenuItemSave = new JMenuItem("Save Game");
		jMenuItemContinue = new JMenuItem("Continue Game");
		// jButton = new JButton("暂停");

		this.setJMenuBar(jMenuBar);
		jMenuBar.add(jMenu1);
		jMenu1.add(jMenuItemSave);
		jMenu1.add(jMenuItemContinue);

		this.add(myPanel);

		// 注册监听器
		this.addKeyListener(myPanel);
		DogListener dog = new DogListener(this);
		jMenuItemSave.addActionListener(dog);
		jMenuItemContinue.addActionListener(dog);

		// this.addWindowFocusListener(new MyWindowListener(this));
		this.setTitle("俄罗斯方块6.0");
		this.setSize(col * 15 + 130, row * 15 + 72);
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.requestFocusInWindow();

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

	JButton pauseButton = null;
	JButton startButton = null;

	int gameX = 14;
	int gameY = 30;
	int topScore = 0;

	BoxUtil boxUtil = new BoxUtil();
	RecorderOperate recorderOperate = null;
	GameRecorder gameRec = null;

	// playPan[33][25] 30*20的俄罗斯方块，但是每边需要多出三格来辅助判断（左边只要多出两格即可)
	int[][] playPanel;
	Box box = null;
	Thread threadBox = null;
	String name = null;

	public MyPanel(int gameY, int gameX, Tetris05 tetris) {
		// 初始化matrix
		this.gameX = gameX;
		this.gameY = gameY;

		// threadBox = new Thread(box);这之前一定要有具体的box（即box!=null)
		box = new Box((this.gameX / 2 - 2) * 15 + 5, 5);
		threadBox = new Thread(box);
		

		// 设置方块初始位置
		// box.setStartX((this.gameX/2-1)*15 + 5);
		// box.setStartY(5);

		this.pauseButton = new JButton("暂停");
		this.startButton = new JButton("开始");

		this.setLayout(null);
		this.add(this.pauseButton);
		this.add(this.startButton);
		this.pauseButton.setBounds((this.gameX * 15) + 30, 160, 60, 30);
		this.startButton.setBounds((this.gameX * 15) + 30, 120, 60, 30);

		// 注册监听器
		MyButtonListener myButtonListener = new MyButtonListener(this, tetris);
		this.pauseButton.addActionListener(myButtonListener);
		this.startButton.addActionListener(myButtonListener);

		playPanel = new int[this.gameY + 3][this.gameX + 5];
		// 此处的matrix 是从boxUtil中得到的
		// box.matrix = boxUtil.getMatrix();

		for (int i = 0; i < playPanel.length; i++) {
			playPanel[i][0] = 1;
			playPanel[i][1] = 1;
			playPanel[i][playPanel[0].length - 3] = 1;
			playPanel[i][playPanel[0].length - 2] = 1;
			playPanel[i][playPanel[0].length - 1] = 1;
		}
		for (int i = 0; i < playPanel[0].length; i++) {
			playPanel[playPanel.length - 3][i] = 1;
			playPanel[playPanel.length - 2][i] = 1;
			playPanel[playPanel.length - 1][i] = 1;
		}

		this.name = "Tom";
		recorderOperate = new RecorderOperate();
		if(recorderOperate.existPlayer(this.name)){
			gameRec = recorderOperate.getGameRecorder(this.name);
			this.topScore = Integer.parseInt(gameRec.getTopScore());
		}else{
			gameRec = new GameRecorder(this.name,"0","0",this.playPanel, new Matrix(),new Matrix());
			this.topScore = 0;
		}
		
		// 将playPanel传给box
		box.setPlayPanel(playPanel);
		threadBox.start();
	}

	public void paint(Graphics g) {

		super.paint(g);
		// g.drawLine(5, 0, 5, 500);
		// Test90Rotate.print(matrix);
		// Test90Rotate.print(matrix);

		// 画游戏区域
		g.setColor(new Color(230, 206, 230));
		g.fillRect(5, 5, this.gameX * 15, this.gameY * 15);
		g.setColor(Color.BLACK);
		g.drawRect(5, 5, this.gameX * 15, this.gameY * 15);
		g.setColor(new Color(206, 162, 206));
		for (int i = 0; i < this.gameX - 1; i++) {
			g.drawLine(20 + i * 15, 6, 20 + i * 15, this.gameY * 15 + 4);
		}
		g.setColor(Color.BLACK);

		// 画下一个方块的出现区域
		g.drawRect((this.gameX * 15) + 30, 40, 60, 60);
		// g.setFont(new Font("宋体",Font.BOLD,12));
		g.drawString("下一个方块", (this.gameX * 15) + 30, 25);

		// 画最高分
		g.drawString("最高分", (this.gameX * 15) + 30, (this.gameY * 15) - 80);
		g.drawString(String.valueOf(this.topScore), (this.gameX * 15) + 30,
				(this.gameY * 15) - 60);

		// 画分数
		g.drawString("分数", (this.gameX * 15) + 30, (this.gameY * 15) - 30);
		g.drawString(box.getScore() + "", (this.gameX * 15) + 30,
				(this.gameY * 15) - 10);

		// 更新最高分
		if (this.topScore < box.getScore()) {
			this.topScore = box.getScore();
		}

		// 判断是游戏否开始
		if (box.getIsBegin()) {
			if (!box.getIsGameOver()) {
				this.drawPanel(this.playPanel, g);
				// 画下一个方块
				drawMatrix(box.nextMatrix.matrix, g, (this.gameX * 15) + 30, 40);
				// 画方块
				drawMatrix(box.curMatrix.matrix, g, box.curMatrix.getMovX(), box.curMatrix.getMovY());
			} else {
				// recorderOperate.save(box.getScore() + "");
				g.setColor(Color.BLACK);
				g.setFont(new Font("华文彩云", Font.BOLD, this.gameX * 2));
				g.drawString("Game Over!", (this.gameX * 15) / 6,
						(this.gameY / 2) * 15);
				recorderOperate.setTopScore(this.gameRec,this.topScore + "");
			}
		}
	}

	// 画背景
	public void drawPanel(int[][] playPanel, Graphics g) {
//		RecorderOperate.print(playPanel);
		for (int i = 0; i < playPanel.length - 3; i++)
			for (int j = 2; j < playPanel[0].length - 3; j++)
				if (playPanel[i][j] == 1) {
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
				if (matrix[i][j] == 1) {
					g.setColor(Color.CYAN);
					g.fillRect(x + j * 15, y + i * 15, 15, 15);
					g.setColor(Color.BLACK);
					g.drawRect(x + j * 15, y + i * 15, 15, 15);
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
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}
}

class MyButtonListener implements ActionListener {

	private MyPanel panel = null;
	private Tetris05 tetris = null;

	public MyButtonListener(MyPanel panel, Tetris05 tetris) {
		this.panel = panel;
		this.tetris = tetris;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("暂停")) {
			this.panel.box.setIsPause(true);
			this.panel.pauseButton.setText("继续");
			tetris.requestFocusInWindow();
		} else if (e.getActionCommand().equals("继续")) {
			this.panel.box.setIsPause(false);
			this.panel.pauseButton.setText("暂停");
			tetris.requestFocusInWindow();
		} else {
			this.panel.box.setIsBegin(true);
			tetris.requestFocus();
		}
	}
}

class DogListener implements ActionListener {

	Tetris05 tetris = null;
	MyPanel myPanel = null;

	public DogListener(Tetris05 tetris) {
		this.tetris = tetris;
		this.myPanel = tetris.myPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Save Game")) {
			myPanel.gameRec.setGameRecorder("Tom", myPanel.topScore + "",
					myPanel.box.getScore() + "",myPanel.playPanel,myPanel.box.curMatrix, myPanel.box.nextMatrix);
			myPanel.recorderOperate.saveGame(myPanel.gameRec);

		} else if (e.getActionCommand().equals("Continue Game")) {
			BoxUtil boxUtil = new BoxUtil();
			RecorderOperate recorderOperate = new RecorderOperate();
/*			
//----------方法1.将此局的GameRecorder对象传入，在以前地址上修改其内容-----------------------------
			GameRecorder gameRec = new GameRecorder("郭振宇", myPanel.topScore + "",
					myPanel.box.getScore() + "",myPanel.playPanel,myPanel.box.curMatrix, myPanel.box.nextMatrix);
			recorderOperate.getGameRecorder(gameRec,"郭振宇");
//-------------------------------------------------------------------------------
*/		

			
//----------方法2.Matrix类不使用以前的地址空间,则只需将curMatrix的next 指向 nextMatrix-------------
			//因为此时返回的是新的curMatrix与nextMatrix
			GameRecorder gameRec = recorderOperate.getGameRecorder("郭振宇");
			
			//恢复现在的方块
			//当恢复游戏时，curMatrix 与 nextMatrix 的地址已经改变
			//方法2.将getGameRecorder()再传递另一个参数——此局的gameRec  next需要重新指
			myPanel.box.curMatrix = (gameRec.getMatrixs())[0];
			myPanel.box.nextMatrix = (gameRec.getMatrixs())[1];
			myPanel.box.curMatrix.next = myPanel.box.nextMatrix;
//---------------------------------------------------------------------------------
			
			//恢复游戏面板
			boxUtil.copyMatrix(myPanel.playPanel, gameRec.getPlayPanel());
			
			int[] startPoint = recorderOperate.getStartPoint(gameRec);
			
//			boxUtil.copyMatrix(myPanel.box.nowMatrix, gameRec.getMatrix());
//			boxUtil.copyMatrix(myPanel.box.nextMatrix, gameRec.getNextMatrix());
//			myPanel.box.setMovX(Integer.parseInt(gameRec.getMovX()));
//			myPanel.box.setMovY(Integer.parseInt(gameRec.getMovY()));	
			
			myPanel.gameX = startPoint[0];
			myPanel.gameY = startPoint[1];
			myPanel.box.setStartX((myPanel.gameX / 2 - 2) * 15 + 5);
			myPanel.box.setStartY(5);
			myPanel.box.setScore(Integer.parseInt(gameRec.getScore()));
			myPanel.topScore = Integer.parseInt(gameRec.getTopScore());
			
			
			myPanel.box.setIsBegin(true);
			myPanel.repaint();
		}

	}

}