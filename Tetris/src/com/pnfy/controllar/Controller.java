package com.pnfy.controllar;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.pnfy.Panel.GamePanel;
import com.pnfy.Tatris.Ground;
import com.pnfy.Tatris.Shape;
import com.pnfy.Tatris.ShapeFactory;
import com.pnfy.listener.ShapeListener;

public class Controller extends KeyAdapter implements ShapeListener {

	private Shape shape;
	private ShapeFactory shapeFactory;
	private Ground ground;
	private GamePanel gamePanel;

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if (ground.isMoveable(shape, Shape.ROTSTE))
				shape.rotate();
			break;
		case KeyEvent.VK_DOWN:
			if (isShapeMoveDownable(shape))
				shape.moveDown();
			;
			break;
		case KeyEvent.VK_LEFT:
			if (ground.isMoveable(shape, Shape.LEFT))
				shape.moveLeft();
			;
			break;
		case KeyEvent.VK_RIGHT:
			if (ground.isMoveable(shape, Shape.RIGHT))
				shape.moveRight();
			;
			break;

		}

		gamePanel.display(ground, shape);
	}

	@Override
	public synchronized boolean isShapeMoveDownable(Shape shape) {
		if (this.shape != shape) {
			return false;
		}
		if (ground.isMoveable(shape, Shape.DOWN)) {
			return true;
		}
		ground.accept(this.shape);
		if (!ground.isFull()) {
			this.shape = shapeFactory.getShape(this);
		}
		return false;
	}

	@Override
	public void shapeMoveDown(Shape shape) {
		gamePanel.display(ground, shape);
	}

	public void newGame() {
		shape = shapeFactory.getShape(this);
	}

	public Controller(ShapeFactory shapeFactory, Ground ground,
			GamePanel gamePanel) {

		this.shapeFactory = shapeFactory;
		this.ground = ground;
		this.gamePanel = gamePanel;
	}

}
