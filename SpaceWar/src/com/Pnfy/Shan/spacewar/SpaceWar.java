package com.Pnfy.Shan.spacewar;

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
 * 这个是主类，内含开始游戏的主程序
 * @author 单鹏飞
 *这个是窗口的设定
 */
public class SpaceWar extends Frame {

	/**
	 * 这是ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 这是定义了一张背景图片，是为了update而用
	 */
	private Image backPicture = null;
	/**
	 * 这是我的战斗机和装有敌人战斗机及双方子弹的容器
	 */
	MyWarcraft myWarcraft = new MyWarcraft(Global.MyWarcraft_Start_X, Global.MyWarcraft_Start_Y, this);
	List<Missile> missiles = new ArrayList<Missile>();
	List<EnemyMissile> enemyMissiles = new ArrayList<EnemyMissile>();
	List<Enemy> enemies = new ArrayList<Enemy>();
	/**
	 * 这个是画出窗口中的内容
	 */
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.drawString("Missiles         Count---" + missiles.size(), 60, 80);
		g.drawString("Enemies          Count---" + enemies.size(), 60, 90);
		g.drawString("Enemy‘s Missiles Count---" + enemyMissiles.size(), 60, 100);
		g.drawString("My   Warcraft    Blood---" + myWarcraft.getLife(), 60, 110);
		g.drawString("My               Grade---" + Missile.num, 60, 70);

		
		Enemy enemy = null; 
		
		g.setColor(c);
		myWarcraft.drawMe(g);
		
		
		for(int i=0; i<enemyMissiles.size(); i++) {
			EnemyMissile enemyMissile = enemyMissiles.get(i);
			enemyMissile.drawMe(g);
			enemyMissile.hitMyWarcraft(myWarcraft);
		}
		for(int i=0; i<enemies.size(); i++) {
			enemy = enemies.get(i);
			enemy.drawMe1(g);
		}
		for(int i=0; i<missiles.size(); i++) {
			Missile missile = missiles.get(i);
			missile.drawMe(g);
			missile.hitEnemys(enemies);
		}  
		
		
	}
	
	/**
	 * 这个是重画，解决双闪的问题
	 */
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		//super.update(g);
		if(backPicture == null) {
			backPicture = createImage(Global.Game_Width,Global.Game_Height);
		}
		Graphics back = backPicture.getGraphics();
		Color c = back.getColor();
		back.setColor(Color.BLACK);
		back.clearRect(0, 0, Global.Game_Width,Global.Game_Height);
		back.setColor(c);
		paint(back);
		g.drawImage(backPicture, 0, 0, null);
		/**
		 * 这个是画出敌人的战斗机
		 */
		
		if(enemies.size() <= 10) {
			for(int i=0; i<20; i++) {
				enemies.add(new Enemy(50*i, 100, Global.Warcraft_Width, Global.Warcraft_Height, this));
			}
		}
		
	}

	/**
	 * 设置调整窗口，含位置，大小，背景，关闭，监听器，线程开始等窗口内容
	 */
	public void launchFrame() {
		setBounds(0, 0, Global.Game_Width,Global.Game_Height);
		setResizable(false);
		setBackground(Color.BLACK);
		setVisible(true);
		addKeyListener(new Monitor());
		new Thread(new PaintDriver()).start();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				//super.windowClosing(e);
				System.exit(0);
			}
		});
	}
	/**
	 * 这个是主类，程序从这里开始
	 * @param args
	 */
	public static void main(String[] args) {
		SpaceWar spaceWar = new SpaceWar();
		spaceWar.launchFrame();
	}
	
	/**
	 * 这是一个重画的线程类，实现了Runnable接口
	 * @author 单鹏飞
	 *
	 */
	public class PaintDriver implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				repaint();
				try {
					Thread.sleep(Global.Sleep_Time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * 这是一个监听器，继承了KeyAdapter,是处理键盘事件的
	 * @author 单鹏飞
	 *
	 */
	private class Monitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myWarcraft.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			myWarcraft.keyPressed(e);
		}
	}
}
