package com.Pnfy.Shan.spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * 我的战斗机的类
 * @author 单鹏飞
 *
 */
public class MyWarcraft {
	/**
	 * 一个引用，俗称大管家
	 */
	private SpaceWar spaceWar;
	
	private int x, y;
	
	/**
	 * 定义了方向和方向的判断
	 */
	private boolean U = false, D = false, L = false, R = false;
	enum Direction  {U, D, L, R, S, LU, LD, RU, RD};
	
	private Direction direction = Direction.S;
	
	/**
	 * 定义了我的战斗机的生命值
	 */
	private int life = 100;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * 我的战斗机是否还活着
	 */
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	/**
	 * 我的战斗机的构造函数
	 * @param x
	 * @param y
	 * @param spaceWar 一个引用
	 */
	MyWarcraft(int x, int y, SpaceWar spaceWar) {
		this.x = x;
		this.y = y;
		this.spaceWar = spaceWar;
	}

	/**
	 * 画出我的战斗机，颜色大小方向，还有死亡不画和移动方法
	 * @param g
	 */
	public void drawMe(Graphics g) {
		if(!live) {
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x,y, Global.Warcraft_Width, Global.Warcraft_Height);
		g.setColor(Color.WHITE);
		g.drawLine(x + Global.Warcraft_Width/2, y + Global.Warcraft_Height/2, x + Global.Warcraft_Width/2, y - Global.Warcraft_Height/2);
		g.setColor(c);
		
		move();
	}
	
	/**
	 * 我的战斗机的移动方法
	 */
	public void move() {
		switch(direction) {
		case U:
			y -= Global.Warcraft_move_Y;
			break;
		case L:
			x -= Global.Warcraft_move_X;
			break;
		case D:
			y += Global.Warcraft_move_Y;
			break;
		case R:
			x += Global.Warcraft_move_X;
			break;
		case S:
			break;
		case LU:
			x -= Global.Warcraft_move_X;
			y -= Global.Warcraft_move_Y;
			break;
		case LD:
			x -= Global.Warcraft_move_X;
			y += Global.Warcraft_move_Y;
			break;
		case RU:
			y -= Global.Warcraft_move_Y;
			x += Global.Warcraft_move_X;
			break;
		case RD:
			y += Global.Warcraft_move_Y;
			x += Global.Warcraft_move_X;
			break;
		}
		/**
		 * 边界处理，不能出界
		 */
		if(x <= 0) x = 1;
		if(y <= 30) y = 31;
		if(x >= Global.Game_Width - 50) x = Global.Game_Width - 51;
		if(y >= Global.Game_Height -50) y = Global.Game_Height - 51;
	}

	/**
	 * 键盘按键按下去时的方法
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP:
			U = true;
			break;
		case KeyEvent.VK_LEFT:
			L = true;
			break;
		case KeyEvent.VK_DOWN:
			D = true;
			break;
		case KeyEvent.VK_RIGHT:
			R = true;
			break;
		case KeyEvent.VK_SPACE:
			fire();
			break;
		case KeyEvent.VK_F1:
			this.life = 100;
			this.live = true;
			break;
		}
		loadDir();
	}
	
	/**
	 * 键盘按键抬起来时的方法
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP:
			U = false;
			break;
		case KeyEvent.VK_LEFT:
			L = false;
			break;
		case KeyEvent.VK_DOWN:
			D = false;
			break;
		case KeyEvent.VK_RIGHT:
			R = false;
			break;
		case KeyEvent.VK_SPACE:
			fire();
			break;
		}
		loadDir();
	}
	
	/**
	 * 判断战斗机的方向
	 */
	public void loadDir() {
		if(U && !L && !D && !R) direction = Direction.U;
		else if(!U && L && !D && !R) direction = Direction.L;
		else if(!U && !L && D && !R) direction = Direction.D;
		else if(!U && !L && !D && R) direction = Direction.R;
		else if(!U && !L && !D && !R) direction = Direction.S;
		else if(U && L && !D && !R) direction = Direction.LU;
		else if(U && !L && !D && R) direction = Direction.RU;
		else if(!U && L && D && !R) direction = Direction.LD;
		else if(!U && !L && D && R) direction = Direction.RD;
		
	}
	
	/**
	 * 我的战斗机开火，造出子弹并添加在容器中
	 */
	public void fire() {
		if(!live) return;
		Missile missile = new Missile(x + Global.Warcraft_Width/2 - Global.Missile_Width/2, y + Global.Warcraft_Height/2 - Global.Missile_Height/2, Global.Missile_Width, Global.Missile_Height, spaceWar);
		spaceWar.missiles.add(missile);
	}
	
	/**
	 * 碰撞处理，矩形
	 * @return 一个矩形
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, Global.Warcraft_Width, Global.Warcraft_Height);
	}
}
