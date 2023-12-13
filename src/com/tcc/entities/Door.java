package com.tcc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tcc.main.Game;
import com.tcc.world.Camera;
import com.tcc.world.World;

public class Door extends Entity {
	public static boolean open;
	public static int[] options = {0,0,0,0};
	public static BufferedImage openSprite;
	public static BufferedImage sprite;
	public static Door[] atual;

	public Door(double x, double y, int width, int height, BufferedImage sprite, BufferedImage openSprite, boolean open, int options, int options1, int options2, int options3 ) {
		super(x, y, width, height, sprite);
		Door.open = open;
		Door.sprite = sprite;
		Door.openSprite = openSprite;
		Door.options[0] = options;
		Door.options[1] = options1;
		Door.options[2] = options2;
		Door.options[3] = options3;
		
		atual = new Door[World.WIDTH*World.HEIGHT];
	}
	public void render(Graphics g) {
		
		if(!open) {		
			g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
		}else {
			g.drawImage(openSprite, getX() - Camera.x, getY() - Camera.y, null);

		}
		//g.setColor(Color.red);
		//g.fillRect(getX() - Camera.x, getY() - Camera.y, getWidth(), getHeight());
		
	}
	
	public static boolean isFree(int xnext, int ynext){
		
			int x1 = xnext/32;
			int y1 = ynext/32;
		
			int x2= (xnext + 32-10) /32;
			int y2= ynext /32;
		
			int x3= xnext / 32; 
			int y3= (ynext + 32-10) /32; 
		
			int x4= (xnext + 32-10) / 32; 
			int y4= (ynext + 32-10) /32;
		
		return (!(atual[x1+(y1*World.WIDTH)]instanceof Door ) ||
				(atual[x2+(y2*World.WIDTH)]instanceof Door) ||
				(atual[x3+(y3*World.WIDTH)]instanceof Door) ||
				(atual[x4+(y4*World.WIDTH)]instanceof Door) );
		
	}
	
}
