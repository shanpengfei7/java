package com.pnfy.tankwar.basic;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.Random;


public class Tank {
	public static final int XSPEED =5;
	public static final int YSPEED =5;
	
	public static final int WIDTH =30;
	public static final int HEIGHT =30;
	
	private int bloodvalue = 100;
	
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	private BloodBar bb = new BloodBar();
	
	private int life = 100;
	

	public void setLife(int life) {
		this.life = life;
	}

	public int getLife() {
		return life;
	}
	
	

	TankClient tc;
	
	
	
	private boolean good;
	
	public boolean isGood() {
		return good;
	}

	private int x, y;
	private int oldx, oldy;
	
	private static Random r = new Random();
	
	private boolean bL=false, bR=false, bU=false, bD=false;
	
	
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	
	private int step = r.nextInt(12) +3;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldx = x;
		this.oldy = y;
		this.good = good;
	}
	
	public Tank(int x, int y, Boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		
		Color c = g.getColor();
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		
		if(good) bb.draw(g);
		g.setColor(Color.WHITE);
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
		g.setColor(c);
		
		move();
	}
	
	void move() {
		
		this.oldx = x;
		this.oldy = y;
		
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= XSPEED;
	
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
		case  STOP:
		
			break;
	
		}
		if(this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		
		if(x < 10) x = 10;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankClient.Game_Width) x = TankClient.Game_Width - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankClient.Game_Height) y = TankClient.Game_Height - Tank.WIDTH;
		
		if(!good) {
			Direction[] dirs = Direction.values();
			if(step == 0) {
				step = r.nextInt(12) +3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
				
			}
		
			step --;
			
			if(r.nextInt(40)>38)this.fire();
		}
	}
	
	private void stay() {
		x = oldx;
		y = oldy;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
		case KeyEvent.VK_F2:
			
				this.live = true;
				this.life = bloodvalue;
				break;
			
			
		}
		locateDirection();
	}
	

	
	void locateDirection( ) {
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

	public void KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
			
		}
		locateDirection();
		
	}


	public Missile fire() {
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, ptDir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir) {
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean collidesWithWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect()) ) {
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTanks(java.util.List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i ++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.live && this.getRect().intersects(t.getRect()) ) {
					this.stay();
					return true;
				}
				
			}
		}
		return false;
	}

	private void superFire() {
		Direction[] dirs = Direction.values();
		for(int i=0; i<8; i++) {
			fire(dirs[i]);
		}
	}
	
	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.YELLOW);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH * life / 100;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}
	
	public boolean eat(Blood b) {
		if(this.live && b.isLive() && this.getRect().intersects(b.getReat()) ) {
			this.life = bloodvalue;
			b.setLive(false);
			
			return true;
		}
		return false;
	}
}


























