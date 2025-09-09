package com.davidclue.tanksii;

import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Scene {
	
	public abstract void tick();
	public abstract void render(ShapeDrawer drawer);
}
