package com.davidclue.tanksii;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.ID;
import com.davidclue.tanksii.entities.particles.Particle;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Handler {
	
	private Queue<Entity> objectList = new ArrayDeque<>();
	private Queue<Particle> particlesList = new ArrayDeque<>();
	private Level level;
	
	private Queue<Entity> objectAddQueueList = new ArrayDeque<>();
	private Queue<Entity> objectRemoveQueueList = new ArrayDeque<>();
	private Queue<Particle> particleAddQueueList = new ArrayDeque<>();
	private Queue<Particle> particleRemoveQueueList = new ArrayDeque<>();
	
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
			if (obj.getX()+obj.getWidth() >= Camera.getX() && obj.getX() <= Camera.getFarX() && obj.getY()+obj.getHeight() >= Camera.getY() && obj.getY() <= Camera.getFarY() && !obj.isDead()) {
				if (obj.getId() == ID.BULLET)
					obj.render(drawer);
				else
					higherPriority.add(obj);
			}
		higherPriority.forEach(e -> e.render(drawer));
		for (Particle particle : particlesList)
			if (particle.getX()+particle.getWidth() >= Camera.getX() && particle.getX() <= Camera.getFarX() && particle.getY()+particle.getHeight() >= Camera.getY() && particle.getY() <= Camera.getFarY())
				particle.render(drawer);
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
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public Queue<Entity> getObjectList() {
		return objectList;
	}
}
