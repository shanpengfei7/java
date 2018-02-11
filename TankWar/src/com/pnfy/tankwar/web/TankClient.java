package com.pnfy.tankwar.web;

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

import com.pnfy.tankwar.mashibing.PropertyMgr;

public class TankClient extends Frame {

	private static final long serialVersionUID = 1L;
	public static final int Game_Width = 800;
	public static final int Game_Height = 600;
	Tank myTank = new Tank(400, 500, true, Direction.STOP, this);

	Wall w1 = new Wall(100, 200, 20, 150, this), w2 = new Wall(200, 450, 400,
			20, this), w3 = new Wall(700, 200, 20, 150, this);

	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();

	Image offScreenImage = null;

	NetClient nc = new NetClient();

	Blood b = new Blood();

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.CYAN);
		g.drawString("missiles count:" + missiles.size(), 10, 40);
		g.drawString("explodes count:" + explodes.size(), 10, 60);
		g.drawString("tanks    count:" + tanks.size(), 10, 80);
		g.drawString("tanks    lifet:" + myTank.getLife(), 10, 100);
		g.setColor(c);
		if (tanks.size() <= 0) {
			for (int i = 0; i < Integer.parseInt(PropertyMgr
					.getProperty("reProduceTankCount")); i++) {
				tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Direction.D,
						this));
			}

		}

		myTank.draw(g);
		myTank.eat(b);
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		b.draw(g);

		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.draw(g);
			m.hitWall(w1);
			m.hitWall(w2);

		}
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}

	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(Game_Width, Game_Height);
		}
		Graphics gOffScreenGraphics = offScreenImage.getGraphics();
		Color c = gOffScreenGraphics.getColor();
		gOffScreenGraphics.setColor(Color.BLACK);
		gOffScreenGraphics.fillRect(0, 0, Game_Width, Game_Height);
		gOffScreenGraphics.setColor(c);
		paint(gOffScreenGraphics);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lauchFrame() {

		int initTankCount = Integer.parseInt(PropertyMgr
				.getProperty("initTankCount"));
		for (int i = 0; i < initTankCount; i++) {
			tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Direction.D, this));
		}

		this.setLocation(500, 100);
		this.setSize(Game_Width, Game_Height);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.GREEN);

		this.addKeyListener(new KeyMonitor());

		setVisible(true);

		new Thread(new PaintThread()).start();

		nc.connect("192.168.1.102", TankServer.TCP_PORT);
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}

	private class PaintThread implements Runnable {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.KeyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);

		}

	}

}
