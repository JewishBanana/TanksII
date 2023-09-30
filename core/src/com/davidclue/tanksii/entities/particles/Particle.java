package com.davidclue.tanksii.entities.particles;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.ID;
import com.davidclue.tanksii.rendering.Renderer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Particle extends Entity {
	
	public int lifeTicks;
	public boolean shouldRemove;
	private Color color;

	public Particle(Level level, double x, double y, double width, double height, int lifeTicks, Color color) {
		super(level, x, y, width, height, ID.PARTICLE);
		this.lifeTicks = lifeTicks;
		this.color = color;
	}
	public Particle(Level level, double x, double y, double width, double height, int lifeTicks) {
		super(level, x, y, width, height, ID.PARTICLE);
		this.lifeTicks = lifeTicks;
		this.color = Color.GREEN;
	}
	@Override
	public void tick() {
	}
	@Override
	public void render(ShapeDrawer drawer) {
		Renderer.renderHollowCircle(x, y, width, color);
	}
}
