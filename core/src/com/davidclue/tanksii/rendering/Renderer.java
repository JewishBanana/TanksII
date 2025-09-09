package com.davidclue.tanksii.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.davidclue.tanksii.Camera;
import com.davidclue.tanksii.map.Tile;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Renderer {
	
	public static ShapeDrawer drawer;
	public static Camera camera;
	public static boolean renderLighting = true;
	
	public static void init(ShapeDrawer drawer) {
		Renderer.drawer = drawer;
	}
	public static void setCamera(Camera camera) {
		Renderer.camera = camera;
	}
	public static void renderRectangle(double x, double y, double width, double height, Color color, double rotation) {
		Rectangle rect = new Rectangle(camera.getScreenX(x), camera.getScreenY(y), (float) (width * camera.getZoom()), (float) (height * camera.getZoom()));
		drawer.filledRectangle(rect, (float) rotation, color, color);
//		Color col = new Color(100, 0, 0, 0.3f);
//		drawer.filledRectangle(rect, (float) rotation, col, col);
	}
	public static void renderRectangle(double x, double y, double width, double height, Color color) {
		Rectangle rect = new Rectangle(camera.getScreenX(x), camera.getScreenY(y), (float) (width * camera.getZoom()), (float) (height * camera.getZoom()));
		drawer.filledRectangle(rect, color);
//		drawer.filledRectangle(rect, new Color(0, 150, 0, 0.3f));
	}
	public static void renderHollowCircle(double x, double y, double size, Color color) {
		drawer.setColor(color);
		drawer.circle(camera.getScreenX(x), camera.getScreenY(y), (float) (size * camera.getZoom()));
	}
	public static void renderLight(Tile tile) {
		if (!renderLighting)
			return;
		byte a = (byte) tile.getAlpha();
		Color col = a == 0 ? Color.BLACK
				: new Color(tile.getRedLighting() * 16, tile.getGreenLighting() * 16, tile.getBlueLighting() * 16, (float) (1.0 - (a * 0.06666667)));
		drawer.filledRectangle(camera.getScreenX(tile.getX()), camera.getScreenY(tile.getY()), (float) tile.getWidth(), (float) tile.getHeight(), col);
	}
}
