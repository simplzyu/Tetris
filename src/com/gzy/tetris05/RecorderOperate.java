package com.gzy.tetris05;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.text.PlainDocument;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class RecorderOperate {
	
//	public static void main(String[] args) {
//		
//		int[][] playPanel = new int[20][12];
//		int[][] matrix = new int[4][4];
//		int[][] nextMatrix = new int[4][4];
////		GameRecorder gameRec = new GameRecorder("郭振宇","13000","200",playPanel,matrix,nextMatrix);
////		new RecorderOperate().saveGame(gameRec);
//		GameRecorder gameRec = new RecorderOperate().getGameRecorder("郭振宇");
//		RecorderOperate.print(gameRec.getPlayPanel());
//		System.out.println();
//		RecorderOperate.print(gameRec.getMatrix());
//		System.out.println();
//		RecorderOperate.print(gameRec.getNextMatrix());
//	}
//	
	
	String url = "src/com/gzy/tetris05/save.xml";
	
	public void saveGame(GameRecorder gameRec){
		Document document = this.getDocument();		
		//判断玩家是否存在
		if(this.existPlayer(document, gameRec)){
			this.savePlayer(document, gameRec);
		}else{
			System.out.println("no exist");
			this.addPlayer(document, gameRec);
		}
		this.update(document, url);
		
		
	}

	public boolean existPlayer(Document document, GameRecorder gameRec){
		Element root = document.getRootElement();
		List<Element> players = root.elements("player");
		int i = 0;
		for(i = 0; i < players.size(); i++){
			Element nameElement = players.get(i).element("name");
			if(nameElement.getTextTrim().equals(gameRec.getName()))
				break;
		}
		if(i == players.size()){
			return false;
		}else{
			return true;
		}
	}

	public void savePlayer(Document document, GameRecorder gameRec){
			
			List<Element> players = document.getRootElement().elements("player");
			for(int i = 0; i < players.size(); i++){
				Element player = players.get(i);
				Element e = player.element("name");
				if(e.getTextTrim().equals(gameRec.getName())){
					player.element("topScore").setText(gameRec.getTopScore());
					player.element("score").setText(gameRec.getScore());
					player.element("movX").setText(gameRec.getMovX());
					player.element("movY").setText(gameRec.getMovY());
					player.element("playPanel").setText(this.matrixToString(gameRec.getPlayPanel()));
					player.element("matrix").setText(this.matrixToString(gameRec.getMatrix()));
					player.element("nextMatrix").setText(this.matrixToString(gameRec.getNextMatrix()));
					//System.out.println(scoreElement.getText());
					break;
				}
			}
		}


	public void addPlayer(Document document, GameRecorder gameRec){
		
		//得到根结点
		Element root = document.getRootElement();
		//创建结点
		Element playerElement = DocumentHelper.createElement("player");
		Element nameElement = DocumentHelper.createElement("name");
		Element topScoreElement = DocumentHelper.createElement("topScore");
		Element scoreElement = DocumentHelper.createElement("score");
		Element movXElement = DocumentHelper.createElement("movX");
		Element movYElement = DocumentHelper.createElement("movY");
		Element playPanelElement = DocumentHelper.createElement("playPanel");
		Element matrixElement = DocumentHelper.createElement("matrix");
		Element nextMatrixElement = DocumentHelper.createElement("nextMatrix");
		
		//给结点设置内容
		nameElement.setText(gameRec.getName());
		topScoreElement.setText(gameRec.getTopScore());
		scoreElement.setText(gameRec.getScore());
		movXElement.setText(gameRec.getMovX());
		movYElement.setText(gameRec.getMovY());
		playPanelElement.setText(this.matrixToString(gameRec.getPlayPanel()));
		matrixElement.setText(this.matrixToString(gameRec.getMatrix()));
		nextMatrixElement.setText(this.matrixToString(gameRec.getNextMatrix()));
		
		//将结点加入xml
		root.add(playerElement);
		playerElement.add(nameElement);
		playerElement.add(topScoreElement);
		playerElement.add(scoreElement);
		playerElement.add(movXElement);
		playerElement.add(movYElement);
		playerElement.add(playPanelElement);
		playerElement.add(matrixElement);
		playerElement.add(nextMatrixElement);
	}

	public GameRecorder getGameRecorder(String name){
		GameRecorder gameRec = new GameRecorder();
		Document document = this.getDocument();
		List<Element> players = document.getRootElement().elements("player");
		for(int i = 0; i < players.size(); i++){
			Element player = players.get(i);
			if(player.element("name").getTextTrim().equals(name)){
				gameRec.setName(name);
				gameRec.setTopScore(player.element("topScore").getTextTrim());
				gameRec.setScore(player.element("score").getTextTrim());
				gameRec.setMovX(player.element("movX").getTextTrim());
				gameRec.setMovY(player.element("movY").getTextTrim());
				gameRec.setPlayPanel(this.StringToMatrix(player.element("playPanel").getTextTrim()));
				gameRec.setMatrix(this.StringToMatrix(player.element("matrix").getTextTrim()));
				gameRec.setNextMatrix(this.StringToMatrix(player.element("nextMatrix").getTextTrim()));
				break;
			}
		}
		return gameRec;		
	}
	
	public Document getDocument(){
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(url));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	public String getTopScore(){
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File("src/com/gzy/tetris05/save.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		List<Element> players = document.getRootElement().elements();
		for(int i = 0; i < players.size(); i++){
			Element player = players.get(i).element("name");
			if(player.getTextTrim().equals("Jack")){
				Element topScore = players.get(i).element("topScore");
				return topScore.getTextTrim();
			}
		}
		return "";
	}
	
	public String matrixToString(int[][] matrix){
		String str = matrix.length + " " + matrix[0].length + " ";
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				str += matrix[i][j] + " ";
			}
		}
		return str;
	}
	
	public int[] getStartPoint(GameRecorder gameRec){
		int[] point = new int[2];
		String[] s = this.matrixToString(gameRec.getPlayPanel()).split(" ");
		point[0] = Integer.parseInt(s[1]) - 5;//startX(列)
		point[1] = Integer.parseInt(s[0]) - 3;//startY(行)
		return point;
	}
	
	public int[][] StringToMatrix(String str){
		String[] nums = str.split(" ");
		int row = Integer.parseInt(nums[0]);
		int col = Integer.parseInt(nums[1]);
		int cursor = 2;
		int[][] matrix = new int[row][col];
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				matrix[i][j] = Integer.parseInt(nums[cursor++]);
			}
		}
		return matrix;
	}

	public void update(Document document, String url){
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(new File(url)),format);
			writer.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void print(int[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				System.out.print(matrix[i][j] + "　");
			}
			System.out.println();
		}
	}
}
