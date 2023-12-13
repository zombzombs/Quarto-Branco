package com.tcc.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tcc.main.Game;

public class Tile {

	public static BufferedImage tile_floorg = Game.spritesheet.getSprite(545, 191, 31, 31);
	public static BufferedImage tile_wallb_up = Game.spritesheet.getSprite(545, 159, 32, 32);
	public static BufferedImage tile_wallb_up_left = Game.spritesheet.getSprite(513, 159, 32, 32);
	public static BufferedImage tile_wallb_up_right = Game.spritesheet.getSprite(577, 159, 32, 32);
	public static BufferedImage tile_wallb_left = Game.spritesheet.getSprite(513, 191, 32, 32);
	public static BufferedImage tile_wallb_right = Game.spritesheet.getSprite(577, 191, 32, 32);
	public static BufferedImage tile_wallb_down = Game.spritesheet.getSprite(545, 223, 32, 32);
	public static BufferedImage tile_wallb_down_left = Game.spritesheet.getSprite(513, 223, 32, 32);
	public static BufferedImage tile_wallb_down_right = Game.spritesheet.getSprite(577, 223, 32, 32);
	
	public static BufferedImage tile_floors = Game.spritesheet.getSprite(481, 95, 32, 32);
	
	public static BufferedImage tile_floorw = Game.spritesheet.getSprite(545, 95, 31, 31);
	public static BufferedImage tile_wallw_up = Game.spritesheet.getSprite(545, 63, 32, 32);
	public static BufferedImage tile_wallw_up_left = Game.spritesheet.getSprite(513, 63, 32, 32);
	public static BufferedImage tile_wallw_up_right = Game.spritesheet.getSprite(577, 63, 32, 32);
	public static BufferedImage tile_wallw_mid = Game.spritesheet.getSprite(609, 127, 32, 32);
	public static BufferedImage tile_wallw_left = Game.spritesheet.getSprite(513, 95, 32, 32);
	public static BufferedImage tile_wallw_right = Game.spritesheet.getSprite(577, 95, 32, 32);
	public static BufferedImage tile_wallw_down = Game.spritesheet.getSprite(545, 127, 32, 32);
	public static BufferedImage tile_wallw_down_left = Game.spritesheet.getSprite(513, 127, 32, 32);
	public static BufferedImage tile_wallw_down_right = Game.spritesheet.getSprite(577, 127, 32, 32);
	
	public static BufferedImage tile_wall_post_left = Game.spritesheet.getSprite(353, 159, 32, 32);
	public static BufferedImage tile_wall_post_right = Game.spritesheet.getSprite(353+32, 159, 32, 32);

	public static BufferedImage tile_floorwr = Game.spritesheet.getSprite(896, 224, 32, 32);
	public static BufferedImage tile_wallwr_up = Game.spritesheet.getSprite(960, 96, 32, 32);
	public static BufferedImage tile_wallwr_up_left = Game.spritesheet.getSprite(928, 96, 32, 32);
	public static BufferedImage tile_wallwr_up_right = Game.spritesheet.getSprite(992, 96, 32, 32);
	public static BufferedImage tile_wallwr_left = Game.spritesheet.getSprite(928, 128, 32, 32);
	public static BufferedImage tile_wallwr_right = Game.spritesheet.getSprite(992, 128, 32, 32);
	public static BufferedImage tile_wallwr_down = Game.spritesheet.getSprite(960, 160, 32, 32);
	public static BufferedImage tile_wallwr_down_left = Game.spritesheet.getSprite(928, 160, 32, 32);
	public static BufferedImage tile_wallwr_down_right = Game.spritesheet.getSprite(992, 160, 32, 32);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
			g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		}
	}
