package com.Pnfy.Shan.spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * �ҵ�ս��������
 * @author ������
 *
 */
public class MyWarcraft {
	/**
	 * һ�����ã��׳ƴ�ܼ�
	 */
	private SpaceWar spaceWar;
	
	private int x, y;
	
	/**
	 * �����˷���ͷ�����ж�
	 */
	private boolean U = false, D = false, L = false, R = false;
	enum Direction  {U, D, L, R, S, LU, LD, RU, RD};
	
	private Direction direction = Direction.S;
	
	/**
	 * �������ҵ�ս����������ֵ
	 */
	private int life = 100;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * �ҵ�ս�����Ƿ񻹻���
	 */
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	/**
	 * �ҵ�ս�����Ĺ��캯��
	 * @param x
	 * @param y
	 * @param spaceWar һ������
	 */
	MyWarcraft(int x, int y, SpaceWar spaceWar) {
		this.x = x;
		this.y = y;
		this.spaceWar = spaceWar;
	}

	/**
	 * �����ҵ�ս��������ɫ��С���򣬻��������������ƶ�����
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
	 * �ҵ�ս�������ƶ�����
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
		 * �߽紦�����ܳ���
		 */
		if(x <= 0) x = 1;
		if(y <= 30) y = 31;
		if(x >= Global.Game_Width - 50) x = Global.Game_Width - 51;
		if(y >= Global.Game_Height -50) y = Global.Game_Height - 51;
	}

	/**
	 * ���̰�������ȥʱ�ķ���
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
	 * ���̰���̧����ʱ�ķ���
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
	 * �ж�ս�����ķ���
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
	 * �ҵ�ս������������ӵ��������������
	 */
	public void fire() {
		if(!live) return;
		Missile missile = new Missile(x + Global.Warcraft_Width/2 - Global.Missile_Width/2, y + Global.Warcraft_Height/2 - Global.Missile_Height/2, Global.Missile_Width, Global.Missile_Height, spaceWar);
		spaceWar.missiles.add(missile);
	}
	
	/**
	 * ��ײ��������
	 * @return һ������
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, Global.Warcraft_Width, Global.Warcraft_Height);
	}
}
