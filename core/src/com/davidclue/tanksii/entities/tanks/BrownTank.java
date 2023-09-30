package com.davidclue.tanksii.entities.tanks;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.entities.Enemy;
import com.davidclue.tanksii.entities.ID;
import com.davidclue.tanksii.entities.projectiles.Projectile;
import com.davidclue.tanksii.entities.projectiles.RayCast;
import com.davidclue.tanksii.rendering.Renderer;
import com.davidclue.tanksii.util.AIUtil;
import com.davidclue.tanksii.util.Utils;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class BrownTank extends Enemy {
	
	private int shotCooldown = 180;
	private Random rand = new Random();

	public BrownTank(Level level, double x, double y, double width, double height, ID id) {
		super(level, x, y, width, height, id);
	}
	@Override
	public void tick() {
		super.tick();
		
		if (target == null)
			target = AIUtil.getRandomTarget(level);
		if (shotCooldown <= 0 && target != null) {
			shotCooldown = 60;
			RayCast cast = rayCastForTarget(8, 1, 60, 200, 8);
			if (cast != null) {
				shotCooldown = 180;
				Projectile bullet = new Projectile(level, cast.getPosX(), cast.getPosY(), 8, 8, ID.BULLET, this);
				bullet.setVelocity(cast.getVelocity().normalize().multiply(8));
				bullet.setBounces(1);
				TanksII.handler.addObject(bullet);
			}
		}
		shotCooldown--;
		
		velocity.setX(velocity.getX()+(rand.nextDouble()*2-1));
		velocity.setY(velocity.getY()+(rand.nextDouble()*2-1));
		
		velocity.setX(Utils.clamp(velocity.getX(), -5, 5));
		velocity.setY(Utils.clamp(velocity.getY(), -5, 5));
	}
	@Override
	public void render(ShapeDrawer drawer) {
		Renderer.renderRectangle(x, y, width, height, Color.BROWN);
	}
}
