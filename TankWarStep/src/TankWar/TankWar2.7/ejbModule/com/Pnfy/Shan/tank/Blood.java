package com.Pnfy.Shan.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {

	int x, y, w, h;
	
	
	int step = 0;
	
	private boolean live  = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	//指明血块的轨迹，由pos中各个点构成
	private int [][] pos= {
			{200, 393},{393, 483},{493, 203},{343, 321},{424, 320}
	};
	
	public Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w=h =15;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
	private void move() {
		step ++;
		if(step == pos.length) {
			step = 0;
		}
		
		x = pos[step][0];
		y = pos[step][1];
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	
}
