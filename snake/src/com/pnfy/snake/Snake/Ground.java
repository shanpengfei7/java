package com.pnfy.snake.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import com.pnfy.snake.util.Global;

public class Ground {

	private int[][] rocks = new int[Global.ESLL_SIZE][Global.ESLL_SIZE];

	public Ground() {
		for (int x = 0; x < Global.WIDTH; x++) {
			rocks[x][0] = 1;
			rocks[x][Global.HEIGHT - 3] = 1;
		}

	}

	public boolean isSnakeEatRock(Snake snake) {
		System.out.println("Ground's isSnakeEatRock");

		for (int x = 0; x < Global.WIDTH; x++) {
			for (int y = 0; y < Global.HEIGHT; y++) {
				if (rocks[x][y] == 1
						&& (x == snake.getHead().x && y == snake.getHead().y)) {
					return true;
				}
			}

		}

		return false;
	}

	public Point getPoint() {
		Random random = new Random();
		int x = 0, y = 0;
		do {
			x = random.nextInt(Global.WIDTH);
			y = random.nextInt(Global.HEIGHT);
		} while (rocks[x][y] == 1);

		return new Point(x, y);
	}

	public void drawMe(Graphics g) {
		System.out.println("Ground's drawMe");

		g.setColor(Color.GRAY);

		for (int x = 0; x < Global.WIDTH; x++) {
			for (int y = 0; y < Global.HEIGHT; y++) {
				if (rocks[x][y] == 1) {
					g.fill3DRect(x * Global.ESLL_SIZE, y * Global.ESLL_SIZE,
							Global.ESLL_SIZE, Global.ESLL_SIZE, true);

				}
			}
		}
	}
}
