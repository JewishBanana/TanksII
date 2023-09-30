package com.davidclue.tanksii.objects.tiles;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.objects.Tile;
import com.davidclue.tanksii.objects.TileType;
import com.davidclue.tanksii.rendering.Renderer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class BoxTile extends Tile {

	public BoxTile(Level level, int tileX, int tileY) {
		super(level, tileX, tileY);
		this.type = TileType.BOXTILE;
		this.width = 64;
		this.height = 64;
	}
	public void render(int x, int y, ShapeDrawer drawer) {
		Renderer.renderRectangle(x, y, width, height, Color.RED);
	}
	public boolean isSolid() {
		return true;
	}
}
