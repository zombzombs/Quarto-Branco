package com.tcc.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.tcc.entities.Door;
import com.tcc.entities.Entity;
import com.tcc.main.Game;

public class Doorg {
	
	public static int[] options = {0,0,0,0};
	public int currentOption = 0;
	public int maxOption = options.length-1;
	public boolean up,down,left,right,enter;

	
	public void tick() {
		if(enter) {
			enter = false;
			Game.gameState = "NORMAL";
			
			if(options[0]==Door.options[0] && options[1]==Door.options[1] && 
					options[2]==Door.options[2] && options[3]==Door.options[3]) {
				Door.open=true;
			}else {
				options[0] = 0;
				options[1] = 0;
				options[2] = 0;
				options[3] = 0;}
		}
		
		if(left) {
			left = false;
			currentOption--;
			//System.out.println("esquerda");
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(right) {
			right = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
		if(up) {
			up = false;
			options[currentOption]++;
			if(options[currentOption]>9) {
				options[currentOption] = 0;
			}
		}
		if(down) {
			down = false;
			options[currentOption]--;
			if(options[currentOption]<0) {
				options[currentOption] = 9;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(0,0,0,230));
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(new Color(255,255,255));
		//g.setFont(new Font("arial", Font.BOLD,30));

		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,24));
		g.drawString(String.valueOf(options[0]), Game.WIDTH*Game.SCALE/2 -60, Game.HEIGHT*Game.SCALE/2-10);
		g.drawString(String.valueOf(options[1]), Game.WIDTH*Game.SCALE/2 -30, Game.HEIGHT*Game.SCALE/2-10);
		g.drawString(String.valueOf(options[2]), Game.WIDTH*Game.SCALE/2 , Game.HEIGHT*Game.SCALE/2-10);
		g.drawString(String.valueOf(options[3]), Game.WIDTH*Game.SCALE/2 +30, Game.HEIGHT*Game.SCALE/2-10);
		if(currentOption==0) {
			g.drawString("_", Game.WIDTH*Game.SCALE/2 -60, Game.HEIGHT*Game.SCALE/2-10);
		}else if(currentOption==1) {
			g.drawString("_", Game.WIDTH*Game.SCALE/2 -30, Game.HEIGHT*Game.SCALE/2-10);
		}else if(currentOption==2) {
			g.drawString("_", Game.WIDTH*Game.SCALE/2 , Game.HEIGHT*Game.SCALE/2-10);
		}else if(currentOption==3) {
			g.drawString("_", Game.WIDTH*Game.SCALE/2 +30, Game.HEIGHT*Game.SCALE/2-10);
		}
	}

}
