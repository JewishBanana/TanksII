package general;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.imageio.ImageIO;

import entities.GameObject;
import entities.ID;
import entities.tanks.BrownTank;
import entities.tanks.Player;
import objects.Tile;
import objects.TileType;
import objects.tiles.BoxTile;
import objects.tiles.FloorTile;
import objects.tiles.VoidTile;

public class Level {
	
	private Game main;
	private Handler handler;
	private Tile[] tiles;
	private int width,height;
	private Tile voidTile;
	
	private Queue<GameObject> targets = new ArrayDeque<>();
	
	public Level(Game main, Handler handler) {
		this.main = main;
		this.handler = handler;
	}
	public void load(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("File "+path+" does not exist!");
			return;
		}
		voidTile = new VoidTile(this, 0, 0);
		width = img.getWidth();
		height = img.getHeight();
		int[] pixels = new int[width * height];
		img.getRGB(0, 0, width, height, pixels, 0, width);
		tiles = new Tile[width * height];
		for (int i=0; i < pixels.length; i++) {
			int x = (i%width)*Game.tileSize, y = (i/width)*Game.tileSize;
			switch (TileType.getTypeByHex(pixels[i])) {
			case MAINPLAYER:
				tiles[i] = new FloorTile(this, x, y);
				Player player = new Player(this, x, y, Game.tileSize, Game.tileSize, ID.PLAYER);
				handler.addObject(player);
				player.setController(main.input);
				Camera.setTrackingObject(player);
				targets.add(player);
				break;
			case BOXTILE:
				tiles[i] = new BoxTile(this, x, y);
				break;
			case FLOORTILE:
				tiles[i] = new FloorTile(this, x, y);
				break;
			case BASICENEMY:
				tiles[i] = new FloorTile(this, x, y);
				handler.addObject(new BrownTank(this, x, y, Game.tileSize, Game.tileSize, ID.BROWNTANK));
				break;
			default:
				tiles[i] = new VoidTile(this, x, y);
				break;
			}
		}
		
		Camera.setMaxX((img.getTileWidth() * Game.tileSize) - Game.WIDTH + 12 < 0 ? 0 : (img.getTileWidth() * Game.tileSize) - Game.WIDTH + 12);
		Camera.setMaxY((img.getTileHeight() * Game.tileSize) - Game.HEIGHT + 35 < 0 ? 0 : (img.getTileHeight() * Game.tileSize) - Game.HEIGHT + 35);
	}
	public void render(Graphics2D g) {
		for (int x = (int) (Camera.getX()/Game.tileSize); x <= Camera.getX()+(Game.WIDTH/Game.tileSize); x++)
			for (int y = (int) (Camera.getY()/Game.tileSize); y <= Camera.getY()+(Game.HEIGHT/Game.tileSize); y++)
				getTileChunk(x, y).render(x*Game.tileSize, y*Game.tileSize, g);
	}
	public void clear() {

	}
	public Tile getTileChunk(int tileX, int tileY) {
		if (tileX >= width || tileY >= height)
			return voidTile;
		int tile = (tileY * height) + tileX;
		if (tile >= tiles.length || tile < 0)
			return voidTile;
		return tiles[tile];
	}
	public Tile getTile(double x, double y) {
		int tile = (((int) y/Game.tileSize) * height) + ((int) x/Game.tileSize);
		if (tile >= tiles.length || tile < 0)
			return voidTile;
		return tiles[tile];
	}
	public Queue<GameObject> getTargets() {
		return targets;
	}
	public void setTargets(Queue<GameObject> targets) {
		this.targets = targets;
	}
}
