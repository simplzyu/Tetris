package com.gzy.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;

public class TestArrays extends JFrame implements KeyListener{
	public int[][] a = new int[500][500];
	static boolean isClose = false;
	
	public static void main(String[] args) throws IOException {
	
//		FileReader fr;
//		BufferedReader br;
//		try {
//			fr = new FileReader(file);
//			br = new BufferedReader(fr);
//			try {
//				System.out.println(br.readLine());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			br.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

//		pw.close();
		
		TestArrays ta = new TestArrays();
		ta.addKeyListener(ta);
	}
	
	public TestArrays(){
		int t = 1;
		for(int i = 0; i < 500; i++)
			for(int j = 0; j < 500; j++){
				this.a[i][j] = t++;
			}
		this.setSize(300,300);
		this.setLocation(600,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		File file = new File("D:\\out.txt");
		try {
			FileWriter fw = new FileWriter(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file);
			pw.print("\n");
			pw.print("\n");
			for(int i = 0; i < 500; i++){
				for(int j =0; j <500; j++){
					pw.print(a[i][j] + " ");
				}
				pw.print("\n");
			}
			pw.close();
			System.out.println("end");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally{
			pw.close();
			System.out.println("end");
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			
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

}
