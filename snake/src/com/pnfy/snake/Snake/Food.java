package com.pnfy.snake.Snake;

import java.awt.Graphics;
import java.awt.Point;

import com.pnfy.snake.util.Global;

public class Food extends Point {

	public void newFood(Point point) {
		this.setLocation(point);
	}

	public boolean isSnakeEatFood(Snake snake) {
		System.out.println("Food's isSnakeEatFood");

		return this.equals(snake.getHead());
	}

	public void drawMe(Graphics g) {
		System.out.println("Food's drawMe");

		g.fill3DRect(x * Global.ESLL_SIZE, y * Global.ESLL_SIZE,
				Global.ESLL_SIZE, Global.ESLL_SIZE, true);
	}
}
