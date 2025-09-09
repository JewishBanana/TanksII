package com.davidclue.tanksii.entities.tanks;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import com.davidclue.tanksii.entities.projectiles.Projectile;

public interface Shooter {
	
	public Queue<Projectile> projectiles = new ArrayDeque<>();
	
	default void removeDeadProjectiles() {
		Iterator<Projectile> it = projectiles.iterator();
		while (it.hasNext())
			if (it.next().isDead())
				it.remove();
	}
	default int aliveProjectiles() {
		return projectiles.size();
	}
}
