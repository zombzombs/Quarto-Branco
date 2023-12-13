package com.tcc.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.tcc.entities.Entity;
import com.tcc.world.World;

public class Menu {
	
	public String[] options = {"Novo Jogo","Carregar Jogo","Sair","Continuar", "Salvar"};
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public boolean up,down,enter;
	public static boolean pause = false;
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	private Entity entidades;
	
	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(enter) {
			enter = false;
			if(options[currentOption]=="Novo Jogo"||options[currentOption]=="Continuar") {
				Game.gameState = "NORMAL";
				pause = false;
				file = new File("save.txt");
				file.delete();
			}else if(options[currentOption]=="Carregar Jogo") {
				file = new File("Save.txt");
				if(file.exists()) {
					String saver = loadGame(0);
					applySave(saver);
				}
			}else if(options[currentOption]=="Salvar") {
				String[] opt1 = {"level","vida","entidades","municao","pX","pY","armado"};
				int[] opt2 = {Game.CUR_LEVEL,(int) Game.player.vida, Game.entities.size(),
						Game.player.ammo,Game.player.getX(),Game.player.getY(),Game.player.gun};
				this.saveGame(opt1, opt2, 0);
				System.out.println("Salvo");
			}else if(options[currentOption]=="Sair") {
				System.exit(1);
			}
		}
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]) {
				case"level":
					World.restartGame("level"+spl2[1]+".png");
					Game.gameState = "NORMAL";
					pause = false;
					break;
				case"vida":
					Game.player.vida = Integer.parseInt(spl2[1]);
					break;
				case"entidades":
					
					break;
				case"municao":
					Game.player.ammo = Integer.parseInt(spl2[1]);
					break;
				case"pX":
					Game.player.setX(Integer.parseInt(spl2[1]));
					break;
				case"pY":
					Game.player.setY(Integer.parseInt(spl2[1]));
					break;
				case"armado":
					Game.player.gun = Integer.parseInt(spl2[1]);
					break;
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line ="";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine())!= null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i = 0; i < val.length; i++) {
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e) {}
			}catch(FileNotFoundException e) {}
		}
		
		return line;
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++) {
				value[n]+=encode;
				current+=value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1) 
					write.newLine();
			}catch(IOException e) {}
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(options[currentOption]=="Continuar") {
			g.drawString(">", Game.WIDTH*Game.SCALE/2 -75, Game.HEIGHT*Game.SCALE/2);
		}else if(options[currentOption]=="Carregar Jogo") {
			g.drawString(">", Game.WIDTH*Game.SCALE/2 -90, Game.HEIGHT*Game.SCALE/2-55);	
		}else if(options[currentOption]=="Salvar") {
			g.drawString(">", Game.WIDTH*Game.SCALE/2-50 , Game.HEIGHT*Game.SCALE/2+30);
		}else if(options[currentOption]=="Sair") {
			g.drawString(">", Game.WIDTH*Game.SCALE/2 -35, Game.HEIGHT*Game.SCALE/2-25);
		}
		if(pause == false) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setColor(new Color(153,153,255));
			g.setFont(new Font("arial", Font.BOLD,30));
			g.drawString("QUARTO BRANCO", Game.WIDTH*Game.SCALE/2 -135, Game.HEIGHT*Game.SCALE/2-120);
			
			//opcoes do menu
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD,24));
			g.drawString(options[0], Game.WIDTH*Game.SCALE/2 -60, Game.HEIGHT*Game.SCALE/2-60);
			g.drawString(options[1], Game.WIDTH*Game.SCALE/2 -75, Game.HEIGHT*Game.SCALE/2-30);
			g.drawString(options[2], Game.WIDTH*Game.SCALE/2 -20, Game.HEIGHT*Game.SCALE/2);
			
			if(options[currentOption]=="Novo Jogo") {
				g.drawString(">", Game.WIDTH*Game.SCALE/2 -75, Game.HEIGHT*Game.SCALE/2-60);
			}else if(options[currentOption]=="Carregar Jogo") {
				g.drawString(">", Game.WIDTH*Game.SCALE/2 -90, Game.HEIGHT*Game.SCALE/2-30);
			}else if(options[currentOption]=="Sair") {
				g.drawString(">", Game.WIDTH*Game.SCALE/2 -35, Game.HEIGHT*Game.SCALE/2);
			}
		}else{
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setColor(new Color(153,153,255));
			g.setFont(new Font("arial", Font.BOLD,30));
			g.drawString("QUARTO BRANCO", Game.WIDTH*Game.SCALE/2 -135, Game.HEIGHT*Game.SCALE/2-120);
			
			//opcoes do menu
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD,24));
			g.drawString(options[1], Game.WIDTH*Game.SCALE/2 -75, Game.HEIGHT*Game.SCALE/2-55);
			g.drawString(options[2], Game.WIDTH*Game.SCALE/2 -20, Game.HEIGHT*Game.SCALE/2-25);
			g.drawString(options[3], Game.WIDTH*Game.SCALE/2 -55, Game.HEIGHT*Game.SCALE/2);
			g.drawString(options[4], Game.WIDTH*Game.SCALE/2 -35, Game.HEIGHT*Game.SCALE/2+30);
			
		}
			
	}

}
