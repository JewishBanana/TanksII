package com.davidclue.tanksii.entities;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.input.InputHandler;

public class InputGameObject extends LivingEntity {

	public InputGameObject(Level level, double x, double y, double width, double height, ID id) {
		super(level, x, y, width, height, id);
		InputHandler.mouseListener.add(this);
	}
	public void mousePressed(int button, int mouseX, int mouseY) {
	}
	public void mouseMoved() {
	}
	public void removeMouseListener() {
		InputHandler.mouseListener.remove(this);
	}
}
