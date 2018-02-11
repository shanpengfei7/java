package com.Pnfy.Shan.spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 这个是敌人的战斗机的子弹
 * @author 单鹏飞
 *
 */
public class EnemyMissile {
	/**
	 *  一个引用，俗称大管家
	 */
	private SpaceWar spaceWar;

	int x, y, w, h;
	
	/**
	 * 敌人战斗机的子弹是生是死
	 */
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * 敌人战斗机的子弹的构造方法
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param spaceWar 一个引用 
	 */
	EnemyMissile(int x, int y, int w, int h, SpaceWar spaceWar) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.spaceWar = spaceWar;
	}
	
	/**
	 * 画出敌人战斗机的子弹 ，颜色位置大小，还有死亡的移动方法
	 * @param g 画笔 
	 */
	public void drawMe(Graphics g) {
		if(!live) {
			spaceWar.enemyMissiles.remove(this);
			return;
		}	
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.fillOval(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
	/**
	 * 敌人战斗机子弹的移动和出界死亡
	 */
	public void move() {
		y += Global.Missile_move_Y;
		
		if(y >= Global.Game_Height) {
			this.setLive(false);
		}
	}
	
	/**
	 * 碰撞检测，检测矩形
	 * @return 一个矩形
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, Global.Missile_Width, Global.Missile_Height);
	}
	
	/**
	 * 敌人的战斗机攻击我的战斗机，一次减少10点血
	 * @param myWarcraft
	 * @return 我的战斗机是还死亡
	 */
	public boolean hitMyWarcraft(MyWarcraft myWarcraft) {
		if(this.live && myWarcraft.isLive() && this.getRect().intersects(myWarcraft.getRect())) {
			this.live = false;
			myWarcraft.setLife(myWarcraft.getLife() - 10);
			if(myWarcraft.getLife() == 0) myWarcraft.setLive(false);
			return true;
		}
		return false;
	}
	
	
}
