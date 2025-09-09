package com.davidclue.tanksii.map.tiles;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.map.Tile;
import com.davidclue.tanksii.rendering.Renderer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class FloorTile extends Tile {

	public FloorTile(Level level, int tileX, int tileY) {
		super(level, tileX, tileY);
		this.width = TanksII.tileSize;
		this.height = TanksII.tileSize;
	}
	public void render(double x, double y, ShapeDrawer drawer) {
		Renderer.renderRectangle(x, y, width, height, Color.GRAY);
		Renderer.renderLight(this);
	}
}
