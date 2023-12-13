package com.tcc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.tcc.main.Game;
import com.tcc.main.Sound;
import com.tcc.world.Camera;
import com.tcc.world.World;

public class Enemy extends Entity {
	
	private double speed = 0.4;
	private int maskx =8, masky = 16, maskw = 8, maskh=16;
	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 2;
	private BufferedImage[] sprites; 
	
	private int life = 10;
	private boolean isDamaged = false;
	public boolean isCollindingDoor = false;
	private int damageFrame = 10, damageCurrent = 0;
	
	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[3];
		sprites[0] = Game.spritesheet.getSprite(137, 263, 12, 25);
		sprites[1] = Game.spritesheet.getSprite(170, 263, 12, 25);
		sprites[2] = Game.spritesheet.getSprite(202, 263, 12, 25);

	}
	public void tick() {
		maskx =8; masky = 16; maskw = 8; maskh=16;
		if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY())< 130 ) {
			if(isCollidingWithPlayer()==false) {
				if(x<Game.player.getX() && World.isFree((int)(x+speed), this.getY())
						&& !isColliding((int)(x+speed), this.getY())) {
					x+=speed;
					
				}else if(x>Game.player.getX()&& World.isFree((int)(x-speed), this.getY())
						&& !isColliding((int)(x-speed), this.getY())) {
					x-=speed;
					
				}
				if(y<Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))
						&& !isColliding(this.getX(),(int)(y+speed))) {
					y+=speed;
					
				}else if(y>Game.player.getY() && World.isFree(this.getX(),(int)(y+speed))
						&& !isColliding(this.getX(),(int)(y-speed))) {
					y-=speed;
					
				}
			}else {
				//estao colidindo com o player
				if(Game.rand.nextInt(100)<10) {
					Sound.hurtEffect.play();
					Game.player.vida-=Game.rand.nextInt(5);
					Game.player.isDamaged = true;
					//System.out.println("Vida "+Player.vida);
				}
				 
			}
		}
			
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
			
			collidingBullet();
			
			if(life<=0) {
				destroySelf();
				return;
			}
			if(isDamaged) {
				this.damageCurrent++;
				if(this.damageCurrent == this.damageFrame) {
					this.damageCurrent = 0;
					this.isDamaged = false;
				}
			}
		
	}
	
	private void destroySelf() {
		Game.entities.remove(this);
		Game.enemies.remove(this);
	}
	private void collidingBullet() {
		for(int i = 0; i < Game.bullets.size();i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof Bullet) {
				if(Entity.isColliding(this, e)) {
					isDamaged = true;
					Sound.hurtEffect.play();
					life-=2;
					Game.bullets.remove(e);
					return ;
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
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY()+masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),32,32);
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext+masky,maskw,maskh);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) 
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX()+ maskx,e.getY()+masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
			
			
		return false;
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.blue);
		//g.fillRect(this.getX()+maskx - Camera.x , this.getY()+masky - Camera.y, maskw, maskh);
		}else{
			g.drawImage(ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
