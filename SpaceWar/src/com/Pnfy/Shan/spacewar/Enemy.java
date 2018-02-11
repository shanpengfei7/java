package com.Pnfy.Shan.spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
/**
 * ������ǵ���ս�������࣬��Ҫд�˵���ս������״��
 * @author ������
 *
 */
public class Enemy {
	/**
	 * �����˵���ս����λ�ã���ߣ���������ü�Ϊ���ӵ����ٶ������������һ��random��
	 */
	int x, y, w, h;
	private SpaceWar spaceWar;
	private static Random random = new Random();
	/**
	 * �����жϵ���ս�����Ƿ񻹻��ŵ�һ����Ա����
	 */
	public boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * ����ǹ��캯����ר��Ϊ�˵���ս����
	 * @param x ��ʼʱx��λ��
	 * @param y ��ʼʱy��λ��
	 * @param w ս�����Ŀ��
	 * @param h ս�����ĸ߶�
	 * @param spaceWar ����һ������
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
	 * �������˵�ս��������С����ɫ���ƶ�����
	 * @param g ����
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
	 * ����ս�������ƶ�·�ߣ������Ƶ�ʺ�����
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
	 * ����ս�������𣬷����ӵ���new���ӵ�����ӵ�������
	 * @return ���˵��ӵ�
	 */
	public EnemyMissile fire() {
		EnemyMissile enemyMissile = new EnemyMissile(x + Global.Warcraft_Width/2 - Global.Missile_Width/2, y + Global.Warcraft_Height/2 - Global.Missile_Height/2, Global.Missile_Width, Global.Missile_Height,spaceWar);
		spaceWar.enemyMissiles.add(enemyMissile);
		return enemyMissile;
		
	}
	
	/**
	 * ��ײ����һ���������йؾ��ε�
	 * @return һ������
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, Global.Warcraft_Width, Global.Warcraft_Height);
	}
}
