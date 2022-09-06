package objects.tiles;

import java.awt.Color;
import java.awt.Graphics;

import general.Level;
import objects.Tile;
import objects.TileType;

public class BoxTile extends Tile {

	public BoxTile(Level level, int tileX, int tileY) {
		super(level, tileX, tileY);
		this.type = TileType.BOXTILE;
		this.width = 64;
		this.height = 64;
	}
	public void render(int x, int y, Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
	public boolean isSolid() {
		return true;
	}
}
