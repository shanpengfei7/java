package com.Pnfy.Shan.spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * ����ǵ��˵�ս�������ӵ�
 * @author ������
 *
 */
public class EnemyMissile {
	/**
	 *  һ�����ã��׳ƴ�ܼ�
	 */
	private SpaceWar spaceWar;

	int x, y, w, h;
	
	/**
	 * ����ս�������ӵ���������
	 */
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * ����ս�������ӵ��Ĺ��췽��
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param spaceWar һ������ 
	 */
	EnemyMissile(int x, int y, int w, int h, SpaceWar spaceWar) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.spaceWar = spaceWar;
	}
	
	/**
	 * ��������ս�������ӵ� ����ɫλ�ô�С�������������ƶ�����
	 * @param g ���� 
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
	 * ����ս�����ӵ����ƶ��ͳ�������
	 */
	public void move() {
		y += Global.Missile_move_Y;
		
		if(y >= Global.Game_Height) {
			this.setLive(false);
		}
	}
	
	/**
	 * ��ײ��⣬������
	 * @return һ������
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, Global.Missile_Width, Global.Missile_Height);
	}
	
	/**
	 * ���˵�ս���������ҵ�ս������һ�μ���10��Ѫ
	 * @param myWarcraft
	 * @return �ҵ�ս�����ǻ�����
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
