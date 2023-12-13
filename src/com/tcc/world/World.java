package com.tcc.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.tcc.entities.*;
import com.tcc.graficos.Spritesheet;
import com.tcc.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static  int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 31;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx+ (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)]=new FloorTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_floorw);
					if(pixelAtual == 0xFF3E443E){
						//FloorS
						tiles[xx + (yy * WIDTH)]=new FloorTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_floors);
					} 
					else if(pixelAtual == 0xFF000000) {
						//FloorG
						tiles[xx + (yy * WIDTH)]=new FloorTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_floorg);
					}else if(pixelAtual == 0xFFFFFFFF) {
						//WallUPB
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallb_up);
					}else if(pixelAtual == 0xFFFFFFBA) {
						//WallUPLB
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallb_up_left);
					}else if(pixelAtual == 0xFFFFFFBB) {
						//WallUPRB
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallb_up_right);
					}else if(pixelAtual == 0xFFFFFFDE) {
						//WallLB
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallb_left);
					}else if(pixelAtual == 0xFFFFFFC4) {
						//WallRB
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallb_right);
					}else if(pixelAtual == 0xFFFFFF93) {
						//WallDownB
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallb_down);
					}else if(pixelAtual == 0xFFFFFFBC) {
						//WallDownLB
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallb_down_left);
					}else if(pixelAtual == 0xFFFFFFBD) {
						//WallDownRB
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallb_down_right);
					}else if(pixelAtual == 0xFF7C091E) {
						//FloorW
						tiles[xx + (yy * WIDTH)]=new FloorTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_floorw);
					}else if(pixelAtual == 0xFF7C0909) {
						//WallUPW
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_up);
					}else if(pixelAtual == 0xFF7A1A1A) {
						//WallUPLW
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_up_left);
					}else if(pixelAtual == 0xFF660000) {
						//WallUPRW
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_up_right);
					}else if(pixelAtual == 0xFFAF2A2A) {
						//WallLW
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_left);
					}else if(pixelAtual == 0xFF7A3B3B) {
						//WallWMid
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_mid);
					}else if(pixelAtual == 0xFFDB3434) {
						//WallRW
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_right);
					}else if(pixelAtual == 0xFFD82424) {
						//WallDownW
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_down);
					}else if(pixelAtual == 0xFFD82433) {
						//WallDownLW
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_down_left);
					}else if(pixelAtual == 0xFFAF2A41) {
						//WallDownRW
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallw_down_right);	
					}else if(pixelAtual == 0xFFD8245D) {
						//WallPostLeft
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wall_post_left);	
					}else if(pixelAtual == 0xFF00AED6) {
						//WallPostRight
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wall_post_right);	
					}else if(pixelAtual == 0xFF00DD00) {
						//Floor WR
						tiles[xx + (yy * WIDTH)]=new FloorTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_floorwr);	
					}else if(pixelAtual == 0xFF00DDB2) {
						//WallUP WR
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallwr_up);	
					}else if(pixelAtual == 0xFF46DBBD) {
						//WallUP left WR
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallwr_up_left);	
					}else if(pixelAtual == 0xFF46DBFF) {
						//WallUP right WR
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallwr_up_right);	
					}else if(pixelAtual == 0xFF46DB97) {
						//Wall left WR
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallwr_left);	
					}else if(pixelAtual == 0xFF46DB74) {
						//Wall right WR
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallwr_right);	
					}else if(pixelAtual == 0xFF469FC6) {
						//Wall down WR
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallwr_down);	
					}else if(pixelAtual == 0xFF469FA7) {
						//Wall down left WR
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallwr_down_left);	
					}else if(pixelAtual == 0xFF469F5D) {
						//Wall down right WR
						tiles[xx + (yy * WIDTH)]=new WallTile(xx*TILE_SIZE,yy*TILE_SIZE,Tile.tile_wallwr_down_right);	
					}else if(pixelAtual ==0xFF35FF4D) {
						//Door
						Door door = new Door(xx*TILE_SIZE,yy*TILE_SIZE,TILE_SIZE,TILE_SIZE,Entity.DOOR_EN, Entity.DOOR_OPEN_EN, false, 1, 9, 2, 6);
						door.setMask(0, 0, 32, 32);
						Game.entities.add(door);
					}else if(pixelAtual == 0xFF0000FF){
						//Player
						Game.player.setX(xx*TILE_SIZE);
						Game.player.setY(yy*TILE_SIZE);
					}else if(pixelAtual == 0xFFFF0000){
						//Enemy
						Enemy en = new Enemy(xx*TILE_SIZE,yy*TILE_SIZE,TILE_SIZE,TILE_SIZE,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}else if(pixelAtual == 0xFFFF6A00){
						//Weapon
						Weapon weapon = new Weapon(xx*TILE_SIZE,yy*TILE_SIZE,TILE_SIZE,TILE_SIZE,Entity.WEAPON_EN);
						weapon.setMask(0, 0, 19, 11);
						Game.entities.add(weapon);
					}else if(pixelAtual == 0xFFFF7F7F){
						//Life Pack
						Lifepack pack = new Lifepack(xx*TILE_SIZE,yy*TILE_SIZE,TILE_SIZE,TILE_SIZE,Entity.LIFEPACK_EN);
						pack.setMask(1, 2, 9, 16);
						Game.entities.add(pack);
					}else if(pixelAtual == 0xFFFFD800){
						//Ammo
						Ammo ammo = new Ammo(xx*TILE_SIZE,yy*TILE_SIZE,TILE_SIZE,TILE_SIZE,Entity.AMMO_EN);
						ammo.setMask(0, 8, 16, 8);
						Game.entities.add(ammo);
					}else if(pixelAtual ==0xFF8C35FF) {
						//Text
						Text text = new Text(xx*TILE_SIZE,yy*TILE_SIZE,TILE_SIZE,TILE_SIZE,Entity.TEXT_EN, "Lista de Afazeres", "1 colocar uma mesa na cozinha(esta faltando apenas uma) \n"
								+"2 Guardar as noves Marcas \n"
								+"3 Lavar os dois banheiros (Feminino e Masculino)\n"
								+"4 Levar os seis Pacientes para os Dormitórios\n"
								+"(Antes do Grande dia) \n"
								+"\n"
								+"Hábitos saudáveis para um ambiente de trabalho:\n"
								+"Ande com cautela  ( Pressione WASD )\n"
								+"Não atire objetos em seus colegas (J para atirar )\n"
								+"Não corra nos corredores ( Shift para correr )\n");
						text.setMask(0, 8, 16, 8);
						Game.entities.add(text);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext){
		int x1 = xnext/TILE_SIZE;
		int y1 = ynext/TILE_SIZE;
		
		int x2= (xnext + TILE_SIZE-10) /TILE_SIZE;
		int y2= ynext /TILE_SIZE;
		
		int x3= xnext / TILE_SIZE; 
		int y3= (ynext + TILE_SIZE-10) /TILE_SIZE; 
		
		int x4= (xnext + TILE_SIZE-10) / TILE_SIZE; 
		int y4= (ynext + TILE_SIZE-10) /TILE_SIZE;
		
		return !((tiles[x1+(y1*World.WIDTH)]instanceof WallTile ) ||
				(tiles[x2+(y2*World.WIDTH)]instanceof WallTile) ||
				(tiles[x3+(y3*World.WIDTH)]instanceof WallTile) ||
				(tiles[x4+(y4*World.WIDTH)]instanceof WallTile) );
	}
	public static void restartGame(String level) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0,0,32,32,Game.spritesheet.getSprite(0,64, 32, 32));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
		//Game.player.speed=0;
		Game.player.dead=false;
		return;
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x/TILE_SIZE;
		int ystart = Camera.y/TILE_SIZE;
		
		int xfinal = xstart + (Game.WIDTH /TILE_SIZE)+4;
		int yfinal = ystart + (Game.HEIGHT /TILE_SIZE)+4;
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx+(yy*WIDTH)];
				tile.render(g);
			}
		}
		
	}

}
