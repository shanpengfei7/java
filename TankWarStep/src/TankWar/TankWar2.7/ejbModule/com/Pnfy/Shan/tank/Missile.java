package com.Pnfy.Shan.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private TankClient tc;
	
	private boolean good;
	
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	int x, y;
	Tank.Direction direction;

	public Missile(int x, int y, Tank.Direction direction) {
		//super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public Missile(int x, int y,boolean good, Tank.Direction direction, TankClient tc) {
		this(x, y, direction);
		this.good = good;
		this.tc = tc;
	}
	
	public void drawMe(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}

	private void move() {
		
		// TODO Auto-generated method stub
		switch(direction) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
			
		}
		if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
			
		}
		
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank tank) {
		if(this.live && this.getRect().intersects(tank.getRect()) && tank.isLive() && this.good != tank.isGood()) {
			if(tank.isGood()) {
				tank.setLife(tank.getLife() - 20);
				if(tank.getLife() <= 0) {
					tank.setLive(false);
				}
			}
			else {
				tank.setLive(false);
			}
			
			//tank.setLive(false);
			this.live = false;
			Explode explode = new Explode(x, y, tc);
			tc.explodes.add(explode);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> enemyTanks) {
		for(int i=0; i<enemyTanks.size(); i++) {
			if(hitTank(enemyTanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall  wall) {
		if(this.live && this.getRect().intersects(wall.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
}
