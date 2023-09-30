package com.davidclue.tanksii.rendering;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Camera;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Renderer {
	
	public static ShapeDrawer drawer;
	
	public static void renderRectangle(double x, double y, double width, double height, Color color, float rotation) {
		drawer.setColor(color);
		drawer.filledRectangle(Camera.getFixedX(x), Camera.getFixedY(y), (float)width, (float)height, rotation);
	}
	public static void renderRectangle(double x, double y, double width, double height, Color color) {
		drawer.filledRectangle(Camera.getFixedX(x), Camera.getFixedY(y), (float)width, (float)height, color);
	}
	public static void renderHollowCircle(double x, double y, double size, Color color) {
		drawer.setColor(color);
		drawer.circle(Camera.getFixedX(x), Camera.getFixedY(y), (float)size);
	}
}
