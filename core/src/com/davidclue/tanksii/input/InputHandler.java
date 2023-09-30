package com.davidclue.tanksii.input;

import java.util.ArrayDeque;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.entities.InputGameObject;

public class InputHandler implements InputProcessor {

	private static double mouseX, mouseY;
	
	public static Queue<InputGameObject> mouseListener = new ArrayDeque<>();
	
	public InputHandler(TanksII game) {
		Gdx.input.setInputProcessor(this);
	}
	@Override
	public boolean keyDown(int keycode) {
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		return true;
	}
	@Override
	public boolean keyTyped(char character) {
		return true;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mouseListener.forEach(c -> c.mousePressed(button, screenX, screenY));
		return true;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}
	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return true;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return true;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseX = screenX;
		mouseY = screenY;
		mouseListener.forEach(c -> c.mouseMoved());
		return true;
	}
	@Override
	public boolean scrolled(float amountX, float amountY) {
		return true;
	}
	
	public static double getMouseX() {
		return mouseX;
	}
	public static double getMouseY() {
		return mouseY;
	}
}
