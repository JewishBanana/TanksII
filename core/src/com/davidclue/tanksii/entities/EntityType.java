package com.davidclue.tanksii.entities;

import java.lang.reflect.InvocationTargetException;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.particles.Particle;
import com.davidclue.tanksii.entities.projectiles.Projectile;
import com.davidclue.tanksii.entities.tanks.BrownTank;
import com.davidclue.tanksii.entities.tanks.GrayTank;
import com.davidclue.tanksii.entities.tanks.GreenTank;
import com.davidclue.tanksii.entities.tanks.Player;

public enum EntityType {
	
	PLAYER(Player.class),
	BROWNTANK(BrownTank.class),
	GRAYTANK(GrayTank.class),
	GREENTANK(GreenTank.class),
	PROJECTILE(Projectile.class),
	
	PARTICLE(Particle.class);
	
	private Class<? extends Entity> entityClass;
	
	private EntityType(Class<? extends Entity> entityClass) {
		this.entityClass = entityClass;
	}
	public Entity createNewEntity(Level level, double x, double y) {
		if (entityClass == null)
			throw new NullPointerException("EntityType "+this.toString()+" has no defined entity class!");
		try {
			Entity entity = entityClass.getDeclaredConstructor(Level.class, double.class, double.class).newInstance(level, x, y);
			level.handler.addObject(entity);
			entity.type = this;
			return entity;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
