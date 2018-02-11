package com.pnfy.listener;

import com.pnfy.Tatris.Shape;

public interface ShapeListener {

	void shapeMoveDown(Shape shape);
	
	boolean isShapeMoveDownable(Shape shape);
}
