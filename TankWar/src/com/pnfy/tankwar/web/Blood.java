package com.pnfy.tankwar.web;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
	int x, y, w, h;
	TankClient tc;
	
	int step = 0;
	
	private boolean live = true;
	
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	private int[][]  pos = {
			{350, 300},{360,300},{600,502},{542, 214},{125, 125},{156, 423},{142,594}
	};
	
	public Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w = h = 30;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		Color c = g.getColor();
		g.setColor(Color. MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}

	
	
	private void move() {
		step ++;
		if (step == pos.length) {
			step = 0;
		}
		
		x = pos[step][0];
		y = pos[step][1];
		
	}
	
	public Rectangle getReat() {
		return new Rectangle(x, y, w, h);
	}
}
















