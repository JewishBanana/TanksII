package entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import entities.GameObject;
import entities.ID;
import entities.LivingEntity;
import general.Game;
import general.Level;
import objects.TileFace;

public class Bullet extends GameObject {
	
	private GameObject shooter;
	private int bounces,immuneFrames = 60;

	public Bullet(Level level, double x, double y, double width, double height, ID id) {
		super(level, x, y, width, height, id);
	}
	public Bullet(Level level, double x, double y, double width, double height, ID id, GameObject shooter) {
		super(level, x, y, width, height, id);
		this.shooter = shooter;
	}
	@Override
	public void tick(Level level) {
		
		TileFace face = simulateMapCollsions(false);
		if (face == TileFace.RIGHT || face == TileFace.LEFT) {
			velX *= -1;
			bounces++;
		} else if (face != null) {
			velY *= -1;
			bounces++;
		}
		if (bounces >= 3)
			shouldRemove = true;
		x += velX;
		y += velY;
		
		for (GameObject obj : Game.handler.getObjectList())
			if (obj instanceof LivingEntity && obj.collides(x, y, width, height) && !obj.equals(shooter)) {
//				((LivingEntity) obj).explodeEntity();
				shouldRemove = true;
				return;
			}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}
}
