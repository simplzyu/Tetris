package com.gzy.tetris05.optimize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.print.Doc;
import javax.swing.text.PlainDocument;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class RecorderOperate {

	
	public static void main(String[] args) {

		int[][] playPanel = new int[23][17];
		Matrix matrix = new Matrix(125, 5);
		Matrix nextMatrix = new Matrix(125, 5);
//		new RecorderOperate().getGameRecorder("Tom");
	}

	String url = "src/com/gzy/tetris05/optimize/save.xml";
	Document document = null;
	
	public RecorderOperate(){
		document = this.getDocument();
	}

	public void saveGame(GameRecorder gameRec) {
		// 判断玩家是否存在
		if (this.existPlayer(gameRec.getName())) {
			this.savePlayer(document, gameRec);
		} else {
			System.out.println("no exist");
			this.addPlayer(document, gameRec);
		}
		this.update(document, url);
	}

	public boolean existPlayer(String name) {
		Element root = document.getRootElement();
		List<Element> players = root.elements("player");
		int i = 0;
		for (i = 0; i < players.size(); i++) {
			Element nameElement = players.get(i).element("name");
			if (nameElement.getTextTrim().equals(name))
				break;
		}
		if (i == players.size()) {
			return false;
		} else {
			return true;
		}
	}

	public void savePlayer(Document document, GameRecorder gameRec) {

		List<Element> players = document.getRootElement().elements("player");
		for (int i = 0; i < players.size(); i++) {
			Element player = players.get(i);
			Element e = player.element("name");
			if (e.getTextTrim().equals(gameRec.getName())) {
				player.element("topScore").setText(gameRec.getTopScore());
				player.element("score").setText(gameRec.getScore());
				player.element("playPanel").setText(
						this.matrixToString(gameRec.getPlayPanel()));
				List<Element> matrixs = player.elements("matrixs");
				for (int j = 0; j < matrixs.size(); j++) {
					Element matrix = matrixs.get(j);
					matrix.element("movX").setText(
							(gameRec.getMatrixs())[j].getMovX() + "");
					matrix.element("movY").setText(
							(gameRec.getMatrixs())[j].getMovY() + "");
					matrix.element("type").setText(
							(gameRec.getMatrixs())[j].getType() + "");
					matrix.element("changeType").setText(
							(gameRec.getMatrixs())[j].getChangeType() + "");
					matrix.element("matrix")
							.setText(
									this
											.matrixToString((gameRec
													.getMatrixs())[j].matrix));
				}
				// System.out.println(scoreElement.getText());
				break;
			}
		}
	}

	public void addPlayer(Document document, GameRecorder gameRec) {

		// 得到根结点
		Element root = document.getRootElement();
		// 创建结点
		Element playerElement = DocumentHelper.createElement("player");
		Element nameElement = DocumentHelper.createElement("name");
		Element topScoreElement = DocumentHelper.createElement("topScore");
		Element scoreElement = DocumentHelper.createElement("score");

		Element[] matrixsElement = new Element[2];

		// 给结点设置内容并将其加入xml
		nameElement.setText(gameRec.getName());
		topScoreElement.setText(gameRec.getTopScore());
		scoreElement.setText(gameRec.getScore());
		// 将结点加入xml
		root.add(playerElement);
		playerElement.add(nameElement);
		playerElement.add(topScoreElement);
		playerElement.add(scoreElement);

		// 创建matrixs结点，给其子结点设置内容并加入xml
		for (int i = 0; i < matrixsElement.length; i++) {
			matrixsElement[i] = DocumentHelper.createElement("matrixs");
			playerElement.add(matrixsElement[i]);
			Element movXElement = DocumentHelper.createElement("movX");
			Element movYElement = DocumentHelper.createElement("movY");
			Element typeElement = DocumentHelper.createElement("type");
			Element changeTypeElement = DocumentHelper
					.createElement("changeType");
			Element matrixElement = DocumentHelper.createElement("matrix");

			Matrix m = (gameRec.getMatrixs())[i];
			movXElement.setText(m.getMovX() + "");
			movYElement.setText(m.getMovY() + "");
			typeElement.setText(m.getType() + "");
			changeTypeElement.setText(m.getChangeType() + "");
			matrixElement.setText(this.matrixToString(m.matrix));

			matrixsElement[i].add(movXElement);
			matrixsElement[i].add(movYElement);
			matrixsElement[i].add(typeElement);
			matrixsElement[i].add(changeTypeElement);
			matrixsElement[i].add(matrixElement);
		}

		Element playPanelElement = DocumentHelper.createElement("playPanel");
		playPanelElement.setText(this.matrixToString(gameRec.getPlayPanel()));
		playerElement.add(playPanelElement);
	}

	public GameRecorder getGameRecorder(String name) {
		GameRecorder gameRec = new GameRecorder();
		Document document = this.getDocument();
		List<Element> players = document.getRootElement().elements("player");
		for (int i = 0; i < players.size(); i++) {
			Element player = players.get(i);
			if (player.element("name").getTextTrim().equals(name)) {
				gameRec.setName(name);
				gameRec.setTopScore(player.element("topScore").getTextTrim());
				gameRec.setScore(player.element("score").getTextTrim());
				List<Element> matrixsEle = player.elements("matrixs");
				Matrix[] matrixs = new Matrix[2];
				for (int j = 0; j < matrixsEle.size(); j++) {
					Element e = matrixsEle.get(j);
					matrixs[j] = new Matrix();
					Matrix m = matrixs[j];// 必须new 创建一个，否则为空指针
					m.setMovX(Integer.parseInt(e.element("movX")
									.getTextTrim()));
					m.setMovY(Integer.parseInt(e.element("movY")
									.getTextTrim()));
					m.setType(Integer.parseInt(e.element("type")
									.getTextTrim()));
					m.setChangeType(Integer.parseInt(e.element("changeType")
							.getTextTrim()));
					BoxUtil.copyMatrix(m.matrix, this.StringToMatrix(e.element(
							"matrix").getTextTrim()));
				}
				gameRec.setMatrixs(matrixs);
				gameRec.setPlayPanel(this.StringToMatrix(player.element(
						"playPanel").getTextTrim()));
				break;
			}
		}
		return gameRec;
	}
	
	public GameRecorder getGameRecorder(GameRecorder gameRec, String name) {
		Document document = this.getDocument();
		List<Element> players = document.getRootElement().elements("player");
		for (int i = 0; i < players.size(); i++) {
			Element player = players.get(i);
			if (player.element("name").getTextTrim().equals(name)) {
				gameRec.setName(name);
				gameRec.setTopScore(player.element("topScore").getTextTrim());
				gameRec.setScore(player.element("score").getTextTrim());
				List<Element> matrixsEle = player.elements("matrixs");
				Matrix[] matrixs = gameRec.getMatrixs();
				for (int j = 0; j < matrixsEle.size(); j++) {
					Element e = matrixsEle.get(j);
					Matrix m = matrixs[j];// 必须new 创建一个，否则为空指针
					m.setMovX(Integer.parseInt(e.element("movX")
									.getTextTrim()));
					m.setMovY(Integer.parseInt(e.element("movY")
									.getTextTrim()));
					m.setType(Integer.parseInt(e.element("type")
									.getTextTrim()));
					m.setChangeType(Integer.parseInt(e.element("changeType")
							.getTextTrim()));
					BoxUtil.copyMatrix(m.matrix, this.StringToMatrix(e.element(
							"matrix").getTextTrim()));
				}
				gameRec.setPlayPanel(this.StringToMatrix(player.element(
						"playPanel").getTextTrim()));
				break;
			}
		}
		return gameRec;
	}

	public Document getDocument() {
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

	public String getTopScore(String name) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(url));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		List<Element> players = document.getRootElement().elements();
		for (int i = 0; i < players.size(); i++) {
			Element player = players.get(i).element("name");
			if (player.getTextTrim().equals(name)) {
				Element topScore = players.get(i).element("topScore");
				return topScore.getTextTrim();
			}
		}
		return "0";
	}
	
	public void setTopScore(GameRecorder gameRec, String topScore){
		if(this.existPlayer(gameRec.getName())){
			List<Element> players = document.getRootElement().elements("player");
			int i = 0;
			for(i = 0; i < players.size(); i++){
				Element nameElement = players.get(i).element("name");
				if(nameElement.getTextTrim().equals(gameRec.getName())){
					nameElement.getParent().element("topScore").setText(topScore);
					break;
				}
			}
		}else{
			gameRec.setTopScore(topScore);
			this.addPlayer(document, gameRec);
			update(document, url);
		}
	}

	public String matrixToString(int[][] matrix) {
		String str = matrix.length + " " + matrix[0].length + " ";
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				str += matrix[i][j] + " ";
			}
		}
		return str;
	}

	public int[] getStartPoint(GameRecorder gameRec) {
		int[] point = new int[2];
		String[] s = this.matrixToString(gameRec.getPlayPanel()).split(" ");
		point[0] = Integer.parseInt(s[1]) - 5;// startX(列)
		point[1] = Integer.parseInt(s[0]) - 3;// startY(行)
		return point;
	}

	public int[][] StringToMatrix(String str) {
		String[] nums = str.split(" ");
		int row = Integer.parseInt(nums[0]);
		int col = Integer.parseInt(nums[1]);
		int cursor = 2;
		int[][] matrix = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				matrix[i][j] = Integer.parseInt(nums[cursor++]);
			}
		}
		return matrix;
	}

	public void update(Document document, String url) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");

		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(new File(url)), format);
			writer.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void print(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + "　");
			}
			System.out.println();
		}
	}
}
