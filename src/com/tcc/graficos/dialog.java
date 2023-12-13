package com.tcc.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.tcc.entities.Entity;
import com.tcc.entities.Text;
import com.tcc.main.Game;

public class dialog {
	
 
	public boolean enter;
	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	

	
	public void tick() {
		if(enter) {
			enter = false;
			Game.gameState = "NORMAL";
		}
		
		//this.checkCollisionText();
	}
	
	public void checkCollisionText() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Text) {
				if(Entity.isColliding(Game.player, atual)){
					System.out.println("PAPEL");
					Game.player.isCollindingText = true;
				}else {
					Game.player.isCollindingText = false;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		
		g.setColor(new Color(0,0,0,230));
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(new Color(255,255,255));
		//g.setFont(new Font("arial", Font.BOLD,30));
		g.drawString(Text.title.toUpperCase(), Game.WIDTH*Game.SCALE/2 -60, Game.HEIGHT*Game.SCALE/2-200);
		g.setColor(new Color(255,255,255,150));
		drawString(g,Text.text,Game.WIDTH*Game.SCALE/2 -200, Game.HEIGHT*Game.SCALE/2-110);
		//g.drawString(Text.text,Game.WIDTH*Game.SCALE/2 -155, Game.HEIGHT*Game.SCALE/2-110);
	}
	

}
