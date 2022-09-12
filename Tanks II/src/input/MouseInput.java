package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Queue;

import entities.InputGameObject;
import general.Game;

public class MouseInput extends MouseAdapter {
	
	private Game game;
	
	private static double mouseX, mouseY;
	
	public static Queue<InputGameObject> mouseListener = new ArrayDeque<>();
	
	public MouseInput(Game game) {
		this.game = game;
	}
	public void mousePressed(MouseEvent e) {
		mouseListener.forEach(c -> c.mousePressed(e));
	}
	public void mouseMoved(MouseEvent e) {
		mouseX = (game.fullWidth / Game.WIDTH) * e.getX();
		mouseY = (game.fullHeight / Game.HEIGHT) * e.getY();
		mouseListener.forEach(c -> c.mouseMoved(e));
	}
	public static double getMouseX() {
		return mouseX;
	}
	public static void setMouseX(double mouseX) {
		MouseInput.mouseX = mouseX;
	}
	public static double getMouseY() {
		return mouseY;
	}
	public static void setMouseY(double mouseY) {
		MouseInput.mouseY = mouseY;
	}
}
