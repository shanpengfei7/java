package com.pnfy.snake.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.pnfy.snake.Listener.SnakeListener;
import com.pnfy.snake.util.Global;

public class Snake {

	public static final int UP = -1;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = -2;

	private int oldDirection, newdirection;

	private Point oldTail;

	private LinkedList<Point> body = new LinkedList<Point>();

	private Set<SnakeListener> listeners = new HashSet<SnakeListener>();

	private boolean live;

	public Snake() {
		init();
	}

	public void init() {
		int x = Global.WIDTH / 2;
		int y = Global.HEIGHT / 2;
		for (int i = 0; i < 3; i++) {
			body.addFirst(new Point(x--, y));
		}
		oldDirection = newdirection = LEFT;
		live = true;
	}

	public void die() {
		live = false;
	}

	public void move() {
		System.out.println("Snake's move");

		if (!(oldDirection + newdirection == 0)) {
			oldDirection = newdirection;
		}

		// 1.»•Œ≤
		oldTail = body.removeLast();

		int x = body.getFirst().x;
		int y = body.getFirst().y;
		switch (oldDirection) {
		case UP:
			y--;
			if (y < 0) {
				y = Global.HEIGHT - 1;
			}
			break;
		case DOWN:
			y++;
			if (y >= Global.HEIGHT) {
				y = 0;
			}
			break;
		case LEFT:
			x--;
			if (x < 0) {
				x = Global.WIDTH - 1;
			}
			break;
		case RIGHT:
			x++;
			if (x > Global.WIDTH) {
				x = 0;
			}
			break;
		}
		Point newHead = new Point(x, y);

		// 2.º”Õ∑
		body.addFirst(newHead);

	}

	public void changeDirection(int direction) {
		System.out.println("Snake's changeDirection");

		newdirection = direction;

	}

	public void eatFood() {
		System.out.println("Snake's eatFood");

		body.addLast(oldTail);

	}

	public boolean isEatBody() {
		System.out.println("Snake's isEatBody");

		for (int i = 1; i < body.size(); i++) {
			if (body.get(i).equals(this.getHead())) {
				return true;
			}
		}
		return false;
	}

	public void drawMe(Graphics g) {
		System.out.println("Snake's drawMe");

		g.setColor(Color.BLUE);
		for (Point point : body) {
			g.fill3DRect(point.x * Global.ESLL_SIZE,
					point.y * Global.ESLL_SIZE, Global.ESLL_SIZE,
					Global.ESLL_SIZE, true);

		}
	}

	public Point getHead() {
		return body.getFirst();
	}

	private class SnakeDriver implements Runnable {
		public void run() {
			while (live) {
				move();
				for (SnakeListener listener : listeners) {
					listener.snakeMoved(Snake.this);

				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void start() {
		new Thread(new SnakeDriver()).start();
	}

	public void addsnakeListener(SnakeListener listener) {
		if (listener != null) {
			this.listeners.add(listener);
		}
	}

}
