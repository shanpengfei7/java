package com.pnfy.snake.Panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.pnfy.snake.Snake.Food;
import com.pnfy.snake.Snake.Ground;
import com.pnfy.snake.Snake.Snake;
import com.pnfy.snake.util.Global;

public class GamePanel extends JPanel {
	
	private Snake snake ;
	private Food food ;
	private Ground ground ;

	public void display(Snake snake, Food food, Ground ground) {
		System.out.println("GamePanel;s display");
		this.snake = snake;
		this.food = food;
		this.ground = ground;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		//super.paintComponent(g);
		//÷ÿ–¬œ‘ æ
		g.setColor(new Color(0xcfcfcf));
		g.fillRect(0, 0, Global.WIDTH * Global.ESLL_SIZE, Global.HEIGHT * Global.ESLL_SIZE);
		
		if(ground != null && snake != null && food != null) {
		
		
			this.ground.drawMe(g);
			this.snake.drawMe(g);
			this.food.drawMe(g);
		}
	}
	
	
}
