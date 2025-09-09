package com.davidclue.tanksii.entities;

import java.util.ArrayDeque;
import java.util.Queue;

import com.davidclue.tanksii.map.Tile;
import com.davidclue.tanksii.util.AIUtil;
import com.davidclue.tanksii.util.Utils;

public class Controller {
	
	private Enemy entity;
	private Queue<Tile> path = new ArrayDeque<>();
	private int tickOnMove;
	
	public Controller(Enemy entity) {
		this.entity = entity;
	}
	public void tick() {
		if (path != null && !path.isEmpty()) {
			Tile to = path.peek();
			if (tickOnMove++ > 20.0*entity.movementSpeed || entity.getTile().equals(to)) {
				path.poll();
				to = path.peek();
				tickOnMove = 0;
				if (to == null)
					return;
			}
			double tileX = to.getX(), tileY = to.getY();
//			entity.level.handler.addParticle(new Particle(entity.level, tileX+32, tileY+32, 10, 10, 1));
			entity.velocity.setX(Math.min(entity.velocity.getX()+entity.acceleration * (entity.x < tileX ? 1.0 : -1.0), tileX-entity.x));
			entity.velocity.setY(Math.min(entity.velocity.getY()+entity.acceleration * (entity.y < tileY ? 1.0 : -1.0), tileY-entity.y));
			
			entity.velocity.setX(Utils.clamp(entity.velocity.getX(), -entity.movementSpeed, entity.movementSpeed));
			entity.velocity.setY(Utils.clamp(entity.velocity.getY(), -entity.movementSpeed, entity.movementSpeed));
		} else
			entity.velocity.set(0, 0);
	}
	public void moveToTile(Tile tile) {
		path = AIUtil.pathFind(entity.level.getTile(entity.x, entity.y), tile);
	}
}
