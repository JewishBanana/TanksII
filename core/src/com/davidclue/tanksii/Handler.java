package com.davidclue.tanksii;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.EntityType;
import com.davidclue.tanksii.entities.particles.Particle;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Handler {
	
	private Set<Entity> objectList = new HashSet<>();
	private Set<Particle> particlesList = new HashSet<>();
	private Level level;
	
	private Queue<Entity> objectAddQueueList = new ArrayDeque<>();
	private Queue<Entity> objectRemoveQueueList = new ArrayDeque<>();
	private Queue<Particle> particleAddQueueList = new ArrayDeque<>();
	private Queue<Particle> particleRemoveQueueList = new ArrayDeque<>();
	
	public Handler(Level level) {
		this.level = level;
	}
	public void tick() {
		Iterator<Entity> handlerIterator = objectList.iterator();
		while (handlerIterator.hasNext()) {
			Entity obj = handlerIterator.next();
			if (obj == null || obj.isDead())
				handlerIterator.remove();
			else
				obj.tick();
		}
		objectList.removeAll(objectRemoveQueueList);
		objectRemoveQueueList.clear();
		objectList.addAll(objectAddQueueList);
		objectAddQueueList.clear();
		
		Iterator<Particle> particleIterator = particlesList.iterator();
		while (particleIterator.hasNext()) {
			Particle particle = particleIterator.next();
			if (particle == null || particle.lifeTicks <= 0)
				particleIterator.remove();
			else
				particle.lifeTicks--;
		}
		particlesList.removeAll(particleRemoveQueueList);
		particleRemoveQueueList.clear();
		particlesList.addAll(particleAddQueueList);
		particleAddQueueList.clear();
	}
	public void render(ShapeDrawer drawer) {
		Queue<Entity> higherPriority = new ArrayDeque<>(objectList.size());
		for (Entity obj : objectList)
			if (obj.getX()+obj.getWidth() >= level.camera.getX() && obj.getX() <= level.camera.getFarX() && obj.getY()+obj.getHeight() >= level.camera.getY() && obj.getY() <= level.camera.getFarY() && !obj.isDead()) {
				if (obj.getType() == EntityType.PROJECTILE)
					obj.render(drawer);
				else
					higherPriority.add(obj);
			}
		higherPriority.forEach(e -> e.render(drawer));
		for (Particle particle : particlesList)
			if (particle.getX()+particle.getWidth() >= level.camera.getX() && particle.getX() <= level.camera.getFarX() && particle.getY()+particle.getHeight() >= level.camera.getY() && particle.getY() <= level.camera.getFarY())
				particle.render(drawer);
	}
	public void clear() {
		for (Entity e : objectList)
			e.remove();
		for (Particle p : particlesList)
			p.remove();
		objectList.clear();
		particlesList.clear();
	}
	public void addObject(Entity tempObject) {
		objectAddQueueList.add(tempObject);
	}
	public void removeObject(Entity tempObject) {
		objectRemoveQueueList.remove(tempObject);
	}
	public void addParticle(Particle particle) {
		particleAddQueueList.add(particle);
	}
	public void removeParticle(Particle particle) {
		particleRemoveQueueList.remove(particle);
	}
	public Set<Entity> getObjectList() {
		return objectList;
	}
}
