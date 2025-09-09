package com.davidclue.tanksii.input;

public interface InputAdapter {

	public void mousePressed(int button, int mouseX, int mouseY);
	
	public void mouseMoved();
	
	public void removeMouseListener();
}
