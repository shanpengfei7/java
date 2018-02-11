package com.pnfy.Panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.pnfy.Global.Global;
import com.pnfy.Tatris.Ground;
import com.pnfy.Tatris.Shape;

public class GamePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ground ground;
	private Shape shape;

	public void display(Ground ground, Shape shape) {
		System.out.println("GamePanel's display");
		this.ground = ground;
		this.shape = shape;
		
		this.repaint();
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		//super.printComponent(g);
		g.setColor(new Color(0xcfcfcf));
		g.fillRect(0, 0, Global.WIDTH * Global.CELL_SIXE, Global.HEIGHT * Global.CELL_SIXE);
		
		if(shape != null && ground != null) {
			shape.drawMe(g);
			ground.drawMe(g);
		}
	}
	
	public GamePanel() {
		this.setSize(Global.WIDTH * Global.CELL_SIXE, Global.HEIGHT * Global.CELL_SIXE);
	}
}
