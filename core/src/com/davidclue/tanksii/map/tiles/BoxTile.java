package com.davidclue.tanksii.map.tiles;

import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.map.Tile;
import com.davidclue.tanksii.map.TileFace;
import com.davidclue.tanksii.rendering.Renderer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class BoxTile extends Tile {
	
	private Runnable sprite;

	public BoxTile(Level level, int tileX, int tileY) {
		super(level, tileX, tileY);
		this.width = TanksII.tileSize;
		this.height = TanksII.tileSize;
	}
	public void render(double x, double y, ShapeDrawer drawer) {
		sprite.run();
		Renderer.renderLight(this);
	}
	public void update() {
		boolean[] relative = new boolean[] {
				getRelative(TileFace.LEFT).getType() == type,
				getRelative(TileFace.DOWN).getType() == type,
				getRelative(TileFace.RIGHT).getType() == type,
				getRelative(TileFace.UP).getType() == type};
		int count = 0;
		for (boolean bool : relative)
			if (bool)
				count++;
		switch (count) {
		default:
		case 0:
			sprite = new Runnable() {
				@Override
				public void run() {
					Renderer.renderRectangle(x, y, width, height, Color.BLACK);
					Renderer.renderRectangle(x+1, y+1, width-2, height-2, Color.RED);
				}
			};
			break;
		case 1:
			for (int i=0; i < 4; i++)
				if (relative[i]) {
					final int angle = i;
					sprite = new Runnable() {
						@Override
						public void run() {
							Renderer.renderRectangle(x, y, width, height, Color.BLACK);
							switch (angle) {
							case 0:
								Renderer.renderRectangle(x, y+1, width-1, height-2, Color.RED);
								break;
							case 1:
								Renderer.renderRectangle(x+1, y+1, width-2, height, Color.RED);
								break;
							case 2:
								Renderer.renderRectangle(x+1, y+1, width, height-2, Color.RED);
								break;
							case 3:
								Renderer.renderRectangle(x+1, y, width-2, height-1, Color.RED);
								break;
							}
						}
					};
					break;
				}
			break;
		case 2:
			for (int i=0; i < 4; i++)
				if (relative[i]) {
					final int angle = i;
					for (int j=i+1; j < 4; j++)
						if (relative[j]) {
							final int angle2 = j;
							sprite = new Runnable() {
								@Override
								public void run() {
									Renderer.renderRectangle(x, y, width, height, Color.BLACK);
									switch (angle) {
									case 0:
										Renderer.renderRectangle(x, y+1, width-1, height-2, Color.RED);
										break;
									case 1:
										Renderer.renderRectangle(x+1, y+1, width-2, height, Color.RED);
										break;
									case 2:
										Renderer.renderRectangle(x+1, y+1, width, height-2, Color.RED);
										break;
									case 3:
										Renderer.renderRectangle(x+1, y, width-2, height-1, Color.RED);
										break;
									}
									switch (angle2) {
									case 0:
										Renderer.renderRectangle(x, y+1, width-1, height-2, Color.RED);
										break;
									case 1:
										Renderer.renderRectangle(x+1, y+1, width-2, height, Color.RED);
										break;
									case 2:
										Renderer.renderRectangle(x+1, y+1, width, height-2, Color.RED);
										break;
									case 3:
										Renderer.renderRectangle(x+1, y, width-2, height-1, Color.RED);
										break;
									}
								}
							};
							break;
						}
				}
			break;
		case 3:
			for (int i=0; i < 4; i++)
				if (relative[i]) {
					final int angle = i;
					for (int j=i+1; j < 4; j++)
						if (relative[j]) {
							final int angle2 = j;
							for (int k=j+1; k < 4; k++)
								if (relative[k]) {
									final int angle3 = k;
									sprite = new Runnable() {
										@Override
										public void run() {
											Renderer.renderRectangle(x, y, width, height, Color.BLACK);
											switch (angle) {
											case 0:
												Renderer.renderRectangle(x, y+1, width-1, height-2, Color.RED);
												break;
											case 1:
												Renderer.renderRectangle(x+1, y+1, width-2, height, Color.RED);
												break;
											case 2:
												Renderer.renderRectangle(x+1, y+1, width, height-2, Color.RED);
												break;
											case 3:
												Renderer.renderRectangle(x+1, y, width-2, height-1, Color.RED);
												break;
											}
											switch (angle2) {
											case 0:
												Renderer.renderRectangle(x, y+1, width-1, height-2, Color.RED);
												break;
											case 1:
												Renderer.renderRectangle(x+1, y+1, width-2, height, Color.RED);
												break;
											case 2:
												Renderer.renderRectangle(x+1, y+1, width, height-2, Color.RED);
												break;
											case 3:
												Renderer.renderRectangle(x+1, y, width-2, height-1, Color.RED);
												break;
											}
											switch (angle3) {
											case 0:
												Renderer.renderRectangle(x, y+1, width-1, height-2, Color.RED);
												break;
											case 1:
												Renderer.renderRectangle(x+1, y+1, width-2, height, Color.RED);
												break;
											case 2:
												Renderer.renderRectangle(x+1, y+1, width, height-2, Color.RED);
												break;
											case 3:
												Renderer.renderRectangle(x+1, y, width-2, height-1, Color.RED);
												break;
											}
										}
									};
									break;
								}
						}
				}
			break;
		case 4:
			sprite = new Runnable() {
				@Override
				public void run() {
					Renderer.renderRectangle(x, y, width, height, Color.BLACK);
					Renderer.renderRectangle(x+1, y, width-2, height, Color.RED);
					Renderer.renderRectangle(x, y+1, width, height-2, Color.RED);
				}
			};
			break;
		}
	}
	public boolean isSolid() {
		return true;
	}
}
