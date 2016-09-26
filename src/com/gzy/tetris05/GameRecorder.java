package com.gzy.tetris05;

public class GameRecorder {
	private String name = null;
	private String topScore = null;
	private String score = null;
	private String movX = null;
	private String movY = null;
	private int[][]	playPanel;
	private int[][] matrix;
	private int[][] nextMatrix;
	
	public GameRecorder(){
		
	}

	public GameRecorder(String name, String topScore, String score, String movX,
			String movY,int[][] playPanel, int[][] matrix, int[][] nextMatrix) {
		super();
		this.name = name;
		this.topScore = topScore;
		this.score = score;
		this.movX = movX;
		this.movY = movY;
		this.playPanel = playPanel;
		this.matrix = matrix;
		this.nextMatrix = nextMatrix;
	}

	public String getMovX() {
		return movX;
	}

	public void setMovX(String movX) {
		this.movX = movX;
	}

	public String getMovY() {
		return movY;
	}

	public void setMovY(String movY) {
		this.movY = movY;
	}

	public void setGameRecorder(String name, String topScore, String score,
			String movX, String movY, int[][] playPanel, int[][] matrix, int[][] nextMatrix){
		this.name = name;
		this.topScore = topScore;
		this.score = score;
		this.movX = movX;
		this.movY = movY;
		this.playPanel = playPanel;
		this.matrix = matrix;
		this.nextMatrix = nextMatrix;
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

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int[][] getNextMatrix() {
		return nextMatrix;
	}

	public void setNextMatrix(int[][] nextMatrix) {
		this.nextMatrix = nextMatrix;
	}
	
	
}
