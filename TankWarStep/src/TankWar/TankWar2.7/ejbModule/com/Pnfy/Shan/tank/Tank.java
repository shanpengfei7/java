package com.Pnfy.Shan.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	private BloodBar bloodBar = new BloodBar();
	
	private int life = 100;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private boolean good;
	
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	TankClient tc;

	private int x, y;
	private int oldx, oldy;
	
	private static Random random = new Random();
	
	private boolean bL = false;
	private boolean bU = false;
	private boolean bR = false;
	private boolean bD = false;
	enum Direction{L, LU, U, RU, R, RD, D, LD, STOP};
	private Direction direction = Direction.STOP;
	private Direction ptDir = Direction.U;
	
	private int step = random.nextInt(12) + 3;
	
	public Tank(int x, int y) {
		//super();
		this.x = x;
		this.y = y;
		this.oldx = x;
		this.oldy = y;
	}
	
	public Tank(int x, int y, TankClient tc, Direction direction, boolean good) {
		this(x, y);
		this.tc = tc;
		this.direction = direction;
		this.good = good;
	}

	public void drawMe(Graphics g) {
		if(!live) {
			if(!good) {
				tc.enemyTanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
			
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		if(good) bloodBar.drawMe(g);
		
	
		switch (ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x - Tank.WIDTH/2, y + Tank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y - Tank.HEIGHT/2);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + 3*Tank.WIDTH/2, y + Tank.HEIGHT/2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + 3*Tank.HEIGHT/2);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT);
			break;
	default:
		break;
	}
		move();
	}
	
	void move() {
		this.oldx = x;
		this.oldy = y;
		
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
		if(this.direction != Direction.STOP) {
			this.ptDir = this.direction;
		}
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
	
		if(!good) {
			Direction[] directions = Direction.values();
			if(step == 0) {
				step = random.nextInt(12) + 3;
				int randomNum = random.nextInt(directions.length);
				direction = directions[randomNum];
			}
			step--;
			if(random.nextInt(40) > 38) fire();
		
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		case KeyEvent.VK_F2:
			if(!live) {
				this.live = true;
				this.life = 100;
			}
			break;
		}
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}
	
	void locateDirection() {
		if(bL && !bU && !bR && !bD) direction = Direction.L;
		else if(!bL && bU && !bR && !bD) direction = Direction.U;
		else if(!bL && !bU && bR && !bD) direction = Direction.R;
		else if(!bL && !bU && !bR && bD) direction = Direction.D;
		else if(bL && bU && !bR && !bD) direction = Direction.LU;
		else if(bL && !bU && !bR && bD) direction = Direction.LD;
		else if(!bL && !bU && bR && bD) direction = Direction.RD;
		else if(!bL && bU && bR && !bD) direction = Direction.RU;
		else if(!bL && !bU && !bR && !bD) direction = Direction.STOP;
	}
	
	public Missile fire() {
		if(!live) return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT;
		Missile missile = new Missile(x, y,good, ptDir, this.tc);
		tc.missiles.add(missile);
		return missile;
		
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	private void stay() {
		x = oldx;
		y = oldy;
	}
	/**
	 * 撞墙
	 * @param wall 被撞的墙
	 * @return 撞上了返回true,否则false
	 */
	public boolean collidesWithWall(Wall wall) {
		if(this.live && this.getRect().intersects(wall.getRect())) {
			//this.direction = Direction.STOP;
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTanks(java.util.List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank tank = tanks.get(i);
			if(this != tank) {
				if(this.live && tank.isLive() && this.getRect().intersects(tank.getRect())) {
					//this.direction = Direction.STOP;
					this.stay();
					tank.stay();
					return true;
				}
			}
		}
		return false;
	}
	
	public Missile  fire(Direction direction) {
		if(!live) return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT;
		Missile missile = new Missile(x, y,good, direction, this.tc);
		tc.missiles.add(missile);
		return missile;
		
	}
	
	private void superFire() {
		Direction[] directions = Direction.values();
		for(int i=0; i<8; i++) {
			fire(directions[i]);
		}
	}
	
	private class BloodBar{
		public void drawMe(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH * life / 100;
			g.fillRect(x, y - 10, w, 10);
			g.setColor(c);
		}
	}
	
	public boolean eatBloodBar(Blood blood) {
		if(this.live && blood.isLive() && this.getRect().intersects(blood.getRect())) {
		this.life = 100;
		blood.setLive(false);
		return true;
	}
	return false;
		
	}
	
}

























