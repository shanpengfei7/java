package com.pnfy.snake.Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.pnfy.snake.Listener.SnakeListener;
import com.pnfy.snake.Panel.GamePanel;
import com.pnfy.snake.Snake.Food;
import com.pnfy.snake.Snake.Ground;
import com.pnfy.snake.Snake.Snake;

public class Controller extends KeyAdapter implements SnakeListener {
	
	private Snake snake;
	private Food food;
	private Ground ground;
	private GamePanel gamePanel;

	
	
	public Controller(Snake snake, Food food, Ground ground, GamePanel gamePanel) {
		super();
		this.snake = snake;
		this.food = food;
		this.ground = ground;
		this.gamePanel = gamePanel;
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//super.keyPressed(e);
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			snake.changeDirection(Snake.UP);
			break;
		case KeyEvent.VK_DOWN:
			snake.changeDirection(Snake.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			snake.changeDirection(Snake.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			snake.changeDirection(Snake.RIGHT);
			break;
		
		}
	}
	
	
	public void snakeMoved(Snake snake) {
		if(food.isSnakeEatFood(snake)) {
			snake.eatFood();
			food.newFood(ground.getPoint());
		}
		
		if(ground.isSnakeEatRock(snake)) {
			snake.die();
		}
		
		if(snake.isEatBody()) {
			snake.die();
		}
		
		
		gamePanel.display(snake, food, ground);
	}
	

	public void newGame() {
		snake.start();
		food.newFood(ground.getPoint());
	}
	
}
