package com.davidclue.tanksii.entities.tanks;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.Enemy;
import com.davidclue.tanksii.rendering.Renderer;
import com.davidclue.tanksii.util.AIUtil;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class GreenTank extends Enemy {
	
	private int shotCooldown = 180;

	public GreenTank(Level level, double x, double y) {
		super(level, x, y);
	}
	@Override
	public void tick() {
		if (target == null)
			target = AIUtil.getRandomTarget(level);
		if (shotCooldown > 0)
			shotCooldown--;
		if (shotCooldown == 0 && target != null) {
			shotCooldown = 60;
			if (shootAtTarget(8, 5, 60, 400, 16))
				shotCooldown = 180;
		}
		
		super.tick();
	}
	@Override
	public void render(ShapeDrawer drawer) {
		Renderer.renderRectangle(x, y, width, height, Color.GREEN);
	}
}
