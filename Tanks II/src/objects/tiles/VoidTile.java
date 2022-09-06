package objects.tiles;

import java.awt.Color;
import java.awt.Graphics;

import general.Level;
import objects.Tile;
import objects.TileType;

public class VoidTile extends Tile {

	public int width = 64,height = 64;
	
	public VoidTile(Level level, int tileX, int tileY) {
		super(level, tileX, tileY);
		this.type = TileType.VOIDTILE;
		this.width = 64;
		this.height = 64;
	}
	public void render(int x, int y, Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
	}
}
