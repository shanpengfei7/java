package com.Pnfy.Shan.spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

/**
 * �ҵ��ӵ���
 * @author ������
 *
 */
public class Missile {
	/**
	 * һ�����ã��׳ƴ�ܼ�
	 */
	private SpaceWar spaceWar;
	
	int x, y, w, h;
	
	/**
	 * �ҵ��ӵ���������
	 */
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean l) {
		this.live = l;
	}
	
	/**
	 * �ҵ��ӵ��Ĺ��췽��
	 * @param x	
	 * @param y
	 * @param w
	 * @param h
	 * @param spaceWar һ������
	 */
	Missile(int x, int y, int w, int h, SpaceWar spaceWar) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.spaceWar = spaceWar;
	}
	
	/**
	 * �����ҵ��ӵ���������С��ɫλ���Լ������Ƴ��������ƶ�
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
	 * �ҵ��ӵ����ƶ�������ٶȣ�������������
	 */
	public void move() {
		y -= Global.Missile_move_Y;
	
		if(y <= 0 || y >= Global.Game_Height) {
			this.setLive(false);
		}
	}
	
	/**
	 * ��ײ����࣬����
	 * @return һ������
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, Global.Missile_Width, Global.Missile_Height);
	}
	
	/**
	 * �������˵�ս�������ҵ��ӵ��͵��˵�ս����ͬ��
	 * @param enemy ���˵�ս����
	 * @return �Ƿ�����
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
	 * �����ҵķ���
	 */
	public static int num;
	
	/**
	 * ÿһ���ӵ�����ÿһ�����˵�ս�����Ĵ��
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
