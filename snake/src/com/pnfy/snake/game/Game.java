package com.pnfy.snake.game;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.pnfy.snake.Controller.Controller;
import com.pnfy.snake.Panel.GamePanel;
import com.pnfy.snake.Snake.Food;
import com.pnfy.snake.Snake.Ground;
import com.pnfy.snake.Snake.Snake;
import com.pnfy.snake.util.Global;

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Snake snake = new Snake();
		Food food = new Food();
		Ground ground = new Ground();
		GamePanel gamePanel = new GamePanel();
		Controller controller = new Controller(snake, food, ground, gamePanel);
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel.setSize(Global.WIDTH * Global.ESLL_SIZE + 10, Global.HEIGHT * Global.ESLL_SIZE + 35);
		frame.setSize(Global.WIDTH * Global.ESLL_SIZE, Global.HEIGHT * Global.ESLL_SIZE);
		frame.add(gamePanel,BorderLayout.CENTER);
		
		gamePanel.addKeyListener(controller);
		snake.addsnakeListener(controller);
		
		frame.addKeyListener(controller);
		
		frame.setVisible(true);
		controller.newGame();
		
		
	}

}
