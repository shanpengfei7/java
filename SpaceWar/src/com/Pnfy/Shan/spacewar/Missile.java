package com.Pnfy.Shan.spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

/**
 * 我的子弹类
 * @author 单鹏飞
 *
 */
public class Missile {
	/**
	 * 一个引用，俗称大管家
	 */
	private SpaceWar spaceWar;
	
	int x, y, w, h;
	
	/**
	 * 我的子弹是生是死
	 */
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean l) {
		this.live = l;
	}
	
	/**
	 * 我的子弹的构造方法
	 * @param x	
	 * @param y
	 * @param w
	 * @param h
	 * @param spaceWar 一个引用
	 */
	Missile(int x, int y, int w, int h, SpaceWar spaceWar) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.spaceWar = spaceWar;
	}
	
	/**
	 * 画出我的子弹，包括大小颜色位置以及死亡移除，还有移动
	 * @param g
	 */
	public void drawMe(Graphics g) {
		if(!live) {
			spaceWar.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
	/**
	 * 我的子弹的移动方向和速度，包括出界死亡
	 */
	public void move() {
		y -= Global.Missile_move_Y;
	
		if(y <= 0 || y >= Global.Game_Height) {
			this.setLive(false);
		}
	}
	
	/**
	 * 碰撞检测类，矩形
	 * @return 一个矩形
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, Global.Missile_Width, Global.Missile_Height);
	}
	
	/**
	 * 攻击敌人的战斗机，我的子弹和敌人的战斗机同死
	 * @param enemy 敌人的战斗机
	 * @return 是否死亡
	 */
	public boolean hitEnemy(Enemy enemy) {
		if(this.live && enemy.isLive() && this.getRect().intersects(enemy.getRect())) {
			this.live = false;
			enemy.setLive(false);
			return true;
		}
		return false;
	}
	
	/**
	 * 返回我的分数
	 */
	public static int num;
	
	/**
	 * 每一发子弹对于每一个敌人的战斗机的打击
	 * @param enemies 
	 * @return
	 */
	public boolean hitEnemys(List<Enemy> enemies) {
		for(int i=0; i<enemies.size(); i++) {
			if(hitEnemy(enemies.get(i))) {
			    ++num;
				return true;
			}
		}
		return false;
	}
}
