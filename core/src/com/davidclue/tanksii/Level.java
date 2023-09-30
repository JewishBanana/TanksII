package com.davidclue.tanksii;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.imageio.ImageIO;

import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.ID;
import com.davidclue.tanksii.entities.tanks.BrownTank;
import com.davidclue.tanksii.entities.tanks.Player;
import com.davidclue.tanksii.objects.Tile;
import com.davidclue.tanksii.objects.TileType;
import com.davidclue.tanksii.objects.tiles.BoxTile;
import com.davidclue.tanksii.objects.tiles.FloorTile;
import com.davidclue.tanksii.objects.tiles.VoidTile;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Level {
	
	private TanksII main;
	private Handler handler;
	private Tile[] tiles;
	private int width,height;
	private Tile voidTile;
	
	private Queue<Entity> targets = new ArrayDeque<>();
	
	public Level(TanksII main, Handler handler) {
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
			int x = (i%width)*TanksII.tileSize, y = (i/width)*TanksII.tileSize;
			switch (TileType.getTypeByHex(pixels[i])) {
			case MAINPLAYER:
				tiles[i] = new FloorTile(this, x, y);
				Player player = new Player(this, x, y, TanksII.tileSize, TanksII.tileSize, ID.PLAYER);
				handler.addObject(player);
//				Camera.setTrackingObject(player);
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
				BrownTank brownTank = new BrownTank(this, x, y, TanksII.tileSize, TanksII.tileSize, ID.BROWNTANK);
				handler.addObject(brownTank);
				Camera.setTrackingObject(brownTank);
				break;
			default:
				tiles[i] = new VoidTile(this, x, y);
				break;
			}
		}
		
		Camera.setMaxX((img.getTileWidth() * TanksII.tileSize) - TanksII.WIDTH + 12 < 0 ? 0 : (img.getTileWidth() * TanksII.tileSize) - TanksII.WIDTH + 12);
		Camera.setMaxY(((img.getTileHeight()+1) * TanksII.tileSize) - TanksII.HEIGHT + 35 < 0 ? 0 : ((img.getTileHeight()+1) * TanksII.tileSize) - TanksII.HEIGHT + 35);
	}
	public void render(ShapeDrawer drawer) {
		for (int x = (int) (Camera.getX()/TanksII.tileSize); x <= (int) ((Camera.getX()+TanksII.WIDTH)/TanksII.tileSize); x++)
			for (int y = (int) (Camera.getY()/TanksII.tileSize); y <= (int) ((Camera.getY()+TanksII.HEIGHT)/TanksII.tileSize)-1; y++)
				getTileChunk(x, y).render(x*TanksII.tileSize, y*TanksII.tileSize, drawer);
	}
	public void clear() {
	}
	public Tile getTileChunk(int tileX, int tileY) {
		if (tileX >= width || tileY >= height || tileX < 0 || tileY < 0)
			return voidTile;
		int tile = (tileY * height) + tileX;
		if (tile >= tiles.length || tile < 0)
			return voidTile;
		return tiles[tile];
	}
	public Tile getTile(double x, double y) {
		int tile = (((int) y/TanksII.tileSize) * height) + ((int) x/TanksII.tileSize);
		if (tile >= tiles.length || tile < 0)
			return voidTile;
		return tiles[tile];
	}
	public Queue<Entity> getTargets() {
		return targets;
	}
	public void setTargets(Queue<Entity> targets) {
		this.targets = targets;
	}
}
