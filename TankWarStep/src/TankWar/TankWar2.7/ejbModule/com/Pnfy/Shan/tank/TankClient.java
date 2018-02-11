package com.Pnfy.Shan.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
/**
 * 这个类的作用是坦克游戏的主窗口
 * @author 单鹏飞
 *
 */
public class TankClient extends Frame {

	/**
	 * 整个坦克游戏的宽度
	 */
	private static final long serialVersionUID = 1L;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(400, 500, this,Tank.Direction.STOP, true);
	List<Missile> missiles = new ArrayList<Missile>();
	//Tank enemyTank = new Tank(100, 100, this, false);
	//Explode e = new Explode(70, 70, this);
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> enemyTanks = new ArrayList<Tank>();
	
	Wall wall1 = new Wall(100, 200,20,  150, this), wall2 = new Wall(300, 100, 300, 20, this);
	
	Image offScreenImage = null;
	
	Blood blood = new Blood();
	/*
	 * 指明子弹-爆炸-坦克的数量
	 * 以及坦克的生命值
	 */
	@Override
	public void paint(Graphics g) {
		g.drawString("missiles count--:" + missiles.size(), 10, 50);
		g.drawString("explodes count--:" + explodes.size(), 10, 60);
		g.drawString("enemyTanks counr--:" + enemyTanks.size(), 10, 70);
		g.drawString("tanks  life--:" + myTank.getLife(), 10, 80);
		// TODO Auto-generated method stub
		//super.print(g);
		/*Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 50, 50);
		g.setColor(c);*/
		
		if(enemyTanks.size() <= 0) {
			for(int i=0; i<10; i++) {
				enemyTanks.add(new Tank(50 + 40*(i + 1), 50, this, Tank.Direction.D, false));
			}
		}
	
		myTank.drawMe(g);
		myTank.eatBloodBar(blood);
		wall1.draw(g);
		wall2.draw(g);
		blood.draw(g);
		//enemyTank.drawMe(g);
		//e.drawMe(g);
		for(int i=0; i<missiles.size(); i++) {
			Missile missile = missiles.get(i);
			//missile.hitTank(enemyTank);
			missile.drawMe(g);
			missile.hitTanks(enemyTanks);
			missile.hitTank(myTank);
			missile.hitWall(wall1);
			missile.hitWall(wall2);
			/*if(!missile.isLive()) missiles.remove(missile);
			else missile.drawMe(g);*/
		}
		for(int i=0; i<explodes.size(); i++) {
			Explode explode = explodes.get(i);
			explode.drawMe(g);
		}
		for(int i=0; i<enemyTanks.size(); i++) {
			Tank tank = enemyTanks.get(i);
			tank.collidesWithWall(wall1);
			tank.collidesWithWall(wall2);
			tank.collidesWithTanks(enemyTanks);
			tank.drawMe(g);
		}
	}
	
	
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		//super.update(g);
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}

	/**
	 * 本方法显示坦克主窗口
	 */
	public void launchFrame() {
		for(int i=0; i<10; i++) {
			enemyTanks.add(new Tank(50 + 40*(i + 1), 50, this, Tank.Direction.D, false));
		}
		
		this.setLocation(500,100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				//super.windowClosing(e);
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
		
	}
	
	
	
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				repaint();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			//super.keyReleased(e);
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			//super.keyPressed(e);
			//System.out.println("KeyMonitor");
			/*int key = e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_LEFT:
				x -= 5;
				break;
			case KeyEvent.VK_UP:
				y -= 5;
				break;
			case KeyEvent.VK_RIGHT:
				x += 5;
				break;
			case KeyEvent.VK_DOWN:
				y += 5;
				break;
			}*/
			myTank.keyPressed(e);
		}
		
	}
}






















