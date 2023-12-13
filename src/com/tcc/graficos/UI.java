package com.tcc.graficos;

import java.awt.Color;
import java.awt.Graphics;

import com.tcc.entities.Player;
import com.tcc.main.Game;

public class UI {
	
	
	public void render(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(8, 4, 70, 8);
		g.setColor(Color.red);
		g.fillRect(8, 4, (int)((Game.player.vida/Game.player.vidaMax)*70), 8);
		if(Game.player.isCollindingText || Game.player.isCollindingDoor) {
			
			g.setColor(Color.white);
			g.drawString("<Pressione E>",Game.WIDTH*Game.SCALE/2 -275, Game.HEIGHT*Game.SCALE/2-160);
		}
		if(Game.player.vida>=0) {
		g.setColor(Color.white);
		g.drawString((int)Game.player.vida+"/"+Game.player.vidaMax, 8, 12);
		}else {
			g.setColor(Color.white);
			g.drawString((int)0+"/"+Game.player.vidaMax, 8, 12);
		}
	}
}
