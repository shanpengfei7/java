package com.Pnfy.Shan.spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
/**
 * 这个类是敌人战斗机的类，主要写了敌人战斗机的状况
 * @author 单鹏飞
 *
 */
public class Enemy {
	/**
	 * 定义了敌人战斗机位置，宽高，主类的引用及为发子弹多少而产生随机数的一个random类
	 */
	int x, y, w, h;
	private SpaceWar spaceWar;
	private static Random random = new Random();
	/**
	 * 这是判断敌人战斗机是否还活着的一个成员变量
	 */
	public boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * 这个是构造函数，专门为了敌人战斗机
	 * @param x 开始时x的位置
	 * @param y 开始时y的位置
	 * @param w 战斗机的宽度
	 * @param h 战斗机的高度
	 * @param spaceWar 这是一个引用
	 */
	public Enemy(int x, int y, int w, int h, SpaceWar spaceWar) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.spaceWar = spaceWar;
	}
	
	/**
	 * 画出敌人的战斗机，大小，颜色和移动方法
	 * @param g 画笔
	 */
	public void drawMe1(Graphics g) {
		if(!live) {
			spaceWar.enemies.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		g.fillOval(x,y, Global.Warcraft_Width, Global.Warcraft_Height);
		g.setColor(Color.WHITE);
		g.drawLine(x + Global.Warcraft_Width/2, y + Global.Warcraft_Height/2, x + Global.Warcraft_Width/2, y + 3 * Global.Warcraft_Height/2);
		g.setColor(c);

		move1();
	}
	
	/**
	 * 敌人战斗机的移动路线，开火的频率和死亡
	 */
	private void move1() {
		x += 5;
		y += 1;
		if(random.nextInt(100) < 1 ) {
			fire();
		}
		if(x<0 || y<0|| x>Global.Game_Width || y>Global.Game_Height) {
			this.setLive(false);
		}
	}
	
	/**
	 * 敌人战斗机开火，发射子弹，new出子弹并添加到窗口中
	 * @return 敌人的子弹
	 */
	public EnemyMissile fire() {
		EnemyMissile enemyMissile = new EnemyMissile(x + Global.Warcraft_Width/2 - Global.Missile_Width/2, y + Global.Warcraft_Height/2 - Global.Missile_Height/2, Global.Missile_Width, Global.Missile_Height,spaceWar);
		spaceWar.enemyMissiles.add(enemyMissile);
		return enemyMissile;
		
	}
	
	/**
	 * 碰撞检测的一个方法，有关矩形的
	 * @return 一个矩形
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, Global.Warcraft_Width, Global.Warcraft_Height);
	}
}
