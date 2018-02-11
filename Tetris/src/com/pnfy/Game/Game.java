package com.pnfy.Game;

import javax.swing.JFrame;

import com.pnfy.Panel.GamePanel;
import com.pnfy.Tatris.Ground;
import com.pnfy.Tatris.ShapeFactory;
import com.pnfy.controllar.Controller;

public class Game {

	public static void main(String[] args) {
		ShapeFactory shapeFactory = new ShapeFactory();
		Ground ground =new Ground();
		GamePanel gamePanel = new GamePanel();
		Controller controllar = new Controller(shapeFactory, ground, gamePanel);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(gamePanel.getSize().width + 15, gamePanel.getSize().height + 40);
		frame.add(gamePanel);
		gamePanel.addKeyListener(controllar);
		frame.addKeyListener(controllar);
		frame.setVisible(true);
		controllar.newGame();
	}

}
