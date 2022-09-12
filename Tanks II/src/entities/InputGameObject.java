package entities;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import general.Level;
import input.MouseInput;

public class InputGameObject extends LivingEntity {

	public InputGameObject(Level level, double x, double y, double width, double height, ID id) {
		super(level, x, y, width, height, id);
		MouseInput.mouseListener.add(this);
	}
	@Override
	public void tick() {
	}
	@Override
	public void render(Graphics2D g) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseMoved(MouseEvent e) {
	}
	public void removeMouseListener() {
		MouseInput.mouseListener.remove(this);
	}
}
