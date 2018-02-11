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
 * ��������࣬�ں���ʼ��Ϸ��������
 * @author ������
 *����Ǵ��ڵ��趨
 */
public class SpaceWar extends Frame {

	/**
	 * ����ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ���Ƕ�����һ�ű���ͼƬ����Ϊ��update����
	 */
	private Image backPicture = null;
	/**
	 * �����ҵ�ս������װ�е���ս������˫���ӵ�������
	 */
	MyWarcraft myWarcraft = new MyWarcraft(Global.MyWarcraft_Start_X, Global.MyWarcraft_Start_Y, this);
	List<Missile> missiles = new ArrayList<Missile>();
	List<EnemyMissile> enemyMissiles = new ArrayList<EnemyMissile>();
	List<Enemy> enemies = new ArrayList<Enemy>();
	/**
	 * ����ǻ��������е�����
	 */
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.drawString("Missiles         Count---" + missiles.size(), 60, 80);
		g.drawString("Enemies          Count---" + enemies.size(), 60, 90);
		g.drawString("Enemy��s Missiles Count---" + enemyMissiles.size(), 60, 100);
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
	 * ������ػ������˫��������
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
		 * ����ǻ������˵�ս����
		 */
		
		if(enemies.size() <= 10) {
			for(int i=0; i<20; i++) {
				enemies.add(new Enemy(50*i, 100, Global.Warcraft_Width, Global.Warcraft_Height, this));
			}
		}
		
	}

	/**
	 * ���õ������ڣ���λ�ã���С���������رգ����������߳̿�ʼ�ȴ�������
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
	 * ��������࣬��������￪ʼ
	 * @param args
	 */
	public static void main(String[] args) {
		SpaceWar spaceWar = new SpaceWar();
		spaceWar.launchFrame();
	}
	
	/**
	 * ����һ���ػ����߳��࣬ʵ����Runnable�ӿ�
	 * @author ������
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
	 * ����һ�����������̳���KeyAdapter,�Ǵ�������¼���
	 * @author ������
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
