package com.gzy.tetris05.optimize;

public class GameRecorder {
	private String name = null;
	private String topScore = null;
	private String score = null;
	private int[][]	playPanel;
	private Matrix[] matrixs ;
	
	public GameRecorder(){
//		matrixs = new Matrix[2];
	}

	public GameRecorder(String name, String topScore, String score,int[][] playPanel, Matrix curMatrix, Matrix nextMatrix) {
		super();
		this.name = name;
		this.topScore = topScore;
		this.score = score;
		this.playPanel = playPanel;
		matrixs = new Matrix[2];
		matrixs[0] = curMatrix;
		matrixs[1] = nextMatrix;
	}

	public void setGameRecorder(String name, String topScore, String score,
			 int[][] playPanel, Matrix curMatrix, Matrix nextMatrix){
		this.name = name;
		this.topScore = topScore;
		this.score = score;
		this.playPanel = playPanel;
		matrixs[0] = curMatrix;
		matrixs[1] = nextMatrix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopScore() {
		return topScore;
	}

	public void setTopScore(String topScore) {
		this.topScore = topScore;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public int[][] getPlayPanel() {
		return playPanel;
	}

	public void setPlayPanel(int[][] playPanel) {
		this.playPanel = playPanel;
	}

	public Matrix[] getMatrixs() {
		return matrixs;
	}

	public void setMatrixs(Matrix[] matrixs) {
		this.matrixs = matrixs;
	}

	
	
	
}
