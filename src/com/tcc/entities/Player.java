package com.tcc.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.tcc.graficos.Spritesheet;
import com.tcc.main.Game;
import com.tcc.main.Sound;
import com.tcc.world.Camera;
import com.tcc.world.World;

public class Player extends Entity{
	
	public boolean right, left, up, down; 
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = down_dir;
	public double speed = 1.4;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 2;
	public boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer; 
	private BufferedImage[] downPlayer; 
	private BufferedImage damagePlayer; 
	private BufferedImage[][] deadPlayer; 
	
	
	public double vida = 15, vidaMax=20;
	
	public int mx, my;
	public boolean mouseShoot = false;
	public boolean shoot = false;
	public boolean dead = false;
	public boolean isDamaged = false;
	public boolean hasGun = false;
	private int damageFrames =0;
	public int ammo, maxAmmo = 25;
	public boolean isCollindingText = false;
	public boolean isCollindingDoor = false;
	public int gun = 0;

	
	

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[3];
		leftPlayer = new BufferedImage[3];
		upPlayer = new BufferedImage[3];
		downPlayer = new BufferedImage[3];
		damagePlayer = Game.spritesheet.getSprite(192,63, 32, 32);
		deadPlayer = new BufferedImage[4][3];
				//Game.spritesheet.getSprite(96,64, 32, 32);

		

		for(int i = 0; i<3; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(0+(i*32),0, 31, 32);	
		}
		for(int i = 0; i<3; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(0+(i*32),32, 32, 32);
		}
		for(int i = 0; i<3; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(0+(i*32),64, 32, 32);
		}
		for(int i = 0; i<3; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(0+(i*32),96, 32, 32);
		}
		
		
		for (int i = 0; i<3; i++) {
			deadPlayer[0][i] = Game.spritesheet.getSprite(0+(i*32),256, 32, 32);
		}
		for (int i = 0; i<3; i++) {
			deadPlayer[1][i] = Game.spritesheet.getSprite(0+(i*32),256+32, 32, 32);
		}
		for (int i = 0; i<3; i++) {
			deadPlayer[2][i] = Game.spritesheet.getSprite(0+(i*32),256+64, 32, 32);
		}
		for (int i = 0; i<3; i++) {
			deadPlayer[3][i] = Game.spritesheet.getSprite(0+(i*32),256+96, 32, 32);
		}

	}
	
	public void tick() {
		moved = false;
		
		if(right && World.isFree((int)(x+speed),this.getY())/*&& Door.isFree((int)(x+speed),this.getY())*/){
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())/*&& Door.isFree((int)(x-speed),this.getY())*/){
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))&& !isCollindingDoor/*Door.isFree(this.getX(),(int)(y-speed))*/){
			moved = true;
			dir = up_dir;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))/*&& Door.isFree(this.getX(),(int)(y+speed))*/){
			moved = true;
			dir = down_dir;
			y+=speed; 
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames==15) {
				this.damageFrames=0;
				isDamaged = false;
			}
		}
		
		if(shoot) {
			shoot = false;
			if(hasGun && ammo > 0) {
				ammo--;
				
				
				int dx = 0;
				int px = 0;
				int py = 12;
				if(dir == right_dir) {
					px = 30;
					dx = 1;
				}else {
					px = -2;
					dx = -1;
				}
				Sound.gunEffect.play();
				Bullet bullet = new Bullet(this.getX()+px, this.getY()+py, 3, 3, null, dx,0);
				Game.bullets.add(bullet);
			}
		}
		/*
		if(mouseShoot){
			mouseShoot = false;
			
			if(hasGun && ammo > 0) {
				ammo--;
				double angle = 0;
				int px = 0;
				int py = 12;
				if(dir == right_dir) {
					angle = Math.atan2(my - (this.getY()+py - Camera.y), mx - (this.getX()+px - Camera.x)); 
					px = 30;
				}else {
					angle = Math.atan2(my - (this.getY()+py - Camera.y), mx - (this.getX()+px - Camera.x)); 
					px = -2;
				}
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				Sound.gunEffect.play();

				Bullet bullet = new Bullet(this.getX()+px, this.getY()+py, 3, 3, null, dx,dy);
				Game.bullets.add(bullet);
			}
		}
		*/
		if (gun == 1) {
			hasGun = true;
		}
		if(hasGun) {
			for(int i = 0; i<3; i++) {
				rightPlayer[i] = Game.spritesheet.getSprite(0+(i*32),128, 32, 32);
			}
			for(int i = 0; i<3; i++) {
				leftPlayer[i] = Game.spritesheet.getSprite(0+(i*32),160, 32, 32);
			}
			for(int i = 0; i<3; i++) {
				upPlayer[i] = Game.spritesheet.getSprite(0+(i*32),192, 32, 32);
			}
			for(int i = 0; i<3; i++) {
				downPlayer[i] = Game.spritesheet.getSprite(0+(i*32),192+32, 32, 32);
			}
			
		}
		
		if(vida<=0) {
			//System.exit(1);
			dead=true;
			Game.gameState="GAME_OVER";
			
		}
		
		checkCollisionDoor();
		checkCollisionText();
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();
		updateCamera();
		
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*32 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*32 - Game.HEIGHT);
	}
	
	public void checkCollisionGun() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColliding(this, atual)){
					gun = 1;
					System.out.println("Pegou a arma");
					Game.entities.remove(atual);
					
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Ammo) {
				if(Entity.isColliding(this, atual)){
					ammo+=5;
					if(ammo>=maxAmmo) {
						ammo=maxAmmo;
					}
					//System.out.println("AMMO :"+ammo);
					Game.entities.remove(atual);
					
				}
			}
		}
	}
	
	public void checkCollisionLifePack(){
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColliding(this, atual)){
					vida+=5;
					if(vida>=vidaMax) {
						vida=vidaMax;
					}
					Game.entities.remove(atual);
					
				}
			}
		}
	}
	
	public void checkCollisionText() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Text) {
				
				if(Entity.isColliding(this, atual)){
					//System.out.println("PAPEL");
					isCollindingText = true;
				}else {
					isCollindingText = false;
				}
			}
		}
	}
	
	public void checkCollisionDoor(){
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Door) {
				if(Entity.isColliding(this, atual)){
					if(!Door.open) {
						isCollindingDoor = true;
					}else {
						isCollindingDoor = false;
					}
				}else {
					isCollindingDoor = false;
				}
			}
		}
	}
	
	
	public void render(Graphics g) {
		if(!dead){
			if(!isDamaged) {
				if(dir == right_dir) {
					g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == left_dir) {
					g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}if(dir == up_dir) {
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == down_dir) {
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				} 
			}else {
				g.drawImage(damagePlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}else {
			if(dir == right_dir) {
				g.drawImage(deadPlayer[0][index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if(dir == left_dir) {
				g.drawImage(deadPlayer[1][index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if(dir == up_dir) {
				g.drawImage(deadPlayer[2][index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if(dir == down_dir) {
				g.drawImage(deadPlayer[3][index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}	
	}
	 
}
