package com.davidclue.tanksii;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import javax.imageio.ImageIO;

import com.davidclue.tanksii.entities.Enemy;
import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.EntityType;
import com.davidclue.tanksii.entities.tanks.Player;
import com.davidclue.tanksii.map.Tile;
import com.davidclue.tanksii.map.TileFace;
import com.davidclue.tanksii.map.TileType;
import com.davidclue.tanksii.util.Pair;
import com.davidclue.tanksii.util.scheduler.TaskScheduler;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Level {
	
	public Handler handler;
	public Camera camera;
	public TaskScheduler scheduler;
	public boolean hasFailed;
	public boolean isCompleted;
	public Player client;
	
	private Tile[] tiles;
	private int width, height;
	private Tile voidTile;
	private Map<Pair<Integer, Integer>, EntityType> spawns = new HashMap<>();
	
	private Queue<Entity> entities = new ArrayDeque<>();
	private Queue<Entity> targets = new ArrayDeque<>();
	private Queue<Entity> enemies = new ArrayDeque<>();
	
	public Level() {
		this.handler = new Handler(this);
		this.camera = new Camera(0, 0);
		this.scheduler = new TaskScheduler();
	}
	public void load(InputStream data) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(data);
		voidTile = TileType.VOIDTILE.createNewTile(this, 0, 0);
		width = img.getWidth();
		height = img.getHeight();
		int[] pixels = new int[width * height];
		img.getRGB(0, 0, width, height, pixels, 0, width);
		tiles = new Tile[width * height];
		for (int i=0; i < pixels.length; i++) {
			int tileX = (i%width), tileY = (i/width);
			TileType type = TileType.getTypeByHex(pixels[i]);
			if (type.isEntity()) {
				tiles[i] = TileType.FLOORTILE.createNewTile(this, tileX, tileY);
				Entity entity = type.createNewEntity(this, tileX, tileY);
				if (type == TileType.MAINPLAYER) {
					camera.setTrackingObject(entity);
					targets.add(entity);
					this.client = (Player) entity;
				} else if (entity instanceof Enemy)
					enemies.add(entity);
				spawns.put(Pair.of(tileX, tileY), entity.getType());
			} else
				tiles[i] = type.createNewTile(this, tileX, tileY);
		}
		for (Tile tile : tiles)
			tile.update();
		for (Entity e : entities)
			addLight(e.getTile(), e.getLighting());
		camera.setTileDimensions(img.getTileWidth(), img.getTileHeight());
	}
	public void reset() {
		handler.clear();
		targets.clear();
		enemies.clear();
		entities.clear();
		for (int i=0; i < tiles.length; i++) {
			Tile tile = tiles[i];
			tiles[i] = tile.getType().createNewTile(this, tile.getTileX(), tile.getTileY());
		}
		for (Tile tile : tiles)
			tile.update();
		for (Entry<Pair<Integer, Integer>, EntityType> entry : spawns.entrySet()) {
			Entity entity = entry.getValue().createNewEntity(this, entry.getKey().getFirst() * TanksII.tileSize, entry.getKey().getSecond() * TanksII.tileSize);
			if (entity instanceof Player) {
				camera.setTrackingObject(entity);
				targets.add(entity);
				this.client = (Player) entity;
			} else if (entity instanceof Enemy)
				enemies.add(entity);
		}
		for (Entity e : entities)
			addLight(e.getTile(), e.getLighting());
	}
	public void tick() {
		handler.tick();
		camera.tick();
		scheduler.tick();
		
		Iterator<Entity> iterator = targets.iterator();
		while (iterator.hasNext())
			if (iterator.next().isDead())
				iterator.remove();
		if (targets.isEmpty())
			hasFailed = true;
		iterator = enemies.iterator();
		while (iterator.hasNext())
			if (iterator.next().isDead())
				iterator.remove();
		if (enemies.isEmpty())
			isCompleted = true;
	}
	public void render(ShapeDrawer drawer) {
		for (int x = (int) (camera.getX()/TanksII.tileSize); x <= (int) (camera.getFarX()/TanksII.tileSize); x++)
			for (int y = (int) (camera.getY()/TanksII.tileSize); y <= (int) (camera.getFarY()/TanksII.tileSize); y++)
				getTileChunk(x, y).render((int) (x * TanksII.tileSize), (int) (y * TanksII.tileSize), drawer);
		handler.render(drawer);
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
		int tile = (((int) (y / TanksII.tileSize)) * height) + ((int) (x / TanksII.tileSize));
		if (tile >= tiles.length || tile < 0)
			return voidTile;
		return tiles[tile];
	}
	public void setTile(int tileX, int tileY, TileType type) {
		if (getTileChunk(tileX, tileY).equals(voidTile))
			return;
		Tile tile = type.createNewTile(this, tileX, tileY);
		tiles[(tileY * height) + tileX] = tile;
		tile.update();
	}
	public void addLight(Tile tile, int light) {
		Set<Tile> touched = new HashSet<>();
		addLight(tile, light, touched);
	}
	private void addLight(Tile initial, int light, Set<Tile> touched) {
		Map<Tile, Byte> lineup = new LinkedHashMap<>();
		int colorLevels = light & 4095;
		lineup.put(initial, (byte) (light >> 12));
		touched.add(initial);
		initial.getRelatives(false).forEach(t -> lineup.put(t, (byte) ((light >> 12) - 1)));
		while (!lineup.isEmpty()) {
			Map<Tile, Byte> newTiles = new LinkedHashMap<>();
			for (Entry<Tile, Byte> entry : lineup.entrySet()) {
				Tile tile = entry.getKey();
				tile.addLight((entry.getValue() << 12) + colorLevels);
//				handler.addParticle(new Particle(this, tile.getX()+32, tile.getY()+32, 10, 10, 60, entry.getValue() == 12 ? Color.GREEN : entry.getValue() == 11 ? Color.YELLOW : entry.getValue() == 10 ? Color.ORANGE : Color.RED));
				if (!tile.isSolid() && entry.getValue() != 1)
					for (TileFace face : TileFace.adjacentFaces) {
						Tile adjacent = tile.getRelative(face);
						if (touched.contains(adjacent))
							continue;
						touched.add(adjacent);
						newTiles.put(adjacent, (byte) (entry.getValue() - 1));
					}
			}
			lineup.clear();
			lineup.putAll(newTiles);
		}
	}
	public void removeLight(Tile tile, int light) {
		Set<Tile> touched = new HashSet<>();
		removeLight(tile, light, touched);
	}
	private void removeLight(Tile initial, int light, Set<Tile> touched) {
		Map<Tile, Byte> lineup = new LinkedHashMap<>();
		int colorLevels = light & 4095;
		lineup.put(initial, (byte) (light >> 12));
		touched.add(initial);
		initial.getRelatives(false).forEach(t -> lineup.put(t, (byte) ((light >> 12) - 1)));
		while (!lineup.isEmpty()) {
			Map<Tile, Byte> newTiles = new LinkedHashMap<>();
			for (Entry<Tile, Byte> entry : lineup.entrySet()) {
				Tile tile = entry.getKey();
				tile.removeLight((entry.getValue() << 12) + colorLevels);
				if (!tile.isSolid() && entry.getValue() != 1)
					for (TileFace face : TileFace.adjacentFaces) {
						Tile adjacent = tile.getRelative(face);
						if (touched.contains(adjacent))
							continue;
						touched.add(adjacent);
						newTiles.put(adjacent, (byte) (entry.getValue() - 1));
					}
			}
			lineup.clear();
			lineup.putAll(newTiles);
		}
	}
	public Queue<Entity> getTargets() {
		return targets;
	}
	public void setTargets(Queue<Entity> targets) {
		this.targets = targets;
	}
	public Queue<Entity> getEnemies() {
		return enemies;
	}
	public void setEnemies(Queue<Entity> enemies) {
		this.enemies = enemies;
	}
	public Tile getVoidTile() {
		return voidTile;
	}
	public Queue<Entity> getEntities() {
		return entities;
	}
}
