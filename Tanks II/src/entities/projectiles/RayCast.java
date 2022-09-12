package entities.projectiles;

import java.awt.Graphics2D;

import entities.GameObject;
import entities.ID;
import entities.LivingEntity;
import general.Game;
import general.Level;
import objects.TileFace;
import util.Velocity;

public class RayCast extends GameObject {
	
	private GameObject shooter;
	private int bounces;
	private double posX,posY;

	public RayCast(Level level, double x, double y, double width, double height, GameObject shooter) {
		super(level, x, y, width, height, ID.RAYCAST);
		this.posX = x;
		this.posY = y;
		this.shooter = shooter;
	}
	@Override
	public void tick() {
	}
	@Override
	public void render(Graphics2D g) {
	}
	public boolean simulateCast(GameObject target, Velocity castVelocity, int maxBounces, int immuneFrames, int lifeSpan) {
		this.velocity = castVelocity.clone();
		for (int i=0; i < lifeSpan; i++) {
			TileFace face = simulateMapCollsions(false);
//			if (face == null)
//				Game.handler.addParticle(new Particle(level, x, y, width, height, 250, Color.green));
//			else
//				Game.handler.addParticle(new Particle(level, x, y, width, height, 250, Color.blue));
			if (face == TileFace.RIGHT || face == TileFace.LEFT) {
				castVelocity.reverseXVel();
				bounces++;
				if (bounces > maxBounces)
					return false;
			} else if (face != null) {
				castVelocity.reverseYVel();
				bounces++;
				if (bounces > maxBounces)
					return false;
			}
			x += castVelocity.getX();
			y += castVelocity.getY();
			for (GameObject obj : Game.handler.getObjectList())
				if (obj instanceof LivingEntity && obj.collides(x, y, width, height)) {
					if (obj.equals(shooter) && immuneFrames > 0)
						break;
					return obj.equals(target);
				}
			immuneFrames--;
		}
		return false;
	}
	public int getBounces() {
		return bounces;
	}
	public void setBounces(int bounces) {
		this.bounces = bounces;
	}
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
}
