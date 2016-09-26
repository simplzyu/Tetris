package com.gzy.tetris04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class SaveDocument {
	
	public void saveScore(String s) throws IOException{
		
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File("src/com/gzy/tetris04/save.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		List<Element> persons = document.getRootElement().elements("person");
		for(int i = 0; i < persons.size(); i++){
			Element e = persons.get(i).element("name");
			if(e.getTextTrim().equals("Jack")){
				Element person = persons.get(i);
				Element scoreElement = person.element("score");
				scoreElement.setText(s);
//				System.out.println(scoreElement.getText());
				break;
			}
		}
	
		OutputFormat output = OutputFormat.createPrettyPrint();
		output.setEncoding("utf-8");
		
		XMLWriter writer = new XMLWriter(new FileWriter("src/com/gzy/tetris04/save.xml"));
		writer.write(document);
		writer.close();
	}
	
	public String getScore(){
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File("src/com/gzy/tetris04/save.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		List<Element> persons = document.getRootElement().elements();
		for(int i = 0; i < persons.size(); i++){
			Element person = persons.get(i).element("name");
			if(person.getTextTrim().equals("Jack")){
				Element score = persons.get(i).element("score");
				return score.getTextTrim();
			}
		}
		return "";
	}
}
