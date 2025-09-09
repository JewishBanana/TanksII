package com.davidclue.tanksii.entities.tanks;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.Enemy;
import com.davidclue.tanksii.rendering.Renderer;
import com.davidclue.tanksii.util.AIUtil;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class GrayTank extends Enemy {
	
	private int shotCooldown = 180, pathFindTicks;

	public GrayTank(Level level, double x, double y) {
		super(level, x, y);
		this.movementSpeed = 3.0;
	}
	@Override
	public void tick() {
		if (target == null)
			target = AIUtil.getRandomTarget(level);
		if (shotCooldown > 0)
			shotCooldown--;
		if (pathFindTicks > 0)
			pathFindTicks--;
		if (shotCooldown == 0 && target != null) {
			shotCooldown = 60;
			if (shootAtTarget(8, 1, 60, 200, 8))
				shotCooldown = 180;
		}
		if (pathFindTicks == 0) {
			pathFindTicks = 40;
			controller.moveToTile(target.getTile());
		}
		
		super.tick();
	}
	@Override
	public void render(ShapeDrawer drawer) {
		Renderer.renderRectangle(x, y, width, height, Color.CYAN);
	}
}
